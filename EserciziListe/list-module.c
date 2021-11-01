#include "list-module.h"
list list_insert_head(list p, int val)
{
	list new_el;

	/* Allocate the new node */
	new_el = malloc(sizeof(*new_el));
	/* Setting the data */
	new_el->value = val;
	/* head insertion: old head becomes .next field of new head */
	new_el->next = p;
	/* new head is the pointer to the new node */
	return new_el;
}

void list_print(list p)
{
	/* Looping all nodes until p == NULL */
	if (p == NULL) {
		printf("Empty list\n");
		return;
	}
	printf("[%i]", p->value);
	for(; p->next!=NULL; p = p->next) {
		printf(" -> [%i]", p->next->value);
	}
	printf("\n");
}

void list_free(list p)
{
	/* If p ==  NULL, nothing to deallocate */
	if (p == NULL) {
		return;
	}
	/* First deallocate (recursively) the next nodes... */
	list_free(p->next);
	/* ... then deallocate the node itself */
	free(p);
}

list list_insert_ordered(list p, int val){
	if(p == NULL){
		printf("Empty list");
		return NULL;
	}
	list tmp=p;
	while(tmp->next != NULL){
		if(tmp->next->value >= val){
			tmp->next= list_insert_head(tmp->next,val);
			return p;
			}
		tmp=tmp->next;
	}
	return p;
}

list list_cat(list before, list after){
	list tmp=before;
	if(before== NULL){
		printf("Empty list\n");
		return NULL;
	}
	for(;tmp->next!=NULL;tmp=tmp->next){
	}
	tmp->next=after;
	return before;
}

list list_insert_tail(list p, int val){
	list new_el,tmp;
	if(p == NULL){
		printf("The list is empty");
		return NULL;
	}
	/*
	Alloco sizeof(*new_el) bytes per il nuovo nodo.
	Non alloco sizeof(list) perchè list è un puntatore.
	Quindi per la size giusta gli passo la lista deferenziata
	*/
	new_el=malloc(sizeof(*new_el));
	new_el->value=val;
	new_el->next=NULL;
	for( tmp=p;tmp->next!=NULL;tmp=tmp->next){
	}
	tmp->next=new_el;
	return p;
}

list list_delete_if(list head , int to_delete){
    list tmp=head;
	list prev=NULL;
    if(head == NULL)
        return head;
	//se l'elemento da togliere è il primo, restituisco la lista a partire dal secondo elemento
	if(head->value == to_delete){
		tmp=tmp->next;//aggiorno il tmp così posso deallocare head
		free(head);
		return tmp;
	}
	while(tmp != NULL){
		if(tmp->value == to_delete){
			prev->next=tmp->next;
			free(tmp);
			return head;
		}
		prev=tmp;
		tmp=tmp->next;
	}
	return head;
}

list list_delete_odd(list head){
	list tmp=head->next,tmp2=head->next;//parto direttamente dal secondo
	list prev=head;
	if(head == NULL)
		return head;
	free(prev);//dealloco il primo nodo
	prev=NULL;
	head=tmp;
	//le posizioni dispari sono in indice pari(i=0,i=2..)

	int i=1;
	while(tmp != NULL){
		
		prev=tmp;
		tmp=tmp->next;
		tmp2=tmp;
		
		if(i%2==0){//se i è pari, allora siamo in posizioni dispari(0,1,2)
			prev->next=tmp->next;
			free(tmp2);
		}
		i++;
	}
	return head;
}
/*



1)	[60] 	 	  [70]   	     [75]	  	   [80] 	  	  [90]
	 |			    
	 V				
2)  DeAl.		  [70]   	     [75]	  	   [80] 	  	  [90]	//i=1,*prev=70   *tmp=75


3)
*/