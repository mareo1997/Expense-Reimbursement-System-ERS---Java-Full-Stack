package com.revature.model;

import java.sql.Timestamp;

public class ERSReimbursement {
	private int ersid, author, resolver;
	private double amt;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private ERSStatus status;
	private ERSType type;

	public ERSReimbursement(int ersid, int author, int resolver, double amt, Timestamp submitted, Timestamp resolved,
			String description, ERSStatus status, ERSType type) {
		super();
		this.ersid = ersid;
		this.author = author;
		this.resolver = resolver;
		this.amt = amt;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	public ERSReimbursement(int author, String description, double amt, ERSStatus status, ERSType type) {
		this.author = author;
		this.amt = amt;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	public ERSReimbursement(int ersid, int author, String description, double amt, Timestamp submitted) {
		this.ersid = ersid;
		this.author = author;
		this.amt = amt;
		this.description = description;
		this.submitted = submitted;
	}

	public ERSReimbursement(int ersid, int author, String description, double amt, Timestamp submitted, ERSStatus status, ERSType type) {
		this.ersid = ersid;
		this.author = author;
		this.amt = amt;
		this.description = description;
		this.status = status;
		this.type = type;
		this.submitted = submitted;
	}

	public ERSReimbursement(int ersid, int author, String description, double amt, Timestamp submitted, int resolver, Timestamp resolved, ERSStatus status, ERSType type) {
		this.ersid = ersid;
		this.author = author;
		this.resolver = resolver;
		this.amt = amt;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	@Override
	public String toString() {
		String result = "ReimbursementID: " + ersid + " \tAuthor: " + author + "\nDescription: " + description
				+ "\nAmount: $" + amt + " \t\tSubmitted: " + submitted + "\nResolver: " + resolver + " \t\tResolved: "
				+ resolved + "\n" + status + "\n" + type+"\n";
		return result;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ERSStatus getStatus() {
		return status;
	}

	public void setStatus(ERSStatus status) {
		this.status = status;
	}

	public ERSType getType() {
		return type;
	}

	public void setType(ERSType type) {
		this.type = type;
	}

	public int getErsid() {
		return ersid;
	}

	public void setErsid(int ersid) {
		this.ersid = ersid;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ersid;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolver;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ERSReimbursement other = (ERSReimbursement) obj;
		if (Double.doubleToLongBits(amt) != Double.doubleToLongBits(other.amt))
			return false;
		if (author != other.author)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (ersid != other.ersid)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver != other.resolver)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
