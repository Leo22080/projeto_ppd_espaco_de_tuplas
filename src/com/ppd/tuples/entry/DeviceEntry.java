package com.ppd.tuples.entry;

import net.jini.core.entry.Entry;

public class DeviceEntry implements Entry{

	private static final long serialVersionUID = 1L;
	public String environmentName;
	public String deviceName;
	
	public DeviceEntry() {}
	
	
	public DeviceEntry(String deviceName, String environmentName) {
		this.deviceName = deviceName;
		this.environmentName = environmentName;
	}	

}
