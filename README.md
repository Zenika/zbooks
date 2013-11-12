Outil de gestion de bibliothèque

Use case :
- gestion et référencement nos livres, avec toutes les infos
- fonction de livre du mois, propositions et votes

Concept de base : 
- Appli simple de CRUD Spring 3.2
- IHM Angular + service REST
- Base relationnelle PosgreSQL ou MySQL
- Source sur Github, libre à vous du choix de l'organisation (mono master, features branching ou forks)
- Intégration continue et Déploiement locaux (on a des rapberry pour ça :p -> je vous mets ça à dispo pour mardi)
- Niveau Accès base, je pense que Mybatis est un bon compromis
- Récupération des infos sur un service web type http://isbndb.com/ ou amazon

Fonctions + : 
- un peu de sécurité avec du OAuth2 (genre se connecter avec nos comptes Google)
- Passae au Cloud (IC + déploiement sur Cloudbees)
- Du mailing de notif (cron spring simple ou avec du spring batch par exemple)
- des fonctionnalités de vote, de revue de lecture, des commentaires....

Fonctions ++ : 
- Passer à une persistance NoSQL sur CouchBase ou MongoDB pour voir la différence.
- approche RESTfull (avec le HATEOAS, la doc et tout le toutim)
- on peut ajouter un ElasticSearch pour le moteur de recherche
- on peut faire un cas d'usage de machine learning Hadoop, Hive et tout le tralala pour faire de la proposition de bouquins en fonction de préférences de navigation
