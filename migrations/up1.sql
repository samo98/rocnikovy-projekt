CREATE TABLE schools (
  id serial primary key,
  name text,
  acronym varchar(10),
  address text,
  webpage_url text
);

CREATE TABLE users (
  id serial primary key,
  admin boolean DEFAULT FALSE,
  name text,
  password text,
  createdAt bigint
);

CREATE TABLE session_tokens (
  token integer primary key,
  userId integer REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  expiresAt bigint,
  createdAt bigint
);
