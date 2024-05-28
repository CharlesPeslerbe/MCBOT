# Chatbot Telegram - API

[![Build With Love](http://forthebadge.com/images/badges/built-with-love.svg)]()


## Description
Mini projet de chatbot Telegram dans le cadre du cours d'interopérabilité. L'objectif du chatbot est d'agir comme un assistant personnel.

Il fonctionne à l'aide de 3 API :
1. La première permet d'envoyer un message sur une conversation Telegram. 
2. La seconde permet d'envoyer des informations météo.
3. La troisième permet de raconter une blague (notée de 0 à 10).

#### Message
Cette API permet d'envoyer des messages sur la conversation Telegram.
 Elle intègre aussi une méthode permettant d'afficher la dernière mise à jour ayant eu lieu sur la conversation.


#### Météo 
Si vous envoyez "météo" au bot, il renverra par défaut la météo du Mans.
 En précisant météo < nom de la ville > /<nombres de jours à afficher>, le bot renverra les informations correspondantes.
 
##### Gestion d'erreurs 
- Si le <nombres de jours à afficher> est supérieur à 5, la réponse n'en affichera que 5.
- Si le < nom de la ville > est inconnue, un message indiquant qu'aucunes prévisions pour ce nom n'a été trouvé.


#### Blague
Si vous envoyez "blague" au bot, il vous renverra une blague qu'il aura lui même noté sur 10.

##### Gestion d'erreurs
- Si le mode entré est différent d'un des modes connu, la blague sera renvoyée en mode global par défaut.
- Si aucun mode n'est défini, le mode global est celui par défaut.


#### Gestion d'erreur globale

Si le message reçu de l'utilisateur est différent de "météo" ou "blague", le chatbot indiquera l'erreur et les différentes opérations qu'il est possible de faire.


## Conçu avec

Les programmes logiciels et outils utilisés pour développer le projet :

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE pour les languages basés sur JAVA
* [Tomcat](https://tomcat.apache.org) - Serveur d'applications
* [Maven](https://maven.apache.org) - Outil de gestion et d'automatisation
* [API Telegram](https://core.telegram.org) - API permettant aux développeurs d'intégrer les fonctionnalités de messagerie de Telegram
  
## Auteurs
Les auteurs du projet sont ici !
* **Charles Peslerbe** _alias_ [@CharlesPeslerbe](https://github.com/CharlesPeslerbe)
* **Makalé Touré** _alias_ [@Makale-Toure](https://github.com/Makale-Toure)

## TO-DO 

Rajouter un gitignore pour les fichiers/dossiers non nécéssaire comme target ou .idea :)
