package com.revature.services;

import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;

public interface ReimbursementServices {
	
	public void insert(Reimbursement e);
	public void insert(Status s);
	public void insert(Type t);
	
	public void submit(Reimbursement r, Status s, Type t);
	public void submit(User u, Reimbursement r);
	
	public List<Reimbursement> pending(User u);
	public List<Status> pendingHQL(User profile);
	public List<Reimbursement> resolved(User u);
	
	public void resolve(int id, String status, int resolver);
	
	public void requests(int userid);
	public void resolvedrequests();
	public void pendingrequests();
	List<Status> resolvedHQL(User profile);
}
