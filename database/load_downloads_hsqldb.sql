--load downloads and needed users for them--
INSERT INTO site_user (user_id, firstname, lastname, email_address)
VALUES (1, 'John', 'Smith', 'jsmith@gmail.com');
INSERT INTO site_user   (user_id, firstname, lastname, email_address)
VALUES (2, 'Andrea', 'Steelman', 'andi@murach.com');
INSERT INTO site_user (user_id, firstname, lastname, email_address)
VALUES (3, 'Joel', 'Murach', 'joelmurach@yahoo.com');
-- using non-standard timestamp literal syntax for HSQLDB:
INSERT INTO download (download_id, user_id, download_date,  track_id) VALUES 
  (1, 1, '2007-05-01 00:00:00', 8);
INSERT INTO download (download_id, user_id, download_date, track_id) VALUES 
  (2, 1, '2009-08-30 08:30:00', 7);
INSERT INTO download (download_id, user_id, download_date, track_id) VALUES 
  (3, 2, '2009-08-30 09:34:00', 7);
INSERT INTO download (download_id, user_id, download_date, track_id) VALUES 
  (4, 3, '2009-08-31 12:01:00', 8);
 
