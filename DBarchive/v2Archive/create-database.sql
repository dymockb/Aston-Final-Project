
-- CREATE A NEW DATABASE:

DROP DATABASE IF EXISTS theatreroyal;
CREATE DATABASE theatreroyal;
USE theatreroyal;

-- END OF CREATE NEW DATABASE

-- CREATE TABLES BELOW

CREATE TABLE ShowLanguage (
ID INT PRIMARY KEY,
LangugeName VARCHAR(25)
);

CREATE TABLE LiveMusic(
ID INT PRIMARY KEY,
Performaner VARCHAR(25)
);

CREATE TABLE PriceBand(
ID INT PRIMARY KEY,
CircleMatinee DECIMAL,
StallsMatinee DECIMAL,
CircleEvening DECIMAL,
StallsEvening DECIMAL
);

CREATE TABLE PaymentType(
ID INT PRIMARY KEY,
PaymentName VARCHAR(15)
);

CREATE TABLE DeliveryType(
ID INT PRIMARY KEY,
DeliveryName VARCHAR(15),
Price DECIMAL,
DeliveryDate DATE
);

CREATE TABLE Gender(
ID INT PRIMARY KEY,
GenderName VARCHAR(25)
);

CREATE TABLE Customer (
ID INT PRIMARY KEY auto_increment,
FirstName VARCHAR(20),
Surname VARCHAR(20),
DOB DATE,
email VARCHAR(40) UNIQUE,
ContactNumber VARCHAR(20),
Address VARCHAR(50),
City VARCHAR(20),
GenderID INT,
Marketing TINYINT,
FOREIGN KEY (GenderID) references Gender(ID)
);

CREATE TABLE Seat(
ID INT PRIMARY KEY,
AreaName VARCHAR(25)
);

CREATE TABLE TypeOfShow (
ID INT PRIMARY KEY,
TypeName VARCHAR(25) NOT NULL
);

CREATE TABLE ShowDetail(
ID INT PRIMARY KEY auto_increment,
TypeOfShowID INT,
ShowName VARCHAR(50) UNIQUE,
ShowDescription VARCHAR(500),
Duration TIME,
LanguageID INT,
StartDate DATE,
EndDate DATE,
LiveMusicID INT,
PriceID INT,
FOREIGN KEY (TypeOfShowID) references TypeOfShow(ID),
FOREIGN KEY (LanguageID) references ShowLanguage(ID),
FOREIGN KEY (LiveMusicID) references LiveMusic(ID)
-- FOREIGN KEY (PriceID) references PriceBand(ID)
);

CREATE TABLE Performance(
ID INT PRIMARY KEY auto_increment,
ShowDetailID INT,
ShowDateTime DATETIME,
FOREIGN KEY (ShowDetailID) references ShowDetail(ID)
);

CREATE TABLE Reservation(
ID INT PRIMARY KEY auto_increment,
CustomerID INT,
PerformanceID INT,
SeatID INT,
PaymentTypeID INT,
DeliveryTypeID INT,
PriceBandID INT, 
ReservationDateTime DATETIME,
CancellationDateTime DATETIME,
FOREIGN KEY (CustomerID) references Customer(ID),
FOREIGN KEY (PerformanceID) references Performance(ID),
FOREIGN KEY (SeatID) references Seat(ID),
FOREIGN KEY (PaymentTypeID) references PaymentType(ID),
FOREIGN KEY (DeliveryTypeID) references DeliveryType(ID),
FOREIGN KEY (PriceBandID) references PriceBand(ID)
);

CREATE TABLE Purchase(
ID INT PRIMARY KEY auto_increment,
ReservationID INT,
CustomerID INT,
NumberOfSeat INT,
UnitPrice DECIMAL,
Price DECIMAL,
Diccounted DECIMAL,
TotalPrice DECIMAL,
FOREIGN KEY (ReservationID) references Reservation(ID),
FOREIGN KEY (CustomerID) references Customer(ID)
);END;
