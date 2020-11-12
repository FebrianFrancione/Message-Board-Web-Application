CREATE TABLE files (
  fileID int(11) NOT NULL,
  file longblob,
  date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  fileName varchar(45) DEFAULT NULL,
  fileSize int(11) DEFAULT NULL,
  fileType varchar(45) DEFAULT NULL,
  PRIMARY KEY (fileID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;