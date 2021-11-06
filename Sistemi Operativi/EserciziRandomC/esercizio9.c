#include <stdio.h>
#include <stdlib.h>
int main(int argc, char const *argv[])
{
    int day=atoi(argv[1]),month=atoi(argv[2]);
    int res=1;
    int anno[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31};

    for(int i=0;i<=month;i++){
        res=res+anno[i];
    }
    printf("Numero di giorni: %d",res);
    return 0;
}
