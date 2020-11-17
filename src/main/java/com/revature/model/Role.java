package com.revature.model;

public class Role {

	private int roleid;
	private String role;

	public Role(int roleid, String role) {
		super();
		this.roleid = roleid;
		this.role = role;
	}

	@Override
	public String toString() {
		String result = "RoleID: "+roleid+" \t\tRole: "+role;
		return result;
	}
	
	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + roleid;
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
		Role other = (Role) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (roleid != other.roleid)
			return false;
		return true;
	}

}
