CREATE SCHEMA IF NOT EXISTS SHOP;
CREATE TABLE IF NOT EXISTS SHOP.users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
CREATE TABLE IF NOT EXISTS SHOP.authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on SHOP.authorities (username,authority);