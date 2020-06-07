CREATE TABLE `transactions` (
  `id` SERIAL,
  `date` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `envelopeId` bigint unsigned NOT NULL,
  `amount` double NOT NULL,
  `transactionName` varchar(50) NOT NULL,
  `transactionStrategy` varchar(50) NOT NULL,
  KEY userIndex (`userId`)
);
