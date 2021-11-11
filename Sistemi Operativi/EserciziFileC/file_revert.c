#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
//Scrivo sul file 2 il file 1 al contrario. Es. ultimo byte del file1 lo scrivo sul file1
#ifdef USE_FD //se la macro USE_FD è definita(ad esempio passandogliela via CLI) allora uso i file descriptor
int f1_fd,f2_fd;
#define FILE_OPEN(name) f1=open(name,O_RDWR)

#else //se non è definita uso gli stream
FILE *f1,*f2;
#define FILE_OPEN(name) f1=fopen(name,"r+")
#define FILE_SEEK_END fseek(f1,0,SEEK_END)//mi posiziono alla fine del f1
#define SEEK_ONE_CHAR_BACK fseek(f1,-1,SEEK_CUR)//mi sposto di un carattere indietro in f1
#define READ_CHAR(c)  c=fgetc(f1)
#define WRITE_CHAR(c)   fputc(c,f2)

#endif
void file_revert(FILE* file1, FILE* file2);

int main(int argc, char const *argv[])
{
    char c;
    if(FILE_OPEN(argv[1]) == NULL){//se il fd è -1 allora c'è stato un errore
        fprintf(stderr,"Error %d(%s) when trying to open file %s",errno,strerror(errno),argv[1]);
        exit(EXIT_FAILURE);
    }else{
        FILE_SEEK_END;//mi posiziono alla fine del file
        while(SEEK_ONE_CHAR_BACK != -1){//finchè sono nel file
            READ_CHAR(c);//leggo un carattere da f1
            WRITE_CHAR(c);//lo scrivo su f2
        }
        
    }
    

    return 0;
}


