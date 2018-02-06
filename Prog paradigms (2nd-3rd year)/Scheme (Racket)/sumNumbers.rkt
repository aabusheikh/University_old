#lang racket

; 1. [0.5 point] Design a function that takes a list of characters and numbers and adds up all the
; numbers returning the list of characters unchanged but only the sum of the numbers.
;
; (sumNumbers ‘(a 2 4 a b 5))
; => ((a a b) 11)
(define (sumNumbers L) 
	(cons (sumNumbersC L) (sumNumbersN L))
)

(define (sumNumbersC L) 
	(if (null? L) 
		'() 
		(if (number? (car L)) 
			(sumNumbersC (cdr L)) 
			(cons (car L) (sumNumbersC (cdr L)))
		)
	)
)

(define (sumNumbersN L) 
	(if (null? L) 
		0 
		(if (number? (car L)) 
			(+ (car L) (sumNumbersN (cdr L)))  
			(sumNumbersN (cdr L))
		)
	) 
)

; 2. [0.5 point] Design a function that converts a positive decimal integer into the quaternary numeral
; system (base 4).
;
; (quaternary 16)
; => 100
(define (quaternary d) 
	(quaternaryH d 0)
)

(define (quaternaryH d n) 
	(let ((q (/ d 4))) 
		(if (= q 0) 
			0 
			(let ((r (modulo d 4))) 
				(+ (* r (expt 10 n)) (quaternaryH (floor q) (+ n 1)))
			)
		) 
	)
)