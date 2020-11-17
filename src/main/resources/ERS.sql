drop table if exists ERSuser cascade;
drop table if exists roles cascade;
drop table if exists reimbursment cascade;
drop table if exists ERSstatus cascade;
drop table if exists ERStype cascade;

create table ERSuser(
	userid serial primary key,
	username varchar(50) not null unique,
	erspassword varchar(50) not null,
	firstname VARCHAR(100) NOT null,
	lastname VARCHAR(100) NOT null,
	email VARCHAR(150) not null unique
	--roleid integer not null,
	--FOREIGN KEY (roleid) REFERENCES roles (roleid) on delete cascade
);

create table roles(
	roleid serial primary key,
	ersroles varchar(10) not null,
	userid integer not null,
	FOREIGN KEY (userid) REFERENCES ersuser (userid) on delete cascade
);

CREATE table reimbursment(
	id serial primary key,
	amount numeric(12,2) not null,
	description varchar(250),
	author integer not null,
	submitted timestamp,
	receipt bytea,
	resolver integer,
	resolved timestamp,
	--statusid integer not null,
	--typeid integer not null,
	FOREIGN KEY (author) REFERENCES ERSuser (userid) on delete cascade,
	FOREIGN KEY (resolver) REFERENCES ERSuser (userid) on delete cascade
	--FOREIGN KEY (statusid) REFERENCES ERSstatus (statusid) on delete cascade,
	--FOREIGN KEY (typeid) REFERENCES ERStype (typeid) on delete cascade
);

CREATE table ERSstatus( 
	statusid serial primary key,
	status varchar(10) not null,
	id integer not null,
	FOREIGN KEY (id) REFERENCES reimbursment (id) on delete cascade
);

create table ERStype(
	typeid serial primary key,
	erstype varchar(10) not null,
	id integer not null,
	FOREIGN KEY (id) REFERENCES reimbursment (id) on delete cascade
);

--insert into ersuser (username, erspassword, firstname, lastname,email) values ('marwil', 'william', 'Marcia', 'Williamson', 'mareo199@gmail.com');

select * from ersuser;
select * from roles;
select * from reimbursment;
select * from ersstatus;
select * from erstype;

create or REPLACE PROCEDURE insert_user(username text, erspassword text, firstname text, lastname text, email text)
AS $$
	BEGIN 
		insert into ersuser (username, erspassword, firstname, lastname,email) values (username, erspassword, firstname, lastname, email);
	END;
$$ LANGUAGE plpgsql;
CALL insert_user('marwil', 'william', 'Marcia', 'Williamson', 'mother@gmail.com');
Call insert_user('king', 'george', 'Kingsley', 'Yapp', 'father@gmail.com');
insert into roles(ersroles,userid) values ('Employee',1);
insert into roles(ersroles,userid) values ('Manager',2);

create OR REPLACE FUNCTION submit_time()
returns TRIGGER AS 
$$
BEGIN 
	new.submitted = now();
	return new;
END;
$$ language plpgsql;

create trigger set_current_time
before insert on reimbursment
for each row EXECUTE PROCEDURE submit_time();

INSERT into reimbursment (amount,description,author) values (10,'description',1);

/*create or replace PROCEDURE insert_statustype(erstype text, id integer)
AS $$
	BEGIN 
		insert into ERSstatus(status,id) values ('Pending',id);
		INSERT into ERStype(erstype,id) values (erstype,id);
	END
$$ LANGUAGE plpgsql;
call insert_statustype('OTHER', 1);*/

create or REPLACE FUNCTION resolved_time()
returns TRIGGER 
AS $$
	BEGIN 
		new.resolved = now();
		return new;
	END;
$$ LANGUAGE plpgsql;

create trigger set_current_time2
before update on reimbursment
for each row EXECUTE PROCEDURE resolved_time()
update reimbursment set resolver =2 where id =2;

create or replace PROCEDURE resolve(rid integer, eid integer)
as $$
	Begin
		update reimbursment set resolver = rid where id=eid;
	end;
$$ LANGUAGE plpgsql;

CALL resolve(2,2);

select * from reimbursment;

update reimbursment set description = 'description' where id=1;
delete from reimbursment where id=1;

select * 
from reimbursment r
inner join ersstatus s on r.id =s.id 
inner join erstype t on t.id =s.id ;

rollback;