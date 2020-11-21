package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Type")
public class ERSType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // this acts like the SERIAL datatype in SQL	
	@Column(name="typeid")
	private int typeid;
	
	@Column(name="type", nullable=false)
	private String type; // Lodging, Travel, Food, Other
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reimbursementid")
	private ERSReimbursement reimbursementid;

	public ERSType(String type, ERSReimbursement ersreimbursement) {
		super();
		this.type = type;
		this.reimbursementid = ersreimbursement;
	}

	public ERSType(int typeid, String type, ERSReimbursement ersreimbursement) {
		super();
		this.typeid = typeid;
		this.type = type;
		this.reimbursementid = ersreimbursement;
	}

	public ERSReimbursement getErsreimbursement() {
		return reimbursementid;
	}

	public void setErsreimbursement(ERSReimbursement ersreimbursement) {
		this.reimbursementid = ersreimbursement;
	}

	public ERSType(int typeid, String type) {
		super();
		this.typeid = typeid;
		this.type = type;
	}
	
	public ERSType(String type) {
		super();
		this.type = type;
	}

	@Override
	public String toString() {
		String result = "TypeID: " + typeid + " \t\tType: " + type;
		return result;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + typeid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ERSType other = (ERSType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (typeid != other.typeid)
			return false;
		return true;
	}

}
