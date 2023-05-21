package com.ppd.tuples.views;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.ppd.tuples.service.EnvironmentControl;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.sound.sampled.Control;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class MainView {

	private JFrame frmControleDeAmbiente;
	private EnvironmentControl control = new EnvironmentControl();
	private JButton btnNewEnvironment;
	private JComboBox cbEnvironment;
	private JButton btnRemoveEvironment;
	private JPanel panelEvironment;
	private JLabel lblNewLabel_1;
	private JButton btnNewUser;
	private JComboBox cbUser;
	private JLabel lblNewLabel_2;
	private JComboBox cbDevice;
	private JButton btnNewDevice;
	private JTextField tfUser;
	private JTextField tfDevice;
	private JButton btnMoveDevice;
	private JButton btnSendMessage;
	
	/*
	 * 
	 */
	public void addLabel(String environmentText) {
		
		/*
		int n = panelEvironment.getComponentCount();
		
		JLabel label = new JLabel(environmentText);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setOpaque(true);
		
		label.setBounds(10, n * 20 + 20, 50, 20);
		panelEvironment.add(label);
		*/
		
		int n = panelEvironment.getComponentCount();
		
		JLabel label = new JLabel(environmentText);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setOpaque(true);
		
		label.setBounds((int) (n / 10) * 60 + 10, (n % 10) * 20 + 20, 50, 20);
		panelEvironment.add(label);
		
	}
	
	
	public void update() {
		String environmentText = (String) cbEnvironment.getSelectedItem();
		
		this.clearPanel();
	
		if (environmentText != null){
			int nUsers = control.getNumUsers(environmentText);
			int nDevices = control.getNumDevices(environmentText);
			
			tfUser.setText("" + nUsers);
			tfDevice.setText("" + nDevices);
			
			panelEvironment.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), environmentText, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

			for (String userText : control.showUsers(environmentText)) {
				this.addLabel(userText);
			}
			
			for (String deviceText : control.showDevices(environmentText)) {
				this.addLabel(deviceText);
			}
		}

	}
	
	public void clearPanel() {
		Component[] componentLabel = panelEvironment.getComponents();
		
		for(Component l : componentLabel) {
			if (l instanceof JLabel) {
				panelEvironment.remove(l);
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
	    try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                	
                	
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frmControleDeAmbiente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmControleDeAmbiente = new JFrame();
		frmControleDeAmbiente.setType(Type.POPUP);
		frmControleDeAmbiente.setResizable(false);
		frmControleDeAmbiente.setTitle("Controle de Ambiente");
		frmControleDeAmbiente.setBounds(100, 100, 815, 513);
		frmControleDeAmbiente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmControleDeAmbiente.getContentPane().setLayout(null);
		
		panelEvironment = new JPanel();
		panelEvironment.setBackground(UIManager.getColor("Button.background"));
		panelEvironment.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "None", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelEvironment.setBounds(112, 136, 566, 247);
		frmControleDeAmbiente.getContentPane().add(panelEvironment);
		GroupLayout gl_panelEvironment = new GroupLayout(panelEvironment);
		gl_panelEvironment.setHorizontalGroup(
			gl_panelEvironment.createParallelGroup(Alignment.LEADING)
				.addGap(0, 545, Short.MAX_VALUE)
		);
		gl_panelEvironment.setVerticalGroup(
			gl_panelEvironment.createParallelGroup(Alignment.LEADING)
				.addGap(0, 235, Short.MAX_VALUE)
		);
		panelEvironment.setLayout(gl_panelEvironment);
		
		JLabel lblNewLabel = new JLabel("Ambiente: ");
		lblNewLabel.setBounds(184, 48, 50, 13);
		frmControleDeAmbiente.getContentPane().add(lblNewLabel);
		
		cbEnvironment = new JComboBox(new DefaultComboBoxModel());
		cbEnvironment.setBounds(244, 44, 132, 21);
		frmControleDeAmbiente.getContentPane().add(cbEnvironment);
		
		btnNewEnvironment = new JButton("Novo");
		btnNewEnvironment.setBounds(386, 44, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnNewEnvironment);
		
		btnNewDevice = new JButton("Novo");
		btnNewDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = (String) cbEnvironment.getSelectedItem();
				control.createDevice(item);
				cbDevice.addItem(control.getDeviceString());
				update();
			}
		});
		btnNewDevice.setBounds(593, 394, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnNewDevice);
		btnNewEnvironment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.createEnvironment();
				cbEnvironment.addItem(control.getStringEnvironment());
			}
		});
		
		btnRemoveEvironment = new JButton("Remover");
		btnRemoveEvironment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				String item = (String) cbEnvironment.getSelectedItem();
				System.out.println(item);
				
				if (control.removeEnvironment(item)) {
					cbEnvironment.removeItem(item);
				}
				else {
					JOptionPane.showMessageDialog(null, "O ambiente " + item + " não está vazio");
				}
			}
		});
		btnRemoveEvironment.setBounds(482, 44, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnRemoveEvironment);
		
		lblNewLabel_1 = new JLabel("Usuários:");
		lblNewLabel_1.setBounds(112, 402, 62, 13);
		frmControleDeAmbiente.getContentPane().add(lblNewLabel_1);
		
		btnNewUser = new JButton("Novo");
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = (String) cbEnvironment.getSelectedItem();
				control.createUser(item);
				cbUser.addItem(control.getUserString());
				update();
			}
		});
		btnNewUser.setBounds(265, 398, 79, 21);
		frmControleDeAmbiente.getContentPane().add(btnNewUser);
		
		cbUser = new JComboBox();
		cbUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSendMessage.setEnabled(true);
			}
		});
		cbUser.setBounds(170, 398, 85, 21);
		frmControleDeAmbiente.getContentPane().add(cbUser);
		
		lblNewLabel_2 = new JLabel("Dispositivo");
		lblNewLabel_2.setBounds(425, 402, 69, 13);
		frmControleDeAmbiente.getContentPane().add(lblNewLabel_2);
		
		cbDevice = new JComboBox();
		cbDevice.setBounds(483, 398, 101, 21);
		frmControleDeAmbiente.getContentPane().add(cbDevice);		
		
		JButton btnMoverUser = new JButton("Mover");
		btnMoverUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String environmenttext = (String) cbEnvironment.getSelectedItem();
				String userText = (String) cbUser.getSelectedItem();
				
				control.userTransport(userText, environmenttext);
				update();
			}
		});
		btnMoverUser.setBounds(170, 429, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnMoverUser);
		
		JLabel lblNewLabel_3 = new JLabel("Usuários:");
		lblNewLabel_3.setBounds(184, 82, 45, 13);
		frmControleDeAmbiente.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Dispisitivos:");
		lblNewLabel_4.setBounds(184, 113, 54, 13);
		frmControleDeAmbiente.getContentPane().add(lblNewLabel_4);
		
		tfUser = new JTextField();
		tfUser.setHorizontalAlignment(SwingConstants.RIGHT);
		tfUser.setText("0");
		tfUser.setEditable(false);
		tfUser.setBounds(244, 79, 50, 19);
		frmControleDeAmbiente.getContentPane().add(tfUser);
		tfUser.setColumns(10);
		
		tfDevice = new JTextField();
		tfDevice.setHorizontalAlignment(SwingConstants.RIGHT);
		tfDevice.setText("0");
		tfDevice.setEditable(false);
		tfDevice.setBounds(244, 107, 50, 19);
		frmControleDeAmbiente.getContentPane().add(tfDevice);
		tfDevice.setColumns(10);
		
		btnMoveDevice = new JButton("Mover");
		btnMoveDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String environmentText = (String) cbEnvironment.getSelectedItem();
				String deviceText = (String) cbDevice.getSelectedItem();
				
				control.deviceTransport(deviceText, environmentText);
				update();
			}
		});
		btnMoveDevice.setBounds(482, 429, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnMoveDevice);
		
		btnSendMessage = new JButton("Mensagem");
		btnSendMessage.setEnabled(false);
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSendMessage.setEnabled(false);
				
				String userText = (String) cbUser.getSelectedItem();

			
				UserFrame frame = new UserFrame(userText, control);
								
		    	frame.setTitle("Usuário");
		    	frame.setVisible(true);
		    	  
		  		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  		  
			}
		});
		btnSendMessage.setBounds(265, 429, 85, 21);
		frmControleDeAmbiente.getContentPane().add(btnSendMessage);

		cbEnvironment.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				update();
				
			}
		});
	}
}

