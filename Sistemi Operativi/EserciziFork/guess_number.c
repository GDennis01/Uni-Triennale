#define _GNU_SOURCE //
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>
#include <string.h>

/*
Es. [guess-number] Scrivere un programma che realizzi un semplice gioco. Il programma seleziona un numero casuale tra 0
e argv[1] (il primo argomento passato a riga di comando), e l’utente deve indovinare questo numero. Per fare questo, viene
realizzato un ciclo in cui il programma legge da tastiera un numero inserito dall’utente:
se il numero `e stato indovinato, il gioco finisce;
se il numero `e maggiore o minore di quello estratto casualmente, viene stampato a video la scritta “maggiore” o “minore”,
rispettivamente.
Se il giocatore non indovina entro argv[2] secondi (da realizzare con alarm e gestendo il segnale SIGALRM), il programma stampa
a video “tempo scaduto”, ed esce.


*/
void handler(int signum){
    printf("Tempo scaduto bro\n");
    //fflush(stdout);
    //kill(getpid(),SIGTERM);
    raise(SIGTERM);
}
int main(int argc, char const *argv[])
{
    int in,guessed=0,rnd=0;
    srand(getpid());
    rnd = rand()%(atoi(argv[1])+1);//range [0-argv1]
    int sec=atoi(argv[2]);

    //Defining sigaction
    struct sigaction sa;
    bzero(&sa,sizeof(sa));//clearing sa data
    sa.sa_handler = handler;
    sigaction(SIGALRM,&sa,NULL);
    
    printf("Indovina il numero!\n");
    alarm(sec);
    while(guessed==0){

    

    //alarm(sec);
    scanf("%d",&in);

    if(in<rnd){

        printf("Il numero da indovinare è maggiore\n");

    }else if(in>rnd){

        printf("Il numero da indovinare è minore\n");

    }else if(in == rnd){

        printf("Hai indovinato il numero. GG\n");
        guessed=1;

    }
    } 
    exit(0);   
    /* code */
    return 0;
}
