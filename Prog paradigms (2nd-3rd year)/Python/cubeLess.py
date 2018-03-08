# Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)

'''
1. Create the function cubeLess (x, b) which calculates the power of 3 and the remainder such that
b =x3+r and returns the remainder r.
'''
def cubeLess(x, b):
    return b-(x*x*x)

#print(cubeless(2, 10))

'''
2. Use the function cubeLess from 1) to find all numbers with a cube smaller than a given limit by
creating a new function smallerCube (b) that generates the results as a list of tuples. For
'''
def smallerCube(b):
    results = []
    for i in range(1,b):
        r = cubeLess(i, b)
        if r>0:
            results.append((i, r))
        else:
            break
    return results

#print(smallerCube (130))

'''
3. Create a helper function restSum (S) to add up all the remainders from finding all cubes up to an
upper limit in 2.
'''
def restSum(s):
    result = 0
    for (n, r) in s:
        result += r
    return result

#print(restSum(smallerCube(130)))

'''
4. Write another function to add all the rest sums that are multiples of 3 within a range into a list.
'''
def showAllRestSum(l, h):
    results = []
    while l<h:
        rs = restSum(smallerCube(l))
        if rs % 3 == 0:
            results.append((l, rs))
        l += 1
    return results

#print(showAllRestSum(1,20))