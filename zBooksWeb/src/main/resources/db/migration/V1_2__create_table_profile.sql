CREATE TABLE zUserProfile (
  profile_id int AUTO_INCREMENT,
  interests VARCHAR(100),
  idUser int
);

CREATE TABLE zUserHistory (
  history_id int AUTO_INCREMENT,
  action VARCHAR(50),
  message VARCHAR(255),
  idProfile int
);