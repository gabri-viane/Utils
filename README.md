# Libreria Utils
Aggiunge diverse classi di utilità base.
Sono presenti 4 *main-packages* (funzionalità distinte) nella libreria:
1. Gestione dinamica della **console**;
2. Implementazione dei **grafi** (Alberi e grafi generici);
3. Dichiarazione di due **registri** (per ID e UUID);
4. Gestione generalizzata di file **XML**.

Verranno mostrate ogni sezione singolarmente per spiegarne l'utilizzo.

------------
## Generalità

Tutte le classi e tipi che **finiscono** con la parola `Engine` permettono di eseguire tramite *reflection* in modo totalmente dinamico e avanzato la gestione dei dati, vengono chiamati per semplicità "motori di ...".
A loro volta tutte le classi e tipi che **iniziano** con `Engine` sono elementi utilizzati dai motori per gestire tramite *reflection* i dati.

------------

### 1. Console
Il pacchetto `ttt.utils.console` contiene tutte le classi che permettono una gestione avanzata di input e output di oggetti e variabile tramite console.

Pacchetti:<br>
1.1 `input`: contiene tutte le classi e tipi necessari per la gestione avanzata di input.<br>
1.2 `output`:  contiene tutte le classi e tipi necessari per la formattazione e la gestione dell'output.<br>
1.3 `menu`: contiene due classi e un'interfaccia, si occupa della creazione di menù da stampare in console.<br>

#### 1.1 Input:
Contiene due classi di utilizzo principale: `ConsoleInput` e `ObjectInputEngine`.
##### ConsoleInput
La classe *ConsoleInput* è una classe non instanziabile, che mette a disposizione il metodo statico `ConsoleInput.getInstance()` per ritornare l'unica istanza.
Questa classe mette a disposizione i metodi per leggere dalla console i seguenti tipi:
- Integer
- Long
- Double
- Float
- Boolean
- Character
- String

Per ogni tipo leggibile (tranne per i boolean in cui viene solamente posta una domanda del tipo sì\no) vengono messi a disposizione 3 metodi in overload. Si porta l'esempio per la lettura degli interi:
```java
   /**
     * Metodo 1
     */
    public int readInteger(String question){...}

    /**
     * Metodo 2
     */
    public Optional<Integer> readInteger(String question, boolean skippable, String skippable_keyword) {...}

    /**
     * Metodo 3
     */
    public Optional<Integer> readInteger(String question, boolean skippable, String skippable_keyword, Validator<Integer> v) {...}

```
###### Spiegazione metodi
- Il metodo della *tipologia 1* chiede come parametro **solamente la domanda** da stampare in console per poi chiedere il valore in input.<br>
- Il metodo di *tipologia 2* chiede come parametri sempre la domanda, inoltre se il parametro `skippable = true` allora all'utente è **concesso di uscire dall'immissione** dal dato tramite una parola-chiave passata come stringa nel parametro `skippable_keyword`. Il metodo ritorna un `Optional<Object>` poiché nel caso si permette all'utente di non inserire nessun valore allora l'oggetto restituito sarà `null`.
- Il metodo di *tipologia 3* rispetta tutte le condizioni della *tipologia 2* inoltre permette di validare l'input inserito dall'utente tramite l'interfaccia funzionale `Validator<T>` che mette a disposizione il metodo *validate* che scatena l'eccezione in caso di input invalido:
```java
public void validate(T value) throws IllegalArgumentException;
```
###### Esepio utilizzo
Un'esempio completo di utilizzo può essere fatto utilizzando il metodo di terza tipologia:
```java
ConsoleInput ci = ConsoleInput.getInstance();
Optional<Integer> valore_letto = ci.readInteger("Inserisci valore intero:", true, "esci!", new Validator<Integer>(){
	@Override
	public void validate(Integer value) throws IllegalArgumentException{
		if(value<0){
			throw new IllegalArgumentException("sono ammessi solo valori positivi");
		}
	}
});
```
Con queste istruzioni si chiede all' utente la domanda "Inserisci valore intero:" e gli si permette di ignorare l'inserimento inserendo la parola-chiave "esci!". Inoltre il valore viene validato e risulta corretto solo se maggiore di 0.
#### ObjectInputEngine
La classe *ObjectInputEngine* permette di "estendere" le funzionalità della classe *ConsoleInput* gestendo l'input completo di oggetti tramite console. Per usufruire correttamente di questa classe bisogna implementare correttamente anche le interfacce e annotazioni: `InputObject` (interfaccia), `InputElement` e `Order` (annotazioni).
La classe *ObjectInputEngine* non è istanziabile e mette a disposizione un solo metodo che gestisce l'input di oggetti:
```java
public static <T extends InputObject> T readNewObject(Class<T> object, String nome_elemento)
```
###### Regole di utilizzo
Il funzionamento è relativamente semplice: si passano come parametri la classe dell'oggetto che si vuole chiedere in input tramite la console e il nome dell'oggetto che verrà chiesto all'utente.<br>
L'oggetto che si vuole chiedere in input deve avere una classe che rispetti delle regole fondamentali, altrimenti l'input non verrà eseguito:
1. La classe deve implementare l'interfaccia `ttt.utils.console.input.interfaces.InputObject`;
2. La classe deve mettere a disposizione i metodi o attributi annotandoli con l'annotazione `ttt.utils.console.input.annotations.InputElement` e nel caso si volessero chiedere i valori in modo ordinato bisogna anche annotarli con `ttt.utils.console.input.annotations.Order`;
3. **La classe deve avere un costruttore che non chiede parametri**;
4. I metodi messi a disposizione devono avere un solo parametro che deve essere tra i tipi primitivi (e le loro classi di boxing) oppure classi che implementano *InputObject*.

