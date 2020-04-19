package mailer;

import java.awt.Font;
import javax.swing.*;
import javax.mail.MessagingException;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.*;
import javax.imageio.ImageIO;


public class MainWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable messagesTable;
	private JScrollPane messagesTableScrollPane;
	private JTextArea messageBodyTextArea;
	private JScrollPane messageBodyTextAreaScrollPane;
	private DefaultTableModel defaultTableModel;
	private JButton btnDraft;

	private JButton btnExit;
	private ArrayList<Gmail> gmails;
	private JButton btnSpam;
	private JButton btnSent;
	private JButton btnTrash;
	private JButton btnInbox;
	private JButton btnCompose;
	private RowPopup rowPopup;

	private void initComponent() {
		ManagerUtils.setLookAndFeel();
		ManagerUtils.setCenterScreen(this, null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,910, 500);
		setTitle("Đồ án lập trình mạng");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Gmail");
		menuBar.add(mnNewMenu);
                mnNewMenu.setFont(new Font("Courier New", Font.PLAIN,25));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("gmail.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(35,35, Image.SCALE_SMOOTH));
                 mnNewMenu.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

		JMenuItem mntmNewMenuItem = new JMenuItem("Thoat");
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		setContentPane(contentPane);

		btnInbox = new JButton("Hộp thư đến");
                btnInbox.setSize(80,30);
                btnInbox.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("hopthuden.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnInbox.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		btnSpam = new JButton("Spam");
                btnSpam.setSize(80,30);
                btnSpam.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("spam.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnSpam.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

		btnDraft = new JButton("Thư nháp");
                btnDraft.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("thunhap.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnDraft.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                

		btnCompose = new JButton("Soạn");
                btnCompose.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("soanthu.jpg"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnCompose.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                

		btnExit = new JButton("Thoát");
                btnExit.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("thoat.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnExit.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                

		messagesTable = new JTable();
		messagesTableScrollPane = new JScrollPane();
		messagesTableScrollPane.setViewportView(messagesTable);
		defaultTableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Người gửi", "Chủ đề", "Thời gian" });
		messagesTable.setModel(defaultTableModel);
		rowPopup = new RowPopup(messagesTable);

		messagesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int rowIndex = messagesTable.getSelectedRow();
					messageBodyTextArea.setText(gmails.get(rowIndex).getContent());
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					rowPopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});

		JLabel lblContent = new JLabel("Nội dung:");
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("noidung.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(22,22, Image.SCALE_SMOOTH));
                 lblContent.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		messageBodyTextArea = new JTextArea();
		messageBodyTextArea.setEnabled(false);
		messageBodyTextArea.setColumns(20);
		messageBodyTextArea.setRows(20);
		messageBodyTextAreaScrollPane = new JScrollPane();
		messageBodyTextAreaScrollPane.setViewportView(messageBodyTextArea);

		btnSent = new JButton("Hộp thư đi");
                btnSent.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("dagui.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnSent.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		JLabel lblDanhSchTin = new JLabel("Danh sách tin");
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("danhmuc.png"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(22,22, Image.SCALE_SMOOTH));
                 lblDanhSchTin.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		btnTrash = new JButton("Thư rác");
                btnTrash.setFont(new Font("", Font.PLAIN,12));
                try {
                BufferedImage Image;
                Image = ImageIO.read(new File("rac.jpg"));
                ImageIcon user = new ImageIcon(Image.getScaledInstance(20,20, Image.SCALE_SMOOTH));
                 btnTrash.setIcon(user);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

		GroupLayout layout = new GroupLayout(contentPane);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addComponent(messagesTableScrollPane, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(btnInbox).addGap(26)
						.addComponent(btnSent).addGap(32).addComponent(btnSpam).addGap(29)
						.addComponent(btnDraft, GroupLayout.PREFERRED_SIZE,110, GroupLayout.PREFERRED_SIZE).addGap(29)
						.addComponent(btnTrash, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(34)
						.addComponent(btnCompose, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE).addGap(27)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE).addGap(60))
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(messageBodyTextAreaScrollPane, GroupLayout.DEFAULT_SIZE, 792,
										Short.MAX_VALUE)
								.addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblContent).addContainerGap(746,
						Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblDanhSchTin, GroupLayout.PREFERRED_SIZE,120, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(712, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(btnInbox)
								.addComponent(btnDraft).addComponent(btnSent).addComponent(btnSpam)
								.addComponent(btnTrash).addComponent(btnCompose).addComponent(btnExit))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblDanhSchTin)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(messagesTableScrollPane, GroupLayout.PREFERRED_SIZE, 129,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblContent)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(messageBodyTextAreaScrollPane, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
						.addContainerGap()));
		contentPane.setLayout(layout);
		addListenter();
		setVisible(true);
	}

	private void addListenter() {
		btnExit.addActionListener(this);
		btnDraft.addActionListener(this);
		btnSpam.addActionListener(this);
		btnSent.addActionListener(this);
		btnTrash.addActionListener(this);
		btnInbox.addActionListener(this);
		btnCompose.addActionListener(this);
	}

	public MainWindow() {
		initComponent();
		new Thread(new Runnable() {
			@Override
			public void run() {
				gmails = MailReceiver.getInstance().findInboxMail();
				addRows();
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnExit)) {
			System.exit(0);
		} else if (e.getSource().equals(btnDraft)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					gmails = MailReceiver.getInstance().findDraftMail();
					addRows();
				}
			}).start();

		} else if (e.getSource().equals(btnSpam)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					gmails = MailReceiver.getInstance().findSpamMail();
					addRows();
				}
			}).start();

		} else if (e.getSource().equals(btnSent)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					gmails = MailReceiver.getInstance().findSentMail();
					addRows();
				}
			}).start();

		} else if (e.getSource().equals(btnTrash)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					gmails = MailReceiver.getInstance().findTrashMail();
					addRows();
				}
			}).start();

		} else if (e.getSource().equals(btnInbox)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					gmails = MailReceiver.getInstance().findInboxMail();
					addRows();
				}
			}).start();
		} else if (e.getSource().equals(btnCompose)) {
			new SendMailWindow();
		}
	}

	private void addRows() {
		defaultTableModel.setNumRows(0);
		for (Gmail gmail : gmails) {
			String[] row = { gmail.getFrom(), gmail.getSubject(), gmail.getDate().toString() };
			defaultTableModel.addRow(row);
		}
	}

	static class RowPopup extends JPopupMenu {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RowPopup(JTable table) {
			JMenuItem dowload = new JMenuItem("download");
			dowload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() >= 0) {
						try {
							MailReceiver.getInstance().download(table.getSelectedRow());
						} catch (MessagingException | IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng");
					}
				}
			});

			add(dowload);
		}
	}
	
}
