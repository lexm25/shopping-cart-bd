SET CHARACTER SET 'utf8';

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
  username VARCHAR(20) PRIMARY  KEY,
  passwd VARCHAR(10)
);

INSERT INTO Users VALUES ('Sandra','1234');
INSERT INTO Users VALUES ('Juan','1234');
INSERT INTO Users VALUES ('Leonardo','1234');
INSERT INTO Users VALUES ('Pilar','1234');

DROP TABLE IF EXISTS Products;
CREATE TABLE Products(
	productname VARCHAR(20) PRIMARY KEY,
	price DECIMAL(10,2) NOT NULL
);

INSERT INTO Products VALUES ('Monitor Led',349.99);
INSERT INTO Products VALUES ('Ratón Inalámbrico',34.63);
INSERT INTO Products VALUES ('Webcam 4K',90.25);
INSERT INTO Products VALUES ('Fuente Alimentación',50.99);
INSERT INTO Products VALUES ('Lenovo Thinkpad',1210.45);


DROP TABLE IF EXISTS Purchases;
CREATE TABLE Purchases(
	username VARCHAR(20) NOT NULL,
	productname VARCHAR(20) NOT NULL,
	date DATETIME NOT NULL,
	units INTEGER NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY(username,productname,date),
	FOREIGN KEY(username) REFERENCES Users(username) ON DELETE CASCADE, 
	FOREIGN KEY(productname) REFERENCES Products(productname) ON DELETE CASCADE
);

INSERT INTO Purchases VALUES ('Sandra','Monitor Led',CONCAT(CURDATE(),' ','09:00:00'),2,349.99);
INSERT INTO Purchases VALUES ('Juan','Ratón Inalámbrico',CONCAT(CURDATE(),' ','09:00:00'),10,10.5);
INSERT INTO Purchases VALUES ('Leonardo','Fuente Alimentación',CONCAT(CURDATE(),' ','09:00:00'),2,50.99);
INSERT INTO Purchases VALUES ('Pilar','Lenovo Thinkpad',CONCAT(CURDATE(),' ','09:00:00'),2,1210.45);
INSERT INTO Purchases VALUES ('Pilar','Webcam 4K',CONCAT(CURDATE(),' ','09:00:00'),2,36.10);
INSERT INTO Purchases VALUES ('Sandra','Webcam 4K',CONCAT(CURDATE(),' ','09:01:00'),2,90.25);

DROP VIEW IF EXISTS UserRanking;
CREATE VIEW UserRanking AS 
SELECT username, 
SUM(price*units) AS gasto,
(SELECT COUNT(DISTINCT date) FROM Purchases WHERE username=P.username) 
AS NumPedidos
FROM Purchases P
GROUP BY username ORDER BY gasto DESC;

DROP VIEW IF EXISTS UserRankingMedio;
CREATE VIEW UserRankingMedio AS 
SELECT username, 
SUM(price*units) AS gasto,
(SELECT COUNT(DISTINCT date) FROM Purchases WHERE username=P.username) 
AS NumPedidos,
SUM(price*units)/(SELECT NumPedidos) AS 'Gasto Medio' 
FROM Purchases P
GROUP BY username ORDER BY gasto DESC;

