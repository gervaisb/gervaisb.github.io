
# Applications du _Domain Driven Design_ ?

> Le _Domain Driven Design_ est une technique de développement qui tente d'appliquer les concepts et les règles métiers pour modéliser et développer un logiciel.
>
> C'est un concept très intéressant qui regorge de littérature. Mais il peut être complexe à appliquer. Il existe pourtant plusieurs idées qui sont applicables dans nos développements quotidiens.

Au cours de la mise en place d'un "panier d'achat" sans aucune prétention, nous allons voir comment un système simple peut bénéficier des idées du _DDD_ pour rendre un code plus clair et stable.

## Le "panier d'achat"

Loin de vouloir représenter toutes les contraintes métiers, l'exemple se base sur une implémentation triviale d'un panier avec les fonctionnalités suivantes :

    + Ajout d'un ou plusieurs articles
    + Consultation des articles
    + Consultation du solde

Ces trois fonctionnalités sont validées par quelques tests. Les sources sont disponibles sur https://github.com/gervaisb/jubilant-journey avec une branche pour chaque itération.

## Itération 1; du code procédural

La première itération se base sur un `ShoppingCartService` qui accumule des `Item`s et calcule le solde. Alors que `Item` est un _DTO_ totalement anémique, la logique se retrouve dans le service. Sur réception d'une dénomination, prix et quantité, le service va créer et stocker un `Item` dans une liste.

L'ajout doit ajouter un article ou augmenter sa quantité s'il est déjà présent. Cela se fait en parcourant la liste afin d'augmenter la quantité d'un article trouvé ou d'un simple ajout dans le cas inverse.

    for (Item item : items) {
        if ( article.equals(item.getArticle()) ) {
            item.setQuantity(item.getQuantity()+quantity);
            found = true;
        }
    }
    if ( !found ) {
        items.add(new Item(article, price, quantity));
    }

Le calcul du total se fait également en parcourant la liste des articles et additionnant le prix total de chaque entrée.

    BigDecimal total = BigDecimal.ZERO;
    for (Item item : items) {
        BigDecimal unitPrice = item.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
        total = total.add(unitPrice.multiply(quantity));
    }    


**Les tests sont aux verts, notre première itération est un succès**

