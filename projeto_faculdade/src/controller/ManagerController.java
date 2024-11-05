package controller;

import java.math.BigDecimal;
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

public class ManagerController {
	public static ManagerModel manager;
	public static Long donorId;
	private Connection connection;

	public ManagerController() {
		connection = ConnectionPostgreSQL.getConnection();
	}

	public boolean managerLogin(String login, String password) {
		String sql = "SELECT * FROM manager_table WHERE login = ? AND password = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				manager = new ManagerModel();
				manager.setManagerId(resultSet.getLong("manager_id"));
				manager.setName(resultSet.getString("name"));

				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createDonor(String donorName, DonorModel donorModel) {
		System.out.println(manager);
		String sql = "INSERT INTO donor_table (manager_id, name, phone) VALUES (?, ?, ?)";
		try {
			if (!findDonorByName(donorName)) {
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setLong(1, manager.getManagerId());
					statement.setString(2, donorModel.getName());
					statement.setString(3, donorModel.getPhone());
					int insertTable = statement.executeUpdate();
					if (insertTable > 0) {
						System.out.println("Doador Criado");
						connection.commit();
						return true;

					} else {
						System.out.println("Erro ao criar doador");
					}
				}
			} else {
				System.out.println("Nome ou telefone já utilizado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean findDonorByName(String donorName) {

		String sql = "Select name, phone From donor_table where name = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1,"%"+ donorName+ "%");
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean findDonor(String donorName, List<DonorModel> donorModelList) {

		String sql = "select  donor_id, name, phone, date_time_donor from donor_table where name ILIKE ? order by date_time_donor desc;";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%" + donorName + "%");
			ResultSet resultSet = statement.executeQuery();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			donorModelList.clear();

			while (resultSet.next()) {
				DonorModel donorModel = new DonorModel();
				donorModel.setDonorId(resultSet.getLong("donor_id"));
				donorModel.setName(resultSet.getString("name"));
				donorModel.setPhone(resultSet.getString("phone"));
				Timestamp dateDonor = resultSet.getTimestamp("date_time_donor");
				String formattedDate = dateFormat.format(dateDonor);
				donorModel.setDateDonor(formattedDate);

				donorModelList.add(donorModel);
				

			}
			return !donorModelList.isEmpty();
		} catch (SQLException e) {
			System.out.println("Usuário não encontrado");
			e.printStackTrace();
		}
		return false;
	}

	public boolean findDonorById(Long donorId) {

		String sql = "SELECT name, phone FROM donor_table WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, donorId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				System.out.println("Usuário encontrado");
				return true;
			} else {
				System.out.println("Usuário não encontrado");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean findAllDonor(List<DonorModel> donorModelList) {
		String sql = "SELECT name, phone, date_time_donor FROM donor_table ORDER BY date_time_donor DESC;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			donorModelList.clear();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			while (resultSet.next()) {
				DonorModel donorModel = new DonorModel();
				donorModel.setName(resultSet.getString("name"));
				donorModel.setPhone(resultSet.getString("phone"));

				Timestamp dateDonor = resultSet.getTimestamp("date_time_donor");
				String formattedDate = dateFormat.format(dateDonor);
				donorModel.setDateDonor(formattedDate);

				donorModelList.add(donorModel);
			}

			return !donorModelList.isEmpty();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createExpense(ExpenseModel expenseModel) {
		String sql = "INSERT INTO expense_table (manager_id, description, value) VALUES (?, ?, ?)";
		try {

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setLong(1, manager.getManagerId());
				statement.setString(2, expenseModel.getDescription());
				statement.setBigDecimal(3, expenseModel.getValue());
				int insertTable = statement.executeUpdate();
				if (insertTable > 0) {
					System.out.println("Despesa registrada");
					connection.commit();
					return true;

				} else {

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean findAllExpense(List<ExpenseModel> expenseModelList) {

		String sql = "select description, value, date_time_expense from expense_table where manager_id ="
				+ " ? order by  date_time_expense desc";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, manager.getManagerId());

			ResultSet resultSet = statement.executeQuery();

			expenseModelList.clear();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			while (resultSet.next()) {
				ExpenseModel expenseModel = new ExpenseModel();
				expenseModel.setDescription(resultSet.getString("description"));
				expenseModel.setValue(resultSet.getBigDecimal("value"));

				Timestamp dateExpense = resultSet.getTimestamp("date_time_expense");
				String formattedDate = dateFormat.format(dateExpense);
				expenseModel.setDateExpense(formattedDate);

				expenseModelList.add(expenseModel);
			}

			return !expenseModelList.isEmpty();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean findDonorByNameAndPhone(String donorName, String donorPhone) {

		String sql = "Select donor_id from donor_table where name = ? and phone = ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, donorName);
			statement.setString(2, donorPhone);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				donorId = resultSet.getLong("donor_id");
				return true;
			}
			return false;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public boolean createDonation(String description, BigDecimal value) {

		String sql = "INSERT INTO public.donation_table(donor_id, manager_id, description, value) VALUES (?, ?, ?, ?); ";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, donorId);
			statement.setLong(2, manager.getManagerId());
			statement.setString(3, description);
			statement.setBigDecimal(4, value);
			int insertSql = statement.executeUpdate();

			if (insertSql > 0) {
				System.out.println("doação realizada com sucesso!");
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean createAnonymousDonation(String description, BigDecimal value) {

		String sql = "INSERT INTO public.donation_table(donor_id, manager_id, description, value) VALUES (?, ?, ?, ?); ";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, 1);
			statement.setLong(2, manager.getManagerId());
			statement.setString(3, description);
			statement.setBigDecimal(4, value);
			int insertSql = statement.executeUpdate();

			if (insertSql > 0) {
				System.out.println("doação realizada com sucesso!");
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// sem utilidade!
	public boolean findDonationByManagerId(List<DonationModel> donationModelList) {
		String sql = "select \n" + "donor_table.name,\n" + "donor_table.phone,\n" + "donation_table.description,\n"
				+ "donation_table.date_time_donation,\n" + "donation_table.value\n" + "from donation_table\n"
				+ "join donor_table on donation_table.donor_id = donor_table.donor_id\n"
				+ "where donation_table.manager_id = ?;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, manager.getManagerId());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean findAllDonationByManagerId(List<DonationModel> donationModelList) {
		String sql = "SELECT \n" + "donor_table.name,\n" + "donor_table.phone,\n" + "donation_table.description,\n"
				+ "donation_table.value,\n" + "donation_table.date_time_donation\n" + "FROM donor_table\n"
				+ "JOIN donation_table ON donor_table.donor_id = donation_table.donor_id\n"
				+ "WHERE donor_table.manager_id = ? order by date_time_donation desc;";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, manager.getManagerId());
			ResultSet resultSet = statement.executeQuery();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			while (resultSet.next()) {
				DonationModel donation = new DonationModel();

				donation.setDonorName(resultSet.getString("name"));
				donation.setPhone(resultSet.getString("phone"));
				donation.setDescription(resultSet.getString("description"));
				donation.setValue(resultSet.getBigDecimal("value"));
				Timestamp dateDonor = resultSet.getTimestamp("date_time_donation");
				String formattedDate = dateFormat.format(dateDonor);
				donation.setDateDonation(formattedDate);

				donationModelList.add(donation);
			
			}
			return !donationModelList.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean findDonationByDonorName(List<DonationModel> donationModelList, String donorNameField) {
		String sql = " SELECT donor_table.name, donor_table.phone, donation_table.description, "
				+ "donation_table.value, donation_table.date_time_donation  " + "from donor_table "
				+ "join donation_table on donor_table.donor_id = donation_table.donor_id "
				+ "where donation_table.manager_id = ? and donor_table.name ILIKE ? " 
				+ " order by  date_time_donation desc";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, manager.getManagerId());
			statement.setString(2,"%" + donorNameField  + "%");
			ResultSet resultSet = statement.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			while (resultSet.next()) {
				DonationModel donation = new DonationModel();
				donation.setDonorName(resultSet.getString("name"));
				donation.setPhone(resultSet.getString("phone"));
				donation.setDescription(resultSet.getString("description"));
				donation.setValue(resultSet.getBigDecimal("value"));
				Timestamp dateDonor = resultSet.getTimestamp("date_time_donation");
				String formattedDate = dateFormat.format(dateDonor);
				donation.setDateDonation(formattedDate);

				donationModelList.add(donation);
				
			}
			return !donationModelList.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
