/**
   DROP EXISTING TABLE IF THESE EXIST
**/

DROP TABLE Game_player cascade constraints;
DROP TABLE Army cascade constraints;
DROP TABLE  MAP cascade constraints;
DROP TABLE TERRITORY cascade constraints;
DROP TABLE CITY cascade constraints;
DROP TABLE GAME cascade constraints;
DROP TABLE PLAYER cascade constraints;


drop sequence seq_id_player;
drop sequence seq_id_game;
drop sequence seq_id_city;

create sequence seq_id_player increment by 1 start with 1;
create sequence seq_id_game increment by 1 start with 1;
create sequence seq_id_city increment by 1 start with 1;

delete from game;

/**
   TABLE PLAYER CREATION
**/
CREATE TABLE Player(
idPlayer number(4) PRIMARY KEY,
username VARCHAR(60),
password VARCHAR(60)
);


/**
   TABLE GAME CREATION
**/
CREATE TABLE Game(
idGame number(4) PRIMARY KEY,
name VARCHAR(50),
currentPlayer number(4),
turnNumber number(4),
status VARCHAR(50) default 'WAITING',
turnRessources number(4),
fieldRessources number(4),
CONSTRAINT fk_currentPlayer FOREIGN KEY (currentPlayer) REFERENCES Player(idPlayer),
CONSTRAINT ck_status CHECK (UPPER(status) =  UPPER('WAITING') OR UPPER(status) = UPPER('RUNNING') OR UPPER(status) = UPPER('FINISH'))
);

select * from game;

/** 
   TABLE GAME_PLAYER 
**/
CREATE TABLE Game_Player(
idPlayer number(4),
idGame number(4),
resourcesNb number(4),
CONSTRAINT pk_game_player PRIMARY KEY(idPlayer,idGame),
CONSTRAINT fk_idPlayer FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer),
CONSTRAINT fk_idGame FOREIGN KEY (idGame) REFERENCES Game(idGame)
);

/**
    TABLE TOWN 
**/
CREATE TABLE CITY(
idCity number(4) PRIMARY KEY,
townOwner number(4) REFERENCES Player(idPlayer),
name VARCHAR(50)
);


/**
   TABLE TERRITORY
**/
CREATE TABLE Territory(
idTerritory number(4) PRIMARY KEY,
xAxis DECIMAL,
yAxis DECIMAL,
territoryType VARCHAR(20),
idCity number(4) REFERENCES City(idCity),
CONSTRAINT ck_territoryType CHECK (UPPER(territoryType) =  UPPER('MOUNTAIN') OR UPPER(territoryType) = UPPER('PLAIN') OR UPPER(territoryType) = UPPER('FIELD') )

);

/**
    TABLE MAP 
**/
CREATE TABLE MAP(
idGame number(4),
idTerritory number(4),
CONSTRAINT fk_map_territory FOREIGN KEY (idTerritory) REFERENCES Territory(idTerritory),
CONSTRAINT fk_map_game FOREIGN KEY (idGame) REFERENCES Game(idGame),
CONSTRAINT pk_map PRIMARY KEY (idGame,idTerritory)
);

/**
   TABLE ARMY
**/
CREATE TABLE Army(
idPlayer number(4),
idTerritory number(4),
armyNumber number(4),
CONSTRAINT pk_army PRIMARY KEY (idPlayer,idTerritory),
CONSTRAINT fk_player FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer),
CONSTRAINT fk_territory FOREIGN KEY(idTerritory) REFERENCES Territory(idTerritory)
);

insert into Player values(1, 'Margaux', 'saturnin');
insert into Game values(1, 'Partie1', 1, 1, 'RUNNING', 10, 10);
--insert into Game_Player values(1, 1, 20);
insert into Game_Player values(1, 4, 20);

insert into Game values(5, 'Partie 3', 1, 1, 'FINISH', 10, 10);

--idteritoire x y type idcity
insert into Territory values (1,0,1,'PLAIN',null);
insert into Territory values (2,2,1,'PLAIN',null);
insert into Territory values (3,1,1,'MOUNTAIN',null);
insert into Territory values (4,1,2,'PLAIN',null);
insert into Territory values (5,0,3,'FIELD',null);
insert into Territory values (6,0,4,'MOUNTAIN',null);
insert into Territory values (7,2,2,'PLAIN',null);
insert into Territory values (8,5,5,'FIELD',null);
insert into Territory values (9,3,1,'PLAIN',null);

insert into Map values (4,1);
insert into Map values (4,2);
insert into Map values (4,3);
insert into Map values (4,4);
insert into Map values (4,5);
insert into Map values (4,6);
insert into Map values (4,7);
insert into Map values (4,8);
insert into Map values (4,9);

commit;