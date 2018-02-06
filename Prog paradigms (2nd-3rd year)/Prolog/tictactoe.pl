%tic-tac-toe

%win using full row
fullrow(P, Board) :- Board = [P,P,P,
						      _,_,_,
						      _,_,_].
						   
fullrow(P, Board) :- Board = [_,_,_,
                              P,P,P,
						      _,_,_].
						   
fullrow(P, Board) :- Board = [_,_,_,
                              _,_,_,
						      P,P,P].

% win using full column
fullcol(P, Board) :- Board = [P,_,_,
                              P,_,_,
						      P,_,_].
						   
fullcol(P, Board) :- Board = [_,P,_,
                              _,P,_,
						      _,P,_].
						   
fullcol(P, Board) :- Board = [_,_,P,
                              _,_,P,
						      _,_,P].
 
%win using full diagonal
fulldia(P, Board) :- Board = [P,_,_,
                              _,P,_,
						      _,_,P].
						   
fulldia(P, Board) :- Board = [_,_,P,
                              _,P,_,
						      P,_,_].
		
% winning checks		
win(Board) :- fullrow(x, Board), !, write('You win!').
win(Board) :- fullcol(x, Board), !, write('You win!').
win(Board) :- fulldia(x, Board), !, write('You win!').

% losing checks
lose(Board) :- fullrow(o, Board), !, write('You lose!').
lose(Board) :- fullcol(o, Board), !, write('You lose!').
lose(Board) :- fulldia(o, Board), !, write('You lose!').

% display board
display([A,B,C,D,E,F,G,H,I]) :- write([A,B,C]), nl, write([D,E,F]), nl, write([G,H,I]), nl, nl .

% player move
xmove([b,B,C,D,E,F,G,H,I], 1, [x,B,C,D,E,F,G,H,I]).
xmove([A,b,C,D,E,F,G,H,I], 2, [A,x,C,D,E,F,G,H,I]).
xmove([A,B,b,D,E,F,G,H,I], 3, [A,B,x,D,E,F,G,H,I]).
xmove([A,B,C,b,E,F,G,H,I], 4, [A,B,C,x,E,F,G,H,I]).
xmove([A,B,C,D,b,F,G,H,I], 5, [A,B,C,D,x,F,G,H,I]).
xmove([A,B,C,D,E,b,G,H,I], 6, [A,B,C,D,E,x,G,H,I]).
xmove([A,B,C,D,E,F,b,H,I], 7, [A,B,C,D,E,F,x,H,I]).
xmove([A,B,C,D,E,F,G,b,I], 8, [A,B,C,D,E,F,G,x,I]).
xmove([A,B,C,D,E,F,G,H,b], 9, [A,B,C,D,E,F,G,H,x]).
xmove(Board, N, Board) :- write('Illegal move.'), nl.

% computer move
omove([b,B,C,D,E,F,G,H,I], [o,B,C,D,E,F,G,H,I]).
omove([A,b,C,D,E,F,G,H,I], [A,o,C,D,E,F,G,H,I]).
omove([A,B,b,D,E,F,G,H,I], [A,B,o,D,E,F,G,H,I]).
omove([A,B,C,b,E,F,G,H,I], [A,B,C,o,E,F,G,H,I]).
omove([A,B,C,D,b,F,G,H,I], [A,B,C,D,o,F,G,H,I]).
omove([A,B,C,D,E,b,G,H,I], [A,B,C,D,E,o,G,H,I]).
omove([A,B,C,D,E,F,b,H,I], [A,B,C,D,E,F,o,H,I]).
omove([A,B,C,D,E,F,G,b,I], [A,B,C,D,E,F,G,o,I]).
omove([A,B,C,D,E,F,G,H,b], [A,B,C,D,E,F,G,H,o]).

oplay(Board, Board2) :- omove(Board, Board2), lose(Board2), !.
oplay(Board, Board2) :- omove(Board, Board2).
oplay(Board, Board2) :- not(member(b,Board)), !, write('Tie!'), nl, Board2 = Board.

% start game
game :- write('You play X by entering integer positions followed by a period.'), nl, display([1,2,3,4,5,6,7,8,9]),
			   play([b,b,b,b,b,b,b,b,b]).
			
% gameplay			
play(Board) :- win(Board).
play(Board) :- lose(Board).
play(Board) :- read(N), 
               xmove(Board, N, Board2), display(Board2), 
			   oplay(Board2, Board3), display(Board3),
			   play(Board3).
