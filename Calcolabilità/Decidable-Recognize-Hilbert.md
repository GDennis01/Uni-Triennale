## Turin-Recognizable
Data una **Macchina di Turing** $M$, la collezione di stringhe che $M$ accetta viene chiamato **linguaggio di M** o il linguaggio riconosciuto da M(**recognized**) e si denota con $L(M)$.

>Dunque un linguaggio è **Turin-Recognizable**(o decidibile positivamente) **se esiste** una macchina di turing **M** che lo decide positivamente(può non terminare su alcuni input.
## Turin-Decidable
Tuttavia, quando attiviamo una $M.T$ con un determinato input, possono capitare 3 cose.
1. **Accept**(accettazione)
2. **Reject**(rifiuto)
3. **Loop**(divergenza)

Una **M** può rifiutare un input andando in **reject** o in **loop** e alcune volte distinguere una macchina che va in **loop** o in **reject** diventa difficile.
Dunque distinguiamo le macchine che entrano sempre in **accept** o in **reject**(dunque si fermano su qualsiasi input) e le chiamiamo **deciders**.

>Un linguaggio è **Turin-Decidable**(o decidibile) se esiste una macchina di Turing che lo decide(termina su ogni input)


### Hilbert 10
E' il linguaggio $H=\{P\in\{+,-,0,\_,9,*,x_1,x_2,..,x_n\}^*\}$
Ti dice se un polinomio ha **radici intere**(ad esempio $2x-3$=> $x=\frac{3}{2}$ non ha radici intere.
E' possibile dire quando il polinomio ha radici intere, tuttavia non esiste un modo per dire quando non le ha, in questo caso **divergerà all'infinito.**
