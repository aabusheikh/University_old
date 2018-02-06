% We saw in class how to represent a binary search tree in Prolog. Write a predicate lca (K1, K2, T) which
% allows one to find the smallest common ancestor of K1 and K2 in the tree T. The predicate lca shall
% print the subtree T such that the root is the smallest common ancestor of K1 and K2.
% For example, given the tree
% treeA (X):-X = t (73, t (31, t(5,nil,nil), nil), t (101, t (83, nil, t(97,nil,nil)), t(2016,nil,nil))).
%
% We can issue the query
% ?- treeA(X), lca(83,2016,X).
% t(101,t(83,nil,t(97,nil,nil)),t(2016,nil,nil))
% X = t(73, t(31, t(5, nil, nil), nil), t(101, t(83, nil, t(97,
% nil, nil)), t(2016, nil, nil))).

% Solution: 
% 1) store inorder traversal of the tree from Node1-Node2 in a list "L"
% 2) perform post order traversal on the tree, and find out which Node "N" in "L" comes last in post order.
% 3) "N" is the answer

treeA(X):- X = t(73, 					%      73
			     t(31, 					%	  /  \
				   t(5,nil,nil), 		%    31  101
				   nil), 				%   /    /  \
				 t(101, 				%  5    83  2016
				   t(83, 				%         \
				     nil, 				%         97
					 t(97,nil,nil)), 
				   t(2016,nil,nil))).
				  
lca(K1,K2,X) :- treeA(X),
				inorder(K1,K2,X,L),
				write(L).
					   
inorder(R1,R2, t(K,L,R), List) :-
(   R2 < K
->  inorder(R1,R2, L, LL),
append(LL,[],List);
    K < R1
->  inorder(R1,R2, R, LR),
append(LR,[],List);
    inorder(R1,R2, L, LL),
    inorder(R1,R2, R, LR),
    append(LL, [K|LR], List)
).

inorder(_,_,nil,[]).