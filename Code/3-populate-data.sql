DELETE FROM account;
DELETE FROM customer;
DELETE FROM employee;
DELETE FROM manager;
DELETE FROM branch;

ALTER TABLE branch AUTO_INCREMENT=1;
ALTER TABLE employee AUTO_INCREMENT=1;
ALTER TABLE customer AUTO_INCREMENT=1;
ALTER TABLE manager AUTO_INCREMENT=1;

-- create branches

INSERT INTO branch (Name, City)
VALUES ('London-branch', 'London'),
('Paris-branch', 'Paris');

-- add managers

INSERT INTO manager (BranchID, FName, LName)
VALUES (1, 'Manager1FN', 'Manager1LN'),
(2, 'Manager2FN', 'Manager2LN');

-- add employees:

INSERT INTO employee (FName, LName, ManagerID)
VALUES ('Emp1FN', 'Emp1LN', 1),
('Emp2FN', 'Emp2LN', 1),
('Emp3FN', 'Emp3LN', 2),
('Emp4FN', 'Emp4LN', 2);

-- create customers

INSERT INTO customer(FName, LName, Street, City, EmployeeAssignedID)
VALUES ('Cust1FN', 'Cust1LN', 'Cust1Street', 'Cust1City', 1),
('Cust2FN', 'Cust2LN', 'Cust2Street', 'Cust2City', 1),
('Cust3FN', 'Cust3LN', 'Cust3Street', 'Cust3City', 2),
('Cust4FN', 'Cust4LN', 'Cust4Street', 'Cust4City', 3),
('Cust5FN', 'Cust5LN', 'Cust5Street', 'Cust5City', 4);

-- add an accounts

INSERT INTO account (AccountNumber, AccountType, BranchID, CustomerID, Balance)
VALUES (100000, 'S', 1, 1, 10),
(100001, 'S', 1, 2, 20),
(100002, 'S', 2, 3, 20),
(100003, 'S', 2, 3, 30),
(100004, 'S', 2, 4, 40);END;