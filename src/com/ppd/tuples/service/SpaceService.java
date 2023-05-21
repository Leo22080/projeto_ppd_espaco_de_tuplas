package com.ppd.tuples.service;

import net.jini.core.entry.Entry;
import net.jini.space.JavaSpace;

public class SpaceService {
	private JavaSpace space;
	
	public SpaceService() {

		System.out.println("Procurando pelo servico JavaSpace...");
		Lookup finder = new Lookup(JavaSpace.class);
		JavaSpace space = (JavaSpace) finder.getService();

		if (space == null) {
			System.out.println("O servico JavaSpace nao foi encontrado. Encerrando...");
			System.exit(-1);
		}
		System.out.println("O servico JavaSpace foi encontrado.");
		this.space = space;

	}
	
	
	public Boolean write(Entry entry, Long timeout) { 		
		try {
			space.write(entry, null, timeout);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Entry read(Entry template, Long timeout) {
		try {
			Entry entry = space.read(template, null, timeout);
			return entry;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Entry take(Entry template, Long timeout) {
		try {
			Entry entry = space.take(template, null, timeout);
			return entry;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
