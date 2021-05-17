IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'Debugged')
DROP DATABASE [Debugged]
GO

USE master
GO

CREATE DATABASE Debugged   
ON PRIMARY(
	NAME= 'Debugged',
    FILENAME = 'C:\Users\marce\Documents\SQL Server Management Studio\Debugged\Debugged.mdf',  
    SIZE = 5MB,   
    FILEGROWTH = 5% )  
LOG ON  
( NAME = 'Debugged_Log',  
    FILENAME = 'C:\Users\marce\Documents\SQL Server Management Studio\Debugged\Debugged_Log.ldf',  
    SIZE = 5MB, 
    FILEGROWTH = 5% ) ;  
GO  

USE Debugged
GO

CREATE TABLE Users(
	id INT IDENTITY,
	username VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	passw VARCHAR(50) NOT NULL,
	securityAnsw VARCHAR(50) NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY(id),
	CONSTRAINT unique_username UNIQUE (username),
	CONSTRAINT unique_email UNIQUE (email)
)
GO

INSERT INTO Users
VALUES	('LisiBeer', 'licia.dpl@gmail.com', 'Licia', 'Du Plessis', 'Licia', 'Gypsey'),
		('PogoSeun', 'pogoseun@gmail.com', 'Marcel', 'Van Der Westhuizen', 'Marcel', 'Tammy'),
		('OuTannie', 'domene@pctech.co.za', 'Domene', 'Van Der Westhuizen', 'Domene', 'Grotes'),
		('TheComputerMan', 'nico@pctech.co.za', 'Nico', 'Van Der Westhuizen', 'Nico', 'Hond')
GO

CREATE TABLE Topics(
	id INT IDENTITY,
	topic VARCHAR(100) NOT NULL,
	descrip TEXT NOT NULL,
	dateCreated DATE NOT NULL,
	topicUser INT NOT NULL,
	CONSTRAINT topics_pk PRIMARY KEY(id),
	CONSTRAINT topic_user_fk FOREIGN KEY (topicUser) REFERENCES Users(id) ON DELETE CASCADE
)
GO

INSERT INTO Topics
VALUES	('It does not want to work!', 'My computer does not want to do anything!', '2018-10-12', 1),
		('My String is not  printing to the console.', 'I am trying to get a String to print from my url, but it does not display in my console when I run my code.', '2018-02-18', 3),
		('I do not know what program to use to create JSP applications', 'I have recently started coding in Java and want to start creating runnable applications. I was wondering what IDEs would work best to do this.', '2019-02-22', 2)
GO

CREATE TABLE Comments(
	id INT IDENTITY,
	descrip TEXT NOT NULL,
	dateCreated DATE NOT NULL,
	topicId INT NOT NULL,
	commentUser INT NOT NULL,
	CONSTRAINT comment_pk PRIMARY KEY(id),
	CONSTRAINT topic_fk FOREIGN KEY (topicId) REFERENCES Topics(id),
	CONSTRAINT comment_user_fk FOREIGN KEY (commentUser) REFERENCES Users(id) ON DELETE CASCADE
)
GO

INSERT INTO Comments
VALUES	('Have you tried putting it on and off again', '2018-10-14', 1, 3),
		('I think you might have to buy a new computer', '2019-01-04', 1, 4),
		('You should use the request.getParameter("parameter_name") method and then print out the methods that it returns', '2019-03-01', 2, 2)
GO

CREATE TABLE Replies(
	id INT IDENTITY,
	descrip TEXT NOT NULL,
	dateCreated DATE NOT NULL,
	commentId INT NOT NULL,
	replyUser INT NOT NULL,
	CONSTRAINT reply_pk PRIMARY KEY(id),
	CONSTRAINT comment_fk FOREIGN KEY (commentId) REFERENCES Comments(id),
	CONSTRAINT reply_user_fk FOREIGN KEY (replyUser) REFERENCES Users(id) ON DELETE CASCADE
)
GO

INSERT INTO Replies
VALUES  ('Thank you vary much that fixed the problem', '2019-01-12', 1, 1),
		('I tried that but it did not want to work. This is a snippet of my code: request.getParameter("name");', '2019-03-01', 3, 3),
		('You method needs domething to put the value into. If you parameter value is a String then you could do something like this: String s = request.getParameter("name"); and then you can use the s variable to print the String', '2019-03-03', 3, 2)
GO

			