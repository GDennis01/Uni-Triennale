#include <stdio.h>
#include <stdlib.h>
#include "list-module.c"
int main()
{
	list head = NULL,head2=NULL,head3=NULL;

	/* insert some numbers in the list... */
	head = list_insert_head(head, 10);
	head = list_insert_head(head, 2);
	head = list_insert_head(head, 15);
	head = list_insert_tail(head, 20);

	head2= list_insert_head(head2, 60);
	head2= list_insert_tail(head2, 70);
	head2= list_insert_tail(head2, 80);
	head2= list_insert_tail(head2, 90);
	head2= list_insert_ordered(head2,75);
	head= list_cat(head,head2);
	head2= list_delete_odd(head2);
	/* ... print them... */
	list_print(head2);
	/* ... and clean everything up  */
	list_free(head2);

	return 0;
}
