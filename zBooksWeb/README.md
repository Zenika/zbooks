Pour lancer l'application en local, il faut utiliser le profil Spring dev. Le plugin Tomcat7 de Maven est correctement configuré pour cela. Le profil test est pour les tests et l'absence de profil est pour CloudBees. 
Cela ne fonctionnera pas si vous ne mettez pas de profil Spring en local !

La BDD est en H2 en local, MySQL sur CloudBees.
Le SSL est activé en local mais désactivé sur CloudBees.


Des utilisateurs sont créés de base à l'initialisation (et présents sur CloudBees) :
 - root@zenika.com avec le mdp pwd qui est le seul admin
 - user@zenika.com avec le mdp test

Vous pouvez vous connectez directement avec votre compte Zenika (et votre compte Zenika seulement) en cliquant sur le bouton "Se connecter avec Google".

mvn -Dspring.profiles.active=dev flyway:info -Dflyway.url="jdbc:h2:~/zBooks;AUTO_SERVER=TRUE" -Dflyway.user=sa -Dflyway.schemas=PUBLIC
mvn -Dspring.profiles.active=dev flyway:migrate -Dflyway.url="jdbc:h2:~/zBooks;AUTO_SERVER=TRUE" -Dflyway.user=sa -Dflyway.schemas=PUBLIC
mvn -Dspring.profiles.active=dev flyway:clean -Dflyway.url="jdbc:h2:~/zBooks;AUTO_SERVER=TRUE" -Dflyway.user=sa -Dflyway.schemas=PUBLIC


mvn -Dspring.profiles.active=dev tomcat:run -Dmaven.tomcat.path="/"
http://localhost:8080/