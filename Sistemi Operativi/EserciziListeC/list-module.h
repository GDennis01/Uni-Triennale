#include <stdlib.h>
typedef struct node {
	int value;
	struct node * next;   /* pointer to the same structure */
} node;

typedef node* list;

/*
 * Assume that the list is in increasing order and insert the element
 * preserving the increasing order
 */
list list_insert_ordered(list p, int val);

/*
 * Concatenate two lists
 */
list list_cat(list before, list after);

/*
 * Insert elements at the head of the list
 */
list list_insert_head(list p, int val);

/*
 * Insert elements at the tail of the list
 */
list list_insert_tail(list p, int val);

/*
 * Print the list content
 */
void list_print(list p);

/*
 * Free the list
 */
void list_free(list p);


/*
* Cancella e dealloca il primo nodo della lista il cui valore del campo è uguale a to_delete.
* Restituisce la lista modificata
*/
list list_delete_if(list head , int to_delete);

/*
* Elimina dalla lista ogni elemento in posizione dispari(il primo, terzo etc).
* Restituisce la lista modificata
*/
list list_delete_odd(list head);


/*
* Elimina dalla lista ogni nodo il cui valore sia inferiore a "cut_value"
* Restituisce la lista modificata
*/
list list_cut_below(list head , int cut_value);

/*
* Restituisce una copia della lista(copia di ogni elemento)
*/
list list_dup(list head);