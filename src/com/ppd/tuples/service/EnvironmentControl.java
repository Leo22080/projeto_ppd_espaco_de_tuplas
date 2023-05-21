package com.ppd.tuples.service;

import com.ppd.tuples.entry.DeviceEntry;
import com.ppd.tuples.entry.EnvironmentEntry;
import com.ppd.tuples.entry.UserEntry;

import net.jini.core.lease.Lease;

import com.ppd.tuples.entry.MessageEntry;
import java.util.ArrayList;

public class EnvironmentControl {
	
	private SpaceService spaceService;
	private int proxUsers, proxEvironment, proxDevice;
	private static long TAKE_TIMEOUT = 3 * 1000;
	private static long READ_TIMEOUT = 1000;
	private static long MESSAGE_TIMEOUT = 5* 60 * 1000;
	private ArrayList<String> evironments = new ArrayList<String>();
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> devices = new ArrayList<String>();
	
	public EnvironmentControl() {
		this.spaceService = new SpaceService();
		this.proxEvironment = 1;
		this.proxDevice = 1;
		this.proxUsers = 1;
		
		/*
		ControlEntry template = new ControlEntry(null, null, null);
		ControlEntry controlEntry = 
				(ControlEntry) this.spaceService.read(template, READ_TIMEOUT);
		if(controlEntry != null) {
			
			this.proxEvironment += controlEntry.evironments.size();
			this.proxDevice  += controlEntry.devices.size();
			this.proxUsers = +controlEntry.users.size();
			
			this.evironments = controlEntry.evironments;
			this.devices = controlEntry.devices;
			this.users = controlEntry.users;
			
			
		}else {
			this.spaceService.write(new ControlEntry(evironments, users, devices), Lease.FOREVER);
		}
		*/

	}
	
	public String getStringEnvironment() {
		int index = this.evironments.size() - 1;
		return this.evironments.get(index);
	}
	
	public String getUserString() {
		int index = this.users.size() - 1;
		return this.users.get(index);
	}
	
	public String getDeviceString() {
		int index = this.devices.size() - 1;
		return this.devices.get(index);		
	}

	/*
	public void controlUpdate() {
		ControlEntry template = new ControlEntry(null, null, null);
		ControlEntry controlEntry = 
				(ControlEntry) this.spaceService.take(template, TAKE_TIMEOUT);
		
		this.spaceService.write(new ControlEntry(this.evironments, this.users, this.devices), Lease.FOREVER);
		
	}
	*/
	
	/*
	 * Cria uma tupla vazia (nome_do_ambiente, numUsuarios=0, numDevices=0)	 * 
	 */
	public void createEnvironment() {
			
		String evironmentName = "amb" + this.proxEvironment++;
		this.evironments.add(evironmentName);
		this.spaceService.write(new EnvironmentEntry(evironmentName ,0, 0), Lease.FOREVER);
		System.out.println("criando o ambiente " + evironmentName);
		//this.controlUpdate();
	}
	
	/*
	 * Cria a tupla do usuário, com seu nome e o nome do ambiente
	 * do tipo (nome_do_ambiente, nome_do_usuario)
	 * altera a tupla do ambiente para adicionar a quantidade de usuários do ambiente
	 */
	public void createUser(String environmentName) {
		EnvironmentEntry template = new EnvironmentEntry(environmentName, null, null);
		
		EnvironmentEntry environmentEntry = 
				(EnvironmentEntry) this.spaceService.take(template, TAKE_TIMEOUT);
		if (environmentEntry != null)
			environmentEntry.nUsers++;
		
		String userName = "user" + this.proxUsers++;
		this.users.add(userName);
		this.spaceService.write(new UserEntry(userName, environmentName), Lease.FOREVER);
		
		this.spaceService.write(environmentEntry, Lease.FOREVER);
		System.out.println("criando o usuário " + userName);
//		this.controlUpdate();
	}
	
	/*
	 * Cria a tupla do dispositivo, com seu nome e o nome do ambiente
	 * do tipo (nome_do_ambiente, nome_do_dispositivo)
	 * altera a tupla do ambiente para adicionar a quantidade de dispositivos do ambiente
	 */
	public void createDevice(String environmentName) {
		EnvironmentEntry template = new EnvironmentEntry(environmentName, null, null);
		
		EnvironmentEntry environmentEntry = 
				(EnvironmentEntry) this.spaceService.take(template, TAKE_TIMEOUT);
		if (environmentEntry != null)
			environmentEntry.nDevices++;
		
		String deviceName = "disp" + this.proxDevice++;
		this.devices.add(deviceName);
		this.spaceService.write(new DeviceEntry(deviceName, environmentName), Lease.FOREVER);		
		
		this.spaceService.write(environmentEntry, Lease.FOREVER);	
		System.out.println("criando o dispositivo " + deviceName);
		//this.controlUpdate();
	}

