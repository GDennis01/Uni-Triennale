#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>//
#include <unistd.h>//Libs needed for getpid() method
/*
[file-shuffle-rows] Scrivere un programma che legge il nome di due file da riga di comando. 
Il programma scrive nel file argv[2] le righe del file argv[1] in ordine 
casuale (si veda man 3 rand per numeri casuali). 
Una riga si identifica come una sequenza di byte terminata dal carattere “a capo”.
*/
int main(int argc, char const *argv[])
{
    char **righefile=malloc(1024);
    int ch=0;
    int i=0;
    int j=0;
    int z=0;
    int flag=0;
    int rnd=0;
    FILE *file1=fopen(argv[1],"r+");
    FILE *file2=fopen(argv[2],"w+");
    srand(getpid());//to initialize the seed for the rng(random number generator)
    //Read the whole file line by line
    while(ch != EOF){
        ch=fgetc(file1);
        //if((char)ch == '\n'){//new line
        while((char)ch != '\n' && ch != EOF){//loop for each line   
            righefile[i]=realloc(righefile[i],1+j);//realloc: 1+each character that has been read
            righefile[i][j]=(char)ch;
            ch=fgetc(file1);
            j++;
        }
        j=0;
        printf("Character:%s\n",righefile[i]);
        i++;
    }
     int indexes[i];//array where I store already used indexes
     for(z=0;z<i;z++){
         indexes[z]=-1;
     }
    //write random lines in file2
    while(flag != 1){
        rnd=rand()%i;//by using modulus I force rand to stay in range 0_i-1
        for(z=0;indexes[z]!=-1 && z<i;z++){
            if(indexes[z]==rnd){
                flag=-1;
                rnd=rand()%i;
                z=0;
            }else{
                flag=0;
            }
        }
        if(flag==0){//rnd number aint already generated
            indexes[z]=rnd;
            fprintf(file2,"%s%c",righefile[rnd],'\n');
        }
        if(z==i-1){
            flag=1;//ifflag is 1 then I exit the for loop
        }
    }
    return 0;
}
