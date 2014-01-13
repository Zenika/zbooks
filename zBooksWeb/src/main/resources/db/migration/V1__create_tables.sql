CREATE TABLE zBooks (
  book_id int AUTO_INCREMENT,
  ISBN VARCHAR(100),
  title VARCHAR(150),
  author VARCHAR(150),
  edition VARCHAR(150),
  zCollection int(2),
  pagesNumber int(5),
  releaseDate VARCHAR(100),
  language char(2),
  cover VARCHAR(400),
  idBorrower int
);

CREATE TABLE zUser  (
  user_id int AUTO_INCREMENT,
  email VARCHAR(100),
  username VARCHAR(100),
  password VARCHAR(100),
  zPower int(2)
);

CREATE TABLE zBooks_borrowed (
  book_borrowed_id int AUTO_INCREMENT,
  idBook int,
  idBorrower int,
  borrow_date DATE,
  return_date DATE
);