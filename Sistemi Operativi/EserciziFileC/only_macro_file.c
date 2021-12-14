//Tramite MACRO, definire un programma che permetta di leggere un file e stamparne il suo contenuto.
//Le macro devono permettere di leggere il file secondo i due modi visti a lezione: File Descriptors e Streams
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
//#define FD
#ifdef FD
#define DEF_FILE  int my_f=open("test.txt",O_RDONLY)
#define BUFF char buff=0
#define AUX_VAR int info = 1
#define LOOP while(info != 0)
#define STAMPA printf("%c",buff)
#define INC info = read(my_f,&buff,1)
#define CLOSE_FILE close(my_f)

#else

#define DEF_FILE  FILE * my_f=fopen("test.txt","r")
#define BUFF
#define AUX_VAR int i=fgetc(my_f)
#define LOOP while(i!=-1)
#define STAMPA printf("%c",(char)i)
#define INC i=fgetc(my_f)
#define CLOSE_FILE fclose(my_f)
#endif
int main(){
        DEF_FILE;
        BUFF;
        AUX_VAR;
        LOOP{
            STAMPA;
            INC;
        }
        CLOSE_FILE;
}