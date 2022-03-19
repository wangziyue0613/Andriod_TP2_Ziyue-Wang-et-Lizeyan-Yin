# Android_TP2_Ziyue-Wang-et-Lizeyan-Yin
Ce projet est une application pour les amoureux des chats de compagnie pour interroger les espèces de chats et les informations.

API utilisée : https://docs.thecatapi.com/

Grâce à l'API que nous utilisons, nous avons inclus presque toutes les sortes de chats dans le monde. 
La page d'accueil de l'API est un affichage miniature de divers chats. 
Cliquez sur l'image d'un certain chat et vous pouvez passer à une introduction à l'interface du chat, 
puis cliquez sur Détails pour ouvrir une interface qui affiche des informations étendues sur le chat.

Pour la partie bonus, j'ai utilisé Room pour créer une base de données et ajouté un bouton dans 
le coin supérieur droit de l'interface principale qui peut ouvrir googleMap et peut appeler la caméra, le fichier et d'autres autorisations.

Nous avons fait des commentaires détaillés dans le code pour chaque module fonctionnel.
Ce qui suit est une introduction aux fonctions des classes principales:

MyAdapter - Adaptateur pour parcourir les enregistrements
AbstractAppDataBase -- classe pour créer une base de données
Bitmap BitmapUtils -- classe de traitement
CatDetailMainActivity -- affichage détaillé
CatDetailQWebViewMainActivity webView -- affichage détaillé
CatMainActivity -- affichage de la page d'accueil
MainActivity -- la classe qui affiche la carte
MyLocationResultActivity - données d'affichage des résultats d'affichage de la carte
MyPicImageDetailActivity -- affichage de l'aperçu de la carte
RecordListActivity -- liste d'enregistrements
