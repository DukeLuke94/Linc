DROP SCHEMA IF EXISTS Linc;
CREATE SCHEMA Linc;
USE Linc;

CREATE USER IF NOT EXISTS 'userLinc'@'localhost' IDENTIFIED BY 'userLincPW';
GRANT ALL PRIVILEGES ON linc . * TO 'userLinc'@'localhost';

CREATE TABLE `users` (
userId long,
userName varchar(45),
userPassword varchar (45),
userDOB date,
userEmail varchar (45));

CREATE TABLE `groups`(
groupId long,
groupName varchar(45));

CREATE TABLE `tasks` (
taskId long,
taskName varchar(45),
taskDescription varchar(45),
taskPriority int,
taskDate date,
taskDueDate date);


