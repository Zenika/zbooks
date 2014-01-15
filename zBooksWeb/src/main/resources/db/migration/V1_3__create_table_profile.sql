CREATE TABLE zUserProfile (
  profile_id int AUTO_INCREMENT,
  interests VARCHAR(100),
  idUser int,
  PRIMARY KEY (profile_id)
);

CREATE TABLE zUserHistory (
  history_id int AUTO_INCREMENT,
  action VARCHAR(50),
  message VARCHAR(255),
  idProfile int,
  PRIMARY KEY (history_id)
);

ALTER TABLE zUserProfile
ADD CONSTRAINT zUserProfile_user_id FOREIGN KEY (idUser)
REFERENCES zUser (user_id);

ALTER TABLE zUserHistory
ADD CONSTRAINT zUserHistory FOREIGN KEY (idProfile)
REFERENCES zUserProfile (profile_id);