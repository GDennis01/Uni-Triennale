#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>//
#include <unistd.h>//Libs needed for getpid() method

  
//scrive nel file argv2 le righe di argv1 in ordine casuale
int file_shuffle_rows(FILE*file1,FILE*file2);



int main(int argc, char const *argv[]){
    FILE * file1,*file2;
    file1=fopen("file1.txt","r+");
    file2=fopen("file2.txt","r+");
    int a=file_shuffle_rows(file1,file2);
    return 0;
}

int file_shuffle_rows(FILE*file1,FILE*file2){
    srand(getpid());

    //Lettura righe file
    char **buffer=malloc(1024);//malloc provvisoria di 1024 bytes
    char charac=0;
    int i=0,j=0;
    while(charac != EOF){//se incontro EOF, smetto di leggere
        buffer[i]=malloc(1);
        while((char)(charac =fgetc(file1)) != '\n' && charac != EOF){//se incontro un \n o un EOF, esco dal ciclo
            buffer[i][j]=charac;
            buffer[i]=realloc(buffer[i],sizeof(buffer[i])+1+j);//a ogni lettura di un singolo carattere, realloco la memoria di mem+1
            j++;
        }
          printf("Riga:%s",buffer[i]);
        j=0;
        printf("\n");
    i++;
    }
    //Fine lettura righe

    //Ora incollo in ordine casuale le righe del file1 nel file2
    int z=0;
    while(z<i){//todo
    int a=rand()%i;
    int pos=fseek(file2,0,SEEK_END);//SEEK_END per l'append
    fprintf(file2,"%s\n",buffer[a]);
    z++;

    }
    return 1;//error with writing files
}
