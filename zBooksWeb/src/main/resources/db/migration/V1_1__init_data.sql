INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',0,10,DATE '2000-10-13','EN');
INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language,cover) VALUES ('0596518846X','SQL in a Nutshell','auteur2','O''Reilly',1,11,DATE '2001-10-13','EN','http://media.wiley.com/product_data/coverImage300/4X/04709422/047094224X.jpg');
INSERT INTO zBooks (ISBN,title,author,edition,zCollection,pagesNumber,releaseDate,language) VALUES ('2744025828X','Javascript - Les bons éléments','auteur3','Pearson',2,12,DATE '2002-10-13','FR');

-- The password is pwd hashed in SHA-256
INSERT INTO zUser (userName,email, password, zPower) VALUES ('Root','root@zenika.com', 'a1159e9df3670d549d04524532629f5477ceb7deec9b45e47e8c009506ecb2c8', 0);
-- The password is test hashed in SHA-256
INSERT INTO zUser (userName,email, password, zPower) VALUES ('User','user@zenika.com', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1);