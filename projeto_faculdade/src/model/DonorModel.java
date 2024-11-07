package model;


import java.util.Objects;

public class DonorModel {

	private Long managerId;
	private Long donorId;
	private String name;
	private String phone;
	private String dateDonor;
	private String managerName;
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public Long getDonorId() {
		return donorId;
	}
	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDateDonor() {
		return dateDonor;
	}
	public void setDateDonor(String dateDonor) {
		this.dateDonor = dateDonor;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateDonor, donorId, managerId, managerName, name, phone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonorModel other = (DonorModel) obj;
		return Objects.equals(dateDonor, other.dateDonor) && Objects.equals(donorId, other.donorId)
				&& Objects.equals(managerId, other.managerId) && Objects.equals(managerName, other.managerName)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}
	@Override
	public String toString() {
		return "DonorModel [managerId=" + managerId + ", donorId=" + donorId + ", name=" + name + ", phone=" + phone
				+ ", dateDonor=" + dateDonor + ", managerName=" + managerName + "]";
	}
	

	
	
}
