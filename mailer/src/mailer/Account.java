package mailer;

public class Account {
	private String username;
	private String password;
	private static Account instance;
	
	public static Account getInstance() {
		if(instance == null) {
			instance = new Account();
		}
		return instance;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
