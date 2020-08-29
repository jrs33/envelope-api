CREATE TABLE transactions2 (
  id SERIAL8,
  year bigint NOT NULL,
  month bigint NOT NULL,
  day bigint NOT NULL,
  userId varchar(50) NOT NULL,
  envelopeId bigint NOT NULL,
  amount float8 NOT NULL,
  transactionName varchar(50) NOT NULL,
  transactionStrategy varchar(50) NOT NULL,
  sourceId bigint NOT NULL
);