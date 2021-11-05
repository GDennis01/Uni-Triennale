#include <string.h>
#include <stdlib.h>
#include <stdio.h>
/*
La quale legge in input l’array v di n puntatori a stringa 
(ciascuna delle quali terminata da zero), e ritorna il puntatore 
ad una stringa (anche essa terminata da zero) ottenuta concatenando le stringhe puntate da v.
Se per vari motivi questo non è possibile, viene ritornato NULL.
Nello svolgimento non è possibile usare le funzioni di libreria srtcat o strncat.
*/
char * concatenate_strings(char ** v, unsigned int n);

int main(int argc, char const *argv[])
{
    char *v[4];
    v[0]="Ciao";
    v[1]="Come stai?";
    v[2]="Io tutto bene, te?";
    v[3]="Sìsì";
    char *bella=concatenate_strings(v,4);
    printf("La stringa concat è: %s",bella);
    return 0;
}

char * concatenate_strings(char ** v, unsigned int n){
    int inc=strlen(v[0])+strlen(v[1]);//me lo salvo per il primo malloc

    char *res=malloc(inc);//primo malloc = dimensione della prima stringa + la seconda
    memcpy(res,v[0],strlen(v[0]));//copio la prima stringa dentro res
    int currPos=0;

    for(int i=0;i<n-1;i++){
        inc=inc+strlen(v[i+1]);//aumento "inc" di sizeof(stringa successiva)

        currPos=currPos+strlen(v[i]); //mi salvo la posizione corrrente del puntatore/offset

        memcpy( res+currPos, v[i+1], strlen(v[i+1])); //copio in coda(ovvero alla fine della stringa), la nuova stringa 
        
        res=realloc(res,sizeof(res)+inc); //aggiorno la dimensione dinamicamente del puntatore
    }
    return res;
}
