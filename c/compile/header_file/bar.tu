# 1 "bar.c"
# 1 "<built-in>" 1
# 1 "<built-in>" 3
# 465 "<built-in>" 3
# 1 "<command line>" 1
# 1 "<built-in>" 2
# 1 "bar.c" 2
# 1 "./bar.h" 1
int div(int x, int y);
int add(int x, int y);

# 2 "bar.c" 2
# 1 "./foo.h" 1
int mul(int x, int y);
int sub(int x, int y);

# 3 "bar.c" 2

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