###### Esempio utilizzo
Segue un esempio di completo di come usare correttamente le classi e metodi:
```java
public class EsempioInput implements InputObject {

    private Esempio2 valore;
    private int valore1;

    public EsempioInput() {
    }

    public Esempio2 getValore() {
        return valore;
    }

	//Dopo aver chiamato il metodo "setValore1" viene chiamato questo metodo
	//Dato che Esempio2 implementa l'interfaccia InputObject a sua volta viene chiamato il metodo di inserimento per quest'oggetto
    @Order(Priority = 1)
    @InputElement(Name = "esempio2", Type = Esempio2.class)
    public void setValore(Esempio2 valore) {
        this.valore = valore;
    }
	//Viene chiamato per primo questo metodo (Priority=0 ha precedenza su tutti quelli con valore maggiore di 0)
    @Order(Priority = 0)
    @InputElement(Name = "ID", Type = Integer.class)
    public void setValore1(Integer valore1) {
        this.valore1 = valore1;
    }
}
```
```java
public class Esempio2 implements InputObject {

    private String valore2;

    public Esempio2() {
    }
//Il valore passato a Name è ciò che viene stampato alla richiesta del valore
//La classe passata a Type è il tipo chi viene richiesto in input all'utente
    @InputElement(Name = "valore 2 in Esempio2", Type = String.class)
    public void setValore2(String valore2) {
        this.valore2 = valore2;
    }
}
```
Definite le due classi e impostati i metodi (e/o le variabili) che richiedono i valori si può fare la chiamata al motore di completamento:
```java
 EsempioInput oggetto_letto = ObjectInputEngine.readNewObject(EsempioInput.class, "crea esempio input");
```
#### 1.2 Output:
Contiene due classi di utilizzo principale: `GeneralFormatter` e `ObjectOutputEngine`.

##### GeneralFormatter
La classe *GeneralFormatter* è una classe non instanziabile, che mette a disposizione 4 metodi statici che aiutano a mantenere una coerenza nella stampa in console.

```java
public static void printOut(String message, boolean newline, boolean error)
```
Questo metodo chiede 3 parametri: il messaggio da stampare in console (*message*), se bisogna tornare a capo (*newline*) ed infine se è un messaggio di errore (*error*). Quando il testo viene stampato vengono inserite prima le indentazioni e poi stampato il testo. Per incrementare o decrementare le indentazioni si usano rispettivamente i metodi *incrementIndents* o *decrementIndents*.<br>
All'inizio dell'esecuzione del programma non vengono stampate indentazioni (non sono ancora state aggiunte) perciò la funzione funziona allo stesso modo di `System.out.print()` se `newline = false`, mentre `System.out.println()` se `System.out.print()`. Allo stesso modo cambiando il parametro *error* si decide se stampare sullo stream di output, perciò se `error = true` viene usato `System.err` altrimenti `System.out`.
Nel caso si volesse stampare sulla stessa riga nella console dopo aver chiamato il metodo *printOut* con il parametro `newline = false` si deve usare il normale metodo `System.out.print()` altrimenti vengono inserite le indentazioni.
###### Esempio utilizzo
Ad esempio:
```java
  printOut("testo1",true,false);
  incrementIndents();
  printOut("testo2",true,false);
  decrementIndents();
  printOut("testo3",true,false);
```
Stamperà:
>testo1<br>
>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;testo2<br>
>testo3

