package org.fabrelab.chayiba.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import weibo4j.http.AccessToken;

public class AccessTokenStore {

	Map<String, AccessToken> tokenMap = new HashMap<String, AccessToken>();
	Map<String, Account> accountMap = new HashMap<String, Account>();

	public static String MAIN_ROBOT_ID = "2725359715";

	public static String[] ASSISTANTS = new String[]{"2731428951","2807126064"};
	
	{
		accountMap.put(MAIN_ROBOT_ID, new Account("linhailue@qq.com", MAIN_ROBOT_ID, "131$sinaa90"));
		accountMap.put("2731428951", new Account("1574433678@qq.com", "2731428951", "chayiba"));
		accountMap.put("2807126064", new Account("2371644015@qq.com", "2807126064", "chayiba"));
	}
	
	public static final Random RAND = new Random();
	
	public void add(String uid, AccessToken token) {
		tokenMap.put(uid, token);
	}

	public AccessToken get(String userId) {
		return tokenMap.get(userId);
	}

	public void setTokenMap(Map<String, AccessToken> tokenMap) {
		this.tokenMap = tokenMap;
	}

	public String getPassword(String userId) {
		return accountMap.get(userId).password;
	}

	public String getAccount(String userId) {
		return accountMap.get(userId).accountName;
	}

	public String getRandomUserId() {
		return ASSISTANTS[RAND.nextInt(ASSISTANTS.length)];
	}

	public String getMainRobotId() {
		return MAIN_ROBOT_ID;
	}

	public static class Account{
		public String accountName;
		public String userId;
		public String password;

		public Account(String accountName, String userId, String password) {
			super();
			this.accountName = accountName;
			this.userId = userId;
			this.password = password;
		}
		
	}
	
}
