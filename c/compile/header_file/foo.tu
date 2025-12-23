# 1 "foo.c"
# 1 "<built-in>" 1
# 1 "<built-in>" 3
# 465 "<built-in>" 3
# 1 "<command line>" 1
# 1 "<built-in>" 2
# 1 "foo.c" 2
# 1 "./foo.h" 1
int mul(int x, int y);
int sub(int x, int y);

# 2 "foo.c" 2

int mul(int x, int y)
{
    return x * y;
}

int sub(int x, int y)
{
    return x - y; 
}