## Itération 2; ValueObject
Pour ajouter un article, il faut connaitre son nom ou sa référence ou son identifiant interne. Ce paramètre  String article` ne signifie pas grand-chose. Les deux paramètres suivants ont des noms parlants, mais ont aussi des défauts. Le prix est un `BigDecimal` mais il peut être négatif, tout comme la quantité; rien ne nous empêche d'ajouter une quantité négative d'articles.

Avec le _DDD_ il y à une notion de _ValueObject_. Un _ValueObject_ est un objet qui possède une ou plusieurs valeurs soumises à des contraintes. L'utilisation de ce modèle nous assure que ces contraintes soient respectées partout dans le programme. Il ne faut plus les valider une fois qu'ils ont été créés.

Un autre avantage est que le rôle de chaque paramètre est clair, sans même connaitre le nom d'un paramètre on sait ce qu'il représente.

Souvent ces objets sont immuables, il n'est pas possible de les modifier. Il faut en créer une nouvelle instance avec les nouvelles valeurs. C'est parfois un peu plus pénible à gérer, mais plus sûr dans un contexte concurrentiel.

Un inconvénient est que ces objets sont souvent utilisés **comme** des types primitifs, il faut donc prendre soin d'implémenter `equals` et `hashCode`. Mais est-ce vraiment un inconvénient ? D'autant que ces deux méthodes se basent la plupart du temps sur toutes les valeurs de l'objet, elles sont donc faciles à générer depuis un IDE.

Peut-être que la méthode d'ajout est plus claire. Mais le code de notre service n'a pas beaucoup évolué[1].

    private Map<ArticleId, Item> items = new HashMap<>();

    public void add(ArticleId articleId, Price price, Quantity quantity) {
        if ( items.containsKey(articleId) ) {
            items.computeIfPresent(articleId, (a, item) -> {
                item.setQuantity(new Quantity(item.getQuantity().getValue()+quantity.getValue()));
                return item;
            });
        } else {
            items.put(articleId, new Item(articleId, price, quantity));
        }
    }

De plus, la création des _ValueObject_ est très verbeuse et alourdi le code. Heureusement il y à une solution élégante pour remédier à ce problème.

**[1]:** _Le code du service n'a pas beaucoup évolué, mais nous n'avons jamais implémenté la validation. Si celà avait été le cas, il est possible que toute cette partie aie été déplacée du service vers un autre composant responsable de créer nos ValueObject à partir des entrées._

## Itération 3; immuable et "factory methods"
Les _ValueObject_ sont souvent immuables. Pour "changer" une valeur, il faut en créer un nouveau. Cela nous permet de définir un mini langage spécifique à chaque _ValueObject_ pour en créer de nouveaux de manière compréhensible.

Le type 'Price' par exemple se voit ajouter deux méthodes :

    public Price multiply(Quantity quantity) {
        BigDecimal multiplicand = BigDecimal.valueOf(quantity.value);
        return new Price(this.amount.multiply(multiplicand), this.unit);
    }

    public Price plus(Price other) {
        if ( this.unit.equals(other.unit) ) {
            BigDecimal sum = this.amount.add(other.amount);
            return new Price(sum, this.unit);
        } else {
            throw new IllegalArgumentException("Cannot add prices of different units");
        }
    }

Il est également agrémenté de plusieurs _factory methods_ qui rendent sa création plus concise :

    public static Price euros(int amount) {
        return euros(BigDecimal.valueOf(amount));
    }

    public static Price euros(BigDecimal amount) {
        return of(amount, Currency.getInstance("EUR"));
    }

    public static Price of(BigDecimal amount, Currency unit) {
        return new Price(amount, unit);
    }

Grâce à ces méthodes statiques, il est possible de bloquer la construction d'un type `Price` et de forcer l'utilisation de ces méthodes statiques de construction. Le type `Quantity` se voit aussi agrémenter d'une méthode statique `of(int value)`, d'un constructeur privé et d'un _fluent builder_[2] `plus` qui additionne deux quantités.

    class Quantity implements Comparable<Quantity> {

        public static Quantity of(int value) {
            return new Quantity(value);
        }

        private Quantity(int value) {
            if ( value<0 ) {
                throw new IllegalArgumentException("Quantity must be bigger or equal to 0");
            }
            this.value = value;
        }


        final int value;

        public Quantity plus(Quantity other) {
            return new Quantity(this.value + other.value);
        }
    }

`ArticleId` aussi reçoit un constructeur privé et deux méthodes statiques. L'une reçoit un `String` et le valide avant de créer une instance correspondant. L'autre génère un nouvel identifiant.

Les _factory methods_ sont utiles pour l'encapsulation et donc pour renforcer les invariants. Mais elles ne sont pas standardisées. Certains frameworks utilisent `valueOf` ou `fromString` pour tenter de créer un type depuis un `String` mais ce n'est pas une généralité. Si vous optez pour ce modèle il est donc préférable de choisir en équipe les conventions pour ces méthodes de création.

Vous noterez que les valeurs de nos _ValueObject_ sont passées de la visibilité `private` à `package-private`. Cette technique est intéressante car elle préserve l'encapsulation tout en permettant à d'autres objets du même package d'y accéder en lecture seule. C'est d'ailleurs ce que fait `Price` lorsqu'il est multiplié par une quantité : `BigDecimal.valueOf(quantity.value);`

Nos tests ont été modifiés pour traiter avec notre nouveau vocabulaire mais la logique est restée inchangée et ils sont toujours au vert.

Grâce à ces nouveaux types et à la syntaxe des lambdas nous pouvons simplifier la méthode de calcul du cout total depuis notre `ShoppingCartService` :
    public Price getTotal() {
        return items.values().stream()
                .map(i -> i.getUnitPrice().multiply(i.getQuantity()))
                .reduce(Price.euros(0), Price::plus);
    }

Nous pouvons remarquer que la seconde ligne de cette méthode fait deux accès sur la même instance pour obtenir un résultat. Ceci est bon signal pour introduire la méthode `getSubTotal` qui va se charger de multiplier le prix unitaire par la quantité, sans nécessairement exposer ces deux propriétés. On retrouve le même genre de problème dans l'ajout d'un article. Ici la modification de la quantité peut-être gérée par le `CartItem` lui-même.

    public class  CartItem {
        // ...

        public void add(Quantity more) {
            this.quantity = this.quantity.plus(more);
        }

        public Price getSubTotal() {
            return unitPrice.multiply(quantity);
        }
    }  

**[2]:** _Le modèle de conception "builder" est un pattern de construction qui nécessite la collaboration de deux classes. Son nom est malheureusement souvent détourné lorsque des méthodes peuvent être chaînées pour construire une instance._

## Itération 4; aggregats

Une fois de plus, nous avons déplacé la logique de calcul dans une classe. Cependant `CartItem` n'est pas un _ValueObject_, nos nouvelles méthodes modifient d'ailleurs l'état de notre classe au lieu d'en créer une nouvelle instance.

Il arrive souvent que l'on veuille appliquer des invariants sur une série d'éléments. Dans le vocabulaire du _Domain Driven Design_, on parle d'_Aggregate_ et d'_AggregateRoot_. Je vous laisse à la littérature pour la différence entre les deux et utiliserai le terme _Aggregate_ dans le reste de l'article.

Dans notre exemple nous ne voulons pas qu'il y ai deux articles identiques dans le panier. Ces invariants ne sont applicables qu'au travers d'une classe que possède tous les articles. Cette classe est actuellement notre service qui, naïvement, ne représente qu'un panier unique pour tous les clients. Renommons le en `ShoppingCart` pour plus de clarté (nous pourrons réintroduire le service par après).

    public class ShoppingCart {
        private final Map<ArticleId, Entry> entries = new HashMap<>();
        private final ClientId clientId;

        public ShoppingCart(ClientId clientId, Collection<Entry> entries) {
            this.clientId = clientId;
            entries.forEach(entry -> {
                this.entries.put(entry.articleId, entry);
            });
        }

        public void add(ArticleId articleId, Price price, Quantity quantity) {
            if ( entries.containsKey(articleId) ) {
                entries.computeIfPresent(articleId, (a, item) -> item.add(quantity));
            } else {
                entries.put(articleId, new Entry(articleId, price, quantity));
            }
        }

        public Price getTotal() {
            return entries.values().stream()
                    .map(Entry::getSubTotal)
                    .reduce(Price.euros(0), Price::plus);
        }

        public List<Entry> getEntries() {
            return Collections.unmodifiableList(new ArrayList<>(entries.values()));
        }
    }

Rien n'a vraiment changé par rapport à notre service initial. Seul l'ajout d'un constructeur qui attends l'identifiant d'un client et une collection de `CartItem`. Avec ces simples changements nous avons un panier lié à un client et qui peut-être sauvegardé. Nous avons également transformé notre `CartItem` en un _ValueObject_ avec pour seule modification possible la méthode `add(Quantity)` qui retourne un nouvel élement augmenté de la quantité donnée.

## A propos des tests
Le _DDD_, comme de nombreuses pratiques de développement, prône l'utilisation des tests et fonctionne relativement bien avec le _TDD_. Notre test était d'ailleursécrit avant l'implémentation de la première itération et il à peu évolué.

On constatera qu'un seul test avec trois méthodes couvre 100% de classes et 87% des méthodes. C'est évidemment la force du _TDD_ car seul le code nécessaire est écrit.

## A propos de la structure

Au cours des itérations, nos classes ont été déplacées dans un package différent. La visibilité de certaines méthodes et constructeurs à été réduite a `package-private` et même `private`. La plupart de nos accesseurs ont été supprimés et, lorsque nécessaire, remplacés par un accès direct sur un champ final avec une visibilité restreinte.

Le _DDD_ introduit le concept de _BoundedContext_ qui vise à regrouper entre elles les classes liées à un même concept métier. Le packaging par fonctionnalité _package by feature_ est aussi une technique qui colle bien à cette méthode de regroupement des classes.

L'architecture hexagonale ou "en oignon" s'adapte très bien à un projet _DDD_, elle isole le métier au centre en extrayant les interactions avec le monde extérieur dans les couches périphériques.