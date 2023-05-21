package com.ppd.tuples.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ppd.tuples.service.EnvironmentControl;

public class UserFrame extends JFrame{
	
	private static UserFrame myFrame;
	
	private JTextField tfMessage;
	private JComboBox cbUserDestiny;
	private JTextArea messageBox;
	private JButton btnSend;
	private String userText;
	private String environmentText;
	private EnvironmentControl control;
	private ArrayList<String> users;
	
	public UserFrame(final String userText, final EnvironmentControl control) {
		
		this.userText = userText;
		this.control = control;		
		this.environmentText = control.getEnvironmenttoUser(userText);;	
		
		this.setBounds(100, 100, 648, 458);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel lbUser = new JLabel(this.userText);
		lbUser.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lbUser.setBounds(243, 20, 89, 27);
		this.getContentPane().add(lbUser); 
		
		this.cbUserDestiny = new JComboBox();
		
		this.users = control.showUsers(environmentText);
		users.remove(userText);
	
		this.cbUserDestiny.setModel(new DefaultComboBoxModel(users.toArray()));
		cbUserDestiny.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				update();
			}
		});
		this.cbUserDestiny.setBounds(428, 29, 160, 21);
		this.getContentPane().add(cbUserDestiny);

		
		JLabel lblNewLabel = new JLabel("Destino:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(376, 31, 54, 14);
		this.getContentPane().add(lblNewLabel);
		
		this.messageBox = new JTextArea();
		this.messageBox.setEditable(false);
		this.messageBox.setBounds(47, 78, 383, 209);
		this.getContentPane().add(messageBox);
		
		JLabel lblNewLabel_1 = new JLabel("Mensagem:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(50, 332, 71, 13);
		this.getContentPane().add(lblNewLabel_1);
		
		this.tfMessage = new JTextField();
		tfMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				btnSend.setEnabled(true);
			}
		});
		this.tfMessage.setBounds(119, 330, 311, 19);
		this.getContentPane().add(tfMessage);
		tfMessage.setColumns(10);
				
		this.btnSend = new JButton("Enviar");
		this.btnSend.setEnabled(false);
		this.btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userDestiny = (String) cbUserDestiny.getSelectedItem();
				String msg = tfMessage.getText();
				
				tfMessage.setText("");				
				control.writeMessage(msg, userText, userDestiny);
				btnSend.setEnabled(false);

			}
		});
		this.btnSend.setBounds(450, 329, 85, 21);
		this.getContentPane().add(this.btnSend);
		
		
		new Thread(){
			public void run(){
				while(true) {
					for (String user : users) {
						String message = control.readMessage(user, userText);
						
						if (message != "")
							messageBox.setText(messageBox.getText() + "\n" + message);
						
					}

				}
			}
		}.start();
		
	}
	
    public static UserFrame getInstance(String userText, EnvironmentControl control) {
        if( myFrame == null ) {
        	myFrame = new UserFrame(userText, control);
        }
        return myFrame;
     }

    /*
	@Override
	public void run() {
		while(true) {
			for (String user : users) {
				String message = control.readMessage(user, userText);
				
				if (message != "")
					messageBox.setText(messageBox.getText() + "\n" + message);
				
			}
		}
		
	}*/
    
    public void update() {
		cbUserDestiny.removeAllItems();
		users = control.showUsers(environmentText);
		users.remove(userText);
		
		for (String user : users) {
			cbUserDestiny.addItem(user);
		}
    }

	

}
