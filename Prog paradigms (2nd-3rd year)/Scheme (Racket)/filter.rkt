#lang racket

; Design the function filter to return the application of the filter to a list (You must normalize the
; weights of the filter). Note that filter and list can have arbitrary length. For example:
;
; (filter '(1 2 5 2 1) '(0.1 0.1 0.1 0.7 0.1 0.2 0.3 0.4 0.5 0.6 0.7))
; => '(0.20909090909090908 0.38181818181818183 0.24545454545454548 0.2636363636363636
; 0.30000000000000004 0.4 0.5 0.5272727272727273 0.4727272727272727
; 0.1818181818181818 0.06363636363636363)

(define (length-list L) 
	(if (null? L) 
		0 
		(+ 1 (length-list (cdr L)))
	)
) 
		
(define (reduce F F0 L) 
	(if (null? L) 
		F0 
		(F (car L) 
		   (reduce F F0 (cdr L))
		)
	)
) 

(define (filter F L) 
	(if (or (null? F) (null? L)) 
		'() 
		(let (r (reduce + 0 F)) 
			(let (NF (map (lambda x (/ x r)) F)) 
				()
			)
		)
	)
) 
		   
(define (apply-factor NF L) 
	(if (null? L) 
		'() 
		() 
	)
) 