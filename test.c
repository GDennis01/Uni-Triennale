#include <stdio.h>
#include <stdlib.h>

int range_of_even(int * nums, int length, int * min, int * max){
    int i, check_min;
    for(i = 0; i < length; i++){
        if(nums[i] % 2 == 0)
            {
                check_min = 1;
            if(nums[i] < *min)
                *min = nums[i];  
                }    
    }

    int j, check_max;
    for(j = 0; j < length; j++){
        if(nums[j] % 2 == 0) 
            {
                check_max = 1;
            if(nums[j] > *max)
                *max = nums[j];
                }
    }

    if(check_min == 1 && check_max == 1)
        return 1;
    else
        return 0;

}


int main(){

    int lunghezza;
    printf("Inserire la lunghezza dell'array di interi:");
    scanf("%i", &lunghezza);

    int i;
    int v[lunghezza];
    //int * v = malloc(lunghezza * sizeof(int));
    for(i= 0; i < lunghezza ; i++){
        printf("Inserire l'elemento n. %i:", i);
        scanf("%i", &v[i]);
    }

    int * minimo=malloc(sizeof(int));
    int * massimo=malloc(sizeof(int));
    int j;
    int trovato = 0;
    for(j = 0; j<lunghezza && trovato == 0; j++){
        if(v[j] % 2 == 0){
            *minimo = v[j];
            *massimo= v[j];
            trovato = 1;
        }
    }




    printf("Il risultato della funzione è: %i\n", range_of_even(v, lunghezza, minimo, massimo));
    printf("%i %i", *minimo, *massimo);



    return 0;
}