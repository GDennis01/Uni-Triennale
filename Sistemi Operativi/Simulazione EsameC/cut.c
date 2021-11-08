#include <stdio.h>
#include <stdlib.h>
struct node{
    int val;
    struct node * next;
};
//head puntatore testa di una lista, begin<=end dove begin ed end rappresentano una posizione all'interno della lista
//Restituisce la sottolista tra le posizioni begin ed end deallocando gli altri nodi
struct node*cut(struct node*head,int begin,int end);

int main(int argc, char const *argv[])
{
    
    return 0;
}

struct node*cut(struct node*head,int begin,int end){
    struct node*tmp=head;
    struct node*elimina=head;

    if(head==NULL)
        return head;
    else{
        if(begin>0){
            elimina=tmp;
            tmp=tmp->next;
            cut(tmp,begin-1,end);
        }else
    }




    return NULL;
}
