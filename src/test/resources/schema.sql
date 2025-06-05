CREATE SCHEMA IF NOT EXISTS shop; 

CREATE TABLE IF NOT EXISTS shop.users(
	user_id bigint not null primary key, --uid
	username varchar(50) not null UNIQUE, --email
	password varchar(500) not null, --password
	enabled boolean not null); --啟用狀態

CREATE TABLE IF NOT EXISTS shop.authorities (
	user_id bigint not null, --uid
	authority varchar(50) not null, --角色
	PRIMARY KEY (user_id,authority), --同使用者，角色不重複
	constraint fk_authorities_users foreign key(user_id) references shop.users(user_id)); --uid 關連到 users.uid