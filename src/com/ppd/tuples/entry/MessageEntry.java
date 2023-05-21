package com.ppd.tuples.entry;

import net.jini.core.entry.Entry;

public class MessageEntry implements Entry{
	
	private static final long serialVersionUID = 1L;
	public String content;
	public String sender;
	public String destiny;
	
	public MessageEntry() {
	}
	
	public MessageEntry(String content, String sender, String destiny) {
		this.content = content;
		this.sender = sender;
		this.destiny = destiny;
	}
	
}
