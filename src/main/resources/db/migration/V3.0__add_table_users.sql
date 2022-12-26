create table if not exists users(
   username varchar(64),
   password varchar(64) not null,
   role varchar(32) not null,
   updated timestamp,
   created timestamp,
   status varchar(16) default 'ACTIVE' not null,

   primary key(username)
);