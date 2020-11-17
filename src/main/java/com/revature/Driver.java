package com.revature;

import com.revature.model.ERSReimbursement;
import com.revature.model.ERSStatus;
import com.revature.model.ERSType;
import com.revature.model.Role;
import com.revature.model.User;

public class Driver {

	public static void main(String[] args) {
		Role r = new Role(1,"Employee");
		User u = new User(1,"Mareo","Yapp","mareo1997","password","mareo1997@gmail.com",r);
		System.out.println(u);
		
		ERSStatus s = new ERSStatus(1, "PENDING");
		//System.out.println(s);
		ERSType t = new ERSType(1, "FOOD");
		//System.out.println(t);
		
		ERSReimbursement e = new ERSReimbursement(1, 1, "description", 10.5, s, t);
		System.out.println(e);

	}
}
