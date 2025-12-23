#include <stdio.h>

void plus_one(int*); 

int main(void) {

    int i = 10;
    int *j = &i;

    // value of the i before plus_one(j): 10
    // value of the parameter n in the plus_one(int*):: 11
    // value of the i before plus_one(j): 11
    printf("value of the j before plus_one(j): %d\n", *j);

    plus_one(j);

    printf("value of the i after plus_one(i): %d\n", *j);
}

void plus_one(int* n) {
    *n = *n + 1;
    printf("value of the parameter n in the plus_one(int*): %d\n", *n);
}