	/*
	 * Remove a tupla EnvironmentEntry do ambiente de tuplas
	 */
	public boolean removeEnvironment(String environmentName) {
		EnvironmentEntry template = new EnvironmentEntry(environmentName, null, null);
		
		EnvironmentEntry environment = 
				(EnvironmentEntry) this.spaceService.take(template, this.TAKE_TIMEOUT);
		
		int elements = environment.nDevices + environment.nUsers;
		
		if (elements > 0) {
			System.out.println(environmentName + " não está vazio");
			return false;
		}else {
			this.evironments.remove(environmentName);
			this.spaceService.take(template, this.TAKE_TIMEOUT);
			System.out.println(environmentName + " removido com sucesso!");
			return true;
		}
		
	}
	
	/*
	 * Função que retorna todos os usuários de um mesmo ambiente
	 */
	public ArrayList<String> showUsers(String environmentName) {
		
		ArrayList<String> users = new ArrayList<String>();
		
		for (String user: this.users) {
			UserEntry template = new UserEntry(user, null);
			UserEntry userEntry = (UserEntry) this.spaceService.read(template, Lease.FOREVER);
			if(userEntry.environmentName.equals(environmentName)) {
				users.add(user);
			}			
				
		}
		System.out.println("Listando os usuários do " + environmentName + ": " + users);
		return users;
		
	}
	
	/*
	 * Função que retorna todos os dispositivos de um mesmo ambiente
	 */
	public ArrayList<String> showDevices(String environmentName) {
		ArrayList<String> devices = new ArrayList<String>();
		
		for (String device: this.devices) {
			DeviceEntry template = new DeviceEntry(device, null);
			DeviceEntry userDevice = (DeviceEntry) this.spaceService.read(template, this.READ_TIMEOUT);
			if(userDevice.environmentName.equals(environmentName)) {
				devices.add(device);
			}			
				
		}
		System.out.println("Listando os dispositivos do " + environmentName + ": " + devices);
		return devices;
		
	}
	
	public void userTransport(String userName, String environmentStringDest) {
		UserEntry templateUser = new UserEntry(userName, null);
		UserEntry userEntry = (UserEntry) this.spaceService.take(templateUser, this.TAKE_TIMEOUT);
		
		String environmentStringOrigin = userEntry.environmentName;
		
		if (environmentStringDest == environmentStringOrigin)
			return;
		
		/*
		 * Função que transfere um usuário de um ambiente para outro fazendo um take na tupla do usuário em questão
		 * e um write no mesmo usuário com o novo ambiente ao mesmo tempo que tem que fazer a mesma coisa com as 
		 * tuplas do ambiente para auterar as quantidades de usuários em seus campos
		 */
		EnvironmentEntry templateEnvironment = new EnvironmentEntry(environmentStringOrigin, null, null);		
		
		EnvironmentEntry environmentOrigin = 
				(EnvironmentEntry) this.spaceService.take(templateEnvironment, this.TAKE_TIMEOUT);		
		environmentOrigin.nUsers--;	
		this.spaceService.write(environmentOrigin, Lease.FOREVER);
		
		templateEnvironment = new EnvironmentEntry(environmentStringDest, null, null);
		EnvironmentEntry environmentDest = 
				(EnvironmentEntry) this.spaceService.take(templateEnvironment, this.TAKE_TIMEOUT);
		environmentDest.nUsers++;
		this.spaceService.write(environmentDest, Lease.FOREVER);
		
		System.out.println("Movendo o " + userName + " do ambiente " + environmentStringOrigin + " para o " + environmentStringDest);
		this.spaceService.write(new UserEntry(userName, environmentStringDest), Lease.FOREVER);
		//this.controlUpdate();
		
	}

