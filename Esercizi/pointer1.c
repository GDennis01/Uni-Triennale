#include <stdio.h>
int main(int argc, char const *argv[])
{
    int *p, v; /* p is pointer to int, v is int */
    v = 2;
    p = &v; //the pointer p is pointing to v's address()
            //pointer p variable has the address of v
    printf("%i",*p); //it prints the value pointed by p
    return 0;
}
