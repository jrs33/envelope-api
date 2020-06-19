CREATE TABLE transactions (
  id SERIAL,
  date varchar(50) NOT NULL,
  userId varchar(50) NOT NULL,
  envelopeId bigint NOT NULL,
  amount float8 NOT NULL,
  transactionName varchar(50) NOT NULL,
  transactionStrategy varchar(50) NOT NULL
);
