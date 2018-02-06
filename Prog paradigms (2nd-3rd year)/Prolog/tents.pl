% You will find the inventory of an outfitter on the following page.

% Tents
% Dreamer,    sleeps 2, 4.5 kg, $ 199.
% Hermite,    sleeps 1, 2 kg,   $ 159.
% Family,     sleeps 5, 5 kg,   $ 349.
% Expedition, sleeps 8, 8.5 kg, $ 699.
%
% Sleeping bags
% Mountain, -15C, mummy,                1.5kg, $ 350.
% Cabin,      5C, double-bag (2 person),  5kg, $ 250.
% Square,    -3C, rectangular,          2.5kg, $ 150.
%
% Sleeping pads
% Rock,    2cm, 0.2kg, $ 100.
% Pfft,    3cm, 0.8kg, $ 10.
% Heaven, 10cm, 0.4kg, $ 80.
% Moon,    5cm, 0.4kg, $ 50.
%
% Backpacks
% Trapper,    20kg, $ 250.
% Prospector, 25kg, $ 220.
% Air,        10kg, $ 150.
% Comfort,    15kg, $ 200.

%********************
% a) Create a Prolog database representing the items that the outfitter sells.

% tents
tent(dreamer).
tent(hermite).
tent(family).
tent(expedition).

sleeps(dreamer,2).
sleeps(hermite,1).
sleeps(family,5).
sleeps(expedition,8).

weight(dreamer,4.5).
weight(hermite,2).
weight(family,5).
weight(expedition,8.5).

price(dreamer,199).
price(hermite,159).
price(family,349).
price(expedition,699).

% sleeping bags
sleepingBag(mountain).
sleepingBag(cabin).
sleepingBag(square).

temp(mountain,-15).
temp(cabin,5).
temp(square,-3).

type(mountain,'mummy').
type(cabin,'double').
type(square,'rectangular').

weight(mountain,1.5).
weight(cabin,5).
weight(square,2.5).

price(mountain,350).
price(cabin,250).
price(square,150).

% sleeping pads
sleepingPad(rock).
sleepingPad(pfft).
sleepingPad(heaven).
sleepingPad(moon).

thickness(rock,2).
thickness(pfft,3).
thickness(heaven,10).
thickness(moon,5).

weight(rock,0.2).
weight(pfft,0.8).
weight(heaven,0.4).
weight(moon,0.4).

price(rock,100).
price(pfft,10).
price(heaven,80).
price(moon,50).

% backpacks
backpack(trapper).
backpack(prospector).
backpack(air).
backpack(comfort).

holds(trapper,20).
holds(prospector,25).
holds(air,10).
holds(comfort,15).

price(trapper,250).
price(prospector,220).
price(air,150).
price(comfort,200).

%********************
% b) Paul is going camping for the first time and needs to buy a tent, sleeping bag and pad as well as
% back pack. Write a query for Paul who likes a sleeping bag good down to at least 0 deg. Celcius,
% and a sleeping pad of 5cm or more. Make sure to also calculate the total price of the package.

% NOTE - Assignment instructions unclear:
% It does not mention whether paul should be limited to strictly 1-person tents or sleeping bags.
paul() :- tent(Tent), weight(Tent, WTent), price(Tent,PTent), 
		  sleepingBag(Bag), temp(Bag,Temp), Temp =< 0, weight(Bag, WBag), price(Bag,PBag),
		  sleepingPad(Pad), thickness(Pad,Thickness), Thickness >= 5, weight(Pad,WPad), price(Pad, PPad),
		  backpack(BP), holds(BP,WMax), WCur is WTent+WBag+WPad, WMax >= WCur, price(BP,PBP),
		  PTotal is PTent+PBag+PPad+PBP,
		  write('Tent: '), write(Tent), write(', $'), writeln(PTent),
		  write('Sleeping Bag: '), write(Bag), write(', $'), writeln(PBag),
		  write('Sleeping Pad: '), write(Pad), write(', $'), writeln(PPad), 
		  write('Backpack: '), write(BP), write(', $'), writeln(PBP),
		  write('Total: $'), writeln(PTotal).

