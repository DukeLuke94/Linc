-- noinspection SqlNoDataSourceInspectionForFile

DROP SCHEMA IF EXISTS Linc;
CREATE SCHEMA Linc;
USE Linc;

CREATE USER IF NOT EXISTS 'userLinc'@'localhost' IDENTIFIED BY 'userLincPW';
GRANT ALL PRIVILEGES ON linc . * TO 'userLinc'@'localhost';

CREATE TABLE `users` (
userId long,
userName varchar(45);

CREATE TABLE `circle`(
circleId long,
circleName varchar(45));

CREATE TABLE `tasks` (
taskId long,
taskName varchar(45);


