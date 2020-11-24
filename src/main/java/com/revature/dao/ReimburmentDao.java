package com.revature.dao;

import java.sql.Timestamp;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;

public interface ReimburmentDao {

	public void submit(User u, Reimbursement r);

	public List<Reimbursement> pending(User u);

	public List<Reimbursement> resolved(User u);

	public void resolve(int id, String status, int resolver);

	public void requests(int userid);

	public void resolvedrequests();

	public void pendingrequests();

	public void insert(Reimbursement e);

	public void insert(Status s);

	public void insert(Type t);

	public Reimbursement findReimHQL(int reimbursementid);

	public void submitHQL(Reimbursement reim);

	public Reimbursement resolveHQL(Reimbursement reim, User resolver, Status status, Timestamp resolved);

	public List<Reimbursement> pendingHQL(User profile);

	public List<Reimbursement> resolvedHQL(User profile);

	public Type typeHQL(String t);

	public Status statusHQL(int s);

}
