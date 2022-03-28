create database Gym;
use Gym;
drop table if exists Users;
drop table if exists Exercises;
drop table if exists Subscription;
drop table if exists Pricing;
drop table if exists Program;
drop table if exists Complex;
create table Users (
UserID int auto_increment primary key,
Login nvarchar(50) not null,
Password char(64) not null,
Role enum('ADMIN', 'COACH', 'CLIENT') not null,
Firstname nvarchar(50) not null,
Lastname nvarchar(50) not null,
Telephone nvarchar(50) not null);

create table Exercises (
ExerciseID int auto_increment primary key,
Name nvarchar(50) not null,
Restrictions nvarchar(255),
CaloriesLost int,
FitnessLevel enum('STARTER', 'CASUAL', 'EXPERT') not null,
Description text not null);

create table Subscription (
SubscriptionID int auto_increment primary key,
ClientID int default '0',
PurchaseDate date not null,
ExpirationDate date not null,
Trial enum('WEEK','MONTH','YEAR', 'ETERNITY'),
Price decimal not null,
IBM int,
IsCoachNeeded tinyint(4) default null,
IsPayed tinyint not null default '0',
Feedback text,
constraint ClientID_fk foreign key (ClientID) references Users
(UserID) on delete set null);

create table Pricing (
SubscriptionType enum('WEEK+COACH', 'WEEK', 'MONTH+COACH',
 'MONTH', 'YEAR','ETERNITY') primary key,
 Price decimal not null
);

create table Program (
ProgramID int auto_increment primary key,
CoachID int not null,
ClientID int,
StartDate date not null,
EndDate date not null,
Diet text not null,
constraint client_fk foreign key (ClientID) references Users (UserID),
constraint coach_fk foreign key (CoachID) references Users (UserID)
);

create table Complex (
ProgramID int not null,
ExerciseId int not null,
Days int not null,
WeightLoss int not null,
Sets int not null default '0',
Repeats int default '1',
primary key (ProgramID, ExerciseID, Days),
constraint complex_exercise_fk foreign key (exerciseID)
references Exercises (ExerciseID),
constraint complex_program_fk foreign key (ProgramID)
references Program (ProgramID)
);



