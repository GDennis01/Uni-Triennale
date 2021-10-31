#include <stdio.h>
#include <stdlib.h>
int main(int argc, char const *argv[])
{
    //printf("char:%ld\nshort:%ld\nint:%d\nlong:%ld\nfloat:%ld\ndouble:%ld\npointers:%ld\n",
    //sizeof(char),sizeof(short),sizeof(int),sizeof(long),sizeof(float),sizeof(double),sizeof(char**));
    //int a=5;
    //void *p=&a;
    //printf("var a:%d",*(int*)p);
    int v[]={1,2,3,4,5,6};
    printf("v:%p\n v2:%p",&v[1],v+1);
    return 0;
}
