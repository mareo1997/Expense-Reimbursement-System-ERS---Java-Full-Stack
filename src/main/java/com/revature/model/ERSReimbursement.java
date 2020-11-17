package com.revature.model;

import java.time.LocalDateTime;

public class ERSReimbursement {
	private int ERSRid, author, resolver=0;
	private double amt;
	private LocalDateTime submitted, resolved;
	private String description;
	private ERSStatus status;
	private ERSType type;

	public ERSReimbursement(int eRSRid, int author, String description, double amt, LocalDateTime submitted,
							int resolver, LocalDateTime resolved, ERSStatus status, ERSType type) {
		this.ERSRid = eRSRid;
		this.author = author;
		this.resolver = resolver;
		this.amt = amt;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	public ERSReimbursement(int eRSRid, int author, String description, double amt, /*LocalDateTime submitted,*/ 
			ERSStatus status, ERSType type) {
		this.ERSRid = eRSRid;
		this.author = author;
		this.amt = amt;
		this.submitted = LocalDateTime.now();
		this.description = description;
		this.status = status;
		this.type = type;
	}
	
	@Override
	public String toString() {
		String result = "ReimbursementID: "+ERSRid+" \tAuthor: "+author+
						"\nDescription: "+description+
						"\nAmount: "+amt+" \t\tSubmitted: "+submitted+
						"\n"+status+
						"\n"+type;
		return result;
	}

	public int getERSRid() {
		return ERSRid;
	}

	public void setERSRid(int eRSRid) {
		ERSRid = eRSRid;
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

	public LocalDateTime getSubmitted() {
		return submitted;
	}

	public void setSubmitted(LocalDateTime submitted) {
		this.submitted = submitted;
	}

	public LocalDateTime getResolved() {
		return resolved;
	}

	public void setResolved(LocalDateTime resolved) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ERSRid;
		long temp;
		temp = Double.doubleToLongBits(amt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		if (ERSRid != other.ERSRid)
			return false;
		if (Double.doubleToLongBits(amt) != Double.doubleToLongBits(other.amt))
			return false;
		if (author != other.author)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
