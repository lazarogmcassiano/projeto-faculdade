package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import controller.ManagerController;
import feature.JNumberFormatField;
import model.DonationModel;
import model.DonorModel;
import model.ExpenseModel;

public class ManagerScreen {

	private ManagerController managerController;

	// Manager main Screen (menu)
	public void ManagerPrincipalScreen() {
		JFrame screen = new JFrame("Painel do Monitor");
		screen.setResizable(false);
		JPanel screenPrincipal = new JPanel();

		screen.setSize(new Dimension(500, 600));
		screen.setLayout(new BorderLayout());

		JButton donation = new JButton("Realizar Doação");
		JButton createDonorButton = new JButton("Adicionar Doador");
		JButton donationRecordButton = new JButton("Doações");
		JButton donorSettingsButton = new JButton("Doadores");
		JButton expenseRecordButton = new JButton("Minhas Despesas");
		JButton exitApp = new JButton("Sair");

		donation.setBounds(150, 130, 200, 30);
		createDonorButton.setBounds(150, 180, 200, 30);
		donationRecordButton.setBounds(150, 230, 200, 30);
		donorSettingsButton.setBounds(150, 280, 200, 30);
		expenseRecordButton.setBounds(150, 330, 200, 30);
		exitApp.setBounds(150, 380, 200, 30);

		screenPrincipal.add(donation);
		screenPrincipal.add(createDonorButton);
		screenPrincipal.add(donationRecordButton);
		screenPrincipal.add(expenseRecordButton);
		screenPrincipal.add(donorSettingsButton);
		screenPrincipal.add(exitApp);
		screenPrincipal.setLayout(null);

		screen.add(screenPrincipal);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
		screen.setLocationRelativeTo(null);

		donation.addActionListener(e -> donationScreen(screen));

		exitApp.addActionListener(e -> System.exit(1));

		createDonorButton.addActionListener(e -> formDonor(screen));

		donorSettingsButton.addActionListener(e -> donorSettingsScreen(screen));

		donationRecordButton.addActionListener(e -> donationFind(screen));
		
		expenseRecordButton.addActionListener(e -> managerExpenseScreen(screen));

	}

