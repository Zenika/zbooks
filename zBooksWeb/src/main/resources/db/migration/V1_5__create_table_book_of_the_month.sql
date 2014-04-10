CREATE TABLE zBookOfTheMonth (
  book_id int AUTO_INCREMENT,
  ISBN VARCHAR(100),
  title VARCHAR(150),
  author VARCHAR(150),
  edition VARCHAR(150),  
  pagesNumber int(5),
  releaseDate VARCHAR(100),
  language char(2),
  cover VARCHAR(400),
  submitter_id int,
  PRIMARY KEY (book_id),  
  FOREIGN KEY (submitter_id) REFERENCES zUser(user_id)
);


CREATE TABLE zBookOfTheMonthVotes (
  book_id int,
  voter_id int,
  PRIMARY KEY (book_id, voter_id),  
  FOREIGN KEY (book_id) REFERENCES zBookOfTheMonth(book_id),
  FOREIGN KEY (voter_id) REFERENCES zUser(user_id)
);

INSERT INTO zBookOfTheMonth (ISBN,title,author,edition,pagesNumber,releaseDate,language, submitter_id) VALUES 
('047094224X','Professional NoSQL','auteur1','John Wiley & Sons Ltd',10,DATE '2000-10-13','EN',1);

INSERT INTO zBookOfTheMonthVotes (book_id, voter_id) VALUES (1,2);