	/*
	 * Função que transfere um dispositivo de um ambiente para outro fazendo um take na tupla do ambiente em 
	 * questão e um write no mesmo dispositivo com o novo ambiente ao mesmo tempo que tem que fazer a mesma coisa
	 *  com as tuplas do ambiente para auterar as quantidades de dispositivos em seus campos.
	 */
	public void deviceTransport(String deviceName, String environmentStringDest) {
		DeviceEntry templateDevice = new DeviceEntry(deviceName, null);
		DeviceEntry deviceEntry = (DeviceEntry) this.spaceService.take(templateDevice, this.TAKE_TIMEOUT);
		
		String environmentStringOrigin = deviceEntry.environmentName;
		
		if (environmentStringDest == environmentStringOrigin)
			return;
			
		EnvironmentEntry templateEnvironment = new EnvironmentEntry(environmentStringOrigin, null, null);		
		
		EnvironmentEntry environmentOrigin = 
				(EnvironmentEntry) this.spaceService.take(templateEnvironment, this.TAKE_TIMEOUT);
		
		environmentOrigin.nDevices--;	
		this.spaceService.write(environmentOrigin, Lease.FOREVER);
		
		templateEnvironment = new EnvironmentEntry(environmentStringDest, null, null);
		
		EnvironmentEntry environmentDest = 
				(EnvironmentEntry) this.spaceService.take(templateEnvironment, this.TAKE_TIMEOUT);
		environmentDest.nDevices++;
		this.spaceService.write(environmentDest, Lease.FOREVER);
		
		this.spaceService.write(new DeviceEntry(deviceName, environmentStringDest), Lease.FOREVER);
		System.out.println("Movendo o " + deviceName + " do ambiente " + environmentStringOrigin + " para o " + environmentStringDest);
		//this.controlUpdate();
		
	}
	
	// Cria uma tupla mensagem com valores do conteúdo, remetente e o destino

	public void writeMessage(String content, String useSender, String userDestiny) {
		MessageEntry message = new MessageEntry(useSender + " > "+content, useSender, userDestiny);

		this.spaceService.write(message, this.MESSAGE_TIMEOUT);
		System.out.println("Mensagem enviada pra " + userDestiny);
		
		System.out.println("("+message.content+", "+useSender+", "+userDestiny+")");
	}
	
	// remove a tupla mensagem do espaço de tuplas

	public String readMessage(String useSender, String userDestiny) {
		MessageEntry template = new MessageEntry(null, useSender, userDestiny);
		
		MessageEntry message = (MessageEntry) this.spaceService.take(template, this.READ_TIMEOUT);
		if (message != null) {
		System.out.println("resposta: " + message.content);
		
		return message.content;
		}
		return "";
	}
	
	// Retorna o número de usuário de um ambiente
	public int getNumUsers(String environmentString) {
		EnvironmentEntry template = new EnvironmentEntry(environmentString, null, null);		
		
		EnvironmentEntry environment = 
				(EnvironmentEntry) this.spaceService.read(template, this.READ_TIMEOUT);
		
		if (environment != null)
			return environment.nUsers;
		else
			return 0;
		
	}
	
	// Retorna o número de dispositivos de um ambiente
	public int getNumDevices(String environmentString) {
		
		EnvironmentEntry template = new EnvironmentEntry(environmentString, null, null);		
		
		EnvironmentEntry environment = 
				(EnvironmentEntry) this.spaceService.read(template, this.TAKE_TIMEOUT);
		
		if (environment != null)
			return environment.nDevices;
		else
			return 0;
		
	}
	
	// Retorna o ambiente de um respectivo usuário
	public String getEnvironmenttoUser(String userName) {
		UserEntry templateUser = new UserEntry(userName, null);
		UserEntry userEntry = (UserEntry) this.spaceService.read(templateUser, this.TAKE_TIMEOUT);
		
		return userEntry.environmentName;	
	}
	
	/*
	
	public static void main(String[] args) {
		EnvironmentControl control = new EnvironmentControl();
		
		control.createEnvironment();
		control.createEnvironment();
		
		control.createUser("amb1");
		control.createUser("amb1");
		control.createUser("amb1");
		control.createUser("amb2");
		control.createUser("amb2");
		control.createUser("amb2");
		control.createDevice("amb1");
		control.createDevice("amb2");
		control.createDevice("amb1");
		
		System.out.println();
		control.showUsers("amb1");
		control.showUsers("amb2");
		control.showDevices("amb1");
		control.showDevices("amb2");
		
		System.out.println();
		control.userTransport("user4", "amb1");
		control.userTransport("user6", "amb1");
		control.deviceTransport("disp2", "amb1");
		control.deviceTransport("disp3", "amb2");
		
		System.out.println();
		control.showUsers("amb1");
		control.showUsers("amb2");
		control.showDevices("amb1");
		control.showDevices("amb2");
		
		System.out.println();
		control.userTransport("user5", "amb1");
		control.deviceTransport("disp3", "amb1");
		
		System.out.println();
		control.showUsers("amb1");
		control.showDevices("amb1");
		control.showUsers("amb2");
		control.showDevices("amb2");
		
		System.out.println();
		control.removeEnvironment("amb1");
		control.removeEnvironment("amb2");
		
		String teste1 = "menssagem de teste";
		String teste2 = "Outra mensagem";
		
		control.writeMessage(teste1, "user1", "user2");
		//control.writeMessage(teste2, "user2", "");
		
		//control.readMessage("");
		control.readMessage("user2");
		
		control.createEnvironment();
		control.removeEnvironment("amb3");
		
	} */

}
