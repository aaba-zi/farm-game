package com.cn.farm.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import com.cn.farm.database.Database;
import com.cn.farm.model.Farm;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class FarmSelect {

	public JFrame frame;
	public JTextField nameText;
	public JTextField dayText;
	public final ButtonGroup buttonGroup = new ButtonGroup();
	public JTextField selectText;

	/**
	 * Launch the application.
	 * @param mainfunction for selection
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FarmSelect window = new FarmSelect();
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
	public FarmSelect() {
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

		JLabel nameLabel = new JLabel("please name your farm");
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, nameLabel, 39, SpringLayout.NORTH, frame.getContentPane());
		nameLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(nameLabel);

		nameText = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, nameLabel, -6, SpringLayout.WEST, nameText);
		springLayout.putConstraint(SpringLayout.NORTH, nameText, 17, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, nameText, 293, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, nameText, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		JLabel daysLabel = new JLabel("how many days do you want");
		springLayout.putConstraint(SpringLayout.SOUTH, nameText, -20, SpringLayout.NORTH, daysLabel);
		springLayout.putConstraint(SpringLayout.NORTH, daysLabel, 19, SpringLayout.SOUTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, daysLabel, 10, SpringLayout.WEST, frame.getContentPane());
		daysLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
		frame.getContentPane().add(daysLabel);

		dayText = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, dayText, 7, SpringLayout.NORTH, daysLabel);
		springLayout.putConstraint(SpringLayout.WEST, dayText, 19, SpringLayout.EAST, daysLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, dayText, -1, SpringLayout.SOUTH, daysLabel);
		springLayout.putConstraint(SpringLayout.EAST, dayText, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(dayText);
		dayText.setColumns(10);

		JLabel selectLabel = new JLabel("select advantage");
		springLayout.putConstraint(SpringLayout.NORTH, selectLabel, 17, SpringLayout.SOUTH, daysLabel);
		springLayout.putConstraint(SpringLayout.WEST, selectLabel, 10, SpringLayout.WEST, frame.getContentPane());
		selectLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
		frame.getContentPane().add(selectLabel);

		selectText = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, selectText, 24, SpringLayout.SOUTH, daysLabel);
		springLayout.putConstraint(SpringLayout.WEST, selectText, 23, SpringLayout.EAST, selectLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, selectText, 0, SpringLayout.SOUTH, selectLabel);
		frame.getContentPane().add(selectText);
		selectText.setColumns(10);

		JLabel advLabel1 = new JLabel("1.  initialized money + 30%");
		springLayout.putConstraint(SpringLayout.NORTH, advLabel1, 6, SpringLayout.SOUTH, selectLabel);
		springLayout.putConstraint(SpringLayout.WEST, advLabel1, 0, SpringLayout.WEST, nameLabel);
		advLabel1.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		frame.getContentPane().add(advLabel1);

		JLabel advLabel2 = new JLabel("2.  fertilizer sell price + 50%");
		springLayout.putConstraint(SpringLayout.EAST, selectText, 82, SpringLayout.EAST, advLabel2);
		springLayout.putConstraint(SpringLayout.NORTH, advLabel2, 6, SpringLayout.SOUTH, advLabel1);
		springLayout.putConstraint(SpringLayout.WEST, advLabel2, 10, SpringLayout.WEST, frame.getContentPane());
		advLabel2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		frame.getContentPane().add(advLabel2);

		JLabel advLabel3 = new JLabel("3.  plant sell price + 50%");
		springLayout.putConstraint(SpringLayout.WEST, advLabel3, 0, SpringLayout.WEST, nameLabel);
		advLabel3.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		frame.getContentPane().add(advLabel3);

		JButton goBtn = new JButton("Go!");
		springLayout.putConstraint(SpringLayout.NORTH, goBtn, 117, SpringLayout.SOUTH, dayText);
		springLayout.putConstraint(SpringLayout.EAST, goBtn, -16, SpringLayout.EAST, frame.getContentPane());
		goBtn.setBackground(Color.PINK);
		goBtn.setForeground(new Color(0, 0, 0));
		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String farmName = nameText.getText();
				String day = dayText.getText();
				String farmType = selectText.getText();
				Farm farm = new Farm();
				farm.setName(farmName);
				farm.setDuration(Integer.parseInt(day));
				farm.setType(Integer.parseInt(farmType));
				try {
					farm.createFarm();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Database.setCurrentFarm(farmName);
				FarmLand farmLand = new FarmLand();
				farmLand.frame.setVisible(true);

			}
		});
		goBtn.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
		frame.getContentPane().add(goBtn);

		JLabel lblNewLabel = new JLabel("4.  moeny + 10% when game over");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 220, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, advLabel3, -6, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, goBtn, 6, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, goBtn, 0, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 331, SpringLayout.WEST, frame.getContentPane());
		lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel);
	}
}

