package com.ppd.tuples.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ppd.tuples.service.EnvironmentControl;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserView {

	private JFrame frmUsurio;
	private JTextField tfMessage;
	private JComboBox cbUserDestiny;
	private JTextArea messageBox;
	private JButton btnSend;
	private EnvironmentControl control = new EnvironmentControl();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView window = new UserView();
					window.frmUsurio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUsurio = new JFrame();
		frmUsurio.setTitle("Usuário");
		frmUsurio.setBounds(100, 100, 648, 458);
		frmUsurio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUsurio.getContentPane().setLayout(null);
		
		JLabel lbUser = new JLabel("User1");
		lbUser.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lbUser.setBounds(243, 20, 89, 27);
		frmUsurio.getContentPane().add(lbUser);
		
		cbUserDestiny = new JComboBox();
		cbUserDestiny.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		cbUserDestiny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cbUserDestiny.setBounds(428, 29, 160, 21);
		frmUsurio.getContentPane().add(cbUserDestiny);
		
		JLabel lblNewLabel = new JLabel("Destino:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(376, 31, 54, 14);
		frmUsurio.getContentPane().add(lblNewLabel);
		
		messageBox = new JTextArea();
		messageBox.setBounds(47, 78, 383, 209);
		frmUsurio.getContentPane().add(messageBox);
		
		JLabel lblNewLabel_1 = new JLabel("Mensagem:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(50, 332, 71, 13);
		frmUsurio.getContentPane().add(lblNewLabel_1);
		
		tfMessage = new JTextField();
		tfMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		tfMessage.setBounds(119, 330, 311, 19);
		frmUsurio.getContentPane().add(tfMessage);
		tfMessage.setColumns(10);
		
		btnSend = new JButton("Enviar");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSend.setBounds(450, 329, 85, 21);
		frmUsurio.getContentPane().add(btnSend);
	}
}
