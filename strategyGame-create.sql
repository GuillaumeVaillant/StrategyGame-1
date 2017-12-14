/**
   DROP EXISTING TABLE IF THESE EXIST
**/

DROP TABLE IF EXISTS Game_player;
DROP TABLE IF EXISTS Army;
DROP TABLE IF EXISTS MAP;
DROP TABLE IF EXISTS TERRITORY;
DROP TABLE IF EXISTS TOWN;
DROP TABLE IF EXISTS GAME;
DROP TABLE IF EXISTS PLAYER;


/**
   TABLE PLAYER CREATION
**/
CREATE TABLE Player(
idPlayer SERIAL PRIMARY KEY,
username VARCHAR(60),
password VARCHAR(60)
);


/**
   TABLE GAME CREATION
**/
CREATE TABLE Game(
idGame SERIAL PRIMARY KEY,
name VARCHAR(50),
currentPlayer INTEGER,
turnNumber INTEGER,
status VARCHAR(50),
turnRessources number(4),
fieldRessources number(4),
CONSTRAINT fk_currentPlayer FOREIGN KEY (currentPlayer) REFERENCES Player(idPlayer),
CONSTRAINT ck_status CHECK (UPPER(status) =  UPPER('WAITING') OR UPPER(status) = UPPER('RUNNING'))
);

/** 
   TABLE GAME_PLAYER 
**/
CREATE TABLE Game_Player(
idPlayer Integer,
idGame Integer,
resourcesNb Integer,
CONSTRAINT pk_game_player PRIMARY KEY(idPlayer,idGame),
CONSTRAINT fk_idPlayer FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer),
CONSTRAINT fk_idGame FOREIGN KEY (idGame) REFERENCES Game(idGame)
);

/**
    TABLE TOWN 
**/
CREATE TABLE TOWN(
idTown SERIAL PRIMARY KEY,
townOwner Integer REFERENCES Player(idPlayer),
name VARCHAR(50)
);


/**
   TABLE TERRITORY
**/
CREATE TABLE Territory(
idTerritory SERIAL PRIMARY KEY,
xAxis DECIMAL,
yAxis DECIMAL,
territoryType VARCHAR(20),
idTown Integer REFERENCES Town(idTown),
CONSTRAINT ck_territoryType CHECK (UPPER(territoryType) =  UPPER('MOUNTAIN') OR UPPER(territoryType) = UPPER('PLAIN') OR UPPER(territoryType) = UPPER('FIELD') )

);

/**
    TABLE MAP 
**/
CREATE TABLE MAP(
idGame Integer,
idTerritory Integer,
CONSTRAINT fk_map_territory FOREIGN KEY (idTerritory) REFERENCES Territory(idTerritory),
CONSTRAINT fk_map_game FOREIGN KEY (idGame) REFERENCES Game(idGame),
CONSTRAINT pk_map PRIMARY KEY (idGame,idTerritory)
);

/**
   TABLE ARMY
**/
CREATE TABLE Army(
idPlayer Integer,
idTerritory Integer,
armyNumber Integer,
CONSTRAINT pk_army PRIMARY KEY (idPlayer,idTerritory),
CONSTRAINT fk_player FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer),
CONSTRAINT fk_territory FOREIGN KEY(idTerritory) REFERENCES Territory(idTerritory)
);


