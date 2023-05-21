package com.ppd.tuples.entry;

import net.jini.core.entry.Entry;

public class UserEntry implements Entry{
	
	//private static final long serialVersionUID = 1L;
	public String userName;
	public String environmentName;
	
	public UserEntry() {}
	
	public UserEntry(String userName, String environmentName) {
		this.userName = userName;
		this.environmentName = environmentName;
	}

}
