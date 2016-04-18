/*==============================================================*/
/*	SQL code							                        */
/*	Grapp					  							        */
/*	by Hein Dauven							                    */
/*==============================================================*/

/*==============================================================*/
/*	DROP STATEMENTS										        */
/*==============================================================*/

drop table users;

/*==============================================================*/
/*	CREATE STATEMENTS											*/
/*==============================================================*/
create table users(
   user_pk int(11) primary key auto_increment,
   name varchar(50) not null,
   email varchar(100) not null unique,
   password varchar(80) not null,
   salt varchar(10) not null,
   _tc timestamp
); 