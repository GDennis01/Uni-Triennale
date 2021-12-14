#define _GNU_SOURCE  /* Per poter compilare con -std=c89 -pedantic */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/sem.h>

#define SEM_NUM 0
#define SEM_KEY 0x1234 //common key
#define TEST_ERROR if (errno) {fprintf(stderr,				\
				       "%s:%d: PID=%5d: Error %d (%s)\n", \
				       __FILE__,			\
				       __LINE__,			\
				       getpid(),			\
				       errno,				\
				       strerror(errno));}
int sem_id;
int main(int argc, char const *argv[])
{
    sem_id = semget(SEM_KEY, 1, 0600 | IPC_CREAT);//create or attach to SEM_KEY's semaphore
	TEST_ERROR;
	semctl(sem_id, SEM_NUM, SETVAL, 0);
	TEST_ERROR;
    struct sembuf sem_op;   
    sem_op.sem_num=0;
    sem_op.sem_flg=0;
    sem_op.sem_op=-1;//consuming resources;
    printf("Consumo risorse 1\n");
    semop(sem_id,&sem_op,1);

    sem_op.sem_num=0;
    sem_op.sem_flg=0;
    sem_op.sem_op=1;//consuming resources;
    printf("Rilascio risorse 1\n");
    semop(sem_id,&sem_op,1);
    
    sem_op.sem_num=0;
    sem_op.sem_flg=0;
    sem_op.sem_op=-1;//consuming resources;
    printf("Consumo risorse 2\n");
    semop(sem_id,&sem_op,1);
    semctl ( sem_id ,0, IPC_RMID );
    printf("Ok ho mangiato bro,cancello il semaforo\n");



    semctl ( sem_id ,0, IPC_RMID );
    return 0;
}