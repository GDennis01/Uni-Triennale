#include <stdio.h>
#include <stdlib.h>
#include <string.h>
char * get_sel(char*s,char sel);//s byte0 terminated string, sel != 0
int main(int argc, char const *argv[])
{
    char *s="Italia Italia";
    char sel='i';
    printf("Substring:%s\n",get_sel(s,sel));
    return 0;
}
//Alloca e ritorna la sottostringa di s compresa fra la prima occorrenza di sel e la seconda
//Se sel compare una sola volta, ritorna la sottostringa s fino alla fine
//Se sel non compare, ritorna NULL
// get_sel("Italia Italia",'i')-> "a Ital"
char * get_sel(char*s,char sel){
    char * res=malloc(1);
    char * tmp=s;
    int i;
    for(;tmp!=NULL;tmp++){
        if(*tmp == sel){//a occurence of sel has been found
            if(*(tmp+1)== sel){//empty substring -> sel=a  s=baab -> res=""
                return res;
            }else{//substring isnt empty
                for(i=0,tmp=tmp+1; *tmp != '\0' && *tmp != sel;tmp++,i++){//loop till 0 byte or second occurrence of sel
                    *(res+i)=*tmp;//appending the new character
                    res=realloc(res,strlen(res)+i);//reallocing the previous size +1
                    
                }
                return res;
            }
        }
    }
    return NULL;
}
