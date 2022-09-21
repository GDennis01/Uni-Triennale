### Macchina di Turing
Può esser pensato come un **automa a stati finiti** con memoria **illimatata e senza restrizioni**.
Usa un nastro come memoria(**tape**).
Ha inoltre una testina che può leggere,scrivere simboli e spostarsi sul nastro.

All'inizio il nastro(o registro) contiene soltanto la stringa di **input**.
Può poi scriverci sopra se ha bisogna di salvare informazioni. La macchina continua a compuare finchè non entra in uno state di *accept* o *reject*. 
Se non entra in nessuno dei due stati, continuerà all'infinito senza mai fermarsi.


#### Definizione formale
>E' una 7-upla, $(Q,\Sigma,\Gamma,\delta,q_0,q_{accept},q_{reject})$, dove $Q,\Sigma$ e $\Gamma$ sono insiemi finiti.
>1. $Q$ è l'insieme degli stati della macchina
>2. $\Sigma$ è l'alfabeto di input(non comprende il simbolo di blank ␣)
>3. $\Gamma$ è l'alfabeto del nastro dove ␣ $\in \Gamma$ e $\Sigma \subseteq \Gamma$
>4. $\delta:Q \times \Gamma \rightarrow Q\times\Gamma\times\{L,R\}$ è la funzione di transizione.
	>>La funzione di transizione $\delta$ è la parte più importante in una macchina di Turing.
	>>Leggendo un input quando si è in un determinato stato ($Q\times\Gamma$), transito in un altro stato cambiando il  simbolo sul registro($Q\times\Gamma$)e poi spostandomi o a sinistra o a destra ($\{L,R\}$)
>5. $q_0$ è lo stato iniziale
>6. $q_{accept}$ è lo stato di accettazione
>7. $q_{reject}$ è lo stato di rifiuto



