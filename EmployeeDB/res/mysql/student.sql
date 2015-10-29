use lynche19;
drop table if exists student;
create table student (
  studentId int unsigned not null auto_increment,
  lastName varchar(24) not null,
  firstName varchar(18) not null,
  resHall varchar(24) not null,
  gradeLevel varchar (10) not null,
  department varchar (40) not null,
  primary key(studentId)
);
