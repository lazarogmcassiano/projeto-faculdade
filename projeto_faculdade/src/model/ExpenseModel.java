package model;

import java.math.BigDecimal;
import java.util.Objects;

public class ExpenseModel {

	private String description;
	private Long exprenseId;
	private Long managerId;
	private BigDecimal value;
	private String dateExpense;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getExprenseId() {
		return exprenseId;
	}
	public void setExprenseId(Long exprenseId) {
		this.exprenseId = exprenseId;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getDateExpense() {
		return dateExpense;
	}
	public void setDateExpense(String dateExpense) {
		this.dateExpense = dateExpense;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dateExpense, description, exprenseId, managerId, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseModel other = (ExpenseModel) obj;
		return Objects.equals(dateExpense, other.dateExpense) && Objects.equals(description, other.description)
				&& Objects.equals(exprenseId, other.exprenseId) && Objects.equals(managerId, other.managerId)
				&& Objects.equals(value, other.value);
	}

	
	
	

}