Mentre non tornare a capo potrebbe causare il seguente comportamento non desiderato:
```java
  printOut("testo1",false,false); //notare che non si torna a capo
  incrementIndents();
  printOut("testo2",true,false);
  decrementIndents();
  printOut("testo3",true,false);
```
Stamperà invece:
>testo1
>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;testo2<br>
>testo3

Per evitare questo comportamento bisogna scrivere:
```java
  printOut("testo1",false,false); //notare che non si torna a capo
  incrementIndents();
  System.out.println("testo2");
  decrementIndents();
  printOut("testo3",true,false);
```
Che stamperà correttamente il contenuto voluto su una singola riga.
>testo1testo2<br>
>testo3

```java
public static void incrementIndents()
```
Permette di incrementare le indentazioni stampate in output prima di un testo passato al metodo *GeneralFormatter.printOut(...)*

```java
public static void decrementIndents()
```
Permette di decrementare le indentazioni stampate in output prima di un testo passato al metodo *GeneralFormatter.printOut(...)*

```java
public static String getIndentes()
```
Ritorna la stringa composta da tante indentazioni (`"\t"`) quante ce ne sono correntemente.
</p>

##### ObjectOutputEngine
La classe *ObjectOutputEngine* è una classe non instanziabile, che mette a disposizione 4 metodi statici che permettono di assegnare degli identificatori ai metodi e variabili. La classe deve implementare `ttt.utils.console.output.interfaces.PrintableObject` e le variabili o metodi marcati con l'annotazione `ttt.utils.console.output.annotations.Printable` possono essere utilizzate per rimpiazzare in una stringa il valore associato all'identificatore.

I quattro metodi eseguono sostanzialmente la stessa procedura ma tornano una stringa costruita con criteri differenti:<br>
tutti i metodi funzionano secondo prendendo una stringa da formattare (molto simile al funzionamento di `System.out.printf()`) in cui sono presenti delle parole chiave precedute da **%** (ad esempio: *%player* o qualsiasi altra cosa preceduta da *%*).

###### Esempio classi
Per spiegare in modo semplice il funzionamento segue un esempio completo di utilizzo seguito con la spiegazione dei metodi:

```java
public class Posizione implements PrintableObject {

    private final double x;
    private final double y;

    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Printable(replace = "X")
    public double getX() {
        return x;
    }

    @Printable(replace = "Y")
    public double getY() {
        return y;
    }

    @Printable(replace = "Pos")
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
```

```java
public class OggettoEsempio implements PrintableObject {

    @Printable(replace = "TT1")
    public String nome = "Jakson";

    @Printable(replace = "TT2")
    public char seso = 'M';

}
```

###### Metodi di formattazione
Notare come entrambe le classi implementano *PrintableObject*, senza questo passaggio il motore di completamento scarterebbe l'oggetto a priori.<br>
L'annotazione *Printable* richiede un parametro: `replace` che prende una stringa, il valore assegnatogli sarà la parola chiave che identifica la variabile o metodo. Nel caso diversi elementi con la stessa parola chiave viene seguito l'ordine con cui vengono passati gli oggetti, e se nello stesso oggetto ci sono più elementi con lo stessa viene seguito l'ordine in cui sono state dichiarate nella classe. Le variabili di classe hanno la priorità sui metodi.

Il metodo `ObjectOutputEngine.printExclusive(...)` ha due overload:
```java
public static String printExclusive(String format, Object o)
```

```java
public static String printExclusive(String format, Object... os)
```
Poi sono presenti gli altri due metodi:
```java
public static String print(String format, Object o)
```

