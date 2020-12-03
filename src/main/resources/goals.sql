CREATE TABLE goals (
  id SERIAL,
  userId varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  goalAmount float8 NOT NULL,
  goalProgress float8 NOT NULL,
  PRIMARY KEY(userId, name)
);