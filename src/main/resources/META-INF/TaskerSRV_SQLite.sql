-- SQLite specific script to create the initial structure for the 
-- locally run database for TaskerCLI.

-- version 1.0, Michal Goly

DROP TABLE IF EXISTS TeamMember;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS TaskElement;

-- Table TeamMember
CREATE TABLE TeamMember (
    email CHAR(80) PRIMARY KEY NOT NULL,
    firstName CHAR(40) NOT NULL,
    lastName CHAR(40) NOT NULL,
    password CHAR(255) NOT NULL
);

-- Table Task
CREATE TABLE Task (
    taskId INTEGER PRIMARY KEY NOT NULL,
    title CHAR(40) NOT NULL,
    startDate INTEGER NOT NULL,
    endDate INTEGER NOT NULL,
    taskStatus INTEGER NOT NULL,
    TeamMember_email CHAR(80) NOT NULL,
    FOREIGN KEY (TeamMember_email) REFERENCES TeamMember (email) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table TaskElement
CREATE TABLE TaskElement (
    taskElementId INTEGER PRIMARY KEY NOT NULL,
    description CHAR(250) NOT NULL,
    comments CHAR(250) NOT NULL,
    Task_taskId INTEGER NOT NULL,
    FOREIGN KEY (Task_taskId) REFERENCES Task (taskID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- insert mock data for development purposes
INSERT INTO TeamMember VALUES 
	('bob@smith.com','Bob','Smith','$2y$10$pSWbF/IkvvXPghFgKoKq..w2y9NF9krJNMBUkcnAHrGYu2doQa5YK'),
	('jane@gmail.com','Jane','Adams','$2y$10$rNWP.zrrmUOefOeJoStu8u2QRkZSxZe1bxUZli5UtFqVZ1TwYrhFO'),
	('m.goly@goly2.com','Michal','Goly','$2y$10$QT3etHPuD9Azt2JGBsLra.4zPTobVf2S17unxeNIUzF6GlZHRad72');

INSERT INTO Task VALUES 
	(1,'Do shopping',1494288000000,1497052800000,1,'m.goly@goly2.com'),
	(2,'Apply to Uni',1336521600000,1740787200000,1,'m.goly@goly2.com'),
	(3,'Buy a new car',1020902400000,1457654400000,1,'bob@smith.com');

INSERT INTO TaskElement VALUES 
	(1,'get out of the house','',1),
	(2,'drive to the store','',1),
	(3,'put items to your basket','',1),
	(4,'pay for the goods','',1),
	(5,'drive back home','',1),
	(6,'get out of your house','',3),
	(7,'go to the car saloon','',3),
	(8,'pick the make of the car','',3),
	(9,'pick the color of the car','',3),
	(10,'test drive','',3),
	(11,'make the purchase','',3),
	(12,'drive back home','',3),
	(13,'go to UCAS website','',2),
	(14,'pick your course','',2),
	(15,'decide where you want to apply','',2),
	(16,'apply','',2),
	(17,'go to the open day','',2),
	(18,'wait for the results','',2),
	(19,'celebrate','',2);

