package mailer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class SendMailWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField toTextField;
	private JTextField ccTextField;
	private JTextField bccTextField;
	private JTextField subjectTextField;
	private JButton btnSend;
	private JTextField attachTextField;
	private JButton btnBrowse;
	private JTextArea messageBodyTextArea;
	private JScrollPane messageBodyTextAreaScrollPane;

	private ArrayList<String> toList;
	private ArrayList<String> ccList;
	private ArrayList<String> bccList;

	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String content;
	private File[] files;

	public SendMailWindow() {
		setResizable(false);
		ManagerUtils.setLookAndFeel();
		ManagerUtils.setCenterScreen(this, null);
		initComponent();
		setVisible(true);
	}

	private void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 540, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel toLabel = new JLabel("To");
		toLabel.setBounds(10, 11, 78, 20);
		contentPane.add(toLabel);

		toTextField = new JTextField();
		toTextField.setBounds(98, 11, 288, 30);
		contentPane.add(toTextField);
		toTextField.setColumns(10);

		JLabel ccLabel = new JLabel("Cc");
		ccLabel.setBounds(10, 60, 46, 14);
		contentPane.add(ccLabel);

		ccTextField = new JTextField();
		ccTextField.setBounds(98, 52, 288, 30);
		contentPane.add(ccTextField);
		ccTextField.setColumns(10);

		btnSend = new JButton("Gửi");
		btnSend.setBounds(396, 10,80, 30);
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("guithu.jpg"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(22,22, Image.SCALE_SMOOTH));
                 btnSend.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
		contentPane.add(btnSend);

		JLabel bccLabel = new JLabel("Bcc");
		bccLabel.setBounds(10, 108, 46, 14);
		contentPane.add(bccLabel);

		bccTextField = new JTextField();
		bccTextField.setBounds(98, 95, 288, 30);
		contentPane.add(bccTextField);
		bccTextField.setColumns(10);

		JLabel subjectLabel = new JLabel("Subject");
		subjectLabel.setBounds(10, 144, 63, 14);
		contentPane.add(subjectLabel);

		subjectTextField = new JTextField();
		subjectTextField.setBounds(98, 136, 288, 30);
		contentPane.add(subjectTextField);
		subjectTextField.setColumns(10);

		JLabel contentLabel = new JLabel("Content");
		contentLabel.setBounds(10, 287, 63, 14);
		contentPane.add(contentLabel);

		messageBodyTextArea = new JTextArea();

		messageBodyTextAreaScrollPane = new JScrollPane();
		messageBodyTextAreaScrollPane.setViewportView(messageBodyTextArea);
		messageBodyTextAreaScrollPane.setBounds(98, 218, 288, 156);
		contentPane.add(messageBodyTextAreaScrollPane);

		JLabel attachLabel = new JLabel("Attach file");
		attachLabel.setBounds(10, 185, 78, 14);
		contentPane.add(attachLabel);

		attachTextField = new JTextField();
		attachTextField.setBounds(98, 177, 288, 30);
		contentPane.add(attachTextField);
		attachTextField.setColumns(10);

		btnBrowse = new JButton("");
		btnBrowse.setBounds(396, 177, 30, 30);
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("paper.jpg"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnBrowse.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
		contentPane.add(btnBrowse);

		addListenter();
	}

	private void addListenter() {
		btnSend.addActionListener(this);
		btnBrowse.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnSend)) {
			getValue();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (!"".equals(to)) {
							toList = parseRecipientType(to);
							if (!"".equals(cc)) {
								ccList = parseRecipientType(cc);
							}
							if (!"".equals(bcc)) {
								bccList = parseRecipientType(bcc);
							}
							JOptionPane.showMessageDialog(null, "Gửi thành công");
							MailSender.getInstance().sendMail(toList, ccList, bccList, subject, content, files);

						} else {
							JOptionPane.showMessageDialog(null, "Vui lòng gửi ít nhất một người");
						}
					} catch (MessagingException | IOException e) {
						JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
					}
				}
			}).start();
		} else if (e.getSource().equals(btnBrowse)) {
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);
			if (chooser.showOpenDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
				files = chooser.getSelectedFiles();
				String name="";
				for (File f : files) {
					name = name + f.getAbsolutePath() + " ";
				}
				attachTextField.setText(name);
			}
		}
	}

	private void getValue() {
		to = toTextField.getText();
		cc = ccTextField.getText();
		bcc = bccTextField.getText();
		subject = subjectTextField.getText();
		content = subjectTextField.getText();
	}

	private ArrayList<String> parseRecipientType(String what) {
		ArrayList<String> result = new ArrayList<>();
		if (!"".equals(what)) {
			if (what.contains(",")) {
				String[] splited = what.split(",");
				for (int i = 0; i < splited.length; i++) {
					if (!"".equals(splited[i]))
						result.add(splited[i].trim());
				}
			} else {
				result.add(what);
			}
		}
		return result;
	}
}
