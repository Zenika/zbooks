CREATE TABLE IF NOT EXISTS `zBooks`
(ISBN INT(10), 
title VARCHAR(50),
edition char(50),
pagesNumber int(4),
releaseDate date,
language char(2),
collection char(10));

CREATE TABLE IF NOT EXISTS `authors`
(id int AUTO_INCREMENT,
firstName varchar(50),
lastName varchar(50),
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS `linkAuthorZBooks`
(id int AUTO_INCREMENT,
ISBN int(10),
authorId int,
PRIMARY KEY (id));
