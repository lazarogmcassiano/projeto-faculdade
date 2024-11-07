package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class DonationModel {
	private Long donationId;
	private Long donorId;
	private String donorName;
	private String managerName;
	private String phone;;
	private Long managerId;
	private String description;
	private BigDecimal value;
	private String dateDonation;
	
	

	@Override
	public String toString() {
		return "DonationModel [donationId=" + donationId + ", donorId=" + donorId + ", donorName=" + donorName
				+ ", managerName=" + managerName + ", phone=" + phone + ", managerId=" + managerId + ", description="
				+ description + ", value=" + value + ", dateDonation=" + dateDonation + "]";
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public Long getDonationId() {
		return donationId;
	}
	public void setDonationId(Long donationId) {
		this.donationId = donationId;
	}
	public Long getDonorId() {
		return donorId;
	}
	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}
	public String getDonorName() {
		return donorName;
	}
	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getDateDonation() {
		return dateDonation;
	}
	public void setDateDonation(String dateDonation) {
		this.dateDonation = dateDonation;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateDonation, description, donationId, donorId, donorName, managerId, managerName, phone,
				value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonationModel other = (DonationModel) obj;
		return Objects.equals(dateDonation, other.dateDonation) && Objects.equals(description, other.description)
				&& Objects.equals(donationId, other.donationId) && Objects.equals(donorId, other.donorId)
				&& Objects.equals(donorName, other.donorName) && Objects.equals(managerId, other.managerId)
				&& Objects.equals(managerName, other.managerName) && Objects.equals(phone, other.phone)
				&& Objects.equals(value, other.value);
	}
	
	
	
	

}
