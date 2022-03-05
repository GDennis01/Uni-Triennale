##### Correttezza
**Correttezza Parziale:** Legato ad una procedura, ovvero l'algoritmo termina solo con determinati ingressi
**Correttezza Totale:** Legato ad un algoritmo, ovvero l'algoritmo termina con ogni possibile ingresso

##### Specifiche di un algoritmo
**Pre-condizioni:** ipotesi sull'ingresso(come deve essere fatto l'*input*)
**Post-condizioni:**proprietà dell'uscita(criterio che stablisce come deve essere fatto l'output).
Esempio divisione intera:
**Div(a,b)**
**Pre:** $a\ge 0,b \gt 0$ numeri interi
**Post:** q e r tali che $a=b\cdot q+r$ e $0\le r \lt b$ 


##### Induzione Completa/Semplice
**Semplice:** Si induce incrementando/diminuendo un parametro di 1 in 1 $P(m) =>P(m+1)$
**Esempio**: per la **Torre di Hanoi**, se ho $n$ dischi richiamo l'algoritmo con $n-$ dischi.
dunque il parametro $n$ viene diminuito di $1$ ad ogni iterazione

**Completa:** Si induce incrementando/diminuendo un parametro di un valore diverso da 1. 
**Esempio**: per la **Divisione Ricorsiva**, la soluzione con input $(a,b)$ si trova richiamando l'algoritmo con input $(a-b,b)$, dunque il parametro $a$ viene diminuito di $b$ ad ogni iterazione

#### Induzione Completa
**Caso Base:** $\exists k \ge 0.P(0) \wedge P(1)\wedge...\wedge P(K)$
Ovvero, dato un $k \ge 0$,  $P(n)$ vale fino a $n=k$

**Passo induttivo:** $\forall m \ge k. P(0) \wedge P(1) \wedge... \wedge P(m) => P(m+1)$
Ovvero, per ogni $m \ge k$, vale $P(m)$
**Conclusione:** caso base e passo induttivo implicano $\forall n \ge 0. P(n)$

#### Invariante di ciclo
**Invariante:** Proposizione che esprime una proprietà delle variabili che persiste in ogni punto dell'algoritmo.
- **inizializzazione:** la proposizione vale subito prima del ciclo
- **mantenimento:** se la proposizione vale prima di entrare nel ciclo, vale anche dopo che il corpo del ciclo è stato eseguito.

L'invariante deve essere **utile** a verificare la correttezza dell'algoritmo.

Nel caso della **divisione iterativa**:
![[div_it_invariante.png]]
una *invariante di ciclo* potrebbe essere $a=bq+r$ con $0 \le r$

Questa proposizione vale prima del ciclo(**inizializzazione**):
$q=0$ dunque $bq$ vale 0 mentre $r=a$ dunque $bq+r => 0+a$

Vale anche durante e dopo il ciclo(**mantenimento**):
![[mantenimento_invariante.png]]

Alla fine del ciclo sappiamo che:
$a=bq+r \wedge 0 \le r$
ma visto che siamo usciti dal ciclo sappiamo anche che $0 \le r \lt b$, dal momento che quella è la condizione di terminazione del ciclo.
Possiamo dunque constatare che l'algoritmo è **corretto** dal momento che soddisfa le post condizioni(**criterio dell'output**)
![[post-condizione.png]]

