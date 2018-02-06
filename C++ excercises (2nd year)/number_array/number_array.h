#ifndef NUMBER_ARRAY_H
#define NUMBER_ARRAY_H

/**
* accepts an integer array of numbers and
* returns a pointer to the first occurence
* of value. If no such value is found, the
* function is to return a null pointer.
**/
int* findFirst(int value, int numArray[], int length);

/**
* accepts an integer array of numbers (in !?!) 
* and returns a pointer to an array. The array 
* is to be allocated in the function and is to 
* contain pointers to all occurences of value 
* in the array of integers
**/
int** findAll(int value, int numArray[], int length, int& numFound);

/**
* accepts an integer array of numbers of length.
**/
void printArray(int numArray[], int length);

/**
* takes two arrays: the first array (in the 
* first argument) of size 4 by array pointer 
* and the second array (in the second argument) 
* of size 3 by reference and returns a pointer 
* to an array of size 7. You must use range 
* loops in the function to loop over the two 
* input arguments. The result array is to be 
* allocated in the function and is to contain 
* all numbers in increasing order from both arrays.
**/
int* append(int (*numArrayA)[4], int (&numArrayB)[3]);

#endif
