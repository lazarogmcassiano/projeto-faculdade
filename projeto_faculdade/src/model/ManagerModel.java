package model;

import java.util.Objects;

public class ManagerModel {
	
	private Long managerId;
	private String name;
	private String login;
	private String password;
	private String phone;
	private String dateManager;
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDateManager() {
		return dateManager;
	}
	public void setDateManager(String dateManager) {
		this.dateManager = dateManager;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateManager, login, managerId, name, password, phone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagerModel other = (ManagerModel) obj;
		return Objects.equals(dateManager, other.dateManager) && Objects.equals(login, other.login)
				&& Objects.equals(managerId, other.managerId) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone);
	}
	@Override
	public String toString() {
		return "ManagerModel [managerId=" + managerId + ", name=" + name + ", login=" + login + ", password=" + password
				+ ", phone=" + phone + ", dateManager=" + dateManager + "]";
	}

	
	
		

}
