-- 清空tabel
DELETE FROM shop.authorities; 
DELETE FROM shop.users; 

-- 插入測試資料
INSERT INTO shop.users(user_id, username, password, enabled)
VALUES (1, 'test123', '$2a$10$w6R2N5NYd9kNcvWUzsn4aeEmeykpag5vYVtBe6ZQVv18UmpAkFMBC', true);

INSERT INTO shop.authorities(user_id, authority)
VALUES(1, 'USER');