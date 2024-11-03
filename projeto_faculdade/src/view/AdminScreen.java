package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.ManagerModel;

public class AdminScreen extends JFrame {

	private AdminController adminController;

	private static final long serialVersionUID = 1L;

	// Admin main Screen (menu)
	public void AdminScreenPrincipal() {
		JFrame jFrame = new JFrame("Admin Screen");
		JPanel screenPrincipal = new JPanel();

		jFrame.setSize(new Dimension(500, 600));
		jFrame.setResizable(false);
		jFrame.setLayout(new BorderLayout());

		JButton configurationManagerButton = new JButton("Monitores");
		JButton generalStatisticsButton = new JButton("Estatísticas");
		JButton donationRecordButton = new JButton("Doações");
		JButton donorButton = new JButton("Doadores");
		JButton expenseRecordButton = new JButton("Despesas");
		JButton exitApp = new JButton("Sair");

		donationRecordButton.setBounds(150, 130, 200, 30);
		expenseRecordButton.setBounds(150, 180, 200, 30);
		generalStatisticsButton.setBounds(150, 230, 200, 30);
		donorButton.setBounds(150, 280, 200, 30);
		configurationManagerButton.setBounds(150, 330, 200, 30);
		exitApp.setBounds(150, 380, 200, 30);
		screenPrincipal.setLayout(null);

		screenPrincipal.add(configurationManagerButton);
		screenPrincipal.add(generalStatisticsButton);
		screenPrincipal.add(donationRecordButton);
		screenPrincipal.add(expenseRecordButton);
		screenPrincipal.add(donorButton);

		jFrame.add(exitApp);
		jFrame.add(screenPrincipal);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);

		donationRecordButton.addActionListener(e -> donationScreen(jFrame));

		expenseRecordButton.addActionListener(e -> expenseScreen(jFrame));

		configurationManagerButton.addActionListener(e -> managerSettingsScreen(jFrame));

		donorButton.addActionListener(e -> donorSettingsScreen(jFrame));
		exitApp.addActionListener(e -> System.exit(1));

	}

	// Donation settings
	private void donationScreen(JFrame screen) {
		JDialog jDialog = new JDialog(screen, "Doações", false);
		jDialog.setSize(900, 400);
		jDialog.setResizable(true);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allDonationButton = new JButton("Todos as doações");
		JButton findDonorButton = new JButton("Buscar por doador");
		JButton refundDonation = new JButton("Extornar doação");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allDonationButton);
		jPanel.add(findDonorButton);
		jPanel.add(refundDonation);
		jPanel.add(returnButton);
		String[] columnNames = { "Doador", "Descrição", "Valor", "Monitor" };
		Object[][] data = {}; // Replace with actual data
		JTable jTable = new JTable(data, columnNames);

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		findDonorButton.addActionListener(e -> findDonorDonation(screen));

		returnButton.addActionListener(e -> jDialog.dispose());

	}

	// Find donor donation
	private void findDonorDonation(JFrame screen) {
		JDialog donorScreen = new JDialog(screen, "Insira o nome do doador", false);
		donorScreen.setLayout(new FlowLayout());
		JTextField donorText = new JTextField(20);
		JButton findDonorButton = new JButton("Buscar");
		JButton returnButton = new JButton("Voltar");

		donorScreen.add(donorText);
		donorScreen.add(findDonorButton);
		donorScreen.add(returnButton);

		donorScreen.setSize(300, 100);
		donorScreen.setLocationRelativeTo(screen);
		donorScreen.setVisible(true);

		returnButton.addActionListener(e -> donorScreen.dispose());
	}

	// Expense
	private void expenseScreen(JFrame screen) {
		JDialog jDialog = new JDialog(screen, "Despesas", false);
		jDialog.setSize(900, 400);
		jDialog.setResizable(true);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allExpenseButton = new JButton("Todos as doações");
		JButton findExpenseButton = new JButton("Buscar por doador");
		JButton deleteExpense = new JButton("Extornar doação");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allExpenseButton);
		jPanel.add(findExpenseButton);
		jPanel.add(deleteExpense);
		jPanel.add(returnButton);
		String[] columnNames = { "Doador", "Descrição", "Valor", "Monitor" };
		Object[][] data = {}; // Replace with actual data
		JTable jTable = new JTable(data, columnNames);

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		findExpenseButton.addActionListener(e -> findExpense(screen));

		returnButton.addActionListener(e -> jDialog.dispose());

	}

	// Find expense
	private void findExpense(JFrame screen) {
		JDialog expenseScreen = new JDialog(screen, "Insira o nome da despesa", false);
		expenseScreen.setLayout(new FlowLayout());
		JTextField expenseText = new JTextField(20);
		JButton findExpenseButton = new JButton("Buscar");
		JButton returnButton = new JButton("Voltar");
		expenseScreen.add(expenseText);
		expenseScreen.add(findExpenseButton);
		expenseScreen.add(returnButton);

		expenseScreen.setSize(300, 100);
		expenseScreen.setLocationRelativeTo(screen);

		returnButton.addActionListener(e -> expenseScreen.dispose());
		expenseScreen.setVisible(true);
	}

	// statistics screen
