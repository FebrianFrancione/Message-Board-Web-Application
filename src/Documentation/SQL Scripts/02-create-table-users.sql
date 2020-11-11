CREATE TABLE users (
  userID int NOT NULL AUTO_INCREMENT,
  username varchar(45) DEFAULT NULL,
  password varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  PRIMARY KEY (userID),
  UNIQUE KEY userID_UNIQUE (userID),
  UNIQUE KEY username_UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci