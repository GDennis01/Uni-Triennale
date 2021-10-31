#include <stdio.h>
int main(int argc, char const *argv[])
{
    int v[10]={1,9,1000}, *q = v+3,giovanni;
    
    //q=v+1; --> q punterà al byte dopo di v
    //printf("1)Valore q:%d\n",*q);

    //v=q+1  --> Non si può fare perchè v è una costante

    //q++;   --> Il puntatore punterà al prossimo intero(quindi 4 byte dopo)
    //printf("3)Valore v:%p\nValore q:%p\n",v,q);

    //*q=*(v+1);   --> Il valore puntato da q diventerà lo stesso di v[1]   N.B: *(v+1) e v[1] sono scritture equivalenti :)
    //printf("\nIl valore di q:%d\nIl valore di v[1]:%d\n",*q,*(v+1) );

    //*q=*v+1;     --> Il valore puntato da q diventerà quello di (v[1]+1)
    //printf("\nIl valore di q:%d\nIl valore di v[1]:%d\n",*q,*v+1 );

    //q[4]=*(v+2); --> Il valore di q[4](ovvero *(q+4) che equivale a v[7]) equivale a quello di v[2]
    //printf("\nIl valore di q[4]:%d\nIl valore di v[2]:%d\n",q[4],*(v+2));

    v[1]= (int)*((char*)q-3); // con il primo casting (char*)q-3 trasformo il tipo del puntatore in char quindi punterà a char di 1 byte
    //Facendo q-3, ed avendolo castato a char, decrementerò di 3 byte invece di 12(ovvero 4*3) visto che ogni char occupa 1 byte.
    //Poi deferenzio il puntatore e ottengo il valore(che sarà shiftato(?)) e lo converto in int
    printf("\nIl valore di v[1]:%d\n",v[1]);
    printf("\nq:%p:\nchar*)q-3:%p\nq-3:%p",q,(char*)q-3,q-3);
    return 0;
}

/*
0x7ffee69567c0+ Il puntatore punta ad un int. L'int occupa 32 bit(ovvero 4 byte).
                Essendo "smart", il puntatore incrementandolo di 1 , punterà "all'int successivo"
                ovvero quello 4 byte dopo(visto che, come detto sopra, un int occupa 4 byte).
                Se facessi un +1 classico, punterei al secondo byte dell'intero puntato dal nostro puntatore.
             1=
0x7ffee69567c4
*/