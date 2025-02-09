INSERT INTO SHOP.users(username, password, enabled)
VALUES ('test123', '$2a$10$w6R2N5NYd9kNcvWUzsn4aeEmeykpag5vYVtBe6ZQVv18UmpAkFMBC', true);

INSERT INTO SHOP.AUTHORITIES
(USERNAME, AUTHORITY)
VALUES('test123', 'USER');