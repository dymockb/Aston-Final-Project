
CREATE TABLE TypeOfShow (
ID INT PRIMARY KEY,
Name VARCHAR(25) NOT NULL,
LiveMusic TINYINT 
);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (1, 'Theatre', 0);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (2, 'Musical', 0);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (3, 'Musical with live music', 1);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (4, 'Concert', 1);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (5, 'Opera', 0);
INSERT INTO TypeOfShow(ID, Name,LiveMusic) VALUE (6, 'Opera with live music', 1);

CREATE TABLE ShowDetail (
ID INT PRIMARY KEY,
Name VARCHAR(25) NOT NULL,
TYPEID INT,
Description VARCHAR(50), 
Duration INT,
EnglishLanguage TINYINT,
FOREIGN KEY (TYPEID) references TypeOfShow(ID)
);
INSERT INTO ShowDetail(ID, TYPEID, Name, Description, Duration, EnglishLanguage) VALUE(1,1,'Romeo and Juliet','Theatre show',120,1);
INSERT INTO ShowDetail(ID, TYPEID, Name, Description, Duration, EnglishLanguage) VALUE(2,2,'Beauty and the Beast','Musical show',150,1);
INSERT INTO ShowDetail(ID, TYPEID, Name, Description, Duration, EnglishLanguage) VALUE(3,3,'Mamma Mia!','ABBA + Musical show',110,1);
INSERT INTO ShowDetail(ID, TYPEID, Name, Description, Duration, EnglishLanguage) VALUE(4,4,'Andrea Bocelli','Andrea Bocelli + Concert',100,null);

CREATE TABLE Performance (
ID INT PRIMARY KEY AUTO_INCREMENT,
ShowDetailID INT,
DateTime datetime,
price decimal,
FOREIGN KEY (ShowDetailID) references ShowDetail(ID)
);
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(1,'2022-04-14 15:00','50.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(1,'2022-04-14 20:00','60.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(2,'2022-04-16 15:00','35.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(2,'2022-04-16 20:00','45.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(3,'2022-05-14 15:00','40.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(3,'2022-05-14 20:00','50.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(4,'2022-05-20 15:00','60.00');
INSERT INTO Performance(ShowDetailID,DateTime,price) VALUE(4,'2022-05-20 20:00','70.00');

CREATE TABLE DeliveryType(
ID INT PRIMARY KEY,
Name VARCHAR(20),
price decimal,
date date
);
INSERT INTO DeliveryType(ID, Name, price) VALUE(1,'Collect',null);
INSERT INTO DeliveryType(ID, Name, price) VALUE(2,'Post','01.00');

CREATE TABLE Employee(
ID INT PRIMARY KEY,
Name VARCHAR(20),
Admin TINYINT
);
INSERT INTO Employee(ID, Name, Admin) VALUE(1,'Employee1',0);
INSERT INTO Employee(ID, Name, Admin) VALUE(2,'Employee2',1);

CREATE TABLE PaymentType(
ID INT PRIMARY KEY,
Name VARCHAR(20)
);
INSERT INTO PaymentType(ID, Name) VALUE(1,'Credit Card');
INSERT INTO PaymentType(ID, Name) VALUE(2,'Cash');

CREATE TABLE Customer(
ID INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(20),
ADDRESS VARCHAR(20),
CREDITCARD INT,
GENDER VARCHAR(1),
CITY VARCHAR(20), 
DATEOFBIRTH DATE,
JOB VARCHAR(20)
);

INSERT INTO Customer (name, address, creditcard) VALUE('Maya Dee', 'Birmingham',1111111111);
INSERT INTO Customer (name, address, creditcard) VALUE('Camaren David', 'London',1111111111);
INSERT INTO Customer (name, address, creditcard) VALUE('Jakson North', 'London',1111111111);
INSERT INTO Customer (name, address, creditcard) VALUE('Ethan Morrison', 'St Albans',1111111111);

CREATE TABLE Seat(
ID INT PRIMARY KEY ,
Name VARCHAR(20)
);
INSERT INTO Seat(ID, Name) VALUE(1,'Circle1');
INSERT INTO Seat(ID, Name) VALUE(2,'Circle2');
INSERT INTO Seat(ID, Name) VALUE(3,'Stall1');
INSERT INTO Seat(ID, Name) VALUE(4,'Stall2');

CREATE TABLE REZERVATION(
ID INT PRIMARY KEY AUTO_INCREMENT,
CUSTOMERID INT,
PERFORMANCEID INT,
SEATID INT,
PAYMENTID INT,
DELIVERYID INT, 
DATETIME datetime,
PRICE DECIMAL,
FOREIGN KEY (CUSTOMERID) references CUSTOMER(ID),
FOREIGN KEY (PERFORMANCEID) references PERFORMANCE(ID),
FOREIGN KEY (SEATID) references SEAT(ID),
FOREIGN KEY (PAYMENTID) references PAYMENTTYPE(ID),
FOREIGN KEY (DELIVERYID) references DELIVERYTYPE(ID)
);
INSERT INTO REZERVATION(CUSTOMERID,PERFORMANCEID,SEATID,PAYMENTID,DELIVERYID,DATETIME,PRICE) VALUE(1,2,2,1,1,'2022-03-15 12:30',45);


CREATE TABLE PURCHASE (
ID INT PRIMARY KEY AUTO_INCREMENT,
REZERVATIONID INT,
CUSTOMERID INT,
NUMBEROFSEAT INT,
UNITPRICE DECIMAL,
PRICE DECIMAL,
DISCOUNTED DECIMAL,
TOTALPRICE DECIMAL,
FOREIGN KEY (REZERVATIONID) references REZERVATION(ID),
FOREIGN KEY (CUSTOMERID) references CUSTOMER(ID)
);
## INSERT INTO PURCHASE(1,1,2,'45',) VALUE();

select * from customer join performance