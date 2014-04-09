CREATE TABLE `userbadges` (
  `seq`      int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  `userseq`  bigint,
  `badge`    nvarchar(20),
  `gameseq`  int,
  `date`     datetime
) ENGINE = MyISAM;
