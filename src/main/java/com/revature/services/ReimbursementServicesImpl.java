package com.revature.services;

import com.revature.dao.ReimbursmentDaoImpl;
import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.User;

public class ReimbursementServicesImpl implements ReimbursementServices {

	ReimbursmentDaoImpl dao = new ReimbursmentDaoImpl();
	
	@Override
	public void insert(ERSReimbursement e) {
		dao.insert(e);
	}

	@Override
	public void insert(ERSStatus s) {
		dao.insert(s);
	}

	@Override
	public void insert(ERSType t) {
		dao.insert(t);
	}

	@Override
	public void submit(User u, ERSReimbursement r) {
		dao.submit(u, r);
	}

	@Override
	public void pending(User u) {
		dao.pending(u);
	}

	@Override
	public void resolved(User u) {
		dao.resolved(u);
	}

	@Override
	public void resolve(int ersid, String status, int resolver) {
		dao.resolve(ersid, status, resolver);
	}

	@Override
	public void requests(int userid) {
		dao.requests(userid);
	}

	@Override
	public void resolvedrequests() {
		dao.resolvedrequests();
	}

	@Override
	public void pendingrequests() {
		dao.pendingrequests();
	}

	@Override
	public void submit(ERSReimbursement r, ERSStatus s, ERSType t) {
		dao.submit(r, s, t);
	}

}
