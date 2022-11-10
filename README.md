# playlist

 * Le projet est à réaliser sur la plateforme Android (API minimum 21) : *Le projet a été compilé avec le SDK 33, avec minSDK 21*

 * Gestion du offline : Les playlist sont persistées dans une BDD SQLite via le SDK Room. J'ai aussi géré un cache des requetes HTTP, cela peut sembler en doublon avec la BDD, mais cela n'impact pas les performances. Il s'agissait de démontrer que les deux techniques sont possibles. L'utilisation d'une base de donnée SQL Lite pourrait devenir necessaire, s'il l'on voulait persister une plus grande quantité de donnés. A noter que le cache HTTP n'est pas utilisé s'il le device possède une connexion internet et que les données ont été rafraichis, il y a plus de 60 secondes. Les images sont stockés en cache par glide.

* Langage utilisé : Kotlin, car c'est aujourd'hui le langage officiel pour développer des applications Android. Mais il serait assez facile de développer une application équivalent en Java.

* Architecture : MVVM qui est une architecture qui permet de bien séparer les données des vues, tout en réduisant les dépendances entre les couches.

* Retrofit : Permet d'implémenter rapidement l'accès à une API distante et la sérialisation des données.

* Coroutine : Simplifie les traitements asynchrones, avant cela, c'était l'enfer sur Android, même si RX Java était pas mal.

* Dagger/hilt : Pour l'injection des dépendances.




Votre code doit être versionné sur un dépôt Git librement consultable

 

Nous observerons particulièrement : 

Les choix d'architecture 

Les choix de patterns

Les choix de librairies

Les performances de l'application

Les tests

Nous rejetterons le test si un des éléments suivants n'est pas présent :

Tests unitaires

La gestion des changements de configuration”
