# playlist

## Prérequis :

 * Le projet est à réaliser sur la plateforme Android (API minimum 21) : *Le projet a été compilé avec le SDK 33, avec minSDK 21*

 * Gestion du offline : *Les playlist sont persistées via le SDK Room. J'ai aussi géré un cache des requetes HTTP, cela peut sembler en doublon avec la BDD, mais cela n'impact pas les performances. Il s'agissait de démontrer que les deux techniques sont possibles. A noter que le cache HTTP n'est pas utilisé s'il le device possède une connexion internet et que les données ont été rafraichis, il y a plus de 60 secondes. Les images sont stockés en cache par glide.

Vous êtes libre d'utiliser le langage et les librairies que vous voulez, mais vous devez expliquer vos choix

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
