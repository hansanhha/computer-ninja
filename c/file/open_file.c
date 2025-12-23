#include <stdio.h>

int main(void) {
    
    FILE *fp;

    fp = fopen("hello.txt", "r"); // open file for "read" mode
                                  // fopen() will return NULL if something goes wrong, you should error check

    int c = fgetc(fp); // read a single character
    printf("%c\n", c);

    fclose(fp); // close the file when done
}