//	private void statisticScreen() {
//}

	// Donor settings
	private void donorSettingsScreen(JFrame screen) {

		JDialog jDialog = new JDialog(screen, "Monitoramento", false);
		jDialog.setSize(900, 400);
		jDialog.setResizable(true);

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allDonorButton = new JButton("Todos os monitores");
		JButton findDonorButton = new JButton("Buscar Doador");
		JButton alterDonorButton = new JButton("Atualizar Doador");
		JButton deleteManagerButton = new JButton("Deletar");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allDonorButton);
		jPanel.add(findDonorButton);
		jPanel.add(alterDonorButton);
		jPanel.add(deleteManagerButton);
		jPanel.add(returnButton);
		String[] columnNames = { "id", "Doador", "Telefone", "Monitor" };
		Object[][] data = {}; // Replace with actual data
		JTable jTable = new JTable(data, columnNames);

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		findDonorButton.addActionListener(e -> findDonor(screen));
		returnButton.addActionListener(e -> jDialog.dispose());

	}

	// Find donor
	private void findDonor(JFrame screen) {
		JDialog donorScreen = new JDialog(screen, "Insira o nome do doador", false);
		donorScreen.setLayout(new FlowLayout());
		JTextField donorText = new JTextField(20);
		JButton findDonorButton = new JButton("Buscar");
		JButton returnButton = new JButton("Voltar");

		donorScreen.add(donorText);
		donorScreen.add(findDonorButton);
		donorScreen.add(returnButton);

		donorScreen.setSize(300, 100);
		donorScreen.setLocationRelativeTo(screen);
		donorScreen.setVisible(true);

		returnButton.addActionListener(e -> donorScreen.dispose());
	}

	// Manager settings
	private void managerSettingsScreen(JFrame screen) {
		JPanel jPanel = new JPanel();
		JDialog jDialog = new JDialog(screen, "Monitores", false);
		List<ManagerModel> managerModelList = new ArrayList<>();

		jDialog.setSize(900, 400);
		jDialog.setResizable(true);
		jDialog.setLayout(new BorderLayout());
		jPanel.setLayout(new FlowLayout());

		JButton allManagerButton = new JButton("Todos os monitores");
		JButton createManagerButton = new JButton("Novo Monitor");
		JButton deleteManagerButton = new JButton("Deletar");
		JButton returnButton = new JButton("Voltar");

		deleteManagerButton.setBackground(Color.pink);
		jPanel.add(allManagerButton);
		jPanel.add(createManagerButton);
		jPanel.add(deleteManagerButton);
		jPanel.add(returnButton);

		String[] columnNames = { "ID", "login", "Nome", "Telefone", "Data de criação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		returnButton.addActionListener(e -> jDialog.dispose());

		createManagerButton.addActionListener(e -> formManager(screen));

		deleteManagerButton.addActionListener(e -> deleteManager(screen));

		allManagerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				managerModelList.clear();

				adminController = new AdminController();
				boolean found = adminController.findAllManager(managerModelList);

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (ManagerModel manager : managerModelList) {
						model.addRow(new Object[] { manager.getManagerId(), manager.getLogin(), manager.getName(),
								manager.getPhone(), manager.getDateManager() });
					}
					JOptionPane.showMessageDialog(screen, "Monitores encontrados: ", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum monitor encontrado ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	// Manager form
	private void formManager(JFrame screen) {

		JDialog jDialog = new JDialog(screen, "Cadastro de Monitor", true);
		jDialog.setSize(400, 400);
		jDialog.setLayout(null);
		jDialog.setResizable(false);

		JTextField nameField = new JTextField();
		JTextField loginField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JTextField phoneField = new JTextField();
		JLabel nameLabel = new JLabel("Nome:");
		JLabel loginLabel = new JLabel("Login:");
		JLabel passwordLabel = new JLabel("Senha:");
		JLabel phoneLabel = new JLabel("Telefone:");
		JButton submitButton = new JButton("Criar");
		JButton cancelButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");
		
		nameLabel.setBounds(50, 50, 100, 30);
		nameField.setBounds(150, 50, 200, 30);
		loginLabel.setBounds(50, 100, 100, 30);
		loginField.setBounds(150, 100, 200, 30);
		passwordLabel.setBounds(50, 150, 100, 30);
		passwordField.setBounds(150, 150, 200, 30);
		phoneLabel.setBounds(50, 200, 100, 30);
		phoneField.setBounds(150, 200, 200, 30);
		clearFieldsButton.setBounds(40, 300, 100, 30);
		submitButton.setBounds(150, 300, 100, 30);
		cancelButton.setBounds(260, 300, 100, 30);
		
		
		jDialog.add(clearFieldsButton);
		jDialog.add(nameLabel);
		jDialog.add(nameField);
		jDialog.add(loginLabel);
		jDialog.add(loginField);
		jDialog.add(passwordLabel);
		jDialog.add(passwordField);
		jDialog.add(phoneLabel);
		jDialog.add(phoneField);
		jDialog.add(submitButton);
		jDialog.add(cancelButton);

		clearFieldsButton.addActionListener(e -> {
			nameField.setText("");
			loginField.setText("");
			passwordField.setText("");
			phoneField.setText("");
			
			
		});
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String login = loginField.getText();
				String password = new String(passwordField.getPassword());
				String phone = phoneField.getText();

				ManagerModel manageModel = new ManagerModel();

				manageModel.setName(name);
				manageModel.setLogin(login);
				manageModel.setPassword(password);
				manageModel.setPhone(phone);

				adminController = new AdminController();

				if (adminController.createManager(manageModel) == true) {
					JOptionPane.showMessageDialog(jDialog, "Monitor Cadastrado com sucesso", "Erro",
							JOptionPane.ERROR_MESSAGE);
					jDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(jDialog, "'Login' já está em uso, por favor use outro.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog.dispose();
			}
		});

		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);
	}

	// Delete manager
	private void deleteManager(JFrame screen) {
		JDialog deleteManagerScreen = new JDialog(screen, "Id e Login do monitor", false);
		deleteManagerScreen.setLayout(new FlowLayout());
		deleteManagerScreen.setSize(300, 100);
		deleteManagerScreen.setLocationRelativeTo(screen);
		JButton clearFieldsButton = new JButton("Limpar");
		JTextField idManagerField = new JTextField(4);
		JTextField loginManagerField = new JTextField(20);
		JButton deleteManagerButton = new JButton("Excluir");
		JButton returnButton = new JButton("Voltar");

		deleteManagerButton.setBackground(Color.pink);
		deleteManagerScreen.add(idManagerField);
		deleteManagerScreen.add(loginManagerField);
		deleteManagerScreen.add(clearFieldsButton);
		deleteManagerScreen.add(deleteManagerButton);
		deleteManagerScreen.add(returnButton);

		clearFieldsButton.addActionListener(e -> {
			idManagerField.setText("");
			loginManagerField.setText("");
		});
		deleteManagerScreen.setVisible(true);
		returnButton.addActionListener(e -> deleteManagerScreen.dispose());

	}

	
	
	public static void main(String[] args) {
		AdminScreen screen = new AdminScreen();
		screen.AdminScreenPrincipal();
	}
}