CREATE TABLE users (
  id         INT(11) PRIMARY KEY AUTO_INCREMENT,
  username   VARCHAR(64)  NOT NULL,
  password   VARCHAR(256) NOT NULL,
  email      VARCHAR(100) NOT NULL UNIQUE,
  created_at DATETIME,
  salt       VARCHAR(10)  NOT NULL
);

CREATE TABLE comments (
  post_id  INT(11) UNIQUE AUTO_INCREMENT,
  username VARCHAR(64),
  title    VARCHAR(120),
  message  VARCHAR(255)
);

CREATE TABLE groups (
  group_id INT(11) UNIQUE AUTO_INCREMENT,
  group_name VARCHAR(64) NOT NULL,
  group_admin VARCHAR(64) NOT NULL
);

CREATE TABLE modules (
  module_id INT(11) PRIMARY KEY AUTO_INCREMENT,
  module_name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE chats (
  chat_id INT(11) PRIMARY KEY AUTO_INCREMENT,
  chat_name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE message (
  message_id  INT(14) UNIQUE AUTO_INCREMENT,
  username VARCHAR(64),
  message  VARCHAR(255)
);