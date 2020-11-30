delete from reimbursement where reimbursementid = 5;
delete from status where statusid > 3;
delete from "type" where typeid > 4;
DELETE FROM "role" WHERE roleid>2;

select * from reimbursement ;

update "type" set "type" = 'TRAVEL' where typeid =2;