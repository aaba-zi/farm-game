package com.cn.farm.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FarmStart {

	public JFrame frame;

	/**
	 * Launch the application.
	 * @param main function for start page
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FarmStart window = new FarmStart();
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
	public FarmStart() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 240, 245));
		frame.getContentPane().setForeground(Color.PINK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel welcomeLbl = new JLabel("welcome to farm game!");
		springLayout.putConstraint(SpringLayout.NORTH, welcomeLbl, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, welcomeLbl, 20, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, welcomeLbl, -229, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welcomeLbl, -20, SpringLayout.EAST, frame.getContentPane());
		welcomeLbl.setBackground(new Color(0, 0, 0));
		welcomeLbl.setForeground(new Color(0, 0, 0));
		welcomeLbl.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
		frame.getContentPane().add(welcomeLbl);
		
		JLabel pictureLabel = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, pictureLabel, 6, SpringLayout.SOUTH, welcomeLbl);
		springLayout.putConstraint(SpringLayout.WEST, pictureLabel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pictureLabel, 436, SpringLayout.WEST, frame.getContentPane());
		pictureLabel.setIcon(new ImageIcon(FarmStart.class.getResource("/com/cn/farm/view/0109.png_300.png")));
		frame.getContentPane().add(pictureLabel);
		
		JButton createFarmBtn = new JButton("create");
		springLayout.putConstraint(SpringLayout.NORTH, createFarmBtn, 210, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, createFarmBtn, 20, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, createFarmBtn, 0, SpringLayout.SOUTH, frame.getContentPane());
		createFarmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FarmSelect farm = new FarmSelect();
				farm.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		createFarmBtn.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
		frame.getContentPane().add(createFarmBtn);
		
		JButton loginBtn = new JButton("login");
		springLayout.putConstraint(SpringLayout.SOUTH, pictureLabel, -7, SpringLayout.NORTH, loginBtn);
		springLayout.putConstraint(SpringLayout.NORTH, loginBtn, 210, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, loginBtn, 227, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, loginBtn, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, loginBtn, -10, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, createFarmBtn, -6, SpringLayout.WEST, loginBtn);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FarmLogin login = new FarmLogin();
				login.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		loginBtn.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
		frame.getContentPane().add(loginBtn);
	}

}
