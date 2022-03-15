DROP DATABASE IF EXISTS theatreroyal;
CREATE DATABASE theatreroyal;
USE theatreroyal;

DROP TABLE IF EXISTS shows;
CREATE TABLE shows(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	ShowName VARCHAR(15),
	TypeID INT, -- FOREIGN KEY (TypeID) REFERENCES types(ID),	
	ShowDescription	VARCHAR(50),
	Duration VARCHAR(5), -- eg 03:15 = 3 hrs 15 minutes
	PriceBandID INT,-- FOREIGN KEY (PriceBandID) REFERENCES priceBands(ID),
	LanguageID INT, -- FOREIGN KEY (LanguageID) REFERENCES languages(ID),	
	LiveMusicID INT -- FOREIGN KEY (LiveMusicID) REFERENCES livemusic(ID)
) AUTO_INCREMENT=1;


DROP TABLE IF EXISTS performances;
CREATE TABLE performances(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	ShowID INT,
	PerformanceDateTime DATETIME,
	CONSTRAINT show_id FOREIGN KEY (ShowID) REFERENCES shows(ID)
) AUTO_INCREMENT=1;END;
