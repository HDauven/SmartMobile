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
	user_id int(11) primary key auto_increment,
   user_name varchar(50) not null,
   user_email varchar(100) not null unique,
   user__password varchar(80) not null,
   _tc timestamp
); 