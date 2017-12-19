/**
   DROP EXISTING TABLE IF THESE EXIST
**/

DROP TABLE Game_player;
DROP TABLE Army;
DROP TABLE  MAP;
DROP TABLE TERRITORY;
DROP TABLE TOWN;
DROP TABLE GAME;
DROP TABLE PLAYER;


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
status VARCHAR(50) default 'RUNNING',
turnRessources number(4),
fieldRessources number(4),
CONSTRAINT fk_currentPlayer FOREIGN KEY (currentPlayer) REFERENCES Player(idPlayer),
CONSTRAINT ck_status CHECK (UPPER(status) =  UPPER('WAITING') OR UPPER(status) = UPPER('RUNNING'))
);

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
idCity number(4) REFERENCES Town(idCity),
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


