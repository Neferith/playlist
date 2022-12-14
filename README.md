# playlist

## Présentation

 * Le projet a été compilé avec le SDK 33, avec minSDK 21

 * Gestion du offline : Les données sont stockées dans une BDD SQLite via le SDK Room. J'ai aussi géré une mise en cache des requêtes HTTP, ce fonctionnement peut paraitre en doublon avec la BDD, mais cela n'impact pas les performances. Il s'agissait de démontrer que les deux techniques sont possibles. L'utilisation d'une base de données pourrait devenir utile, si l’on souhaitait  sauvegarder une grande quantité de données et faire des traitements dessus (Tri, filtre…). A noter que le cache HTTP n'est pas utilisé si le device possède une connexion internet et que les données ont été rafraichies, il y a plus de 60 secondes. Les images sont stockées en cache par Glide.

* Langage utilisé : Kotlin, car c'est aujourd'hui le langage officiel pour développer des applications Android. Mais il serait assez facile de développer une application équivalent en Java.

* Architecture : MVVM qui est une architecture qui permet de bien séparer les données des vues, tout en réduisant les dépendances entre les couches. Les données sont séparés du reste de l'application par une couche domain. Pour une application aussi simple, un tel découpage n'est pas forcément nécessaire, mais il s'agissait de démontrer une architecture la plus modulaire possible.

## Librairies utilisées

* Retrofit : Permet d'implémenter rapidement l'accès à une API distante et la sérialisation des données.

* Coroutine : Simplifie les traitements asynchrones, avant cela, c'était l'enfer sur Android, même si RX Java était pas mal.

* Dagger/hilt : Pour l'injection des dépendances.

* Glide pour les images : Pour l'affichage des images, j'ai du modifier l'user agent, car sinon le serveur de placeholder.com me retourner une 410. J'ai aussi forcé l'affichage en JPG (En ajoutant .JPG au nom du fichier), car le format par défaut me posait le meme souci.

## Test unitaires

J'ai réalisé des tests unitaires sur les trois couches principales Repository, Domain et view model, car ce sont les couches qui structurent l'application. Tout changement important de l'application impacterait nécessairement ces couches, c'est donc un bon moyen d'identifier d'éventuelles régressions dans l'application.

## Evolutions possibles

* Ajout d'une fiche détaillée avec plus d'informations.
* Ne pas charger toutes les données d'un coup. Actuellement, les requetes SQL lite chargent l'ensemble des données. Cela est très rapide. Néanmoins, s'il devait y avoir une plus grande quantité de données, il pourrait être utile de paginer l'ensemble.

