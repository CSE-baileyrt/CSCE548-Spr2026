USE pbjproject;

INSERT INTO Bread (brand, wheatLevel, price) VALUES
('Wonder', 1, 1.99),
('Nature Valley', 3, 2.49),
('Dave''s Killer Bread', 5, 3.99);


INSERT INTO PeanutButter (brand, isCrunchy, price) VALUES
('Jif', false, 2.99),
('Skippy', true, 3.29),
('Peter Pan', false, 2.79);


INSERT INTO Jelly (brand, flavor, price) VALUES
('Smuckers', 'Grape', 2.49),
('Welch''s', 'Strawberry', 2.59),
('Bonne Maman', 'Raspberry', 3.99);


INSERT INTO PbjSandwich (customer, bread1_id, pb_id, jelly_id, bread2_id, totalCost) VALUES
('Alice', 1, 1, 1, 1, 7.47),
('Bob', 2, 2, 2, 2, 8.37),
('Charlie', 3, 3, 3, 3, 10.77);