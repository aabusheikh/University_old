% Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
% Assignment by Prof. Jochen Lang

% Q1:
% Five golfers, named Jules, Gilles, Jean, Joe and Ghislain often play a round of golf together (use small
% caps for the players� names in your program). Their occupations are, not necessarily in this order,
% mason, plasterer, carpenter, tinsmith and roofer.
% They have played a round of golf but at the end their score cards don�t match. Their results were even
% numbers between 64 and 84.
%
% � Ghislain played par with 72, beating the roofer by 2 strokes.
% � Gilles had a bad day, and has ended with 10 strokes above par, or 12 strokes more than the
% mason.
% � The tinsmith has beaten Joe by 4 strokes and has won the round.
% � Jules has beaten the carpenter by 8 strokes, but did not win overall.
%
% Find the occupation and the score of each player.



% a) Design a predicate to enumerate all possible combinations for players where N is the name of the
% player, O is the occupation and S is the score.

person(jules).
person(gilles).
person(jean).
person(joe).
person(ghislain).

occupation(mason).
occupation(plasterer).
occupation(carpenter).
occupation(tinsmith).
occupation(roofer).

score(64).
score(66).
score(68).
score(70).
score(72).
score(74).
score(76).
score(78).
score(80).
score(82).
score(84).

player(N,O,S) :- person(N), occupation(O), score(S).



% b) Express the above bullet list as constraints, e.g., as below. You will have to fill-in the dots with the
% corresponding Prolog logic.
%
% constraintRoofer(player(N,O,S)) :- �
% constraintMason( �
% constraintTinsmith(�
% constraintCarpenter(�
% constraintWin(�

constraintRoofer(player(N,O,S)) :- player(N,O,S),
								   N \= ghislain, N \= gilles,
								   O = roofer,
								   S =:= 74.
								   
constraintMason(player(N,O,S)) :- player(N,O,S),
								  N \= gilles, N \= ghislain, 
								  O = mason,
								  S =:= 70.
								  
constraintTinsmith(player(N,O,S)) :- player(N,O,S),
									 N \= joe,
									 O = tinsmith,
									 player(joe,OJ,SJ),
									 S is SJ - 4.
									 
constraintCarpenter(player(N,O,S)) :- player(N,O,S),
									  N \= jules,
									  player(jules,OJ,SJ),
									  S is SJ + 8.
									  
constraintWin(player(N,O,S)) :- constraintTinsmith(player(N,O,S)).



% c) Finally, you will need to apply all the constraints, using the goal:
%
% ?- scores(player(jules,M1,P1), player(jean,M2,P2),
% player(gilles,M3,P3), player(joe,M4,P4), player(ghislain,M5,P5)).

scores(player(jules,M1,P1), 
	   player(jean,M2,P2),
	   player(gilles,M3,P3), 
	   player(joe,M4,P4), 
	   player(ghislain,M5,P5)) :- constraints(player(jules,M1,P1)),
								  constraints(player(jean,M2,P2)), M2 \= M1,
								  constraints(player(gilles,M3,P3)), M3 \= M2, M3 \= M1,
								  constraints(player(joe,M4,P4)), M4 \= M3, M4 \= M2, M4 \= M1,
								  constraints(player(ghislain,M5,P5)), M5 \= M4, M5 \= M3, M5 \= M2, M5 \= M1.
								  
constraints(player(N,O,S)) :- constraintRoofer(player(N,O,S)), !.
constraints(player(N,O,S)) :- constraintMason(player(N,O,S)), !.
constraints(player(N,O,S)) :- constraintTinsmith(player(N,O,S)), !.
constraints(player(N,O,S)) :- constraintCarpenter(player(N,O,S)), !.
constraints(player(N,O,S)) :- constraintWin(player(N,O,S)), !.