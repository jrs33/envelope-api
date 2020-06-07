CREATE TABLE `envelopes` (
  `id` SERIAL,
  `userId` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `envelopeType` varchar(50) NOT NULL,
  `allocatedMoney` double NOT NULL,
  `spentMoney` double NOT NULL,
  UNIQUE KEY(`userId`, `name`)
);