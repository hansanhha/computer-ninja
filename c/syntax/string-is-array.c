#include <stdio.h>

int main(void)
{
    char s[] = "Hello, world!";
    char *s2 = "Hello, world!";

    for (int i = 0; i < 13; i++)
        printf("%c", s[i]);
    printf("\n");

    for (int i = 0; i < 13; i++)
        printf("%c", s2[i]);
    printf("\n");
   
}
