/*
[sum-rand-kids] Sia NUM_KIDS una macro definita con #define (di valore 20, per esempio). Si scriva un programma
in cui il processo padre genera NUM_KIDS processi figli. Ogni processo figlio genera casualmente un numero intero "N" da 1 a 6,
stampa il suo PID e ne esce con exit status uguale al numero casuale estratto (man 3 rand per la generazione di numeri interi
casuali. Si usi anche srand(getpid()) per l’inizializzazione del seed del random). Il processo padre attende la terminazione di
tutti i processi figli (con wait) e stampa la somma dei valori di uscita dei propri figli.
*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#define NUM_KIDS 10
int main(int argc, char const *argv[])
{
    int pid;
    int rnd=0;
    int status;
    int sum=0;

    for(int i=0;i<NUM_KIDS;i++)
    {
        switch(pid=fork()){
        case 0://child
            srand(getpid());
            rnd=(rand()%6)+1;//range [1,6]
            printf("Process ID:%i  N estratto:%i\n",getpid(),rnd);
            exit(rnd);
            
        break;

        default:
            while(wait(NULL)!= -1);//wait for every child process to end
        break;
    }
    }
    return 0;
}
