package mailer;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public final class ManagerUtils {
	private ManagerUtils() {

	}

	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
		} catch (Exception e) {
		}
	}

	public static void setCenterScreen(JFrame f, JDialog diaglog) {
		if (f != null)
			f.setLocationRelativeTo(null);
		else if (diaglog != null)
			diaglog.setLocationRelativeTo(null);
	}
}
