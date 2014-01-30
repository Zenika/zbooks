CREATE TABLE activity (
  activity_id int AUTO_INCREMENT,
  type VARCHAR(100),
  userId int,
  date DATE,
  PRIMARY KEY (activity_id)
);

ALTER TABLE activity
ADD CONSTRAINT activity_user_id FOREIGN KEY (userId)
REFERENCES zUser (user_id);
