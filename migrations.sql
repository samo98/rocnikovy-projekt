CREATE TABLE schools (
  id serial primary key,
  name text,
  acronym varchar(10),
  address text,
  webpage_url text
);

DROP TABLE schools;

INSERT INTO schools(name, acronym, address, webpage_url) VALUES (
  'Gymnázium Grösslingová',
  'GAMČA',
  'Gymnázium Grösslingová 18 Bratislava',
  'https://www.gamca.sk'
);
INSERT INTO schools(name, acronym, address, webpage_url) VALUES (
  'Gymnázium Jura Hronca',
  'GJH',
  'Spojená škola Novohradská 3 Bratislava',
  'https://www.gjh.sk/'
);
INSERT INTO schools(name, acronym, address, webpage_url) VALUES (
  'Gymnázium Antona Bernoláka v Námestove',
  'GABNAM',
  'Mieru 307/23, Námestovo',
  'http://www.gabnam.sk/'
);
INSERT INTO schools(name, acronym, address, webpage_url) VALUES (
  'Gymnázium Varšavská cesta',
  'GVARZA',
  'Varšavská cesta 1, Žilina',
  'http://www.gvarza.edu.sk/'
);

CREATE TABLE users (
  id serial primary key,
  name text,
  password text,
  createdAt bigint
);

INSERT INTO users(name, password, createdAt) VALUES (
  'admin',
  'admin',
  '1525093810242'
);

DROP TABLE users;

CREATE TABLE session_tokens (
  token serial primary key,
  userId integer REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  expiresAt bigint,
  createdAt bigint
);

DROP TABLE session_tokens;
