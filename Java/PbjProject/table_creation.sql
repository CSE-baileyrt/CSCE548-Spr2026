CREATE TABLE Bread (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    WheatLevel INT,
    Price DECIMAL(6,2)
);

CREATE TABLE Peanut_Butter (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    IsCrunchy BOOLEAN,
    Price DECIMAL(6,2)
);

CREATE TABLE Jelly (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    Flavor VARCHAR(100),
    Price DECIMAL(6,2)
);

CREATE TABLE Pbj_Sandwich (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Customer VARCHAR(100),

    Bread1_ID INT,
    Pb_ID INT,
    Jelly_ID INT,
    Bread2_ID INT,

    TotalCost DECIMAL(6,2),

    FOREIGN KEY (Bread1ID) REFERENCES Bread(ID),
    FOREIGN KEY (Bread2ID) REFERENCES Bread(ID),
    FOREIGN KEY (PbID) REFERENCES Peanut_Butter(ID),
    FOREIGN KEY (JellyID) REFERENCES Jelly(ID)
);
