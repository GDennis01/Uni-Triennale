#include <stdio.h>
#include <stdlib.h>
typedef struct node{
    int val;
    struct node * next;
} nodo;

static void printNode(nodo* head){
    while(head != NULL){
        printf("[%i]->",head->val);
        head=head->next;
    }
}
//Restituisce la lista di nodi contenente i 3 valori più alti. Dealloca gli altri
nodo *top3(nodo * head);
int main(int argc, char const *argv[])
{
    nodo *head=malloc(sizeof(head));
    head->val=1;
    nodo *nodo2=malloc(sizeof(head));
    head->next=nodo2;


    nodo2->val=3;
    nodo *nodo3=malloc(sizeof(head));
    nodo2->next=nodo3;

    nodo3->val=10;
    nodo *nodo4=malloc(sizeof(head));
    nodo3->next=nodo4;

    nodo4->val=29;
    nodo *nodo5=malloc(sizeof(head));
    nodo4->next=nodo5;

    nodo5->val=170;
    nodo *nodo6=malloc(sizeof(head));
    nodo5->next=nodo6;

    nodo6->val=2;
    nodo *nodo7=malloc(sizeof(head));
    nodo6->next=nodo7;

    printNode(head);

    nodo *newHead=top3(head);
    printNode(newHead);

    return 0;
}

nodo *top3(nodo * head){
    nodo *tmp=head;
    int val1=head->val,val2=head->val,val3=head->val;
    int i;
    for(i=0;tmp!=NULL;i++){
        tmp=tmp->next;
    }
    if(i<=2)
        return head;//La lista ha 3 o meno nodi

    nodo *firstNode=malloc(sizeof(firstNode));
    nodo *secondNode=malloc(sizeof(secondNode));
    firstNode->next=secondNode;
    nodo *thirdNode=malloc(sizeof(thirdNode));
    secondNode->next=thirdNode;
    printf("\nProva\n");
    tmp=head;
    while(tmp != NULL){//first top value
        if(val1<tmp->val)
            val1=tmp->val;
        tmp=tmp->next;
    }
    tmp=head;
    while(tmp != NULL){//first top value
        if(val2<tmp->val && tmp->val != val1)
            val2=tmp->val;
        tmp=tmp->next;
    }
    tmp=head;
    while(tmp != NULL){//first top value
        if(val3<tmp->val && tmp->val != val2 && tmp->val != val3 && tmp->val != val1)
            val3=tmp->val;
        tmp=tmp->next;
    }
    firstNode->val=val1;
    secondNode->val=val2;
    thirdNode->val=val3;
    return firstNode;
}
