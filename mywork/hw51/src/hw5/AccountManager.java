package hw5;

import java.util.HashMap;

public class AccountManager {
	public static AccountManager getInstance() {
		if (am == null) am = new AccountManager();
		return am;
	}
	
	public boolean isCorrectPassword(String name, String password) {
		if (accountMap.containsKey(name) &&
				accountMap.get(name).compareTo(password)==0) {
			return true;
		}
		return false;
	}
	
	public boolean addNewAccount(String name, String password) {
		if (accountMap.containsKey(name)) return false;
		accountMap.put(name,  password);
		return true;
	}
	
	private static AccountManager am = null;
	private AccountManager() {
		accountMap = new HashMap<String, String>();
		accountMap.put("wandou", "mybaby");
	}

	private HashMap<String, String> accountMap;
	
}
