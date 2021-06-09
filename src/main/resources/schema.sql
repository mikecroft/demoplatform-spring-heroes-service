drop table if exists HERO;

create table HERO(
  ID int not null AUTO_INCREMENT,
  NAME varchar(128) not null,
  POWER varchar(128) not null,
  WEAKNESS varchar(128),
  HP int,
  PRIMARY KEY ( ID )
);