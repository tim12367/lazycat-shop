CREATE SCHEMA IF NOT EXISTS shop;
CREATE TABLE IF NOT EXISTS shop.users(
	user_id bigint not null primary key, 
	username varchar(50) not null UNIQUE,
	password varchar(500) not null,
	enabled boolean not null);
CREATE TABLE IF NOT EXISTS shop.authorities (
	user_id bigint not null primary key, 
	authority varchar(50) not null,
	UNIQUE (user_id,authority),
	constraint fk_authorities_users foreign key(user_id) references shop.users(user_id));