	private void formDonor(JFrame screen) {

		JDialog jDialog = new JDialog(screen, "Cadastro de Doador(a)");
		jDialog.setSize(400, 250);
		jDialog.setLayout(null);

		JTextField nameField = new JTextField();
		JTextField DonorphoneField = new JTextField();

		JLabel nameLabel = new JLabel("Nome:");
		JLabel phoneLabel = new JLabel("Telefone:");
		JButton submitButton = new JButton("Criar");
		JButton cancelButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");

		nameLabel.setBounds(50, 50, 100, 30);
		nameField.setBounds(150, 50, 200, 30);
		phoneLabel.setBounds(50, 100, 100, 30);
		DonorphoneField.setBounds(150, 100, 200, 30);
		clearFieldsButton.setBounds(40, 170, 100, 30);
		submitButton.setBounds(150, 170, 100, 30);
		cancelButton.setBounds(260, 170, 100, 30);

		jDialog.add(clearFieldsButton);
		jDialog.add(nameLabel);
		jDialog.add(nameField);

		jDialog.add(phoneLabel);
		jDialog.add(DonorphoneField);
		jDialog.add(submitButton);
		jDialog.add(cancelButton);

		clearFieldsButton.addActionListener(e -> {
			nameField.setText("");
			DonorphoneField.setText("");

		});

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String phone = DonorphoneField.getText();

				DonorModel donorModel = new DonorModel();

				donorModel.setName(name);
				donorModel.setPhone(phone);

				managerController = new ManagerController();

				if (managerController.createDonor(donorModel.getName(), donorModel) == true) {
					JOptionPane.showMessageDialog(jDialog, "Doador cadastrado com sucesso", "Erro",
							JOptionPane.ERROR_MESSAGE);
					jDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(jDialog, "'Nome' já está em uso, por favor use outro.", "Erro",
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

		jDialog.setLocationRelativeTo(screen);
		jDialog.setResizable(false);
		jDialog.setVisible(true);

	}

	private void managerExpenseScreen(JFrame screen) {
		JDialog jDialog = new JDialog(screen, "Minhas Despesas", false);
		JPanel jPanel = new JPanel();
		List<ExpenseModel> expenseModelList = new ArrayList<>();

		jDialog.setSize(900, 400);
		jDialog.setResizable(true);
		jDialog.setLayout(new BorderLayout());
		jPanel.setLayout(new FlowLayout());

		JButton allExpenseButton = new JButton("Minhas despesas");
		JButton createManagerButton = new JButton("Nova despesa");
		JButton returnButton = new JButton("Voltar");

		jPanel.add(allExpenseButton);
		jPanel.add(createManagerButton);
		jPanel.add(returnButton);

		String[] columnNames = { "Descrição", "Valor", "Data da despesa" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jTable.getSelectedRow();
				if (row != -1) {
					String expenseDescription = (String) jTable.getValueAt(row, 0);
					BigDecimal expenseValue = new BigDecimal(jTable.getValueAt(row, 1).toString());
					String expenseDate = (String) jTable.getValueAt(row, 2);
					findByTableExpense(screen, expenseDescription, expenseValue, expenseDate);
				}
			}
		});

		allExpenseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				expenseModelList.clear();

				managerController = new ManagerController();
				boolean found = managerController.findAllExpense(expenseModelList);

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {

					for (ExpenseModel expense : expenseModelList) {
						model.addRow(new Object[] { expense.getDescription(), expense.getValue(),
								expense.getDateExpense() });
					}
					JOptionPane.showMessageDialog(screen, "Despesa encontrada: ", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum monitor encontrado ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		createManagerButton.addActionListener(e -> expenseForm(screen));

		returnButton.addActionListener(e -> jDialog.dispose());
		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

	}

	public static void main(String[] args) {
		ManagerScreen manangerScreen = new ManagerScreen();
		manangerScreen.ManagerPrincipalScreen();
	}

	private void donorSettingsScreen(JFrame screen) {
		List<DonorModel> donorModelList = new ArrayList<DonorModel>();
		JDialog jDialog = new JDialog(screen, "Doadores", false);
		JPanel jPanel = new JPanel();

		JButton allDonorButton = new JButton("Todos os doadores");
		JButton findDonorButton = new JButton("Buscar doador");
		JButton returnButton = new JButton("Voltar");

		jDialog.setSize(600, 400);
		jDialog.setResizable(true);
		jPanel.setLayout(new FlowLayout());

		jPanel.add(allDonorButton);
		jPanel.add(findDonorButton);
		jPanel.add(returnButton);
		String[] columnNames = { "Doador", "Telefone", "Data da criação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		findDonorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog donorFindDialog = new JDialog(screen, "Insira o nome do doador", false);
				donorFindDialog.setLayout(new FlowLayout());
				donorFindDialog.setSize(300, 100);
				donorFindDialog.setLocationRelativeTo(screen);
				JButton findDonorButton = new JButton("Buscar");
				JButton clearFieldsButton = new JButton("Limpar");
				JTextField donorFindField = new JTextField(20);
				JButton returnButton = new JButton("Voltar");

				donorFindDialog.add(donorFindField);
				donorFindDialog.add(clearFieldsButton);
				donorFindDialog.add(findDonorButton);
				donorFindDialog.add(returnButton);

				clearFieldsButton.addActionListener(b -> {
					donorFindField.setText("");
				});

				findDonorButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						donorModelList.clear();

						managerController = new ManagerController();
						boolean found = managerController.findDonor(donorFindField.getText(), donorModelList);

						DefaultTableModel model = (DefaultTableModel) jTable.getModel();
						model.setRowCount(0);

						if (found) {
							for (DonorModel donor : donorModelList) {
								model.addRow(new Object[] { donor.getName(), donor.getPhone(), donor.getDateDonor() });
							}
							JOptionPane.showMessageDialog(screen, "Doador encontrado: ", "Resultado",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(screen, "Nenhum doador encontrado ", "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				donorFindDialog.setVisible(true);
				returnButton.addActionListener(b -> donorFindDialog.dispose());

			}
		});
		returnButton.addActionListener(e -> jDialog.dispose());

		allDonorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				donorModelList.clear();

				managerController = new ManagerController();
				boolean found = managerController.findAllDonor(donorModelList);

				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (DonorModel donor : donorModelList) {
						model.addRow(new Object[] { donor.getName(), donor.getPhone(), donor.getDateDonor() });
					}
					JOptionPane.showMessageDialog(screen, "Doadores encontrados: ", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum doador encontrado ", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	private void expenseForm(JFrame screen) {

		JDialog jDialog = new JDialog(screen, "Registrar nova despesa");
		jDialog.setSize(600, 400);
		jDialog.setLayout(null);

		JTextArea expenseDescription = new JTextArea();
		JLabel expenseDescriptionLabel = new JLabel("descrição:");
		JLabel expenseValueLabel = new JLabel("valor:");
		JButton submitButton = new JButton("Gerar");
		JButton cancelButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");
		JNumberFormatField expenseValue = new JNumberFormatField(new DecimalFormat(" #,##0.00"));
		expenseValue.setLimit(7);
		expenseDescription.setFont(new Font("Serif", Font.ITALIC, 12));
		expenseDescription.setLineWrap(true);
		expenseDescription.setWrapStyleWord(true);

		expenseDescriptionLabel.setBounds(30, 30, 100, 40);
		expenseDescription.setBounds(130, 40, 400, 200);
		expenseValueLabel.setBounds(20, 250, 50, 30);

		expenseValue.setBounds(70, 250, 80, 30);
		cancelButton.setBounds(365, 300, 100, 30);
		submitButton.setBounds(255, 300, 100, 30);
		clearFieldsButton.setBounds(145, 300, 100, 30);

		jDialog.add(expenseDescription);
		jDialog.add(expenseDescriptionLabel);
		jDialog.add(expenseValueLabel);
		jDialog.add(expenseValue);
		jDialog.add(clearFieldsButton);
		jDialog.add(submitButton);
		jDialog.add(cancelButton);

		clearFieldsButton.addActionListener(e -> {
			expenseDescription.setText("");
			expenseValue.setText("");

		});

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String description = expenseDescription.getText();
				String valueText = expenseValue.getText();
				BigDecimal value = new BigDecimal(valueText);

				ExpenseModel expenseModel = new ExpenseModel();

				expenseModel.setDescription(description);
				expenseModel.setValue(value);

				managerController = new ManagerController();

				if (managerController.createExpense(expenseModel)) {
					JOptionPane.showMessageDialog(jDialog, "Despesa registrada com sucesso", "Erro",
							JOptionPane.ERROR_MESSAGE);
					jDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(jDialog, "Ocorreu algum erro.", "Erro", JOptionPane.ERROR_MESSAGE);
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
		jDialog.setResizable(false);
		jDialog.setVisible(true);

	}

	private void donationScreen(JFrame screen) {
		List<DonorModel> donorModelList = new ArrayList<>();
		JDialog jDialog = new JDialog(screen, "Doadores", false);
		JPanel jPanel = new JPanel();

		JButton anonymousDonationButton = new JButton("Doação Anônima");
		JButton donationByDonorButton = new JButton("Por doador");
		JButton returnButton = new JButton("Voltar");

		jDialog.setSize(400, 400);
		jDialog.setResizable(true);
		jPanel.setLayout(new FlowLayout());

		jPanel.add(donationByDonorButton);
		jPanel.add(anonymousDonationButton);
		jPanel.add(returnButton);
		String[] columnNames = { "Doador", "Telefone" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jTable.getSelectedRow();
				if (row != -1) {
					String donorName = (String) jTable.getValueAt(row, 0);
					String donorPhone = (String) jTable.getValueAt(row, 1);
					donationForm(screen, donorName, donorPhone);
				}
			}
		});

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		donationByDonorButton.addActionListener(e -> {
			JDialog donorFindDialog = new JDialog(screen, "Insira o nome do doador", false);
			donorFindDialog.setLayout(new FlowLayout());
			donorFindDialog.setSize(300, 100);
			donorFindDialog.setLocationRelativeTo(screen);
			JButton findDonorButton = new JButton("Buscar");
			JButton clearFieldsButton = new JButton("Limpar");
			JTextField donorFindField = new JTextField(20);
			JButton donorReturnButton = new JButton("Voltar");

			donorFindDialog.add(donorFindField);
			donorFindDialog.add(clearFieldsButton);
			donorFindDialog.add(findDonorButton);
			donorFindDialog.add(donorReturnButton);

			clearFieldsButton.addActionListener(b -> donorFindField.setText(""));

			findDonorButton.addActionListener(e1 -> {
				donorModelList.clear();
				managerController = new ManagerController();
				boolean found = managerController.findDonor(donorFindField.getText(), donorModelList);
				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (DonorModel donor : donorModelList) {
						model.addRow(new Object[] { donor.getName(), donor.getPhone() });
					}
					donorFindDialog.dispose();
					JOptionPane.showMessageDialog(screen, "Doador encontrado!", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum doador encontrado", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			donorFindDialog.setVisible(true);
			donorReturnButton.addActionListener(b -> donorFindDialog.dispose());
		});

		returnButton.addActionListener(e -> jDialog.dispose());

		anonymousDonationButton.addActionListener(e -> donationFormAnonymous(screen));
	
	}

	private void donationForm(JFrame screen, String donorName,String donorPhone) {
		JDialog donationDialog = new JDialog(screen, "Doação", false);
		donationDialog.setSize(500, 450);
		donationDialog.setLocationRelativeTo(screen);
		donationDialog.setLayout(null);
		JTextField donorNameField = new JTextField(20);
		JTextArea donorDescriptionField = new JTextArea();
		JNumberFormatField donorValueField = new JNumberFormatField(new DecimalFormat("#,##0.00"));
		donorValueField.setLimit(7);
		JTextField donorPhoneField = new JTextField(15);
		JLabel donorNameLabel = new JLabel("Nome:");
		JLabel donorPhoneLabel = new JLabel("Telefone:");
		JLabel donorDescriptionLabel = new JLabel("Descrição:");
		JLabel donorValueLabel = new JLabel("Valor:");
		JButton submitButton = new JButton("Gerar");
		JButton donorReturnButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");

		
		donorNameField.setText(donorName);
		donorPhoneField.setText(donorPhone);
		donorDescriptionField.setFont(new Font("Serif", Font.ITALIC, 12));
		donorDescriptionField.setLineWrap(true);
		donorDescriptionField.setWrapStyleWord(true);

		JScrollPane scrooll = new JScrollPane(donorDescriptionField);

		scrooll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		donorDescriptionField.setText("");
		donorNameField.setEnabled(false);
		donorPhoneField.setEnabled(false);
		donorNameLabel.setBounds(10, 25, 100, 30);
		donorNameField.setBounds(60, 25, 180, 30);
		donorPhoneLabel.setBounds(250, 25, 100, 30);
		donorPhoneField.setBounds(320, 25, 150, 30);
		donorDescriptionLabel.setBounds(30, 80, 100, 30);
		donorDescriptionField.setBounds(120, 80, 300, 200);
		donorValueLabel.setBounds(20, 330, 50, 30);
		donorValueField.setBounds(85, 330, 80, 30);
		clearFieldsButton.setBounds(170, 330, 100, 30);
		submitButton.setBounds(280, 330, 100, 30);
		donorReturnButton.setBounds(390, 330, 100, 30);
		
		
		
		donationDialog.add(scrooll);
		donationDialog.add(donorNameLabel);
		donationDialog.add(donorNameField);
		donationDialog.add(donorDescriptionLabel);
		donationDialog.add(donorDescriptionField);
		donationDialog.add(donorPhoneLabel);
		donationDialog.add(donorPhoneField);
		donationDialog.add(donorValueLabel);
		donationDialog.add(donorValueField);
		donationDialog.add(clearFieldsButton);
		donationDialog.add(submitButton);
		donationDialog.add(donorReturnButton);

		clearFieldsButton.addActionListener(b -> {
			donorDescriptionField.setText("");
			donorValueField.setText("");
		});

		submitButton.addActionListener(b -> {
			String description = donorDescriptionField.getText();
			String value = donorValueField.getText();
			BigDecimal valueDecimal = new BigDecimal(value);
			System.out.println(value);
			ManagerController managerController = new ManagerController();
			if(managerController.findDonorByNameAndPhone(donorName, donorPhone) == true) {
				managerController.createDonation(description, valueDecimal);
				JOptionPane.showMessageDialog(screen, "Doação realizada com sucesso!", "Resultado",
						JOptionPane.INFORMATION_MESSAGE);
				donationDialog.dispose();
			};
	

		});

		donorReturnButton.addActionListener(b -> donationDialog.dispose());

		donationDialog.setVisible(true);
	}

	private void findByTableExpense(JFrame screen, String description, BigDecimal value, String date) {
		JDialog donationDialog = new JDialog(screen, "Doação", false);
		donationDialog.setSize(600, 400);
		donationDialog.setLocationRelativeTo(screen);
		donationDialog.setLayout(null);

		JLabel expenseDateValue = new JLabel();
		JTextArea expenseDescription = new JTextArea();
		JNumberFormatField expenseValueField = new JNumberFormatField(new DecimalFormat("#,##0.00"));
		JLabel expenseDescriptionLabel = new JLabel("Descrição:");
		JLabel expenseValueLabel = new JLabel("Valor:");
		JButton expenseReturnButton = new JButton("Voltar");

		String valueExpense = value.toString();
		expenseDateValue.setText(date);
		expenseDescription.setText(description);
		expenseValueField.setText("R$" + valueExpense);

		expenseDescription.setFont(new Font("Serif", Font.ITALIC, 12));
		expenseDescription.setLineWrap(true);
		expenseDescription.setWrapStyleWord(true);

		JScrollPane scrooll = new JScrollPane(expenseDescription);

		scrooll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		expenseDateValue.setBounds(240, 7, 300, 30);
		expenseDescriptionLabel.setBounds(30, 30, 100, 40);
		expenseDescription.setBounds(130, 40, 400, 200);
		expenseValueLabel.setBounds(20, 270, 50, 30);

		expenseValueField.setBounds(70, 270, 80, 30);
		expenseReturnButton.setBounds(250, 300, 100, 30);

		donationDialog.add(expenseDateValue);
		donationDialog.add(expenseDescription);
		donationDialog.add(expenseDescriptionLabel);
		donationDialog.add(expenseValueLabel);
		donationDialog.add(expenseValueField);
		donationDialog.add(expenseReturnButton);

		expenseReturnButton.addActionListener(b -> donationDialog.dispose());

		donationDialog.setVisible(true);
	}

	
	private void donationFormAnonymous(JFrame screen) {
		JDialog donationDialog = new JDialog(screen, "Doação", false);
		donationDialog.setSize(500, 450);
		donationDialog.setLocationRelativeTo(screen);
		donationDialog.setLayout(null);
		JLabel donorNameField = new JLabel("Doação anônima");
		JTextArea donorDescriptionField = new JTextArea();
		JNumberFormatField donorValueField = new JNumberFormatField(new DecimalFormat("#,##0.00"));
		donorValueField.setLimit(7);
		JLabel donorDescriptionLabel = new JLabel("Descrição:");
		JLabel donorValueLabel = new JLabel("Valor:");
		JButton submitButton = new JButton("Gerar");
		JButton donorReturnButton = new JButton("Cancelar");
		JButton clearFieldsButton = new JButton("Limpar");

		
		donorDescriptionField.setFont(new Font("Serif", Font.ITALIC, 12));
		donorDescriptionField.setLineWrap(true);
		donorDescriptionField.setWrapStyleWord(true);

		JScrollPane scrooll = new JScrollPane(donorDescriptionField);

		scrooll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		donorDescriptionField.setText("");
		donorNameField.setBounds(200, 25, 250, 30);
		donorDescriptionLabel.setBounds(30, 80, 100, 30);
		donorDescriptionField.setBounds(120, 80, 300, 200);
		donorValueLabel.setBounds(20, 330, 50, 30);
		donorValueField.setBounds(85, 330, 80, 30);
		clearFieldsButton.setBounds(170, 330, 100, 30);
		submitButton.setBounds(280, 330, 100, 30);
		donorReturnButton.setBounds(390, 330, 100, 30);

		donationDialog.add(scrooll);
		donationDialog.add(donorNameField);
		donationDialog.add(donorDescriptionLabel);
		donationDialog.add(donorDescriptionField);
		donationDialog.add(donorValueLabel);
		donationDialog.add(donorValueField);
		donationDialog.add(clearFieldsButton);
		donationDialog.add(submitButton);
		donationDialog.add(donorReturnButton);

		clearFieldsButton.addActionListener(b -> {
			donorDescriptionField.setText("");
			donorValueField.setText("");
		});

		submitButton.addActionListener(b -> {
			String description = donorDescriptionField.getText();
			String value = donorValueField.getText();
			BigDecimal valueDecimal = new BigDecimal(value);
			System.out.println(value);
			ManagerController managerController = new ManagerController();
			
				managerController.createAnonymousDonation(description, valueDecimal);
				JOptionPane.showMessageDialog(screen, "Doação realizada com sucesso!", "Resultado",
						JOptionPane.INFORMATION_MESSAGE);
				donationDialog.dispose();


		});

		donorReturnButton.addActionListener(b -> donationDialog.dispose());

		donationDialog.setVisible(true);
	}
	
	public void donationFind(JFrame screen) {
		List<DonationModel> donationModelList = new ArrayList<>();
		JDialog jDialog = new JDialog(screen, "Doadores", false);
		JPanel jPanel = new JPanel();

		JButton anonymousDonationButton = new JButton("Doação Anônima");
		JButton donationByDonorButton = new JButton("Por doador");
		JButton returnButton = new JButton("Voltar");

		jDialog.setSize(400, 400);
		jDialog.setResizable(true);
		jPanel.setLayout(new FlowLayout());

		jPanel.add(donationByDonorButton);
		jPanel.add(anonymousDonationButton);
		jPanel.add(returnButton);
		String[] columnNames = { "Doador", "Telefone", "Data da doação" };
		JTable jTable = new JTable(new DefaultTableModel(columnNames, 0));

		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jTable.getSelectedRow();
				if (row != -1) {
					String donorName = (String) jTable.getValueAt(row, 0);
					String donorPhone = (String) jTable.getValueAt(row, 1);
					String donorDate = (String) jTable.getValueAt(row,2);
					donationForm(screen, donorName, donorPhone);
				}
			}
		});

		jDialog.add(new JScrollPane(jTable), BorderLayout.CENTER);
		jDialog.add(jPanel, BorderLayout.SOUTH);
		jDialog.setLocationRelativeTo(screen);
		jDialog.setVisible(true);

		donationByDonorButton.addActionListener(e -> {
			JDialog donorFindDialog = new JDialog(screen, "Insira o nome do doador", false);
			donorFindDialog.setLayout(new FlowLayout());
			donorFindDialog.setSize(300, 100);
			donorFindDialog.setLocationRelativeTo(screen);
			JButton findDonorButton = new JButton("Buscar");
			JButton clearFieldsButton = new JButton("Limpar");
			JTextField donorFindField = new JTextField(20);
			JButton donorReturnButton = new JButton("Voltar");

			donorFindDialog.add(donorFindField);
			donorFindDialog.add(clearFieldsButton);
			donorFindDialog.add(findDonorButton);
			donorFindDialog.add(donorReturnButton);

			clearFieldsButton.addActionListener(b -> donorFindField.setText(""));

			findDonorButton.addActionListener(e1 -> {
				donationModelList.clear();
				managerController = new ManagerController();
				boolean found = managerController.findDonationByManagerId(donationModelList);
				DefaultTableModel model = (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);

				if (found) {
					for (DonationModel donation : donationModelList) {
						model.addRow(new Object[] { donation.getDescription(), donation.getValue() });
					}
					donorFindDialog.dispose();
					JOptionPane.showMessageDialog(screen, "Doações encontrado!", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(screen, "Nenhum doador encontrado", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			donorFindDialog.setVisible(true);
			donorReturnButton.addActionListener(b -> donorFindDialog.dispose());
		});

		returnButton.addActionListener(e -> jDialog.dispose());

		anonymousDonationButton.addActionListener(e -> donationFormAnonymous(screen));
	
	
	}
}
