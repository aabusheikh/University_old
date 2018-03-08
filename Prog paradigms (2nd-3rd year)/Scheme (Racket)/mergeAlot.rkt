#lang racket

; Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
; Assignment by Prof. Jochen Lang

; Write a Scheme function mergeALot that takes a list of sorted lists and merge them into one sorted list.
; If the top-level list contains entries which are not sorted, than your function must return an error
; message indicating which is the offending list.
;
; For example:
;
; (mergeALot '((1 3 5) (2 4 6) (2 6 8)))
; => (1 2 3 4 5 6 6 8)
;
; (mergeALot '((1 3 5) (2 0 6) (2 6 8) (3 1) (5 4 3 2 1 0) (1 5 9 13 17)))
; => "ERROR: The following lists are not sorted: " (2 4 5)

(define (mergeALot L)
	(let ((s (allSorted L 1))) 
		(if (equal? s '()) 
			(mergeAll L) 
			(begin (display "ERROR: The following lists are not sorted: ") (display s))
		)
	)
)

(define (allSorted L n) 
	(if (null? L) 
		'() 
		(if (= (sorted (car L)) 1) 
			(allSorted (cdr L) (+ n 1)) 
			(cons n (allSorted (cdr L) (+ n 1)))
		)
	)
)

(define (sorted L) 
	(if (null? L) 
		(+ 0 1) 
		(if (equal? (cdr L) '()) 
			(+ 0 1) 
			(if (<= (car L) (car (cdr L))) 
				(sorted (cdr L)) 
				(+ 0 0)
			)
		) 
	)
)

(define (mergeAll L) 
	(if (null? L) 
		'() 
		(merge (car L) (mergeAll (cdr L)))
	)
)

(define (merge L1 L2) 
	(cond 
		((null? L1) L2) 
		((null? L2) L1) 
		((< (car L1) (car L2)) 
			(cons (car L1) (merge (cdr L1) L2))
		) 
		((= (car L1) (car L2)) 
			(merge (cdr L1) L2)
		) 
		(else (cons (car L2) (merge L1 (cdr L2)))) 
	)
)

; This version of merge keeps duplicates
(define (merge2 L1 L2) 
	(cond 
		((null? L1) L2) 
		((null? L2) L1) 
		((<= (car L1) (car L2)) 
			(cons (car L1) (merge (cdr L1) L2))
		) 
		(else (cons (car L2) (merge L1 (cdr L2)))) 
	)
)

