#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>//Libs needed for getpid() method
/*
[kids-write-file] Si scriva un programma che legge da riga di comando 3 command-line arguments:
1. il primo `e un nome di file da aprire in scrittura
2. il secondo `e un numero n_kids di processi figlio da creare
3. il terzo `e un numero n_writes di scritture che ogni processo figlio deve fare
Il programma, apre il file (con nome passato a riga di comando) in scrittura e crea n_kids processi figlio. Ogni processo figlio
scrive n_writes volte nel file il proprio PID ed il PID del parent sulla stessa riga. Si provi a eseguire con:
./es-kids-write-file out.csv 10 1000
Investigare:
-i valori minimi di n_kids e n_writes per cui i PID stampati da un figlio sono interrotti da altri PID
-come la rimozione della bufferizzazione delle scritture possa far comparire numeri inattesi che non sono il PID di alcun
processo figlio.
*/
int main(int argc, char const *argv[]){

    FILE *file=fopen(argv[1],"r+");
    int n_kids=atoi(argv[2]);
    int n_writes=atoi(argv[3]);
    int pid=0;//Borse della spesa, arrivo..

    setvbuf(file,NULL,_IONBF,0);
    for(int i=0;i<n_kids;i++){
        switch(pid=fork()){

            case 0://child case
                //fprintf(file,"Figlio Generazione %i;\n",i);
                for(int j=0;j<n_writes;j++){
                    fprintf(file,"Parent Process ID:%i   Process ID:%i\n",getppid(),getpid());
                }
            break;

            default://parent case
                //printf("Bruh");
            break;
        }
    }
    return 0;
}



/*
Ancestor 4 figli
         |
         |___________________________________
          |         |           |           |
          4         4           4           4
          |
         _|__
        | |  |
        3 3  3
        |
        |
        |___
        |   |
        2   2
        |   |
        1   1
        */        