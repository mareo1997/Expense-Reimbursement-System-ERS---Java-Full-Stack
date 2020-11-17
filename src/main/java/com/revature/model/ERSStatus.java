package com.revature.model;

public class ERSStatus {
	private int statusid;
	private String status; // Pending, Approved, Denied

	public ERSStatus(int statusid, String status) {
		super();
		this.statusid = statusid;
		this.status = status;
	}

	@Override
	public String toString() {
		String result = "StatusID: " + statusid + " \t\tStatus: " + status;
		return result;
	}

	public int getStatusid() {
		return statusid;
	}

	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + statusid;
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
		ERSStatus other = (ERSStatus) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusid != other.statusid)
			return false;
		return true;
	}

}
