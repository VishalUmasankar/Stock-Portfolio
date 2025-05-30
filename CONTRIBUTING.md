
use springboot_db;
		
select * from user;
truncate user;
select * from activity;
truncate activity;
update holdings set alert='ON' where id = 1;
select * from holdings;
drop table user_details;
truncate holdings;
drop table user;
drop table user_details;
drop table activity;
drop table holdings;
