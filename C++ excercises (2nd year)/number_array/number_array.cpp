#include <vector>
#include <iostream>

/** 
*  accepts an integer array of numbers and 
*  returns a pointer to the first occurence 
*  of value. If no such value is found, the 
*  function is to return a null pointer.
**/
int* findFirst(int value, int numArray[], int length) {
	int *first;
	for (int i = 0; i < length; ++i) {
		if (numArray[i] == value) {
			first = &numArray[i];
			break;
		}
	}
	return first;
}

/**
* accepts an integer array of numbers (in !?!)
* and returns a pointer to an array. The array
* is to be allocated in the function and is to
* contain pointers to all occurences of value
* in the array of integers
**/
int** findAll(int value, int numArray[], int length, int& numFound) {
	std::vector<int*> ptrVector;
	for (int i = 0; i < length; ++i) {
		if (numArray[i] == value) {
			ptrVector.push_back(&numArray[i]);
			++numFound;
		}
	}
	return &ptrVector[0];
}

/**
* accepts an integer array of numbers of length.
**/
void printArray(int numArray[], int length) {
	std::cout << "[ ";
	for (int i = 0; i < length; ++i) {
		std::cout << numArray[i] << " ";
	}
	std::cout << "]" << std::endl;
}

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
int* append(int (*numArrayA)[4], int (&numArrayB)[3]) {
	int numArrayC[7];

	for (int i = 0; i < 4; ++i) {
		numArrayC[i] = *numArrayA[i];
	}

	for (int j = 4; j < 7; ++j) {
		numArrayC[j] = numArrayB[j-4];
	}

	return &numArrayC[0];
}
