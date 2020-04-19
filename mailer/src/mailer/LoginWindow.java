package mailer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordPasswordField;
	private JButton btnCancel;
	private JButton btnLogin;
	
	public LoginWindow() {
		initComponent();
	}

	public void initComponent() {
		ManagerUtils.setLookAndFeel();
		ManagerUtils.setCenterScreen(this, null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
                
		setBounds(100, 150, 410, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel titleLabel = new JLabel("LOGIN");
		titleLabel.setBounds(110, 11, 312,40);
		contentPane.add(titleLabel);
                titleLabel.setFont(new Font("Courier New", Font.BOLD,40));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("Login.png"));
                ImageIcon login = new ImageIcon(Image.getScaledInstance(40,38, Image.SCALE_SMOOTH));
                 titleLabel.setIcon(login);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                
		JLabel usernameLabel = new JLabel();
		usernameLabel.setBounds(30, 60, 312,40);
		contentPane.add(usernameLabel);
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("user.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(35,35, Image.SCALE_SMOOTH));
                 usernameLabel.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

		usernameTextField = new JTextField();
		usernameTextField.setBounds(75, 70, 250,30);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(20);

		JLabel passwordLabel = new JLabel();
		passwordLabel.setBounds(30, 100, 312,40);
		contentPane.add(passwordLabel);
                 try {
                BufferedImage Image;
                Image = ImageIO.read(new File("pass.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(35,35, Image.SCALE_SMOOTH));
                 passwordLabel.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

		passwordPasswordField = new JPasswordField();
		passwordPasswordField.setBounds(75, 110, 250,30);
		contentPane.add(passwordPasswordField);
		passwordPasswordField.setColumns(20);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(245, 150, 80, 28);
		contentPane.add(btnCancel);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(160, 150, 80, 28);
		contentPane.add(btnLogin);
		addListenter();
		setVisible(true);
	}
	
	private void addListenter() {
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnCancel)) {
			System.exit(0);
		} else if(e.getSource().equals(btnLogin)) {
			String username = usernameTextField.getText();
			String password = new String(passwordPasswordField.getPassword());
			Account.getInstance().setUsername(username);
			Account.getInstance().setPassword(password);
			if("".equals(username) || "".equals(password)) {
				JOptionPane.showMessageDialog(null, "Lỗi !!! Vui lòng không để trống mục nào");
			} else {
				if(MailReceiver.getInstance().checkLogin()) {
					MainWindow mainWindow = new MainWindow();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, " Tài khoản hoặc mật khẩu không đúng");
				}
			}
		}
	}
}
