-- This script creates the default database needed for the application's default usage. This script is for the docker file & used by the docker while setting up the container. Modify if you know what you're doing ok :). I'm dumb
-- Create a database named "LibraryShelf"
CREATE DATABASE LibraryDB;
GO

-- Use the "LibraryShelf" database
USE LibraryShelf;
GO

-- Create a table named "Books"
CREATE TABLE Books (
    BookID INT PRIMARY KEY IDENTITY(1,1),
    BookName NVARCHAR(255),
    AuthorName NVARCHAR(255),
    BookPrice DECIMAL(10, 2)
);
GO