```java
public static String printAll(String format, Object... os)
```
Tutti e quattro prendono in input la stringa da formattare e un'oggetto o la lista di oggetti (che devono implementare *PrintableObject*!!) e ritornano la stringa da formattare ma con le parole chiave sostituite.<br>
Il funzionamento tra i 4 metodi è esattamente lo stesso con un'unica eccezione: quelli definiti come *printExclusive* se gli viene passata una stringa che contiene parole chiave non presenti negli oggetti passati come argomento vengono rimosse, mentre quelli non *exclusive* se non trovano una parola chiave la saltano. Segue un'esempio:

Ora immaginiamo di eseguire istanziare in questo modo i due oggetti:
```java
Posizione pos = new Posizione(5.4, 6.7);
OggettoEsempio ogg = new OggettoEsempio();

String da_formattare1 = "La x vale : %X e la y: %Y";
String da_formattare2 = "La posizione di %TT1 è %Pos";
```
Vogliamo ora inserire nelle stringhe al posto delle parole-chiave i valori delle istanze.

```java
String f1 = printExclusive(da_formattare1,pos);
String f2 = print(da_formattare1,pos);
String f3 = printExclusive(da_formattare1,ogg);
String f4 = print(da_formattare1,ogg);

String f5 = printExclusive(da_formattare2,pos);
String f6 = print(da_formattare2,pos);
```
>f1 = "La x vale: 5.4 e la y: 6.7"<br>
>f2 = "La x vale: 5.4 e la y: 6.7"<br>
>f3 = "La x vale:  e la y: "<br>
>f4 = "La x vale: %X e la y: %Y "<br><br>
>f5 = "La posizione di  è (5.4,6.7)"<br>
>f6 = "La posizione di %TT1 è (5.4,6.7)"<br>

Come si può notare se le funzioni trovano i valori associati ad una parola-chiave lo sostituiscono nella stringa, altrimenti si comportano in modo diverso (*printExclusive* rimuove le parole-chiave non associate ad un valore mentre *print* lascia le parole-chiave nella stringa finale).<br>
Allo stesso modo:
```java
String f1 = printExclusive(da_formattare2,pos,ogg);
String f2 = printAll(da_formattare1,pos,ogg);
String f3 = printExclusive(da_formattare1,ogg);
String f4 = printAll(da_formattare1,ogg);
```

>f1 = "La posizione di Jakson è (5.4,6.7)"<br>
>f2 = "La posizione di Jakson è (5.4,6.7)"<br>
>f3 = "La posizione di Jakson è "<br>
>f4 = "La posizione di Jakson è %Pos"<br>

Anche qui le stesse considerazioni fatte per i metodi che prendono un solo oggetto oltre alla stringa come parametro.
**Da notare: se in una stringa ci sono due parole-chiave uguali non verranno sostituite entrambe con il primo valore associato che si trova ma con gli oggetti successivi.**
Esempio:
```java
Posizione pos1 = new Posizione(5.4, 6.7);
Posizione pos1 = new Posizione(0.1, 3.3);

String format = "%Pos %Pos";

String output = printExclusive(format,pos2,pos1);
```
>output = "(0.1,3.3) (5.4,6.7)"

#### 1.3 Menu:
Contiene una classe d'utilizzo principale: `ttt.utils.console.menu.Menu` che permette di avere un menù dinamico in output nella console per interagire con l'utente.

**Questa classe è *abstract* poiché permette di sovrascrivere i propri metodi per cambiare a piacimento l'interazione con  l'utente.**

###### Istanziare nuovo menù
La classe fa uso dei *generics* per poter permettere anche di avere un tipo di ritorno dalla selezione fatta dall'utente. Il menù principale di un'applicazione console dovrebbe essere definito con il tipo `Void` poiché a fine esecuzione teoricamente il programma esce.<br>
Sono disponibili 2 costruttori:
```java
public Menu(String title)
```
```java
public Menu(String title, boolean print_menu_title)
```
Il primo chiama il secondo passando a *print_menu_title* il valore `false`. Il parametro *title* è semplicemente la stringa che viene stampata come titolo del menu, mentre *print_menu_title* se `true` stampa "MENU PRINCIPALE".<br>
Segue un'esempio:
```java
Menu<Void> menu = new Menu<>("Principale"){};
```

