package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import connection.ConnectionPostgreSQL;
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

}
