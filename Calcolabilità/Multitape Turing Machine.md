### M.T. a 3 registri
Avere un solo registro può essere molto **sconveniente**.
Se avessimo un registro con due stringhe lunghe in totale **1 milione** e volessimo controllare se fossero uguali o meno, dovremmo impiegare $O(N^2)$ e dunque dovremmo scorrere il registro circa ***1 miliardo*** di volte. 

Con **3 registri** tuttavia posso salvarmi le due stringhe su due registri di appoggio(il primo registro è quello di input).
Per segnare l'inizio dell'area di memoria mettiamo un **#** all'inizio di ogni area.

In una $M.T$ la funzione di transizione cambia diventando:
$\delta:Q\times\Gamma^k \rightarrow Q\times \Gamma^k\times\{L,R,S\}$($S$ significa *stay put* ovvero la testina resta ferma)
dove $k$ è il numero di registri(o nastri).

Dunque $\delta(q_i,a_1,..,a_k)=(q_j,b_1,...,b_k,L,R,..,L)$
significa che se la macchina è in $q_i$ e sta leggendo i simboli da $a_1$ fino ad $a_k$, transito nello stato $q_j$, scrivendo i simboli $b_1...b_k$ e muovendo ogni testa a sinistra o destra(oppure non si muove)



