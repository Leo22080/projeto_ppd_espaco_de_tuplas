package com.ppd.tuples.entry;

import net.jini.core.entry.Entry;

public class EnvironmentEntry implements Entry{

	private static final long serialVersionUID = 1L;
	public String nameEnvironment;
	public Integer nUsers;
	public Integer nDevices;
	
	public EnvironmentEntry() {}

	public EnvironmentEntry(String name, Integer users, Integer devices) {
		this.nameEnvironment = name;
		this.nUsers = users;
		this.nDevices = devices;
	}

}
