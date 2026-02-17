-- Bread
INSERT INTO Bread (Brand, WheatLevel, Price) VALUES
('Wonder', 20, 2.50),
('Nature Own', 80, 3.75),
('Store Brand', 50, 2.10);

-- Peanut Butter
INSERT INTO PeanutButter (Brand, IsCrunchy, Price) VALUES
('Jif', false, 3.20),
('Skippy', true, 3.40);

-- Jelly
INSERT INTO Jelly (Brand, Flavor, Price) VALUES
('Smuckers', 'Grape', 2.80),
('Welchs', 'Strawberry', 3.00);

-- PBJ Sandwich
INSERT INTO PbjSandwich
(Customer, Bread1_ID, Pb_ID, Jelly_ID, Bread2_ID, TotalCost)
VALUES
('Alice', 1, 1, 1, 1, 8.00),
('Bob', 2, 2, 2, 2, 10.15);
