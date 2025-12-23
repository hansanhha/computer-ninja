#include <stdio.h>
#include <string.h>

int main(void) {
    
    char src[] = "Hello, world";
    char dest[100];

    strcpy(dest, src);

    dest[0] = 'z';

    printf("%s\n", src);  // "Hello, world"
    printf("%s\n", dest); // "zello, world"

    return 0;
}
