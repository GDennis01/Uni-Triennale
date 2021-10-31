#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int range_of_even(int * nums , int length , int *min , int *max);

int main(int argc, char const *argv[])
{
    int start,stop;
    start=clock();
    int v[]={1,2,3,4,5,6,7,8,9,10,12,19};
    int *min=malloc(sizeof(int)),*max=malloc(sizeof(int));
    *min=+2147483647,*max=-2147483648;
    int a=range_of_even(v,12,min,max);
    printf("\nC'è un numero pari:%d\nMin:%d\nMax:%d\n",a,*min,*max);
    stop=clock();
    printf("\nTempo:%d\n",stop-start);
    return 0;
}
int range_of_even(int * nums , int length , int *min , int *max){
    int containsEven=0;
    for(int i=0;i<length;i++){
        printf("Numero:%d\n",nums[i]);
        if((nums[i]&1)==0){
            containsEven=1;
            if(nums[i]<*min){
                *min=(nums[i]);
            }
            if(nums[i]>*max){
                *max=(nums[i]);
            }
            }
            
    }
    printf("\nMin:%d\nMax:%d\n",*min,*max);
    return containsEven;
}
