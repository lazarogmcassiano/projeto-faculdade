package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import feature.JNumberFormatField;
import model.DonationModel;
import model.DonorModel;
import model.ExpenseModel;
import model.ManagerModel;

public class AdminScreen extends JFrame {

	private AdminController adminController;

	private static final long serialVersionUID = 1L;

	// Admin main Screen (menu)
	public void AdminScreenPrincipal() {
		JFrame jFrame = new JFrame("Painel do Administrador(a)");
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

		generalStatisticsButton.addActionListener(e -> new StatisticScreen(jFrame));
		
		donationRecordButton.addActionListener(e -> donationScreen(jFrame));

		expenseRecordButton.addActionListener(e -> expenseScreen(jFrame));

		configurationManagerButton.addActionListener(e -> managerSettingsScreen(jFrame));

		donorButton.addActionListener(e -> donorSettingsScreen(jFrame));
		exitApp.addActionListener(e -> System.exit(1));

	}

	// Donation settings
	private void donationScreen(JFrame screen) {
		List<DonationModel> donationModelList = new ArrayList<DonationModel>();
		JDialog jDialog = new JDialog(screen, "Doações", false);
		jDialog.setSize(1000, 400);
		jDialog.setResizable(true);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allDonationButton = new JButton("Todos as doações");
		JButton findDonorButton = new JButton("Buscar por doador");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allDonationButton);
		jPanel.add(findDonorButton);
		jPanel.add(returnButton);
		String[] columnNames = { "ID", "Doador", "Descrição", "Valor", "Monitor", "Data da Criação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		// doação!
		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int row = jTable.getSelectedRow();
				jTable.setEditingRow(row);
				if (row != -2) {
					DonationModel donation = new DonationModel();
					
					donation.setDonationId((Long) jTable.getValueAt(row, 0));
					donation.setDonorName((String) jTable.getValueAt(row, 1));
					donation.setDescription((String) jTable.getValueAt(row,2));
					donation.setValue((BigDecimal) jTable.getValueAt(row, 3));
					donation.setDateDonation((String) jTable.getValueAt(row, 5));
					
					JDialog expenseDialogScreen = new JDialog(screen, "Despesa", false);
					expenseDialogScreen.setSize(new Dimension(360, 470));
					expenseDialogScreen.setLocationRelativeTo(screen);
					expenseDialogScreen.setLayout(null);

					JLabel expenseId = new JLabel("ID: " + donation.getDonationId());
					JLabel expenseName = new JLabel("Monitor(a): " + donation.getDonorName());
					JNumberFormatField expenseValue = new JNumberFormatField(new DecimalFormat("R$ #,##0.00"));
					JTextArea expenseDescriptionArea = new JTextArea();
					expenseValue.setLimit(8);
					JButton deleteDonation = new JButton("Extornar");
					JButton turnBackButton = new JButton("Voltar");
					deleteDonation.setBackground(Color.pink);
					expenseId.setBounds(165, 1, 50, 50);
					expenseName.setBounds(80, 35, 200, 30);
					expenseDescriptionArea.setBounds(50, 70, 250, 250);
					expenseValue.setBounds(135, 330, 100, 30);
					deleteDonation.setBounds(60, 380, 110, 30);
					turnBackButton.setBounds(200, 380, 110, 30);
					expenseValue.setText(donation.getValue().toString());
					expenseDescriptionArea.setText(donation.getDescription());

					expenseDialogScreen.add(turnBackButton);
					expenseDialogScreen.add(deleteDonation);
					expenseDialogScreen.add(expenseId);
					expenseDialogScreen.add(expenseName);
					expenseDialogScreen.add(expenseValue);
					expenseDialogScreen.add(expenseDescriptionArea);

					expenseDialogScreen.setVisible(true);

					deleteDonation.addActionListener(c -> {
						AdminController admin = new AdminController();

						if (admin.deleteDonation(donation.getDonationId())) {
							JOptionPane.showMessageDialog(screen, "Doação extornada", "Resultado",
									JOptionPane.INFORMATION_MESSAGE);
							expenseDialogScreen.dispose();
						} else {
							JOptionPane.showMessageDialog(screen, "Doação não extornada", "Resultado",
									JOptionPane.ERROR_MESSAGE);
						}
					});
					
					
					turnBackButton.addActionListener(b -> expenseDialogScreen.dispose());

			
				}
		
					
				}

			

		});

		allDonationButton.addActionListener(e -> {

			AdminController admin = new AdminController();

			boolean found = admin.findAllDonation(donationModelList);
			if (found) {

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (DonationModel donation : donationModelList) {
						model.addRow(new Object[] { donation.getDonationId(), donation.getDonorName(),
								donation.getDescription(), donation.getValue(), donation.getManagerName(),
								donation.getDateDonation() });
					}
					JOptionPane.showMessageDialog(screen, "Doações encontradas", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(screen, "Doação não encontrada", "Resultado",
							JOptionPane.ERROR_MESSAGE);

				}
			}
			;
		});

		findDonorButton.addActionListener(e -> {
			JDialog findDonorScreen = new JDialog(screen, "Insira o nome do doador(a)", false);
			findDonorScreen.setLayout(new FlowLayout());
			JTextField donorText = new JTextField(20);
			JButton findDonorByName = new JButton("Buscar");
			JButton returnButtonByName = new JButton("Voltar");
			jDialog.setLocationRelativeTo(screen);
			findDonorScreen.add(donorText);
			findDonorScreen.add(findDonorByName);
			findDonorScreen.add(returnButtonByName);
			findDonorScreen.setSize(300, 100);
			findDonorScreen.setLocationRelativeTo(screen);
			findDonorScreen.setVisible(true);

				
			
				findDonorByName.addActionListener(b -> {

					AdminController admin = new AdminController();
						System.out.println(donorText.getText());
					boolean found = admin.findDonationByDonorName(donationModelList, donorText.getText());
					if (found) {

						DefaultTableModel model = (DefaultTableModel) jTable.getModel();
						model.setRowCount(0);

						if (found) {
							for (DonationModel donation : donationModelList) {
								model.addRow(new Object[] { donation.getDonationId(), donation.getDonorName(),
										donation.getDescription(), donation.getValue(), donation.getManagerName(),
										donation.getDateDonation() });
							}
							findDonorScreen.dispose();
							JOptionPane.showMessageDialog(screen, "Doações encontradas", "Resultado",
									JOptionPane.INFORMATION_MESSAGE
									);

						} else {
							JOptionPane.showMessageDialog(screen, "Doação não encontrada", "Resultado",
									JOptionPane.ERROR_MESSAGE);

						}
					}
					;
				});

				returnButtonByName.addActionListener(b -> findDonorScreen.dispose());
		});

		returnButton.addActionListener(e -> jDialog.dispose());

	}

	// Expense
	private void expenseScreen(JFrame screen) {
		List<ExpenseModel> expenseModelList = new ArrayList<>();
		JDialog jDialog = new JDialog(screen, "Despesas", false);
		jDialog.setSize(900, 400);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setResizable(true);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allExpenseButton = new JButton("Todos as doações");
		JButton findExpenseButton = new JButton("Buscar por doador");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allExpenseButton);
		jPanel.add(findExpenseButton);
		jPanel.add(returnButton);
		String[] columnNames = { "ID", "Monitor", "Descrição", "Valor", "Data da despesa" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));
		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setVisible(true);

		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ExpenseModel expense = new ExpenseModel();

				int row = jTable.getSelectedRow();
				jTable.setEditingRow(row);
				if (row != -1) {
					expense.setExpenseId((Long) jTable.getValueAt(row, 0));
					expense.setManagerName((String) jTable.getValueAt(row, 1));
					expense.setDescription((String) jTable.getValueAt(row, 2));
					expense.setValue((BigDecimal) jTable.getValueAt(row, 3));
					expense.setDateExpense((String) jTable.getValueAt(row, 4));
				}
				;

				JDialog expenseDialogScreen = new JDialog(screen, "Despesa", false);
				expenseDialogScreen.setSize(new Dimension(360, 470));
				expenseDialogScreen.setLocationRelativeTo(screen);
				expenseDialogScreen.setLayout(null);

				JLabel expenseId = new JLabel("ID: " + expense.getExpenseId());
				JLabel expenseName = new JLabel("Monitor(a): " + expense.getManagerName());
				JNumberFormatField expenseValue = new JNumberFormatField(new DecimalFormat("R$ #,##0.00"));
				JTextArea expenseDescriptionArea = new JTextArea();
				expenseValue.setLimit(8);
				JButton deleteExpense = new JButton("Apagar");
				JButton turnBackButton = new JButton("Voltar");

				expenseId.setBounds(165, 1, 50, 50);
				expenseName.setBounds(80, 35, 200, 30);
				expenseDescriptionArea.setBounds(50, 70, 250, 250);
				expenseValue.setBounds(135, 330, 100, 30);
				deleteExpense.setBounds(60, 380, 100, 30);
				turnBackButton.setBounds(200, 380, 100, 30);
				expenseValue.setText(expense.getValue().toString());
				expenseDescriptionArea.setText(expense.getDescription());

				expenseDialogScreen.add(turnBackButton);
				expenseDialogScreen.add(deleteExpense);
				expenseDialogScreen.add(expenseId);
				expenseDialogScreen.add(expenseName);
				expenseDialogScreen.add(expenseValue);
				expenseDialogScreen.add(expenseDescriptionArea);

				expenseDialogScreen.setVisible(true);

				turnBackButton.addActionListener(b -> expenseDialogScreen.dispose());

				deleteExpense.addActionListener(c -> {
					AdminController admin = new AdminController();

					if (admin.deleteExpense(expense)) {
						JOptionPane.showMessageDialog(screen, "Despesa deletada", "Resultado",
								JOptionPane.INFORMATION_MESSAGE);
						expenseDialogScreen.dispose();
					} else {
						JOptionPane.showMessageDialog(screen, "Despesa não deletada", "Resultado",
								JOptionPane.ERROR_MESSAGE);
					}
				});
			}
		});

		allExpenseButton.addActionListener(e -> {

			expenseModelList.clear();

			AdminController admin = new AdminController();

			boolean found = admin.findAllExpense(expenseModelList);

			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			model.setRowCount(0);

			if (found) {
				for (ExpenseModel expenseModel : expenseModelList) {
					model.addRow(new Object[] { expenseModel.getExpenseId(), expenseModel.getManagerName(),
							expenseModel.getDescription(), expenseModel.getValue(), expenseModel.getDateExpense() });
				}
				JOptionPane.showMessageDialog(screen, "Despesas encontradas", "Resultado",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(screen, "Despesas não encontradas", "Resultado",
						JOptionPane.ERROR_MESSAGE);

			}

		});

		findExpenseButton.addActionListener(e -> {

			JDialog expenseScreen = new JDialog(screen, "Insira o nome do monitor(a)", false);
			expenseScreen.setLayout(new FlowLayout());
			JTextField expenseText = new JTextField(20);
			JButton findExpenseByNameButton = new JButton("Buscar");
			JButton returnNameButton = new JButton("Voltar");
			expenseScreen.add(expenseText);
			expenseScreen.add(findExpenseByNameButton);
			expenseScreen.add(returnNameButton);

			expenseScreen.setSize(300, 100);
			expenseScreen.setLocationRelativeTo(screen);
			expenseScreen.setVisible(true);

			findExpenseByNameButton.addActionListener(c -> {
				expenseModelList.clear();
				AdminController admin = new AdminController();

				boolean found = admin.findAllExpenseByManagerName(expenseText.getText(), expenseModelList);

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (ExpenseModel expenseModel : expenseModelList) {
						model.addRow(new Object[] { expenseModel.getExpenseId(), expenseModel.getManagerName(),
								expenseModel.getDescription(), expenseModel.getValue(),
								expenseModel.getDateExpense() });
					}
					JOptionPane.showMessageDialog(screen, "Despesas Encontradas", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Despesas Encontradas", "Resultado",
							JOptionPane.ERROR_MESSAGE);

				}

			});
			returnNameButton.addActionListener(t -> expenseScreen.dispose());

		});

		returnButton.addActionListener(e -> jDialog.dispose());

	}

	// Donor settings
	private void donorSettingsScreen(JFrame screen) {
		List<DonorModel> donorModelList = new ArrayList<DonorModel>();
		JDialog jDialog = new JDialog(screen, "Doadores", false);
		jDialog.setSize(900, 400);
		jDialog.setResizable(true);

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());

		JButton allDonorButton = new JButton("Todos os doadores");
		JButton findDonorButton = new JButton("Buscar doador");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allDonorButton);
		jPanel.add(findDonorButton);
		jPanel.add(returnButton);
		String[] columnNames = { "id", "Doador", "Telefone", "Monitor", "Data de Criação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int row = jTable.getSelectedRow();
				jTable.setEditingRow(row);
				if (row != -1) {
					DonorModel donorModelTable = new DonorModel();
					donorModelTable.setDonorId((Long) jTable.getValueAt(row, 0));
					donorModelTable.setName((String) jTable.getValueAt(row, 1));
					donorModelTable.setPhone((String) jTable.getValueAt(row, 2));
					donorModelTable.setManagerName((String) jTable.getValueAt(row, 3));
					donorModelTable.setDateDonor((String) jTable.getValueAt(row, 4));

					JDialog donorTableScreen = new JDialog(screen, "Doadores", false);
					donorTableScreen.setLocationRelativeTo(screen);
					donorTableScreen.setLayout(null);
					donorTableScreen.setSize(new Dimension(360, 300));
					JLabel idLabel = new JLabel("ID: " + donorModelTable.getDonorId());
					JLabel donorNameLabel = new JLabel("Nome: " + donorModelTable.getName());
					JLabel donorPhoneLabel = new JLabel("Telefone: " + donorModelTable.getPhone());
					JButton updateDonor = new JButton("Atualizar");
					JButton deleteManager = new JButton("Deletar");
					JButton turnBack = new JButton("Voltar");
					deleteManager.setBackground(Color.pink);

					idLabel.setBounds(10, 10, 200, 30);
					donorNameLabel.setBounds(10, 50, 200, 30);
					donorPhoneLabel.setBounds(10, 90, 200, 30);
					updateDonor.setBounds(20, 200, 100, 30);
					deleteManager.setBounds(130, 200, 100, 30);
					turnBack.setBounds(240, 200, 100, 30);
					donorTableScreen.add(updateDonor);
					donorTableScreen.add(deleteManager);
					donorTableScreen.add(turnBack);
					donorTableScreen.add(idLabel);
					donorTableScreen.add(donorNameLabel);
					donorTableScreen.add(donorPhoneLabel);
					donorTableScreen.setVisible(true);

					updateDonor.addActionListener(b -> formDonorUpdate(screen, donorModelTable));

					deleteManager.addActionListener(b -> {
						
						AdminController adminC = new AdminController();
						boolean delManager = adminC.deleteDonor(donorModelTable.getDonorId());
							
						if(delManager) {
							
							JOptionPane.showMessageDialog(screen, "Doador(a) deletado ", "Resultado",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(screen, "Doador(a) não deletado", "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
					
					
					turnBack.addActionListener(c -> donorTableScreen.dispose());

				}
			}
		}

		);

		findDonorButton.addActionListener(e -> {
			JDialog donorScreen = new JDialog(screen, "Insira o nome do doador", false);
			donorScreen.setLayout(new FlowLayout());
			JTextField donorText = new JTextField(20);
			JButton findDonorByNameButton = new JButton("Buscar");
			JButton returnButtonByName = new JButton("Voltar");

			donorScreen.add(donorText);
			donorScreen.add(findDonorByNameButton);
			donorScreen.add(returnButtonByName);

			donorScreen.setSize(300, 100);
			donorScreen.setLocationRelativeTo(screen);
			donorScreen.setVisible(true);

			findDonorByNameButton.addActionListener(c -> {
				donorModelList.clear();
				AdminController admin = new AdminController();

				boolean found = admin.findAllDonorByName(donorModelList, donorText.getText());

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (DonorModel donor : donorModelList) {
						model.addRow(new Object[] { donor.getDonorId(), donor.getName(), donor.getPhone(),
								donor.getManagerName(), donor.getDateDonor() });
					}
					JOptionPane.showMessageDialog(screen, "Doadores encontrados: ", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum doador(a) encontrado ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			});

			returnButtonByName.addActionListener(t -> donorScreen.dispose());
		});

		returnButton.addActionListener(e -> jDialog.dispose());

		allDonorButton.addActionListener(e -> {
			donorModelList.clear();
			AdminController admin = new AdminController();

			boolean found = admin.findAllDonor(donorModelList);

			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			model.setRowCount(0);

			if (found) {
				for (DonorModel donor : donorModelList) {
					model.addRow(new Object[] { donor.getDonorId(), donor.getName(), donor.getPhone(),
							donor.getManagerName(), donor.getDateDonor() });
				}
				JOptionPane.showMessageDialog(screen, "Doadores encontrados: ", "Resultado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(screen, "Nenhum doador(a) encontrado ", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		});

	}

	// Find donor
	// Manager settings
	private void managerSettingsScreen(JFrame screen) {
		JPanel jPanel = new JPanel();
		JDialog managerScreen = new JDialog(screen, "Monitores", false);
		List<ManagerModel> managerModelList = new ArrayList<>();

		managerScreen.setSize(900, 400);
		managerScreen.setResizable(true);
		managerScreen.setLayout(new BorderLayout());
		managerScreen.setLocationRelativeTo(screen);
		jPanel.setLayout(new FlowLayout());

		JButton allManagerButton = new JButton("Todos os monitores");
		JButton createManagerButton = new JButton("Novo monitor");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allManagerButton);
		jPanel.add(createManagerButton);

		jPanel.add(returnButton);

		String[] columnNames = { "ID", "login", "Nome", "Telefone", "Data de criação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jTable.getSelectedRow();
				jTable.setEditingRow(row);
				if (row != -1) {
					ManagerModel managerModel = new ManagerModel();

					managerModel.setManagerId((Long) jTable.getValueAt(row, 0));
					managerModel.setLogin((String) jTable.getValueAt(row, 1));
					managerModel.setName((String) jTable.getValueAt(row, 2));
					managerModel.setDateManager((String) jTable.getValueAt(row, 3));

					JDialog jDialog = new JDialog(screen, "", false);
					jDialog.setLocationRelativeTo(screen);
					jDialog.setLayout(null);
					jDialog.setSize(new Dimension(360, 300));
					JLabel idLabel = new JLabel("ID: " + managerModel.getManagerId());
					JLabel loginLabel = new JLabel("Login: " + managerModel.getLogin());
					JLabel nameLabel = new JLabel("Nome: " + managerModel.getName());
					JButton updateManager = new JButton("Atualizar");
					JButton deleteManager = new JButton("Deletar");
					JButton turnBack = new JButton("Voltar");
					deleteManager.setBackground(Color.pink);

					idLabel.setBounds(10, 10, 200, 30);
					loginLabel.setBounds(10, 50, 200, 30);
					nameLabel.setBounds(10, 90, 200, 30);
					updateManager.setBounds(20, 200, 100, 30);
					deleteManager.setBounds(130, 200, 100, 30);
					turnBack.setBounds(240, 200, 100, 30);
					jDialog.add(updateManager);
					jDialog.add(deleteManager);
					jDialog.add(turnBack);
					jDialog.add(idLabel);
					jDialog.add(loginLabel);
					jDialog.add(nameLabel);
					jDialog.setVisible(true);

					updateManager.addActionListener(c -> {
						formManagerUpdate(screen, managerModel);
						jDialog.dispose();
					});
						
					deleteManager.addActionListener(b -> {
						
						AdminController adminC = new AdminController();
						boolean delManager = adminC.deleteManager(managerModel.getManagerId());
							
						if(delManager) {
							
							JOptionPane.showMessageDialog(screen, "Monitor(a) deletado ", "Resultado",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(screen, "Monitor(a) não deletado", "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
					turnBack.addActionListener(b -> jDialog.dispose());
				}
			}
		});

		managerScreen.add(new JScrollPane(jTable), BorderLayout.CENTER);
		managerScreen.add(jPanel, BorderLayout.SOUTH);
		managerScreen.setVisible(true);

		returnButton.addActionListener(e -> managerScreen.dispose());

		createManagerButton.addActionListener(e -> formManager(screen));

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
					JOptionPane.showMessageDialog(screen, "Nenhum monitor(a) encontrado ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	// ManagerUpdateScreen
	public void formManagerUpdate(JFrame screen, ManagerModel managerModel) {
		JDialog jDialog = new JDialog(screen, "Atualizar Monitor", true);
		jDialog.setSize(400, 400);
		jDialog.setLayout(null);
		jDialog.setResizable(false);
		jDialog.setLocationRelativeTo(screen);
		JTextField nameField = new JTextField();
		JLabel loginField = new JLabel();
		JPasswordField passwordField = new JPasswordField();
		JTextField phoneField = new JTextField();
		JLabel nameLabel = new JLabel("Nome:");
		JLabel loginLabel = new JLabel("Login:");
		JLabel passwordLabel = new JLabel("Senha:");
		JLabel phoneLabel = new JLabel("Telefone:");
		JLabel idLabel = new JLabel("ID: " + managerModel.getManagerId());
		JButton submitButton = new JButton("Atualizar");
		JButton cancelButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");
		loginField.setText(managerModel.getLogin());

		idLabel.setBounds(50, 10, 100, 30);
		loginLabel.setBounds(50, 50, 100, 30);
		loginField.setBounds(150, 50, 200, 30);
		nameLabel.setBounds(50, 100, 100, 30);
		nameField.setBounds(150, 100, 200, 30);
		passwordLabel.setBounds(50, 150, 100, 30);
		passwordField.setBounds(150, 150, 200, 30);
		phoneLabel.setBounds(50, 200, 100, 30);
		phoneField.setBounds(150, 200, 200, 30);
		clearFieldsButton.setBounds(40, 300, 100, 30);
		submitButton.setBounds(150, 300, 100, 30);
		cancelButton.setBounds(260, 300, 100, 30);

		jDialog.add(idLabel);
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

		clearFieldsButton.addActionListener(b -> {
			nameField.setText("");
			loginField.setText("");
			passwordField.setText("");
			phoneField.setText("");

		});

		submitButton.addActionListener(e -> {
			String passText = new String(passwordField.getPassword());

			managerModel.setName((String) nameField.getText());
			managerModel.setPassword(passText);
			managerModel.setPhone(phoneField.getText());

			AdminController admin = new AdminController();

			if (!admin.findManagerByNameAndPhone(managerModel)) {
				admin.updateManager(managerModel);
				JOptionPane.showMessageDialog(jDialog, "monitor(a) atualizado com sucesso!", "information",
						JOptionPane.INFORMATION_MESSAGE);
				jDialog.dispose();

			} else {
				JOptionPane.showMessageDialog(jDialog, "Nome e/ou telefone Já em uso!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);
	}

	// Manager form
	private void formManager(JFrame screen) {

		JDialog jDialog = new JDialog(screen, "Cadastro de Monitor", true);
		jDialog.setSize(400, 400);
		jDialog.setLayout(null);
		jDialog.setResizable(false);
		jDialog.setLocationRelativeTo(screen);
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
				ManagerModel manageModel = new ManagerModel();
				String name = nameField.getText();
				String login = loginField.getText();
				String password = new String(passwordField.getPassword());
				String phone = phoneField.getText();

				manageModel.setName(name);
				manageModel.setLogin(login);
				manageModel.setPassword(password);
				manageModel.setPhone(phone);

				adminController = new AdminController();

				if (adminController.createManager(manageModel) == true) {
					JOptionPane.showMessageDialog(jDialog, "Monitor cadastrado com sucesso", "Erro",
							JOptionPane.INFORMATION_MESSAGE);
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

	public void formDonorUpdate(JFrame screen, DonorModel donorModel) {
		JDialog jDialog = new JDialog(screen, "Atualizar Doador(a)", true);
		jDialog.setSize(400, 400);
		jDialog.setLayout(null);
		jDialog.setResizable(false);
		jDialog.setLocationRelativeTo(screen);
		JTextField nameField = new JTextField();
		JLabel loginField = new JLabel();
		JTextField phoneField = new JTextField();
		JLabel nameLabel = new JLabel("Nome:");
		JLabel loginLabel = new JLabel("Nome Atual: " + donorModel.getName());
		JLabel phoneLabel = new JLabel("Telefone:");

		JLabel idLabel = new JLabel("ID: " + donorModel.getDonorId());
		JButton submitButton = new JButton("Atualizar");
		JButton cancelButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");

		idLabel.setBounds(50, 10, 100, 30);
		loginLabel.setBounds(50, 50, 400, 30);
		loginField.setBounds(150, 50, 200, 30);
		nameLabel.setBounds(50, 100, 100, 30);
		nameField.setBounds(150, 100, 200, 30);
		phoneLabel.setBounds(50, 150, 100, 30);
		phoneField.setBounds(150, 150, 200, 30);
		clearFieldsButton.setBounds(40, 250, 100, 30);
		submitButton.setBounds(150, 250, 100, 30);
		cancelButton.setBounds(260, 250, 100, 30);

		jDialog.add(idLabel);
		jDialog.add(clearFieldsButton);
		jDialog.add(nameLabel);
		jDialog.add(nameField);
		jDialog.add(loginLabel);
		jDialog.add(loginField);
		jDialog.add(phoneLabel);

		jDialog.add(phoneField);
		jDialog.add(submitButton);
		jDialog.add(cancelButton);

		clearFieldsButton.addActionListener(b -> {
			nameField.setText("");
			phoneField.setText("");

		});

		submitButton.addActionListener(e -> {

			donorModel.setName((String) nameField.getText());
			donorModel.setPhone(phoneField.getText());

			AdminController admin = new AdminController();

			if (admin.updateDonor(donorModel)) {

				JOptionPane.showMessageDialog(jDialog, "Doador(a) atualizado com sucesso!", "information",
						JOptionPane.INFORMATION_MESSAGE);
				jDialog.dispose();

			} else {
				JOptionPane.showMessageDialog(jDialog, "Nome e/ou telefone Já em uso!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);
	}

	public static void main(String[] args) {
		AdminScreen screen = new AdminScreen();
		screen.AdminScreenPrincipal();
	}

}