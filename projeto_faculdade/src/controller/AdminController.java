package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import connection.ConnectionPostgreSQL;
import model.DonationModel;
import model.DonorModel;
import model.ExpenseModel;
import model.ManagerModel;

public class AdminController {
	Connection connection;

	public AdminController() {
		connection = ConnectionPostgreSQL.getConnection();
	}

	public boolean createManager(ManagerModel managerModel) {

		String sql = "INSERT INTO manager_table ( name, login, password, phone) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;

		if (!findManager(managerModel.getLogin())) {

			try {
				statement = connection.prepareStatement(sql);
				statement.setString(1, managerModel.getName());
				statement.setString(2, managerModel.getLogin());
				statement.setString(3, managerModel.getPassword());
				statement.setString(4, managerModel.getPhone());

				int insertTable = statement.executeUpdate();
				if (insertTable < 0) {
					System.out.println("usuário não criado");
				}
				connection.commit();
				System.out.println("usuário criado");
				return true;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		System.out.println("conta não criada");
		return false;

	}

	public boolean findManager(String login) {
		String sql = "SELECT login FROM manager_table WHERE login = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				System.out.println("Usuário encontrado");
				return true;
			} else {
				System.out.println("Usuário não encontrado");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean findAllManager(List<ManagerModel> managerModelList) {
		String sql = "SELECT manager_id, login, name, phone, date_time_manager FROM manager_table;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			ResultSet resultSet = statement.executeQuery();
			managerModelList.clear();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			while (resultSet.next()) {
				ManagerModel modelManager = new ManagerModel();
				modelManager.setManagerId(resultSet.getLong("manager_id"));
				modelManager.setLogin(resultSet.getString("login"));
				modelManager.setName(resultSet.getString("name"));
				modelManager.setPhone(resultSet.getString("phone"));
				Timestamp dateManager = resultSet.getTimestamp("date_time_manager");
				String formattedDate = dateFormat.format(dateManager);
				modelManager.setDateManager(formattedDate);

				managerModelList.add(modelManager);
			}

			return !managerModelList.isEmpty();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateManager(ManagerModel managerModel) {

		String sql = "UPDATE public.manager_table " + "	SET  name=?, password=?, phone=? "
				+ "	WHERE manager_id = ? and login = ?;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, managerModel.getName());
			statement.setString(2, managerModel.getPassword());
			statement.setString(3, managerModel.getPhone());
			statement.setLong(4, managerModel.getManagerId());
			statement.setString(5, managerModel.getLogin());

			int updateManager = statement.executeUpdate();
			if (updateManager > 0) {

				connection.commit();

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;

	}

	public boolean findManagerByNameAndPhone(ManagerModel manager) {

		String sql = "Select name, phone from manager_table where name = ? and phone = ? ";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, manager.getName());
			statement.setString(2, manager.getPhone());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	public boolean findAllExpense(List<ExpenseModel> expenseModelList) {
		String sql = "SELECT expense_table.expense_id, " + " manager_table.name, " + " expense_table.description, "
				+ " expense_table.value, " + " expense_table.date_time_expense " + " FROM expense_table " + " join "
				+ " manager_table on expense_table.manager_id = manager_table.manager_id "
				+ " order by date_time_expense desc ;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			expenseModelList.clear();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			while (resultSet.next()) {
				ExpenseModel expenseModel = new ExpenseModel();
				Timestamp dateExpense = resultSet.getTimestamp("date_time_expense");
				String formatteDateExpense = dateFormat.format(dateExpense);

				expenseModel.setExpenseId(resultSet.getLong("expense_id"));
				expenseModel.setManagerName(resultSet.getString("name"));
				expenseModel.setDescription(resultSet.getString("description"));
				expenseModel.setValue(resultSet.getBigDecimal("value"));
				expenseModel.setDateExpense(formatteDateExpense);
				expenseModelList.add(expenseModel);
			}
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean findAllExpenseByManagerName(String managerName, List<ExpenseModel> expenseModelList) {
		String sql = "SELECT expense_table.expense_id, " + " manager_table.name, " + " expense_table.description, "
				+ " expense_table.value, " + " expense_table.date_time_expense " + " FROM expense_table " + " join "
				+ " manager_table on expense_table.manager_id = manager_table.manager_id "
			+ " where manager_table.name ILIKE ? "
			+ " order by date_time_expense desc " ;

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + managerName + "%");
			ResultSet resultSet = statement.executeQuery();
			expenseModelList.clear();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			while (resultSet.next()) {
				ExpenseModel expenseModel = new ExpenseModel();
				Timestamp dateExpense = resultSet.getTimestamp("date_time_expense");
				String formatteDateExpense = dateFormat.format(dateExpense);

				expenseModel.setExpenseId(resultSet.getLong("expense_id"));
				expenseModel.setManagerName(resultSet.getString("name"));
				expenseModel.setDescription(resultSet.getString("description"));
				expenseModel.setValue(resultSet.getBigDecimal("value"));
				expenseModel.setDateExpense(formatteDateExpense);
				expenseModelList.add(expenseModel);
			}
			return !expenseModelList.isEmpty();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean deleteExpense(ExpenseModel expense) {

		String sql = "delete from expense_table where expense_id = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, expense.getExpenseId());
			int deleteQuery = statement.executeUpdate();

			if (deleteQuery > 0) {
				connection.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean findAllDonor(List<DonorModel> donorModelList) {

		String sql = "select donor_table.donor_id, donor_table.name, " + "donor_table.phone,"
				+ "manager_table.name as managerName, donor_table.date_time_donor " + "from donor_table "
				+ " join manager_table on donor_table.manager_id = manager_table.manager_id";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			donorModelList.clear();
			ResultSet resultSet = statement.executeQuery();
			SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/YYYY hh:mm");
			while (resultSet.next()) {
				DonorModel donorModel = new DonorModel();
				Timestamp dateTime = resultSet.getTimestamp("date_time_donor");
				String dateTimedonor = formattedDate.format(dateTime);

				donorModel.setDonorId(resultSet.getLong("donor_id"));
				donorModel.setName(resultSet.getString("name"));
				donorModel.setPhone(resultSet.getString("phone"));
				donorModel.setManagerName(resultSet.getString("managerName"));
				donorModel.setDateDonor(dateTimedonor);
				donorModelList.add(donorModel);
			}
			return !donorModelList.isEmpty();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean findAllDonorByName(List<DonorModel> donorModelList, String donorName) {

		String sql = "select donor_table.donor_id, donor_table.name, " + "donor_table.phone,"
				+ "manager_table.name as managerName, donor_table.date_time_donor " + "from donor_table "
				+ " join manager_table on donor_table.manager_id = manager_table.manager_id"
				+ " where donor_table.name ILIKE ? " + "order by date_time_donor desc;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + donorName + "%");
			donorModelList.clear();
			ResultSet resultSet = statement.executeQuery();
			SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/YYYY hh:mm");
			while (resultSet.next()) {
				DonorModel donorModel = new DonorModel();
				Timestamp dateTime = resultSet.getTimestamp("date_time_donor");
				String dateTimedonor = formattedDate.format(dateTime);

				donorModel.setDonorId(resultSet.getLong("donor_id"));
				donorModel.setName(resultSet.getString("name"));
				donorModel.setPhone(resultSet.getString("phone"));
				donorModel.setManagerName(resultSet.getString("managerName"));
				donorModel.setDateDonor(dateTimedonor);
				donorModelList.add(donorModel);
			}
			return !donorModelList.isEmpty();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean updateDonor(DonorModel donorModel) {

		String sql = "UPDATE public.donor_table " + "	SET  name=?,  phone=? " + "	WHERE donor_id = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, donorModel.getName());
			statement.setString(2, donorModel.getPhone());

			statement.setLong(3, donorModel.getDonorId());

			int updateDonor = statement.executeUpdate();

			if (updateDonor > 0) {
				connection.commit();
				return true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	public boolean findAllDonation(List<DonationModel> donationModelList) {

		String sql = "select donation_id, " + " donor_table.name as donorName, " + " donation_table.description, "
				+ " value, " + " manager_table.name as managerName, " + " date_time_donation "
				+ "	from public.donation_table "
				+ "	join donor_table on donation_table.manager_id = donor_table.manager_id "
				+ "	join manager_table on donor_table.manager_id = manager_table.manager_id;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
				donationModelList.clear();
			ResultSet resultSet = statement.executeQuery();
			SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/YYYY hh:mm");
			while (resultSet.next()) {

			 	Timestamp donationDate = resultSet.getTimestamp("date_time_donation");
			 	String date = formattedDate.format(donationDate);
				DonationModel	donation = new DonationModel();
				donation.setDonationId(resultSet.getLong("donation_id"));
			 	donation.setDonorName(resultSet.getString("donorName"));
			 	donation.setDescription(resultSet.getString("description"));
			 	donation.setValue(resultSet.getBigDecimal("value"));
			 	donation.setManagerName(resultSet.getString("managerName"));
			 	donation.setDateDonation(date);
			 	donationModelList.add(donation);
			 	System.out.println(donation);
			}
			return true;
			
		} catch (Exception e) {
		
		}

		return false;
	}

}