###### Rimuovere opzione
Di default tutti i menù hanno come opzione 1 l'opzione "Esci" che non fa altro che chiamare `System.exit(0)`. Per rimuovere un'opzione basta chiamare il metodo `removeOption(int)` passando come parametro l'indice dell'opzione, **ATTENZIONE: questo indice parte da 1 e NON da 0.** Perciò per rimuovere l'opzione "Esci" basta fare:
```java
Menu<Void> menu = new Menu<>("Principale"){};
menu.removeOption(1);
```
Esiste anche `removeOption(Pair<String,FutureAction>)` che permette di rimuovere una coppia precedentemente aggiunta.

###### Trovare indice opzione
Per trovare a che indice è un'opzione basta chiamare il metodo `optionLookup(String)` che richiede l'esatta voce dell'opzione per poterla trovare e ritorna l'indice dell'opzione, **ATTENZIONE: l'indice ritornato parte da 0.** Usando questo metodo per rimuovere l'opzione "Esci" basta fare:
```java
Menu<Void> menu = new Menu<>("Principale"){};
menu.removeOption(menu.optionLookup("Esci") + 1);
```

###### Cambiare voce opzione
Si può inoltre cambiare la voce di un'opzione sapendo l'esatto testo precedente chiamando `changeOption(String,String)` , per esempio per cambiare "Esci" con "Abbandona":
```java
menu.changeOption("Esci","Abbandona");
```