% A more generalized form of this rule could be made, 
% adding parameters for Temp and Thickness such as tripFor1(Tmp, Thk) :- 
% and replacing 0 and 5 with the new variables.		  
tripFor1(Tmp, Thk) :- tent(Tent), weight(Tent, WTent), price(Tent,PTent), 
					  sleepingBag(Bag), temp(Bag,Temp), Temp =< Tmp, weight(Bag, WBag), price(Bag,PBag),
					  sleepingPad(Pad), thickness(Pad,Thickness), Thickness >= Thk, weight(Pad,WPad), price(Pad, PPad),
					  backpack(BP), holds(BP,WMax), WCur is WTent+WBag+WPad, WMax >= WCur, price(BP,PBP),
					  PTotal is PTent+PBag+PPad+PBP,
					  write('Tent: '), write(Tent), write(', $'), writeln(PTent),
					  write('Sleeping Bag: '), write(Bag), write(', $'), writeln(PBag),
					  write('Sleeping Pad: '), write(Pad), write(', $'), writeln(PPad), 
					  write('Backpack: '), write(BP), write(', $'), writeln(PBP),
					  write('Total: $'), writeln(PTotal).
		  
%********************
% c) The travel company Cheap Thrills is offering a trip for 4. Mary, Sean, Paula and Thomas are
% signed up. Create a predicate for Cheap Thrills to find equipment for their four customers. Mary
% does not want to share a tent with Sean. Paula and Thomas would like to share a sleeping bag but
% still need a sleeping pad. They want it all to fit in two back packs and the customers are allowed
% to bring personal belongings of 7 kg each.
%
% ?- tripFor4(7,mary(MB,MP),sean(SB,SP),
%               paula(PTB,PP),thomas(PTB,TP), tents( T1, T2),
%               backpacks(B1,B2)).

% NOTE: it is assumed each person wants their equipment 
% and personal belongings to be carried in the same backpack
% and that paula and thomas will use the same backpack.
% Additionally, it is assumed Mary will sleep in Paula and Thomas's tent.
% Therefore, the tent must sleep >2 persons
tripFor4(PW,
		mary(MBag,MPad),sean(SBag,SPad),
        paula(PTBag,PPad),thomas(PTBag,TPad), 
		tents(Tent1,Tent2),backpacks(BP1,BP2)) :- mary(MBag,MPad), sean(SBag,SPad),
												  paula(PTBag,PPad), thomas(PTBag,TPad), type(PTBag,'double'), 												  
												  weight(MBag,WMBag), weight(MPad,WMPad), WMary is WMBag+WMPad+PW,
												  weight(SBag,WSBag), weight(SPad,WSPad), WSean is WSBag+WSPad+PW,
												  weight(PTBag,WPTBag), weight(PPad,WPPad), WPaula is WPTBag+WPPad+PW,
												  weight(TPad,WTPad), WThomas is WTPad+PW,
												  tents(Tent1,Tent2), sleeps(Tent2,Sleeps), Sleeps>2,
												  weight(Tent1,WT1), weight(Tent2,WT2),
												  backpacks(BP1,BP2),
												  W1 is WMary+WSean+WT1, holds(BP1,WMax1), WMax1>=W1,
												  W2 is WPaula+WThomas+WT2, holds(BP2,WMax2), WMax2>=W2.
												
mary(B,P) :- sleepingBag(B), sleepingPad(P).

sean(B,P) :- sleepingBag(B), sleepingPad(P).

paula(B,P) :- sleepingBag(B), sleepingPad(P).

thomas(B,P) :- sleepingBag(B), sleepingPad(P).

tents(T1,T2) :- tent(T1), tent(T2).

backpacks(B1,B2) :- backpack(B1), backpack(B2).

