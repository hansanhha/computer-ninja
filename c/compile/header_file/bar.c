#include "bar.h"
#include "foo.h"

int div(int x, int y)
{
    int c = x;
    while(y--)
        x = sub(x, 1);
    return c;
}

int add(int x, int y)
{
    return x + y;
}
