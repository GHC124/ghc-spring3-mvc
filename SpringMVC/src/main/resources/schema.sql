create table contact(
	id int not null auto_increment,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	birth_date date,
	description varchar(2000),
	photo blob,
	version int not null default 0,
	unique UQ_CONTACT_1 (first_name, last_name),
	primary key (id)
);
