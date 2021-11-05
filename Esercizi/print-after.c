#include <stdio.h>
#include <string.h>
int main(){
char s1[81],s2[81];
int i,j;
int flag=0;

printf("Inserire la prima stringa:");
fgets(s1,sizeof(s1),stdin);

printf("Inserire la seconda stringa:");
fgets(s2,sizeof(s2),stdin);

for( i=0;i<sizeof(s1);i++){
    if(s1[i]<32 || s1[i]>126){
        s1[i]=0;
    }
}

for( i=0;i<sizeof(s2);i++){
    if(s2[i]<32 || s2[i]>126){
        s2[i]=0;
    }
}
if(sizeof(s2)<=sizeof(s1)){
    for(i=0;i<sizeof(s2);i++){
        for( j=0;j<sizeof(s1);j++){
            if(s2[i] == s1[j]){
                flag++;
                break;
            }else flag=0;
        }
    }
}
if(flag-1 == strlen(s2)){
    printf("%s",s1+flag);
}




return 0;
}