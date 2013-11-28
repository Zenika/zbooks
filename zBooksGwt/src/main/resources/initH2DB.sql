CREATE TABLE IF NOT EXISTS `zBooks`
(ISBN BIGINT, 
title VARCHAR(50),
edition char(50),
pagesNumber int(4),
releaseDate date,
language int,
collection int,
PRIMARY KEY(isbn));

CREATE TABLE IF NOT EXISTS `authors`
(ID int AUTO_INCREMENT,
firstName varchar(50),
lastName varchar(50),
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS `LINK_ZBOOKS_AUTHORS`
(id int AUTO_INCREMENT,
ZBOOK_ISBN BIGINT,
AUTHOR_ID int,
PRIMARY KEY (id));