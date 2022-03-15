
-- QUERY 1
-- select all accounts from the bank, showing account numbers, account type, customer names, balance, the employee assigned to the customer and the manager of the branch.

SELECT AccountNumber, AccountType, branch.City, customer.FName, customer.LName, Balance, employee.LName as CustomerRep, manager.LName as BranchManager FROM branch 
JOIN account ON branch.ID = account.BranchID
JOIN customer ON account.CustomerID = customer.ID
JOIN employee ON customer.EmployeeAssignedID = employee.ID
JOIN manager ON employee.managerID = manager.ID;

-- QUERY 2
-- show average balance of accounts in each location

SELECT AVG(Balance), City FROM account
JOIN branch ON account.BranchID = branch.ID
GROUP BY City;

-- show number of customers assigned to each employee

SELECT COUNT(customer.ID), employee.LName as CustomerRep FROM customer
JOIN employee ON customer.EmployeeAssignedID = employee.ID
GROUP BY CustomerRep;END;