%********************
% d) The outfitter also sells two package deal: Basic for 2 at $ 600 and Extra for one person at $650.
% Basic for 2 consists of the Dreamer tent, the Cabin sleeping bag, two sleeping pads Pfft and one
% Trapper back pack. Extra consists of a Hermite tent, a Mountain sleeping bag, a Rock sleeping
% pad and a Air backpack.
%
% Jill and Kyle are buying equipment for their camping trip. They will each bring 5 kg of personal
% belongings. They like the Dreamer tent for sure, want to use only one back pack and they can
% spent up to $750 in total. Write the rule equipment2, that will produce all solutions for them:
%
% ?- equipment2(5,jill(JB,JP),kyle(KB,KP),tents(dreamer),
%                 backpacks(BP),750).

equipment2(PW,jill(JBag,JPad),
			  kyle(KBag,KPad),
			  tents(dreamer),
			  backpacks(BP),PMax) :- tents(dreamer), jill(JBag,JPad), kyle(KBag,KPad), 
									 weight(JBag,WJBag), weight(JPad,WJPad), WJill is WJBag+WJPad+PW,
									 weight(KBag,WKBag), weight(KPad,WKPad), WKyle is WKBag+WKPad+PW,
									 W is WJill+WKyle, backpacks(BP), holds(BP,WMax), WMax>=W,
									 price2(P,JBag,JPad,KBag,KPad,BP), P=<PMax.
									
price2(P,B1,P1,B2,P2,BP) :- B1=B2, B2=cabin,
							P1=P2, P2=pfft,
							BP=trapper, !,
							P is 600.
							
price2(P,B1,P1,B2,P2,BP) :- price(B1,PB1), price(B2,PB2),
							price(P1,PP1), price(P2,PP2),
							price(BP,PBP), P is PB1+PB2+PP1+PP2+PBP.

tents(T) :- tent(T).

backpacks(BP) :- backpack(BP).

jill(B,P) :- sleepingBag(B), sleepingPad(P).

kyle(B,P) :- sleepingBag(B), sleepingPad(P).

%********************
% e) Cheap Thrills wants to offer a comfort trip for which their guide carries the tents and a Heaven
% sleeping pad for all customers. The predicate needs to return true if the number of people N can
% be served with one guide (i.e., the guide must carry in her/his backpack all tents and sleeping
% pads). Take into account that the guide also has to carry his/her personal belongings of 5 kg, a
% sleeping bag and a pad.
%
% ?- comfort(5,guide(GB,GP),backpacks(GBP),N).

comfort(PW,guide(GBag,GPad),
		   backpacks(GBP),N) :- tentsForN(N,0,WTents), guide(GBag,GPad), 
								weight(GBag,WGBag), weight(GPad,WGPad), WGuide is WGBag+WGPad+PW,
								WPads is 0.4*N, WTotal is WGuide+WTents+WPads, 
								backpacks(GBP), holds(GBP,WMax), WMax>=WTotal, !.
								
tentsForN(N,M,W) :- N>5, !,
			        tent(T),
			        sleeps(T,8),
				    weight(T,WT),
					NN is N-8,
				    MM is M+WT,
			        tentsForN(NN,MM,W).
			   
tentsForN(N,M,W) :- N>2, !,
			        tent(T),
			        sleeps(T,5),
			        weight(T,WT),
					NN is N-5,
				    MM is M+WT,
			        tentsForN(NN,MM,W).
			   
tentsForN(N,M,W) :- N>1, !,
				    tent(T),
				    sleeps(T,2),
					weight(T,WT),
				    NN is N-2,
					MM is M+WT,
				    tentsForN(NN,MM,W).
				  
tentsForN(N,M,W) :- N>0, !,
				    tent(T),
				    sleeps(T,1),
					weight(T,WT),
				    NN is N-1,
					MM is M+WT,
				    tentsForN(NN,MM,W).
				  
tentsForN(N,M,W) :- N=<0, !,
				    W is M.



guide(GBag,GPad) :- sleepingBag(GBag), sleepingPad(GPad).