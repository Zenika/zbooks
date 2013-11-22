CREATE TABLE IF NOT EXISTS `zBooks`
(
id int AUTO_INCREMENT,
ISBN VARCHAR(100),
title VARCHAR(150),
author VARCHAR(150),
edition VARCHAR(150),
pagesNumber int(5),
releaseDate date,
language char(2)
);

INSERT INTO zBooks (ISBN,title,author,edition,pagesNumber,releaseDate,language) VALUES ('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',10,DATE '2000-10-13','EN');
INSERT INTO zBooks (ISBN,title,author,edition,pagesNumber,releaseDate,language) VALUES ('0596518846X','SQL in a Nutshell','auteur2','O''Reilly',11,DATE '2001-10-13','EN');
INSERT INTO zBooks (ISBN,title,author,edition,pagesNumber,releaseDate,language) VALUES ('2744025828X','Javascript - Les bons éléments','auteur3','Pearson',12,DATE '2002-10-13','FR');
