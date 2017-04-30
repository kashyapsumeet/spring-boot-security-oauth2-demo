CREATE TABLE USER (
  id         BIGINT auto_increment,
  email      VARCHAR(100),
  name       VARCHAR(100),
  github     VARCHAR(30),
  twitter    VARCHAR(30),
  avatar_url VARCHAR(200),
  bio        TEXT,
  PRIMARY KEY (id)
);
