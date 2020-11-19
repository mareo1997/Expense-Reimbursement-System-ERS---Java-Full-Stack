package com.revature.dao;

import com.revature.model.ERSReimbursement;
import com.revature.model.User;

public interface ReimburmentDao {
	
	public void submit(User u, ERSReimbursement r);
	
	public void pending(User u);
	public void resolved(User u);
	
	public void resolve(int ersid, String status, int resolver);
	
	public void requests(int userid);
	public void resolvedrequests();
	public void pendingrequests();

}
