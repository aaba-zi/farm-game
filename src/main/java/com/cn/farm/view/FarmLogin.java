package com.cn.farm.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;

import com.cn.farm.database.Database;
import com.cn.farm.model.Farm;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FarmLogin {

	JFrame frame;
	public JTextField loginNameText;

	/**
	 * Launch the application.
	 * @param main function login page
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FarmLogin window = new FarmLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FarmLogin() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 228, 225));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel welbackLabel = new JLabel("Welcome Back!");
		springLayout.putConstraint(SpringLayout.NORTH, welbackLabel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, welbackLabel, -201, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welbackLabel, -83, SpringLayout.EAST, frame.getContentPane());
		welbackLabel.setForeground(new Color(128, 128, 128));
		welbackLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
		frame.getContentPane().add(welbackLabel);
		
		JLabel inputNameLabel = new JLabel("please input your farm name:");
		springLayout.putConstraint(SpringLayout.NORTH, inputNameLabel, 5, SpringLayout.SOUTH, welbackLabel);
		springLayout.putConstraint(SpringLayout.WEST, inputNameLabel, 20, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, inputNameLabel, -159, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, inputNameLabel, 0, SpringLayout.EAST, frame.getContentPane());
		inputNameLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
		frame.getContentPane().add(inputNameLabel);
		
		loginNameText = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, loginNameText, 19, SpringLayout.SOUTH, inputNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, loginNameText, 110, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, loginNameText, 304, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(loginNameText);
		loginNameText.setColumns(10);
		
		JButton retartBtn = new JButton("Let's roll!");
		springLayout.putConstraint(SpringLayout.NORTH, retartBtn, 202, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, loginNameText, -27, SpringLayout.NORTH, retartBtn);
		springLayout.putConstraint(SpringLayout.WEST, welbackLabel, 0, SpringLayout.WEST, retartBtn);
		springLayout.putConstraint(SpringLayout.WEST, retartBtn, 93, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, retartBtn, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, retartBtn, 19, SpringLayout.EAST, loginNameText);
		retartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = loginNameText.getText();
				Farm farm2 = new Farm();
				
				Database.setCurrentFarm(userName);
				FarmLand farm = new FarmLand();
				farm.frame.setVisible(true);
			
				frame.setVisible(false);


			}
		});
		retartBtn.setFont(new Font("Lucida Handwriting", Font.PLAIN, 27));
		frame.getContentPane().add(retartBtn);
	}

}
