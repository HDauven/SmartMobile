CREATE TABLE users (
  id       INT(11) PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64)  NOT NULL UNIQUE,
  password VARCHAR(256) NOT NULL
);

CREATE TABLE comments (
  post_id  INT(11) UNIQUE AUTO_INCREMENT,
  username VARCHAR(64),
  title    VARCHAR(120),
  message  VARCHAR(255)
);