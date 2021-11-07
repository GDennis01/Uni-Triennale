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
	if(head == NULL)
		return head;
	list tmp=head->next;//parto direttamente dal secondo
	list prev=head;
	free(prev);//deallocating first node
	prev=NULL;
	head=tmp;
	//odd nodes are at even indexes(i=0,i=2 etc)
	int i=1;
	while(tmp != NULL){
		if(i%2==0){//deallocating odd nodes
			prev->next=prev->next->next;
			free(tmp);
			tmp=prev->next;
		}else{
			prev=tmp;
			tmp=tmp->next;
		}
		i++;
	}
	return head;
}
list list_cut_below(list head , int cut_value){
	if(head == NULL)
		return head;
	list tmp=head,prev=NULL;
	if(head->value < cut_value){//first node having a value below *cut_value*
		head=head->next;
		free(tmp);
		tmp=head;
	}
	while(tmp != NULL){
		if(tmp->value < cut_value){
			prev->next=tmp->next;
			free(tmp);
			tmp=prev->next;
		}else{
		prev=tmp;	
		tmp=tmp->next;
		}
	}
	return head;
}

list list_dup(list head){
	list new_list=malloc(sizeof(*head));
	list tmp=head;
	list tmp2=new_list;
	while(tmp!= NULL){
		new_list->value=tmp->value;
		new_list->next=malloc(sizeof(tmp->next));
		tmp=tmp->next;
		new_list=new_list->next;
	}
	return tmp2;
}