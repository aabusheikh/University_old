% Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
% Assignment by Prof. Jochen Lang

%*******************
% a) Create the rule cubeLess(X,B,R) which calculates the power of 3 and the remainder such that
% b =x^3 + r. Therefore, for a query cubeLess( 2, 10, R) , the result should be R=2. Note, that you will
% not be able to call the rule without X and B instantiated.

cubeLess(X,B,R) :- C is X*(X*X),
				   R is B-C.
				   
%*******************
% b) Use the rule cubeLess from a) to find all numbers with a cube smaller than a given limit by
% creating a new rule smallerCube(X,B). Print your results to the console with write/1 and
% writeln/1 . Example:
% ?- smallerCube(X,130).
% 1 rest 129
% 2 rest 122
% 3 rest 103
% 4 rest 66
% 5 rest 5
% Because 1^3= 1 + 129 = 130, 2^3= 8 + 122 = 130 and so on.

smallerCube(X,B) :- smallerCube(X,B,1).

smallerCube(X,B,N) :- cubeLess(N,B,R),
					  R > 0,
					  write(N), write(' rest '), writeln(R),
					  NN is N+1,
					  smallerCube(X,B,NN).
					  
%*******************
% c) Create another (and separate) rule restSum(B,S)to add up all the remainders from finding all
% cubes up to an upper limit.
% ?- restSum(130,S).
% S = 425.

restSum(B,S) :- restSum(B,S,1,0).

restSum(B,S,N,T) :- cubeLess(N,B,R),
				    R>0, !,
				    SS is T+R,
				    NN is N+1,
				    restSum(B,S,NN,SS).

restSum(B,S,N,T) :- cubeLess(N,B,R),
				    R =< 0,
				    S is T.
				  
%*******************
% d) Write another rule to print all the rest sums that are multiples of 3 within a range. For example,
% in the range from 1 to 20:
% ?- showAllRestSum(1,20).
% 1 rest 0
% 4 rest 3
% 7 rest 6
% 9 rest 9
% 12 rest 15
% 15 rest 21
% 18 rest 27
% true

showAllRestSum(L,H) :- L=<H,
					   showAllRestSum(L),
					   LL is L+1,
					   showAllRestSum(LL,H).
					   
showAllRestSum(N) :- restSum(N,S),
					 S mod 3 =:= 0, !,
					 write(N), write(' rest '), writeln(S).
					 
showAllRestSum(N).