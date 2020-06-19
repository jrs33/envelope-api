CREATE TABLE envelopes (
  id SERIAL,
  userId varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  envelopeType varchar(50) NOT NULL,
  allocatedMoney float8 NOT NULL,
  spentMoney float8 NOT NULL,
  PRIMARY KEY(userId, name)
);