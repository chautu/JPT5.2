package mailer;

import java.awt.EventQueue;

public class Program {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}