###### Aggiungere opzioni
Per aggiungere un'opzione esistono 4 overload del metodo `addOption`:
 * Il primo prende prende una stringa e un'interfaccia *FutureAction*;
 * Il secondo un istanza di tipo *Pair<String,FutureAction>*;
 * Il terzo prende un intero (l'indice **che parte da 1 e non 0!**), una stringa e un'interfaccia *FutureAction*;
 * Il quarto prende un intero (l'indice **che parte da 1 e non 0!**) e un istanza di tipo *Pair<String,FutureAction>*;

Rispettivamente:
1.  
```java
public void addOption(String option_message, FutureAction<P> action)
```
2.  
```java
public void addOption(Pair<String, FutureAction<P>> option)
```
3.  
```java
public void addOption(int index, String option_message, FutureAction<P> action)
```
4.  
```java
public void addOption(int index,Pair<String, FutureAction<P>> option)
```

La stringa rappresenta la voce con cui verrà mostrata l'opzione in console mentre il codice nel metodo *onSelected* viene eseguito quando l'utente sceglie l'opzione. La *FutureAction&lt;P&gt;* è un'interfaccia generica che in questo caso eredita il *generic* dall'istanza del menù e perciò il metodo *onSelected* deve ritornare un tipo uguale a quello definito per la classe *Menu&lt;P&gt;*.

###### Azioni future (Eseguibili futuri)
Si possono aggiungere inoltre infinite *FutureAction&lt;P&gt;* che verranno eseguite una sola volta dopo che l'utente seleziona un'opzione, dopo essere state eseguite vengono rimosse perciò è necessario aggiungerle nuovamente. Per aggiungere le interfacce si usa il metodo:
```java
public void addLazyExecutable(FutureAction<P> action)
```
###### Reset del menu
Per reimpostare un menù basta chiamare il metodo `reset()`, questa chiamata rimuove tutte le opzioni precedentemente aggiunte, rimuove inoltre tutti gli eseguibili futuri (lazy executables) oltre a reimpostare il menù sullo stato `quit = false` nel caso fosse stato precedentemente segnato come uscito (fine vita ciclo del menù). Come ultima cosa viene aggiunta nuovamente l'opzione "Esci".

###### Eseguire il menù
Esistono 2 tipi di esecuzione:
 1. Ciclico: una volta iniziato esistono due modi per terminarlo: *System.exit()* o chiamare il metodo `quit()`;

 2. Singolo: viene mostrato all'utente e una volta che viene scelta l'opzione viene ritornato il valore di esecuzione del metodo *onSelected* dell'interfaccia associata e poi il menù viene terminato.

Si può scegliere come eseguire il menù semplicemente chiamando per (1) `paintMenu()` mentre per (2) `showAndWait()` che ritorna il tipo definito per il menù corrente.
```java
public void paintMenu();

public P showAndWait();
```
###### Funzionalità aggiuntive
Sono presenti inoltre metodi che permettono di modificare minimamente le impostazioni di stampa di un menù:
1. Questo metodo permette di cambiare il numero di ritorni a capo stampati dopo aver eseguito un'opzione scelta dall'utente (default 3):
```java
public void setDefaultSpaces(int new_value)
```
2. Il seguente metodo permette di scegliere se stampare i ritorni a capo oppure no dopo aver eseguito un'opzione e prima di stampare il menù:
```java
public void autoPrintSpaces(boolean value)
```
3. Questo metodo segna un menù come finito e non permette di continuare il ciclo di stampa:
```java
public void quit()
```

###### Chiarimenti
La classe `Menu<P>` utilizza la classe *GeneralFormatter* e i suoi metodi per eseguire le stampe in console perciò segue le indentazioni impostate e modificate durante e prima della sua stampa.

Si possono creare sotto-menù semplicemente creando una nuova istanza di `Menu<P>` e facendoli partire quando l'utente sceglie un'opzione del menù padre. Segue un breve esempio:
```java
//Creo il menù principale
Menu<Void> principale = new Menu<>("Menù padre"){};
//Aggiungo un'opzione che fa partire un menù secondario
principale.addOption("Menu secondrio",()->{
  GeneralFormatter.incrementIndents();
  //Istanzio un nuovo menù:
  Menu<Integer> sotto_menu = new Menu<>("Menù figlio"){};
  //Rimuovo l'opzione "Esci"
  sotto_menu.removeOption(1);
  //Aggiungo l'opzione per tornare al menù padre
  sotto_menu.addOption("Indietro",()->{
    return null;
  });
  //Aggiungo al menù figlio due opzioni che ritornano un valore;
  sotto_menu.addOption("45",()->{
    return 45;
  });
  sotto_menu.addOption("782",()->{
    return 782;
  });
  //Mostro il menù e aspetto il risultato
  Integer result = sotto_menu.showAndWait();
  //Controllo se ho un risultato, se null allora l'utente ha scelto "Indietro"
  if(result != null){
    GeneralFormatter.printOut("Valore scelto: "+ result,true,false);
  }
  GeneralFormatter.decrementIndents();
  return null;//Ricordarsi di tornare un valore!!!!!
});

//Faccio partire il menù principale in modo ciclico.
principale.paintMenu();
```
### 2. Grafi
(Sezione incompleta)

### 3. Registro
(Nessuna voglia di spiegarlo per ora)

### 4. XML
La gestione di file XML è estremamente automatizzata in questa libreria tanto che basta seguire delle regole generali per riuscire a gestire un qualsiasi tipo di struttura XML.

##### Elementi XML
Tutta la parte di lettura e scrittura si basa su 4 classi principali del package `ttt.utils.xml.document`:
 * XMLElement (implementa tutti e soli i metodi di `ttt.utils.xml.engine.interfaces.IXMLElement`)
 * XMLTag (implementa tutti e soli i metodi di `ttt.utils.xml.engine.interfaces.IXMLTag`)
 * XMLDocument (estende XMLElement)
 * XMLComment **(Opzionale)** (implementa tutti e soli i metodi di `ttt.utils.xml.engine.interfaces.IXMLComment`)

Partendo a spiegare le classi più semplici (le classi,ad eccezione di *XMLDocument* implementano solamente i metodi delle interfacce associate) e di cosa rappresentano:
1. XMLComment: <br>
rappresenta tutti un commento XML definito come:
  ```xml
  <!-- Commento -->
  ```
 * Il costruttore non chiede argomenti.
 * Imposta il testo del commento
    ```java
    public void setValue(String value)
    ```
 * Ritorna il testo contenuto nel commento
    ```java
    public String getValue()
    ```
2. XMLTag: <br>
rappresenta un attributo associato ad un elemento:
  ```xml
  <elemento tag="valore della tag"/>
  ```
Dove "tag" è il nome della Tag e "valore della tag" è il valore testuale associato alla Tag. L'insieme di questi due elementi rappresenta un XMLTag che verrà associata al relativo XMLElement.

 * Il costruttore chiede il nome della tag
    ```java
    public XMLTag(String name)
    ```
 * Imposta il nome della tag
    ```java
    public void setName(String name)
    ```
 * Ritorna il nome della tag
    ```java
    public String getName()
    ```
 * Imposta il valore della tag
    ```java
    public void setValue(String value)
    ```
 * Ritorna il valore della tag
    ```java
    public String getValue()
    ```
3. XMLElement:<br>
rappresenta un elemento con tutte le sue tags, il suo valore e i suoi sotto-elementi:
  ```xml
  <elemento1 tag1="value"> Valore elemento1
    <elemento2></elemento2>
  </elemento1>
  ```
Il seguente codice XML è interpretato come: un *XMLElement* di nome "elemento1" che ha una *XMLTag* (con nome "tag1" e valore "value") che a sua volta ha un *XMLElement* come sotto-elemento. (Ci possono essere infiniti o non essere sotto elementi, stesso vale per le tags e anche per il valore (può esserci ma anche no)).<br>
Per questa classe sono implementati molti metodi ma la struttura di funzionamento base è:
 * XMLElement:<br>
 &nbsp;|-- Nome (uguale al nome dell'elemento nel file)<br>
 &nbsp;|-- Valore (null se non presente) <br>
 &nbsp;|-- Mappa di *IXMLTag* (vuota se non presenti tags)<br>
 &nbsp;|-- Lista di *IXMLElement* (vuota se non presenti sotto elementi)

 Quindi per l'esempio precedente:
 * XMLElement:<br>
 &nbsp;|-- Nome : "elemento1"<br>
 &nbsp;|-- Valore : "Valore elemento1" <br>
 &nbsp;|-- Map&lt;IXMLTag&gt;<br>
 &nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;|-- ["tag1", XMLTag("tag1","value")]<br>
 &nbsp;|-- List&lt;IXMLElement&gt;<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- XMLElement:<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- Nome : "elemento2"<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- Valore : null<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- Map&lt;IXMLTag&gt; : empty<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- List&lt;IXMLElement&gt; : empty<br>

Sperando di aver chiarito come viene costruito un XMLElement ora vengono elencati i metodi:

###### Metodi XMLElement
* Costruttore: esattamente come per XMLTag prende il nome dell'elemento
  ```java
  public XMLElement(String name);
  ```
* Attributi base:
  * nome:
    ```java
    public String getName();
    ```
  * valore:
    ```java
    public void setValue(String value);
    ```
    ```java
    public String getValue();
    ```
* Gestione sotto-elementi:
 * aggiunta:
    ```java
    public void addSubElement(IXMLElement element);
    ```
 * rimozione:
    ```java
    public void removeSubElement(IXMLElement element);
    ```
 * controllo:
    ```java
    public boolean hasSubElements();
    public boolean hasElement(IXMLElement element);
    ```
 * selezione e ricerca:
    ```java
    //Ritorna la lista di tutti i sotto-elementi
    public List<IXMLElement> getElements();
    //Ritorna la prima occorrenza di un elemento.
    public IXMLElement getFirstElement(String name);
    ```
* Gestione tags:
  * aggiunta:
    ```java
    public void addTag(IXMLTag tag);
    ```

  * rimozione:
    ```java
    public void removeTag(IXMLTag tag);
    ```

  * controllo:
    ```java
    public boolean hasTag(String name);
    ```

  * selezione e ricerca:
    ```java
    public List<IXMLTag> getTags();
    public IXMLTag getTag(String name);
    ```
* Gestione commenti:
  * aggiunta:
    ```java
    public void addComment(IXMLComment comment);
    ```
  * selezione:
    ```java
    public List<IXMLComment> getComments();
    ```

4. XMLDocument:<br>
rappresenta il documento intero. Questa libreria considera il documento stesso un elemento perciò questa classe estende ed eredita tutti i metodi e valori di `XMLElement`.<br>
Bisogna tenere presente che un documento XML contiene al massimo 1 elemento che rappresenta la *root* perciò la lista di sotto-elementi ereditata dalla classe padre conterrà solamente un elemento, per l'appunto la radice.</p>
I metodi aggiunti sono:

* Costruttore che chiede il file di output/input (può anche non esistere o essere `null`):
  ```java
  public XMLDocument(File file);
  ```
  Per prendere il file impostato chiamare il metodo:
  ```java
  public File getSourceFile();
  ```

* Ritornare l'elemento *root*:
  ```java
  public IXMLElement getRoot();
  ```

##### Lettura e scrittura XML
Per adempiere a queste due funzioni si utilizzano le due classi del package `ttt.utils.xml.io`:
 * XMLReader
 * XMLWriter

###### Scrittura

###### Lettura
