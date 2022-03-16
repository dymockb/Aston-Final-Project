DROP DATABASE IF EXISTS theatreroyal;
CREATE DATABASE theatreroyal;
USE theatreroyal;

CREATE TABLE TypeOfShow (
ID INT PRIMARY KEY,
Name VARCHAR(25) NOT NULL,
LiveMusic TINYINT 
);

CREATE TABLE ShowDetail (
ID INT PRIMARY KEY,
Name VARCHAR(25) NOT NULL,
TYPEID INT,
Description VARCHAR(50), 
Duration INT,
EnglishLanguage TINYINT,
FOREIGN KEY (TYPEID) references TypeOfShow(ID)
);

CREATE TABLE Performance (
ID INT PRIMARY KEY AUTO_INCREMENT,
ShowDetailID INT,
DateTime datetime,
price decimal,
FOREIGN KEY (ShowDetailID) references ShowDetail(ID)
);

CREATE TABLE DeliveryType(
ID INT PRIMARY KEY,
Name VARCHAR(20),
price decimal,
date date
);

CREATE TABLE Employee(
ID INT PRIMARY KEY,
Name VARCHAR(20),
Admin TINYINT
);

CREATE TABLE PaymentType(
ID INT PRIMARY KEY,
Name VARCHAR(20)
);

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

CREATE TABLE Seat(
ID INT PRIMARY KEY ,
Name VARCHAR(20)
);

CREATE TABLE REZERVATION(
ID INT PRIMARY KEY AUTO_INCREMENT,
CUSTOMERID INT,
PERFORMANCEID INT,
SEATID INT,
PAYMENTID INT,
DELIVERYID INT, 
DATETIME datetime,
PRICE DECIMAL
-- FOREIGN KEY (CUSTOMERID) references CUSTOMER(ID),
-- FOREIGN KEY (PERFORMANCEID) references PERFORMANCE(ID),
-- FOREIGN KEY (SEATID) references SEAT(ID),
-- FOREIGN KEY (PAYMENTID) references PAYMENTTYPE(ID),
-- FOREIGN KEY (DELIVERYID) references DELIVERYTYPE(ID)
);

CREATE TABLE PURCHASE (
ID INT PRIMARY KEY AUTO_INCREMENT,
REZERVATIONID INT,
CUSTOMERID INT,
NUMBEROFSEAT INT,
UNITPRICE DECIMAL,
PRICE DECIMAL,
DISCOUNTED DECIMAL,
TOTALPRICE DECIMAL
-- FOREIGN KEY (REZERVATIONID) references REZERVATION(ID),
-- FOREIGN KEY (CUSTOMERID) references CUSTOMER(ID)
);END;