#include <stdio.h>
#include <stdlib.h>
int main(int argc, char const *argv[])
{
    int a,nBit,i;
    printf("Inserire il numero intero:");
    scanf("%d",&a);
    
    for( i=1,nBit=0;i<a;i=i*2,nBit++){}//mi trovo il numero di bit per rappresentare "a"
    char v[nBit];

    for(int j=0;j<nBit;j++){
        v[nBit-j-1]=a&1;//controllo se il bit a più destra è 0 o 1, lo salvo in coda nell'array
        a=a>>1;//shifto a destra di 1
    }
    
    for(int z=0;z<nBit;z++){
        printf("%d",v[z]);
    }
    return 0;
}
