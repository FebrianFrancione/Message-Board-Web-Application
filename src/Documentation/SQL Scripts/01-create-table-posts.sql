 CREATE TABLE posts (
  postID int(11) NOT NULL AUTO_INCREMENT,
  userID varchar(45) DEFAULT NULL,
  text varchar(45) DEFAULT NULL,
  attachmentSource longblob,
  date datetime DEFAULT NULL,
  tags varchar(45) DEFAULT NULL,
  lastUpdated timestamp NULL DEFAULT NULL,
  PRIMARY KEY (postID)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
