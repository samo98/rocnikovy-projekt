INSERT INTO schools(name, acronym, address, webpage_url) VALUES (
  'Gymnázium Grösslingová',
  'GAMČA',
  'Gymnázium Grösslingová 18 Bratislava',
  'https://www.gamca.sk'
),(
  'Gymnázium Jura Hronca',
  'GJH',
  'Spojená škola Novohradská 3 Bratislava',
  'https://www.gjh.sk/'
),(
  'Gymnázium Antona Bernoláka v Námestove',
  'GABNAM',
  'Mieru 307/23, Námestovo',
  'http://www.gabnam.sk/'
),(
  'Gymnázium Varšavská cesta',
  'GVARZA',
  'Varšavská cesta 1, Žilina',
  'http://www.gvarza.edu.sk/'
);

INSERT INTO users(admin, name, password, createdAt) VALUES (
  true, 
  'admin',
  'admin',
  '1525093810242'
),(
  false, 
  'user',
  'user',
  '1525094810242'
);
