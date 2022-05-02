# Peer-Review 1: UML

Folini, Grimaldi, Giandomenico

Gruppo 45

Valutazione del diagramma UML delle classi del gruppo 44.


## Lati positivi

Pur non avendo definito totalmente le componenti Controller e View del pattern MVC, abbiamo apprezzato che siano state fornite le interfacce _GameModifier_ e _ModelObserver_ (con le relative classi) che permettono di avere un'idea ad alto livello di come avverrà l'interazione con il Model.
Inoltre molto comoda l'interfaccia _GameModifier_, che permette ai vari _Character_ di modificare direttamente lo stato del gioco in base ai loro effetti, oltre a gestire le altre azioni di gioco effettuate dai giocatori.
Anche l'interfaccia _Data_ risulta essere comoda per il salvataggio dei dati offline per implementare la F.A. della persistenza.

Ci è sembrata appropriata, per quanto riguarda la gestione dei diversi _Character_, la scelta di utilizzare il design pattern _Strategy_.

Interessante, infine, è stata l'adozione della struttura dati _Union Find_ per l'organizzazione delle isole e della loro unione, in quanto questa struttura permette di ottimizzare l'operazione di fusione.


## Lati negativi

Tra i lati negativi vorremmo evidenziare la scelta di salvare tutte le carte assistente giocate in precedenza dal player anziché solo l'ultima, poiché è l'unica carta di cui dover tenere conto durante ciascun turno secondo il regolamento del gioco.
Inoltre si sarebbero potute implementare le carte assistente come un'enumerazione al posto di una normale classe in quanto è noto a priori la presenza di 10 assistenti distinti con attributi prefissati.

Non ci sembra necessario inserire _IP_ e _Port_ come attributi della classe _Player_ in quanto non sono strettamente legati alla logica di gioco ma sono inerenti alla comunicazione tra client e server.

Non è chiaro come verranno gestite le differenze tra le varianti facile/esperto e tra le modalità di gioco in 2, 3 o 4 giocatori. In particolare, per queste ultime (per ciò che riguarda il numero di nuvole, di torri e di studenti presenti sulle nuvole e nell'ingresso della scuola) sono stati indicati gli attributi di ciascuna classe ma non è specificato chi si occuperà dell'inizializzazione degli stessi in base al numero di giocatori.


## Confronto tra le architetture

Confrontando i due diagrammi UML abbiamo trovato particolarmente efficace l'introduzione dell'interfaccia _GameModifier_ che svolge il ruolo di intermediario tra _Character_ e il tavolo di gioco per le variazioni apportate dagli effetti allo stato di gioco.
Inizialmente avevamo optato per un differente approccio al problema, ma la soluzione individuata dal gruppo 44 ci ha convinti maggiormente in quanto più elegante.