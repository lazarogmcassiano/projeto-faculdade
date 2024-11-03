package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class DonationModel {
	private Long donationId;
	private Long donorId;
	private Long managerId;
	private String description;
	private BigDecimal value;
	private Timestamp dateDonation;
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
	public Timestamp getDateDonation() {
		return dateDonation;
	}
	public void setDateDonation(Timestamp dateDonation) {
		this.dateDonation = dateDonation;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateDonation, description, donationId, donorId, managerId, value);
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
				&& Objects.equals(managerId, other.managerId) && Objects.equals(value, other.value);
	}
	@Override
	public String toString() {
		return "DonationModel [donationId=" + donationId + ", donorId=" + donorId + ", managerId=" + managerId
				+ ", description=" + description + ", value=" + value + ", dateDonation=" + dateDonation + "]";
	}
	
	

}
