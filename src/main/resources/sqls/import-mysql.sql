-- ufuk - 07/05/2016 23:00
CREATE SCHEMA `expercise`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_turkish_ci;

-- batu - 11/02/2015 22:50
INSERT INTO Configuration (name, value) VALUES ('googleAnalytics.applicationKey', 'UA-59509615-1');

-- ufuk - 28/02/2015 14:57
INSERT INTO Configuration (name, value)
VALUES ('userReport.applicationKey.English', 'f1b8f729-6958-4e6f-b4d3-8922c4d93e86');
INSERT INTO Configuration (name, value)
VALUES ('userReport.applicationKey.Turkish', '82947d3b-8714-45f6-aba2-4360a66689dc');

-- ufuk - 08/05/2016 02:49
INSERT INTO Configuration (name, value) VALUES ('defaultChallenge', '1');