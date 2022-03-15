DELETE FROM shows;
DELETE FROM performances;

ALTER TABLE shows AUTO_INCREMENT=1;
ALTER TABLE performances AUTO_INCREMENT=1;

-- create show data

INSERT INTO shows (
	ShowName,
	TypeID,	
	ShowDescription,
	Duration,
	PriceBandID,
	LanguageID,
	LiveMusicID
) VALUES 
('Show1',1,'Show1Description','01:30',1, 1, NULL),
('Show2',2,'Show2Description','02:30',2, 1, 1),
('Show3',2,'Show3Description','03:30',2, 2, 3);

-- add performances

INSERT INTO performances (
	ShowID,
	PerformanceDateTime
) VALUES 
(1, '2022-03-01 14:30:00'),
(1, '2022-03-01 20:30:00'),
(2, '2022-03-02 15:00:00'),
(2, '2022-03-02 21:00:00'),
(3, '2022-03-03 19:00:00');END;