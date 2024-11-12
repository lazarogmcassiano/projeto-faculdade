package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import controller.StatisticController;

public class StatisticScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	public StatisticScreen(JFrame screen) {
		JDialog jDialog = new JDialog(screen, "Estatísticas", false);
		jDialog.setSize(new Dimension(300, 300));
		jDialog.setLocationRelativeTo(null);

		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		JButton monthButton = new JButton("Dados Mensal");
		JButton expenseByManagerButton = new JButton("Despesas");
		JButton donationByDonorButton = new JButton("Doações");
		monthButton.addActionListener(e -> screenExpenseAndDonation());
		expenseByManagerButton.addActionListener(e -> screenExpenseByManager());
		donationByDonorButton.addActionListener(e -> screenDonationByDonor());
		expenseByManagerButton.setBounds(50,40,200,30);
		donationByDonorButton.setBounds(50,90,200,30);
		monthButton.setBounds(50,140,200,30);
		mainPanel.add(expenseByManagerButton);
		mainPanel.add(donationByDonorButton);
		mainPanel.add(monthButton);
		jDialog.add(mainPanel);
		jDialog.setVisible(true);
	}

	public JFreeChart graphicExpenseAndDonation(Map<String, BigDecimal[]> data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Map.Entry<String, BigDecimal[]> entry : data.entrySet()) {
			String month = entry.getKey();
			BigDecimal[] values = entry.getValue();
			dataset.addValue(values[0], "Despesa", month);
			dataset.addValue(values[1], "Doação", month);
		}

		return ChartFactory.createBarChart("Despesas vs Doações", "Mês/Meses", "Valor", dataset,
				PlotOrientation.VERTICAL, true, true, false);
	}

	public void screenExpenseAndDonation() {
		JDialog jDialogExpenseAndDonation = new JDialog(this, "Despesas e Gastos", false);
		jDialogExpenseAndDonation.setSize(900, 600);
		jDialogExpenseAndDonation.setLayout(new BorderLayout());


		JLabel monthStartComboLabel = new JLabel("Escolha entre o mês: ");
		JLabel monthEndComboLabel = new JLabel("Até o mês: ");
		JComboBox<String> monthStartCombo = new JComboBox<>();
		JComboBox<String> monthEndCombo = new JComboBox<>();
		JButton findDataButton = new JButton("Procurar dados");
		JButton turnBack = new JButton("Voltar");

		for (int year = LocalDate.now().getYear(); year <= 2040; year++) {
			for (int month = 1; month <= 12; month++) {
				String monthStr = String.format("%02d", month);
				String yearStr = String.format("%02d", year % 100);
				monthStartCombo.addItem(monthStr + "/" + yearStr);
				monthEndCombo.addItem(monthStr + "/" + yearStr);
			}
		}

		findDataButton.addActionListener(e -> {
			String startMonth = (String) monthStartCombo.getSelectedItem();
			String endMonth = (String) monthEndCombo.getSelectedItem();

			if (startMonth.compareTo(endMonth) > 0) {
				JOptionPane.showMessageDialog(jDialogExpenseAndDonation,
						"O mês inicial não pode ser maior que o mês final.", "Período inválido",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {

				Map<String, BigDecimal[]> data = new StatisticController().getValueMounthexpenseAndDonation(startMonth,
						endMonth);

				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(jDialogExpenseAndDonation,
							"Nenhum dado encontrado para o período selecionado.", "Sem Dados",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JFreeChart chart = graphicExpenseAndDonation(data);

				ChartPanel chartPanel = new ChartPanel(chart);
				chartPanel.setPreferredSize(new Dimension(800, 400));
				jDialogExpenseAndDonation.add(chartPanel, BorderLayout.CENTER);

				jDialogExpenseAndDonation.revalidate();
				jDialogExpenseAndDonation.repaint();

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(jDialogExpenseAndDonation,
						"Erro ao acessar os dados do banco de dados: " + ex.getMessage(), "Erro de SQL",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jDialogExpenseAndDonation, "Erro inesperado: " + ex.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		turnBack.addActionListener(e -> jDialogExpenseAndDonation.dispose());
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(monthStartComboLabel);
		southPanel.add(monthStartCombo);
		southPanel.add(monthEndComboLabel);
		southPanel.add(monthEndCombo);
		southPanel.add(findDataButton);
		southPanel.add(turnBack);
		jDialogExpenseAndDonation.add(southPanel, BorderLayout.SOUTH);

		jDialogExpenseAndDonation.setLocationRelativeTo(this);
		jDialogExpenseAndDonation.setVisible(true);
	}

	public JFreeChart graphicExpenseByManager(Map<String, BigDecimal> data) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Map.Entry<String, BigDecimal> entry : data.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue());
		}


		return ChartFactory.createPieChart("Distribuição de Despesas", dataset, true, true, false);
	}

	public void screenExpenseByManager() {
		JDialog jDialogExpenseAndDonation = new JDialog(this, "Despesas por Monitor", false);
		jDialogExpenseAndDonation.setSize(900, 600);
		jDialogExpenseAndDonation.setLayout(new BorderLayout());

		JLabel monthStartComboLabel = new JLabel("Escolha o mês: ");
		JButton turnBack = new JButton("Voltar");
		JComboBox<String> monthStartCombo = new JComboBox<>();
		JButton findDataButton = new JButton("Procurar dados");

		for (int year = 2024; year <= 2040; year++) {
			for (int month = 1; month <= 12; month++) {
				String monthStr = String.format("%02d", month);
				String yearStr = String.format("%04d", year);
				monthStartCombo.addItem(monthStr + "/" + yearStr);
			}
		}

		findDataButton.addActionListener(e -> {
			String selectedMonth = (String) monthStartCombo.getSelectedItem();
			String[] monthYear = selectedMonth.split("/");

			String year = monthYear[1];
			String month = monthYear[0];

			try {

				StatisticController statisticController = new StatisticController();

				Map<String, BigDecimal> data = statisticController.getExpensesByManager(year, month);

				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(jDialogExpenseAndDonation,
							"Nenhum dado encontrado para o período selecionado.", "Sem Dados",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JFreeChart chart = graphicExpenseByManager(data);

				ChartPanel chartPanel = new ChartPanel(chart);
				chartPanel.setPreferredSize(new Dimension(800, 400));
				jDialogExpenseAndDonation.add(chartPanel, BorderLayout.CENTER);

				jDialogExpenseAndDonation.revalidate();
				jDialogExpenseAndDonation.repaint();

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(jDialogExpenseAndDonation,
						"Erro ao acessar os dados do banco de dados: " + ex.getMessage(), "Erro de SQL",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jDialogExpenseAndDonation, "Erro inesperado: " + ex.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		turnBack.addActionListener(e -> jDialogExpenseAndDonation.dispose());
		
		JPanel southPanel = new JPanel();
		southPanel.add(monthStartComboLabel);
		southPanel.add(monthStartCombo);
		southPanel.add(findDataButton);
		southPanel.add(turnBack);
		jDialogExpenseAndDonation.add(southPanel, BorderLayout.SOUTH);

		jDialogExpenseAndDonation.setLocationRelativeTo(this);
		jDialogExpenseAndDonation.setVisible(true);
	}

	public JFreeChart graphicDonationByDonor(Map<String, BigDecimal> data) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Map.Entry<String, BigDecimal> entry : data.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue());
		}

	
		return ChartFactory.createPieChart("Distribuição de Doações por Doador",
				dataset,
				true, 
				true, 
				false 
		);
	}

	public void screenDonationByDonor() {
		JDialog jDialogDonationByDonor = new JDialog(this, "Doações por Doador", false);
		jDialogDonationByDonor.setSize(900, 600);
		jDialogDonationByDonor.setLayout(new BorderLayout());

		JLabel monthStartComboLabel = new JLabel("Escolha o mês: ");
		JComboBox<String> monthStartCombo = new JComboBox<>();
		JButton findDataButton = new JButton("Procurar dados");
		JButton turnBack = new JButton("Voltar");
		for (int year = 2024; year <= 2040; year++) {
			for (int month = 1; month <= 12; month++) {
				String monthStr = String.format("%02d", month);
				String yearStr = String.format("%04d", year);
				monthStartCombo.addItem(monthStr + "/" + yearStr);
			}
		}

		findDataButton.addActionListener(e -> {
			String selectedMonth = (String) monthStartCombo.getSelectedItem();
			String[] monthYear = selectedMonth.split("/");

			String year = monthYear[1];
			String month = monthYear[0];

			try {

				StatisticController statisticController = new StatisticController();

				Map<String, BigDecimal> data = statisticController.getDonationByDonor(year, month);

				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(jDialogDonationByDonor,
							"Nenhum dado encontrado para o período selecionado.", "Sem Dados",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				JFreeChart chart = graphicDonationByDonor(data);

				ChartPanel chartPanel = new ChartPanel(chart);
				chartPanel.setPreferredSize(new Dimension(800, 400));
				jDialogDonationByDonor.add(chartPanel, BorderLayout.CENTER);
				jDialogDonationByDonor.revalidate();
				jDialogDonationByDonor.repaint();

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(jDialogDonationByDonor,
						"Erro ao acessar os dados do banco de dados: " + ex.getMessage(), "Erro de SQL",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jDialogDonationByDonor, "Erro inesperado: " + ex.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		turnBack.addActionListener(e -> jDialogDonationByDonor.dispose());

		JPanel southPanel = new JPanel();
		southPanel.add(monthStartComboLabel);
		southPanel.add(monthStartCombo);
		southPanel.add(findDataButton);
		southPanel.add(turnBack);
		jDialogDonationByDonor.add(southPanel, BorderLayout.SOUTH);
		jDialogDonationByDonor.setLocationRelativeTo(this);
		jDialogDonationByDonor.setVisible(true);
	}

}