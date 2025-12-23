extern int undVar;                 // Should be U  
int defVar;                        // Should be B

extern const int undConst;         // Should be U
const int defConst = 1;            // Should be R

extern int undInitVar;             // Should be U
int defInitVar = 1;                // Should be D

static int staticVar;              // Should be b
static int staticInitVar=1;        // Should be d
static const int staticConstVar=1; // Should be r

static void staticFun(int x) {}    // Should be t

extern void foo(int x);            // Should be U

void bar(int x) {                  // Should be T 
  foo(undVar);
  staticFun(undConst);
}
