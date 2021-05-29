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

##### 1.1 Input:
Contiene due classi di utilizzo principale: `ConsoleInput` e `ObjectInputEngine`.<br>
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
- Il metodo della *tipologia 1* chiede come parametro **solamente la domanda** da stampare in console per poi chiedere il valore in input.<br>
- Il metodo di *tipologia 2* chiede come parametri sempre la domanda, inoltre se il parametro `skippable = true` allora all'utente è **concesso di uscire dall'immissione** dal dato tramite una parola-chiave passata come stringa nel parametro `skippable_keyword`. Il metodo ritorna un `Optional<Object>` poiché nel caso si permette all'utente di non inserire nessun valore allora l'oggetto restituito sarà `null`.
- Il metodo di *tipologia 3* rispetta tutte le condizioni della *tipologia 2* inoltre permette di validare l'input inserito dall'utente tramite l'interfaccia funzionale `Validator<T>` che mette a disposizione il metodo *validate* che scatena l'eccezione in caso di input invalido:
```java
public void validate(T value) throws IllegalArgumentException;
```
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
Con queste istruzioni si chiede all' utente la domanda "Inserisci valore intero:" e gli si permette di ignorare l'inserimento inserendo la parola-chiave "esci!". Inoltre il valore viene validato e risulta corretto solo se maggiore di 0.<br>
La classe *ObjectInputEngine* permette di "estendere" le funzionalità della classe *ConsoleInput* gestendo l'input completo di oggetti tramite console. Per usufruire correttamente di questa classe bisogna implementare correttamente anche le interfacce e annotazioni: `InputObject` (interfaccia), `InputElement` e `Order` (annotazioni).
La classe *ObjectInputEngine* non è istanziabile e mette a disposizione un solo metodo che gestisce l'input di oggetti:
```java
public static <T extends InputObject> T readNewObject(Class<T> object, String nome_elemento)
```
Il funzionamento è relativamente semplice: si passano come parametri la classe dell'oggetto che si vuole chiedere in input tramite la console e il nome dell'oggetto che verrà chiesto all'utente.<br>
L'oggetto che si vuole chiedere in input deve avere una classe che rispetti delle regole fondamentali, altrimenti l'input non verrà eseguito:
1. La classe deve implementare l'interfaccia `ttt.utils.console.input.interfaces.InputObject`;
2. La classe deve mettere a disposizione i metodi o attributi annotandoli con l'annotazione `ttt.utils.console.input.annotations.InputElement` e nel caso si volessero chiedere i valori in modo ordinato bisogna anche annotarli con `ttt.utils.console.input.annotations.Order`;
3. **La classe deve avere un costruttore che non chiede parametri**;
4. I metodi messi a disposizione devono avere un solo parametro che deve essere tra i tipi primitivi (e le loro classi di boxing) oppure classi che implementano *InputObject*.

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
##### 1.2 Output:
Contiene due classi di utilizzo principale: `GeneralFormatter` e `ObjectOutputEngine`.<br>
La classe *GeneralFormatter* è una classe non instanziabile, che mette a disposizione 4 metodi statici che aiutano a mantenere una coerenza nella stampa in console.
<p>

```java
public static void printOut(String message, boolean newline, boolean error)
```
Questo metodo chiede 3 parametri: il messaggio da stampare in console (*message*), se bisogna tornare a capo (*newline*) ed infine se è un messaggio di errore (*error*). Quando il testo viene stampato vengono inserite prima le indentazioni e poi stampato il testo. Per incrementare o decrementare le indentazioni si usano rispettivamente i metodi *incrementIndents* o *decrementIndents*.<br>
All'inizio dell'esecuzione del programma non vengono stampate indentazioni (non sono ancora state aggiunte) perciò la funzione funziona allo stesso modo di `System.out.print()` se `newline = false`, mentre `System.out.println()` se `System.out.print()`. Allo stesso modo cambiando il parametro *error* si decide se stampare sullo stream di output, perciò se `error = true` viene usato `System.err` altrimenti `System.out`.
Nel caso si volesse stampare sulla stessa riga nella console dopo aver chiamato il metodo *printOut* con il parametro `newline = false` si deve usare il normale metodo `System.out.print()` altrimenti vengono inserite le indentazioni.<br>
<br>Ad esempio:
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

La classe *ObjectOutputEngine* è una classe non instanziabile, che mette a disposizione 4 metodi statici che permettono di assegnare degli identificatori ai metodi e variabili. La classe deve implementare `ttt.utils.console.output.interfaces.PrintableObject` e le variabili o metodi marcati con l'annotazione `ttt.utils.console.output.annotations.Printable` possono essere utilizzate per rimpiazzare in una stringa il valore associato all'identificatore.

I quattro metodi eseguono sostanzialmente la stessa procedura ma tornano una stringa costruita con criteri differenti:<br>
tutti i metodi funzionano secondo prendendo una stringa da formattare (molto simile al funzionamento di `System.out.printf()`) in cui sono presenti delle parole chiave precedute da **%** (ad esempio: *%player* o qualsiasi altra cosa preceduta da *%*).

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

##### 1.3 Menu:
Contiene una classe d'utilizzo principale: `ttt.utils.console.menu.Menu` che permette di avere un menù dinamico in output nella console per interagire con l'utente.

**Questa classe è *abstract* poiché permette di sovrascrivere i propri metodi per cambiare a piacimento l'interazione con  l'utente.**

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
Di default tutti i menù hanno come opzione 1 l'opzione "Esci" che non fa altro che chiamare `System.exit(0)`. Per rimuovere un'opzione basta chiamare il metodo `removeOption(int)` passando come parametro l'indice dell'opzione, **ATTENZIONE: questo indice parte da 1 e NON da 0.** Perciò per rimuovere l'opzione "Esci" basta fare:
```java
Menu<Void> menu = new Menu<>("Principale"){};
menu.removeOption(1);
```
Esiste anche `removeOption(Pair<String,FutureAction>)` che permette di rimuovere una coppia precedentemente aggiunta.

Per trovare a che indice è un'opzione basta chiamare il metodo `optionLookup(String)` che richiede l'esatta voce dell'opzione per poterla trovare e ritorna l'indice dell'opzione, **ATTENZIONE: l'indice ritornato parte da 0.** Usando questo metodo per rimuovere l'opzione "Esci" basta fare:
```java
Menu<Void> menu = new Menu<>("Principale"){};
menu.removeOption(menu.optionLookup("Esci") + 1);
```
Si può inoltre cambiare la voce di un'opzione sapendo l'esatto testo precedente chiamando `changeOption(String,String)` , per esempio per cambiare "Esci" con "Abbandona":
```java
menu.changeOption("Esci","Abbandona");
```
Per aggiungere un'opzione esistono
