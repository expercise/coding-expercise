-- batu - 11/02/2015 22:50
INSERT INTO Configuration (name,value) VALUES ('googleAnalytics.applicationKey', 'UA-59509615-1');

-- ufuk - 28/02/2015 14:57
INSERT INTO Configuration (name,value) VALUES ('userReport.applicationKey.English', 'f1b8f729-6958-4e6f-b4d3-8922c4d93e86');
INSERT INTO Configuration (name,value) VALUES ('userReport.applicationKey.Turkish', '82947d3b-8714-45f6-aba2-4360a66689dc');

-- batu - 29/03/2015 14:40
UPDATE Challenge set ChallengeType = 'ALGORITHM';

-- batu - 12/04/2015 15:40
SET @prev := -1000;
SET @cnt := 1;
UPDATE expercise.TestCase AS atc JOIN
	(SELECT tc.id, IF(@prev <> tc.challenge_id, @cnt := 1, @cnt := @cnt + 1) AS rank,
									@prev := tc.challenge_id
		FROM expercise.TestCase as tc) qu
		ON qu.id = atc.id
SET atc.priority = qu.rank;