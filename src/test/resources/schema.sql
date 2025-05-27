CREATE SCHEMA IF NOT EXISTS SHOP;
CREATE TABLE IF NOT EXISTS SHOP.users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
CREATE TABLE IF NOT EXISTS SHOP.authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references SHOP.users(username));
CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username on SHOP.authorities (username,authority);