#lang racket

; 1. [0.5 point] Create the function (cubeLess x b) which calculates the power of 3 and the remainder
; such that b =x3+r and returns the remainder r. For example,
;
; (cubeless 2 10)
; => 2
(define (cubeLess x b) 
	(- b (* x (* x x)))
)

; 2. [0.5 point] Use the function cubeLess from 1) to find all numbers with a cube smaller than a
; given limit by creating a new function (smallerCube b) that generates the results as a list. For
; example:
;
; smallerCube 130)
; => ((1 129) (2 122)(3 103)(4 66)(5 5))
(define (smallerCube b) 
	(smallerCubeH b 1)
)


(define (smallerCubeH b n)  
	(let ((r (cubeLess n b))) 
		(if (< r 0) 
			'() 			
			(cons (cons n r) (smallerCubeH b (+ n 1)))
		)
	)
)

; 3. [0.5 point] Create a helper function (restSum S) to add up all the remainders from finding all
; cubes up to an upper limit in 2.
;
; (restSum (smallerCube 130))
; => 425
(define (restSum S) 
	(restSumH S)
)

(define (restSumH L) 
	(if (null? L) 
		0 
		(+ (cdr (car L)) (restSumH (cdr L)))
	)
)

; 4. [0.5 point] Write another function to add all the rest sums that are multiples of 3 within a range
; into a list. For example, in the range from 1 to 20:
;
; (showAllRestSum 1 20)
; => ((1 0)(4 3) (7 6)(9 9)(12 15)(15 21)(18 27))
(define (showAllRestSum l h) 
	(if (< l h) 
		(showAllRestSumH l h) 
		'()
	)
)

(define (showAllRestSumH n h) 
	(let ((S (restSum (smallerCube n)))) 
		(if (= (modulo S 3) 0) 
			(cons (cons n S) (showAllRestSum (+ n 1) h)) 
			(showAllRestSum (+ n 1) h)
		)
	)
)