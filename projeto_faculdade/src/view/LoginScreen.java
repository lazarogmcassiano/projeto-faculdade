package view;



import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ManagerController;


public class LoginScreen extends JFrame {


	private static final long serialVersionUID = 1L;

	public LoginScreen() {

		JFrame screen = new JFrame();
		screen.setSize(new Dimension(800, 600));
		screen.setLocationRelativeTo(null);
		screen.setResizable(false);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JLabel userLabel = new JLabel("Usuário:");
		JTextField userField = new JTextField();
		JLabel passLabel = new JLabel("Senha:");
		JPasswordField passField = new JPasswordField();
		JButton loginButton = new JButton("Login");
		JButton exitButton = new JButton("Sair");
		
		
		screen.setLayout(null);	

		userLabel.setBounds(275, 200, 100, 30);
		userField.setBounds(350, 200, 150, 30);
		passLabel.setBounds(275, 250, 100, 30);
		passField.setBounds(350, 250, 150, 30);
		loginButton.setBounds(255, 300, 125, 30);
		exitButton.setBounds(395, 300, 125, 30);

		
	
		
		exitButton.addActionListener(e -> 	System.exit(0));

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerController managerController = new ManagerController();
				String user = userField.getText();
				String pass = new String(passField.getPassword());

				if (managerController.managerLogin(user, pass) == true){
					screen.dispose();
					ManagerScreen managerScreen = new ManagerScreen();
					managerScreen.ManagerPrincipalScreen();
					return;
				}
				if (user.equals("admin") && pass.equals("admin")) {
					screen.dispose();
					AdminScreen adminScreen = new AdminScreen();
					adminScreen.AdminScreenPrincipal();
		            return; 
					
				} else {
					JOptionPane.showMessageDialog(screen, "Usuário ou senha inválidos!");
				}
			}
		});
		
		screen.add(userLabel);
		screen.add(userField);
		screen.add(passLabel);
		screen.add(passField);
		screen.add(loginButton);
		screen.add(exitButton);
		screen.setTitle("Login");
		screen.setVisible(true);

	}

	
}


