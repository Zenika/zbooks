Outil de gestion de la bibliothèque Zenika

Itération 1 : FAIT
- [Feature] : Tableau
    Un tableau simple avec ISBN, Titre, Langue
- [Feature] : Fiche
    Une fiche détail (ISBN, titre, langue, editeur, nbPage, auteur, collection, papier et/ou ebook, date de sortie)
- [Tech] Maven, Spring, AngulasJS, MyBatis, H2, REST

Itération 2 : FAIT sauf Emprunts en cours
- [Tech] : OAuth2 - FAIT
- [Tech] : Cloud - FAIT
- [Tech] MySQL - FAIT pour CloudBees
- [Feature] Import ISBN - FAIT
- [Feature] Intégrer les Emprunts - FAIT (il manque le nom de la personne l'ayant emprunté)
- [Feature.Fiche] : détail++ (illustration, année) - FAIT


Backlog : 


- [Feature.Tableau] : Filtre
- [Feature.Tableau] : détail++
- [Feature] Livre du mois (proposition, votes)
- [Tech] : NoSQL (couchbase ou MongoDB)
- [Feature] : notif mail
- [Feature] : feed back (revue de lecture, commentaires)
- [Tech]: RESTfull (HATEOAS, la doc et tout le toutim)
- [Feature] : moteur de recherche (via ElasticSearch)
- [Feature] : Recommandation de livre (machine learning Hadoop, Hive..)
- [Feature.emprunt] : Un bouton "j'attend ce livre STP"
- [Feature.emprunt] : Un bouton "je l'ai lu !"
- [Feature] : Ajout d'un livre simplement en scannant le code barre avec un tel Android.
