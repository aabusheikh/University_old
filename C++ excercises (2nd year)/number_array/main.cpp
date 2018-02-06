#include <iostream>
#include <random>

#include "number_array.h"

using std::cout;
using std::endl;

/** Do not alter
* Your solution must work with this main program
**/
int main() {
	int sz;
	cout << "Enter an array size (<100):" << endl;
	std::cin >> sz;
	if (sz<0 || sz >= 100) return -1;
	std::default_random_engine generator;
	std::uniform_int_distribution<int> distribution(1, 9);
	int* array = new int[sz];
	for (int i = 0; i<sz; ++i) {
		array[i] = distribution(generator);
	}
	printArray(array, sz); cout << endl;
	int val;
	cout << "Enter a value:" << endl;
	std::cin >> val;
	int* first = findFirst(val, array, sz);
	if (first == nullptr)
		cout << "Value:" << val << " not found" << endl;
	else
		cout << "Found: " << *first << " at " << first - array << endl;
	int szAll = 0;
	int **result = findAll(val, array, sz, szAll);
	for (int i = 0; i<szAll; ++i) {
		cout << i << ": " << *result[i] << " at " << result[i] - array << '\t';
	}
	cout << endl;
	delete[] array;
	delete[] result;
	// initialize an array of 4 and 3
	int aArr[4], bArr[3];
	for (auto& rv : aArr) rv = distribution(generator);
	for (auto& rv : bArr) rv = distribution(generator);
	cout << "Append: " << endl;
	printArray(aArr, 4); cout << " + "; printArray(bArr, 3);
	int* cArr = append(&aArr, bArr);
	cout << " = "; printArray(cArr, 7); cout << endl;
	delete[] cArr;
	return 0;
}


