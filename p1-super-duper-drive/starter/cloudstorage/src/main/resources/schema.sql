CREATE TABLE IF NOT EXISTS USERS (
  userid INTEGER PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INTEGER PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INTEGER,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INTEGER PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INTEGER,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INTEGER PRIMARY KEY auto_increment,
    userid INTEGER,
    url VARCHAR(100),
    username VARCHAR (30),
    password VARCHAR,
    salt VARCHAR,
    foreign key (userid) references USERS(userid)
);