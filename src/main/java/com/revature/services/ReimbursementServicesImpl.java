package com.revature.services;

import java.util.List;

import com.revature.dao.ReimbursmentDaoImpl;
import com.revature.model.Reimbursement;
import com.revature.model.Status;
import com.revature.model.Type;
import com.revature.model.User;

public class ReimbursementServicesImpl implements ReimbursementServices {

	ReimbursmentDaoImpl dao = new ReimbursmentDaoImpl();
	
	@Override
	public void insert(Reimbursement e) {
		dao.insert(e);
	}

	@Override
	public void insert(Status s) {
		dao.insert(s);
	}

	@Override
	public void insert(Type t) {
		dao.insert(t);
	}

	@Override
	public void submit(User u, Reimbursement r) {
		dao.submit(u, r);
	}

	@Override
	public List<Reimbursement> pending(User u) {
		return dao.pending(u);
	}

	@Override
	public List<Reimbursement> resolved(User u) {
		return dao.resolved(u);
	}

	@Override
	public void resolve(int id, String status, int resolver) {
		dao.resolve(id, status, resolver);
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
	public void submit(Reimbursement r, Status s, Type t) {
		dao.submit(r, s, t);
	}

	@Override
	public List<Status> pendingHQL(User u) {
		return dao.pendingHQL(u);
	}

	@Override
	public List<Status> resolvedHQL(User profile) {
		return dao.resolvedHQL(profile);
	}

}
