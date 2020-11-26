package com.revature.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.util.HibernateUtil;

public class EmplReimbursementDaoImpl implements EmplReimbursementDao {// Applied log

	private static Logger log = Logger.getLogger(EmplReimbursementDaoImpl.class);

	@Override
	public Reimbursement submitHQL(Reimbursement r) {
		log.info("Attempting to submit " + r + "\n");
		Session ses = HibernateUtil.getSession(); // capture the session

		Transaction tx = ses.beginTransaction();
		ses.save(r);
		tx.commit(); // commit the transaction by utilizing the methods from the Transaction interface
		//HibernateUtil.closeSes();
		return r;
	}

	@Override
	public List<Reimbursement> pendingHQL(User u) { // - An Employee can view their pending reimbursement requests ****
		log.info("Attempting to get pending reim for " + u.getUsername() + "\n");
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> reim = ses
				.createQuery("FROM Reimbursement where status_statusid = 1 and authorfk = " + u.getUserid() + " ",
						Reimbursement.class)
				.list();

		//HibernateUtil.closeSes();

		if (reim.size() > 0) {
			log.info("Returning pending requests");
			return reim;
		} else {
			log.warn(u.getUsername() + " has no pending requests\n");
			return null;
		}

	}

	@Override
	public List<Reimbursement> resolvedHQL(User u) { // - An Employee can view their pending reimbursement requests ****

		log.info("Attempting to get resolved requests for " + u.getUsername() + "\n");
		Session ses = HibernateUtil.getSession();

		List<Reimbursement> reim = ses
				.createQuery("FROM Reimbursement where authorfk = " + u.getUserid() + " and not status_statusid = 1 ",
						Reimbursement.class)
				.list();
		
		//HibernateUtil.closeSes();

		if (reim.size() > 0) {
			log.info("Returning resolved requests");
			return reim;
		} else {
			log.warn(u.getUsername() + " has no resolved requests\n");
			return null;
		}

	}

}
