package com.cn.farm.view;

import java.awt.EventQueue;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cn.farm.database.Database;
import com.cn.farm.model.*;
import com.cn.farm.tools.FormatTool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import com.cn.farm.controller.Application;
import com.cn.farm.database.Database;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Color;

public class FarmLand {

	public JFrame frame;
	/**
	 * get the input
	 */
	public JTextField selectNoText;
	/**
	 * create a farm object
	 */
	public Farm farm;
	/**
	 * initialize the application object
	 */
	public Application application;
	/**
	 * public the testArea
	 */
	public JTextArea textArea;
	/**
	 * public the button
	 */
	public JButton confirmBtn;
	/**
	 * declare the day variable
	 */
	int day;
	/**
	 * declare the operator which is the input
	 */
	public int operate;
	/**
	 * declare animalFeed inputs
	 */
	public int animalFeedTxt1, animalFeedTxt2;
	/**
	 * declare animalPlay inputs
	 */
	public int animalPlayTxt1, animalPlayTxt2;
	/**
	 * declare plantWatering inputs
	 */
	public int plantWateringTxt;
	/**
	 * declare plantFertilize inputs
	 */
	public int plantFertilizeTxt1, plantFertilizeTxt2;
	/**
	 * declare plantSell inputs
	 */
	public int plantSellTxt;

	/**
	 * Launch the application.
	 * @param string arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Database.setCurrentFarm("fantasy");
					FarmLand window = new FarmLand();
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
	public FarmLand() {
		initialize();
	}
	/**
	 * print all animal
	 * @return return animal info string
	 */
	public String printGlobalAnimal() {
		List<Animal> animalList = Database.getGlobalAnimal();
		String result = "";
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			result += "\n------------------------No." + i + "-------------------------";
			result += "\n name:\t" + animal.getName();
			result += "\n purchase price:\t" + animal.getPurchasePrice();
			result += "\n description:\n" + animal.getDescription();
		}
		return result;
	}
	/**
	 * print animal information
	 * @return animal info
	 */

	public String printAnimalInfo() {
		Farm farm = Database.currentFarm;
		List<Animal> animalList = farm.getAnimalList();
		String result = "";
		if (animalList.size() < 1) {
			result += "You didn't buy animal.\n";
		}
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);

			result += "------------------------No." + i + " Animal-------------------------";
			result += "\n name:\t" + animal.getName();
			result += "\n purchase price:\t" + animal.getPurchasePrice();
			result += "\n description:\t" + animal.getDescription();
			result += "\n health index:\t" + animal.getHealthIndex();
			result += "\n happiness index:\t" + animal.getHappinessIndex();
			result += "\n create time:\n" + animal.getCreateTime();

		}
		return result;
	}
	/**
	 * pursue animal
	 */
	public void choosePursueAnimal() {
		List<Animal> animalList = Database.getGlobalAnimal();
		Farm farm = Database.currentFarm;
		int operate;
		textArea.append("\nPlease enter the number of the animal you want to buy :\n");
		textArea.append("-------------------enter -1 to exit-----------------------\n");
		operate = getSelectNoText();
		if (operate == -1) {
			animalManage();
			return;
		} else if (operate >= 0 && operate < animalList.size()) {
			if (animalList.get(operate).purchase()) {
				textArea.append("successful purchase, you now have a new animal in your farm\n");
				return;
			} else {
				textArea.append("purchase faile\n");
			}
		} else {
			textArea.append("type error, please select again\n");
		}
	}
	/**
	 * pursue the animal and print list
	 */
	public void pursueAnimal() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePursueAnimal();
			}
		});

		textArea.setText("Find animal list below: \n" + printGlobalAnimal() + "\nYour current available balance:"
				+ farm.getMoney() + "\n");
	}
	/**
	 * feed animal
	 */

	public void animalFeedB() {
		animalFeedTxt2 = getSelectNoText();
		animalFeed();
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				careAnimal();
			}
		});
	}

	public void animalFeedA() {
		animalFeedTxt1 = getSelectNoText();
		textArea.append(printFeedInfo());
		textArea.append("\nplease choose food to feed: \n");
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animalFeedB();;
			}
		});
	}
/**
 * feed animal through animalFeedA
 */
	public void animalFeed() {
		List<Animal> animalList = farm.getAnimalList();
		List<Feed> feedList = farm.getFeedList();
		if (animalList.get(animalFeedTxt1).feed(feedList.get(animalFeedTxt2))) {
			textArea.append("successful feed£¡\n");
		} else {
			textArea.append("fail to feed\n");
		}
	}
	
	/**
	 * play with animal
	 */

	public void animalPlay() {
		textArea.append("play with animal could increase their happiness\n");
		animalPlayTxt1 = getSelectNoText();
		List<Animal> animalList = farm.getAnimalList();
		if (animalList.get(animalPlayTxt1).play()) {
			textArea.append("successful play£¡\n");
		} else {
			textArea.append("fail to play\n");
		}
	}
	
	
	/**
	 * care animal
	 */
	public void chooseCareAnimal() {
		Farm farm = Database.currentFarm;
		List<Animal> animalList = farm.getAnimalList();
		List<Feed> feedList = farm.getFeedList();
		int operate, animalNum, feedNum;
		String result = "";
		String notice = "";

		switch (getSelectNoText()) {
		case 0:
			animalManage();
			return;
		case 1:
			textArea.setText(textArea.getText() + printAnimalInfo());
			textArea.setText(textArea.getText() + "\nchoose animal you want to feed: \n");
			removeBtnAction(confirmBtn);
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					animalFeedA();;
				}
			});
			// printFeedInfo();
			// animalList.get(animalNum).feed(feedList.get(feedNum));
			break;
		case 2:
			textArea.setText(textArea.getText() + printAnimalInfo());
			textArea.setText(textArea.getText() + "\nchoose animal you want to play with: \n");
			removeBtnAction(confirmBtn);
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					animalPlay();;
				}
			});
			// printFeedInfo();
			// animalList.get(animalNum).play();
			break;
		default:
			break;
		}
		farm.updateFarm();
	}
/**
 * care animal and print list of animal
 */
	public void careAnimal() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseCareAnimal();
			}
		});
		String notice = "";
		notice += "********************Animal Care*************************\n";
		notice += "*\n1. \tFeed";
		notice += "*\n2. \tPlay";
		notice += "*\n0. \tGo back to upper level\n";
		notice += "****************************************************\n";
		notice += "please select actions: ";
		textArea.setText(notice);

	}
	/**
	 * get input
	 * @return input
	 */

	public int getSelectNoText() {
		int result = Integer.parseInt(selectNoText.getText());
		selectNoText.setText("");
		return result;
	}
	/**
	 * animal management
	 */

	public void chooseAnimalManage() {
		Farm farm = Database.currentFarm;
		String result = "";
		int operate;
		result += "********************Animal Management*************************";
		result += "*\n1. \t print all animal info";
		result += "*\n2. \t purchase new animal";
		result += "*\n3. \t manage animal";
		result += "*\n0. \t go back to upper level";
		result += "\n*****************************************************\n";
		result += "please select actions:";
		textArea.setText(result);
		operate = getSelectNoText();
		switch (operate) {
		case 0:
			mainFun();
			return;
		case 1:
			textArea.setText(printAnimalInfo() + result);
			break;
		case 2:
			pursueAnimal();
			break;
		case 3:
			careAnimal();
			break;
		default:
			break;
		}
	}

	public void animalManage() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseAnimalManage();
			}
		});

		String result = "";
		int operate;
		result += "********************Animal Management*************************";
		result += "*\n1. \t print all animal info";
		result += "*\n2. \t purchase new animal";
		result += "*\n3. \t manage animal";
		result += "*\n0. \t go back to upper level";
		result += "\n*****************************************************\n";
		result += "please select actions:";
		textArea.setText(result);
	}


	/**
	 * print all plant and information
	 * @return global plant info
	 */
	public String printGlobalPlant() {
		List<Plant> plantList = Database.getGlobalPlant();
		String result = "";
		for (int i = 0; i < plantList.size(); i++) {
			Plant plant = plantList.get(i);
			result += "------------------------No." + i + "-------------------------";
			result += "\n name:\t" + plant.getName();
			result += "\n purchase price:\t" + plant.getPurchasePrice();
			result += "\n sell price:\t" + plant.getSellPrice();
			result += "\n grow period:\t" + plant.getDurationHour();
			result += "\n description:\t" + plant.getDescription();
			result += "\n";
		}
		textArea.setText(result);
		return result;
	}

	public String printPlantInfo() {
		Farm farm = Database.currentFarm;
		String result = "";
		List<Plant> plantList = farm.getPlantList();
		if (plantList.size() < 1) {
			result += "you didn't purchase plant.\n";
		}
		for (int i = 0; i < plantList.size(); i++) {
			Plant plant = plantList.get(i);
			result += "------------------------No." + i + " plant-------------------------";
			result += "\n Name:\t" + plant.getName();
			result += "\n purchase price:\t" + plant.getPurchasePrice();
			result += "\n sell price:\t" + plant.getSellPrice();
			result += "\n description:\t" + plant.getDescription();
			result += "\n last time watering:\t" + plant.getCreateTime();
			result += "\n Feed:\t" + plant.getLastWatering();
			Date start = DateUtil.parse(plant.getCreateTime());
			Date end = DateUtil.offset(start, DateField.HOUR, plant.getDurationHour() - plant.getAdjHour());
			result += "\n time to grow:\t" + FormatTool.dateBetweenParse(start, end);
			result += "\n status:\t" + plant.getStatus() + "\n";
		}
		textArea.setText(result);
		return result;
	}
	/**
	 * pursue plant
	 */
	public void choosePursuePlant() {
		List<Plant> plantList = Database.getGlobalPlant();
		Farm farm = Database.currentFarm;
		int operate;
		String result = "";
		textArea.setText(textArea.getText() + "below it plant list:\n" + printGlobalPlant() + "\nyour balance is "
				+ farm.getMoney() + "\nPlease enter the number of the plant you want to buy :\n");
		textArea.append("-------------------enter -1 to exit-----------------------");
		operate = getSelectNoText();
		if (operate == -1) {
			plantManage();
			return;
		} else if (operate >= 0 && operate < plantList.size()) {
			if (plantList.get(operate).purchase()) {
				textArea.setText(textArea.getText() + "\nsuccessful purchase.\n");
				return;
			} else {
				textArea.setText(textArea.getText() + "\npurchase failed.\n");
			}
		} else {
			textArea.setText(textArea.getText() + "\ntype error, please select again.\n");
		}
		farm.updateFarm();
	}

	public void pursuePlant() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePursuePlant();
			}
		});
		String result = "";
		int operate;
		result += "********************Animal Management*************************";
		result += "*\n1. \t print all animal info";
		result += "*\n2. \t purchase new animal";
		result += "*\n3. \t manage animal";
		result += "*\n0. \t go back to upper level";
		result += "\n*****************************************************\n";
		result += "please select actions:";
		textArea.setText(result);
	}
	/**
	 * watering
	 */
	public void plantWatering() {
		plantWateringTxt = getSelectNoText();
		List<Plant> plantList = farm.getPlantList();
		if (plantList.get(plantWateringTxt).watering()) {
			textArea.append("successful watering£¡\n");
		} else {
			textArea.append("watering once per day, try it tommorow\n");
		}
	}
	
	/**
	 * fertilize the plant
	 */
	public void plantFertilizerB() {
		plantFertilizeTxt2 = getSelectNoText();
		plantFertilizer();
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carePlant();
			}
		});
	}

	public void plantFertilizerA() {
		plantFertilizeTxt1 = getSelectNoText();
		textArea.append(printFeedInfo());
		textArea.append("\nplease choose food to feed: \n");
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animalFeedB();;
			}
		});
	}

	public void plantFertilizer() {
		List<Plant> plantList = farm.getPlantList();
		List<Muck> muckList = farm.getMuckList();
		if (plantList.get(plantFertilizeTxt1).fertilize(muckList.get(plantFertilizeTxt2))) {
			textArea.append("successful fertilized£¡\n");
		} else {
			textArea.append("fail to fertilize\n");
		}
	}
	
	/**
	 * plant sell
	 */
	
	public void plantSell() {
		plantSellTxt = getSelectNoText();
		List<Plant> plantList = farm.getPlantList();
		if (plantList.get(plantSellTxt).sell()) {
			textArea.append("successful selling£¡\n");
		} else {
			textArea.append("oh, The product is not available for sale\n");
		}
	}
	
	
	/**
	 * care plant such as watering and fertilize
	 */
	public void chooseCarePlant() {
		Farm farm = Database.currentFarm;
		List<Plant> plantList = farm.getPlantList();
		List<Muck> muckList = farm.getMuckList();
		int operate, plantNum, muckNum;
		String result = "";

		result += "\n********************Plant Management*************************\n";
		result += "\n*1. \t Fertilizer\n";
		result += "\n*2. \t watering\n";
		result += "\n*3. \t sell\n";
		result += "\n*0. \t back to upper level\n";
		result += "\n*************************************************************\n";

		switch (getSelectNoText()) {
		case 0:
			plantManage();
			return;
		case 1:
			textArea.setText(textArea.getText() + printPlantInfo());
			textArea.setText(textArea.getText() + "\nchoose plant you want to fertilize: \n");
			removeBtnAction(confirmBtn);
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					plantFertilizerA();;
				}
			});
			break;
		case 2:
			textArea.setText(textArea.getText() + printPlantInfo());
			textArea.setText(textArea.getText() + "\nchoose plant you want to watering: \n");
			removeBtnAction(confirmBtn);
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					plantWatering();;
				}
			});
			break;
		case 3:
			textArea.setText(textArea.getText() + printPlantInfo());
			textArea.setText(textArea.getText() + "\nchoose plant you want to sell: \n");
			removeBtnAction(confirmBtn);
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					plantSell();
				}
			});
			break;
			
		default:
			break;
		}
		farm.updateFarm();
	}

	public void carePlant() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseCarePlant();
			}
		});
		String notice = "";
		notice += "\n********************Plant Management*************************\n";
		notice += "\n*1. \t Fertilizer\n";
		notice += "\n*2. \t watering\n";
		notice += "\n*3. \t sell\n";
		notice += "\n*0. \t back to upper level\n";
		notice += "\n*************************************************************\n";
		textArea.setText(notice);

	}
	/**
	 * plant management
	 */
	public void choosePlantManage() {
		Farm farm = Database.currentFarm;
		List<Plant> plantList;
		int operate, plantNum;
		String result = "";
		result += "********************plant management*************************";
		result += "*\n1. \t print all plant info";
		result += "*\n2. \t purchase new palnt";
		result += "*\n3. \t manage plant";
		result += "*\n4. \t sell plant";
		result += "*\n0. \t back to upper level";
		result += "****************************************************\n";
		result += "please select: \n";

		plantList = farm.getPlantList();
		textArea.setText(result);
		operate = getSelectNoText();
		switch (operate) {
		case 0:
			mainFun();
			return;
		case 1:
			printPlantInfo();
			break;
		case 2:
			pursuePlant();
			break;
		case 3:
			carePlant();
			break;
		case 4:
			printPlantInfo();
			System.out.println("please select items you want to sell: ");
			plantNum = 1;
			if (plantList.get(plantNum).sell()) {
				farm.setMoney(farm.getMoney() + plantList.get(plantNum).getSellPrice());
				plantList.remove(plantNum);
				farm.updateFarm();
			}
			break;
		default:
			break;
		}
		farm.updateFarm();
	}

	public void plantManage() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePlantManage();
			}
		});

		String result = "";
		result += "********************plant management*************************";
		result += "*\n1. \t print all plant info";
		result += "*\n2. \t purchase new palnt";
		result += "*\n3. \t manage plant";
		result += "*\n4. \t sell plant";
		result += "*\n0. \t back to upper level";
		result += "****************************************************\n";
		result += "please select: \n";
		textArea.setText(result);
	}

	/**
	 * print all muck information
	 * @return fertilizer information
	 */
	public String printMuckInfo() {
		Farm farm = Database.currentFarm;
		List<Muck> muckList = farm.getMuckList();
		String result = "";
		for (int i = 0; i < muckList.size(); i++) {
			Muck muck = muckList.get(i);
			result += "\n------------------------No." + i + "Fertilizer-------------------------\n";
			result += "Name:\t" + muck.getName() + "\n";
			result += "purchase price:\t" + muck.getPurchasePrice() + "\n";
			result += "sell price:\t" + muck.getSellPrice() + "\n";
			result += "Fertilizer level:\t" + muck.getLevel() + "\n";
			result += "speed up(hours):\t" + muck.getEffectHour() + "\n";
			result += "remaining hours:\t" + muck.getCount() + "\n";
		}
		return result;
	}

	/**
	 * pursue muck
	 */
	public void choosePursueMuck() {
		List<Muck> muckList = farm.getMuckList();
		Farm farm = Database.currentFarm;
		int muckNum;
		textArea.setText("please find fertilizer list below: \n");
		for (int i = 0; i < muckList.size(); i++) {
			Muck muck = muckList.get(i);
			textArea.setText(textArea.getText() + "------------------------No." + i + "-------------------------\n");
			textArea.setText(textArea.getText() + "Name:\t" + muck.getName() + "\n");
			textArea.setText(textArea.getText() + "purchase price:\t" + muck.getPurchasePrice() + "\n");
			textArea.setText(textArea.getText() + "sell price:\t" + muck.getSellPrice() + "\n");
			textArea.setText(textArea.getText() + "Fertilizer level:\t" + muck.getLevel() + "\n");
			textArea.setText(textArea.getText() + "speed up(hours):\t" + muck.getEffectHour() + "\n");
			textArea.setText(textArea.getText() + "description:\t" + muck.getDescription() + "\n");
		}
		textArea.setText(textArea.getText() + "your current balance is :\t" + farm.getMoney() + "\n");
		textArea.setText(
				textArea.getText() + "Please enter the number of the Fertilier you want to buy :\n");
		textArea.append("-------------------enter - 1 to exit-----------------------");
		muckNum = getSelectNoText();
		if (muckNum == -1) {
			return;
		} else if (muckNum >= 0 && muckNum < muckList.size()) {
			if (muckList.get(muckNum).purchase()) {
				farm.setMoney(farm.getMoney() - muckList.get(muckNum).getPurchasePrice());
				farm.updateFarm();
				textArea.setText("successful purchase.");
				return;
			} else {
				textArea.setText(textArea.getText() + "purchase failed.\n");
			}
		} else {
			textArea.setText(textArea.getText() + "type error, please select again.\n");
		}
		farm.updateFarm();
	}

	public void pursueMuck() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePursueMuck();
			}
		});

		textArea.setText("Find animal list below: \n" + printMuckInfo() + "\nYour current available balance:"
				+ farm.getMoney() + "\n");
	}

	/**
	 * Muck manage.
	 */

	public void chooseMuckManage() {
		Farm farm = Database.currentFarm;
		List<Muck> muckList = farm.getMuckList();
		int operate, muckNum;
		String result = "";
		result += "********************Fertilizer Management*************************\n";
		result += "*\t1. \t print all fertilizer info\n";
		result += "*\t2. \t purchase fertilizer\n";
		result += "*\t3. \t sell fertilizer\n";
		result += "*\t0. \t go back to upper level\n";
		result += "****************************************************\n";
		result += "plase select action: \n";
		textArea.setText(result);
		operate = getSelectNoText();
		switch (operate) {
		case 0:
			mainFun();
			return;
		case 1:
			textArea.setText(textArea.getText() + printMuckInfo());
			break;
		case 2:
			pursueMuck();
			break;
		case 3:
			textArea.setText(textArea.getText() + printMuckInfo());
			textArea.setText(textArea.getText() + "please select fertilizer you want to sell\n");
			muckNum = getSelectNoText();
			if (muckList.get(muckNum).sell()) {
				farm.setMoney(farm.getMoney() + muckList.get(muckNum).getSellPrice());
				farm.updateFarm();
			}
			break;
		default:
			break;
		}
		farm.updateFarm();
	}

	public void muckManage() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMuckManage();
			}
		});
		String notice = "";
		notice += "********************Fertilizer Management*************************\n";
		notice += "*\t1. \t print all fertilizer info\n";
		notice += "*\t2. \t purchase fertilizer\n";
		notice += "*\t3. \t sell fertilizer\n";
		notice += "*\t0. \t go back to upper level\n";
		notice += "****************************************************\n";
		notice += "plase select action: \n";
		textArea.setText(notice);
	}

	/**
	 * Print feed info.
	 * @return feed information
	 */
	public String printFeedInfo() {
		Farm farm = Database.currentFarm;
		List<Feed> feedList = farm.getFeedList();
		String result = "";
		for (int i = 0; i < feedList.size(); i++) {
			Feed feed = feedList.get(i);
			result += "\n------------------------No." + i + "Feed-------------------------\n";
			result += "Name:\t" + feed.getName() + "\n";
			result += "purchase price:\t" + feed.getPurchasePrice() + "\n";
			result += "sell price:\t" + feed.getSellPrice() + "\n";
			result += "increase health index:\t" + feed.getHealthEffect() + "\n";
			result += "increase happiness index:\t" + feed.getHappinessEffect() + "\n";
			result += "feed level:\t" + feed.getLevel() + "\n";
			result += "remaining amount:\t" + feed.getCount() + "\n";
			result += "description:\t" + feed.getDescription() + "\n";
		}
		return result;
	}
	

	/**
	 * Pursue feed.
	 */

	public void choosePursueFeed() {
		Farm farm = Database.currentFarm;
		List<Feed> feedList = farm.getFeedList();
		int feedNum;
		textArea.setText("please find Feed list below: \n");
		for (int i = 0; i < feedList.size(); i++) {
			Feed feed = feedList.get(i);

			textArea.setText(textArea.getText() + "------------------------No." + i + "-------------------------\n");
			textArea.setText(textArea.getText() + "Name:\t" + feed.getName() + "\n");
			textArea.setText(textArea.getText() + "purchase price:\t" + feed.getPurchasePrice() + "\n");
			textArea.setText(textArea.getText() + "sell price:\t" + feed.getSellPrice() + "\n");
			textArea.setText(textArea.getText() + "feed level:\t" + feed.getLevel() + "\n");
			textArea.setText(textArea.getText() + "increase health index:\t" + feed.getHealthEffect() + "\n");
			textArea.setText(textArea.getText() + "increase happiness index:\t" + feed.getHappinessEffect() + "\n");
			textArea.setText(textArea.getText() + "description:\t" + feed.getDescription() + "\n");
		}

		textArea.setText(textArea.getText() + "your current balance is :\t" + farm.getMoney() + "\n");
		textArea.setText(
				textArea.getText() + "Please enter the number of the Feed you want to buy:\n");
		textArea.append("-------------------enter -1 to exit-----------------------");
		feedNum = getSelectNoText();
		if (feedNum == -1) {
			feedManage();
			return;
		} else if (feedNum >= 0 && feedNum < feedList.size()) {
			if (feedList.get(feedNum).purchase()) {
				farm.setMoney(farm.getMoney() - feedList.get(feedNum).getPurchasePrice());
				farm.updateFarm();
				textArea.setText(textArea.getText() + "successful purchase.\n");
				return;
			} else {
				textArea.setText(textArea.getText() + "purchase failed.\n");
			}
		} else {
			textArea.setText(textArea.getText() + "type error, please select again.\n");
		}
	}

	public void pursueFeed() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosePursueFeed();
			}
		});

		textArea.setText("Find Fodder list below: \n" + printFeedInfo() + "\nYour current available balance:"
				+ farm.getMoney() + "\n");
	}
	// --------------------------------------------------------------------------------------------
	/**
	 * Feed manage.
	 */

	public void chooseFeedManage() {
		Farm farm = Database.currentFarm;
		List<Feed> feedList = farm.getFeedList();
		String result = "";
		int operate, feedNum;
		result += "********************Feed Management*************************\n";
		result += "*\t1. \t print all fodder info\n";
		result += "*\t2. \t purchase new fodder\n";
		result += "*\t3. \t sell fodder\n";
		result += "*\t0. \t go back to upper level\n";
		result += "****************************************************\n";
		result += "plase select action: \n";
		textArea.setText(result);
		operate = getSelectNoText();
		switch (operate) {
		case 0:
			mainFun();
			return;
		case 1:
			textArea.setText(textArea.getText() + printFeedInfo());
			break;
		case 2:
			pursueFeed();
			break;
		case 3:
			textArea.setText(textArea.getText() + printFeedInfo());
			textArea.setText(textArea.getText() + "Please select which fertilizzer to sell: \n");
			feedNum = getSelectNoText();
			if (feedList.get(feedNum).sell()) {
				farm.setMoney(farm.getMoney() + feedList.get(feedNum).getSellPrice());
				farm.updateFarm();
			}
			break;
		default:
			break;
		}
	}

	public void feedManage() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFeedManage();
			}
		});
		String notice = "";
		notice += "********************Feed Management*************************\n";
		notice += "*\t1. \t print all fodder info\n";
		notice += "*\t2. \t purchase new fodder\n";
		notice += "*\t3. \t sell fodder\n";
		notice += "*\t0. \t go back to upper level\n";
		notice += "****************************************************\n";
		notice += "plase select action: \n";
		textArea.setText(notice);
	}

	/**
	 * Item manage.
	 */

	public void chooseItemManage() {
		Farm farm = Database.currentFarm;
		int operate;
		String result = "";
		result += "********************Item Management*************************\n";
		result += "*\n1. \t fodder management\n";
		result += "*\n2. \t fertilizer management\n";
		result += "*\n0. \t go back to upper level\n";
		result += "****************************************************\n";
		result += "plase select action: \n";
		textArea.setText(result);

		operate = getSelectNoText();
		switch (operate) {
		case 0:
			mainFun();
			return;
		case 1:
			feedManage();
			break;
		case 2:
			muckManage();
			break;
		default:
			break;
		}
	}

	public void itemManage() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseItemManage();
			}
		});

		String notice = "";
		notice += "********************Item Management*************************\n";
		notice += "*\n1. \t fodder management\n";
		notice += "*\n2. \t fertilizer management\n";
		notice += "*\n3. \t back to upper level\n";
		notice += "*\n0. \t go back to upper level\n";
		notice += "****************************************************\n";
		notice += "plase select action: \n";
		textArea.setText(notice);
	}

	/**
	 * print farm information
	 * @return farm info
	 */

	public String printFarmInfo() {
		String result = "";
		Farm farm = Database.currentFarm;
		result += "======================Farm=======================";
		result += "\n name:\t" + farm.getName();
		result += "\n type:\t" + farm.getType();
		result += "\n damaged or not:\t" + farm.getFenceDamaged();
		result += "\n money:\t" + farm.getMoney();
		result += "\n remaining actions:\t" + farm.getFarmerRemainderCount();
		result += "\n create time:\t" + farm.getCreateTime();
		result += "\n game time:\t" + farm.getDuration() + "day";
		result += "\n====================================================\n";
		return result;
	}
	/**
	 * choose main function 
	 */
	public void chooseMain() {
		String result = "***********" + Database.currentFarm.getName()
				+ "************\r\n1. print all info\r\n2. animal management\r\n3. plant management\r\n4. item management\r\n***********************************";
		int opt = getSelectNoText();
		switch (opt) {
		case 1:
			textArea.setText(printFarmInfo() + result);
			selectNoText.setText("");
			break;
		case 2:
			animalManage();
			break;
		case 3:
			plantManage();
			break;
		case 4:
			itemManage();
			break;
		default:
			break;
		}
	}

	/**
	 * main 
	 */
	public void mainFun() {
		removeBtnAction(confirmBtn);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMain();
			}
		});
		textArea.setText(
				"***********"+ farm.getName() +" farm************\r\n1. print all info\r\n2. animal management\r\n3. plant management\r\n4. item management\r\n***********************************");
	}

	public boolean removeBtnAction(JButton btn) {
		for (ActionListener actionListener : confirmBtn.getActionListeners()) {
			System.out.println(actionListener.toString());
			btn.removeActionListener(actionListener);
		}
		return true;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		farm = Database.currentFarm;
		frame = new JFrame();
		application = new Application();
		frame.setTitle(frame.getName());
		frame.getContentPane().setBackground(new Color(255, 228, 225));

		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel selectNoLabel = new JLabel("select: ");
		selectNoLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 16));
		frame.getContentPane().add(selectNoLabel);

		selectNoText = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, selectNoText, 6, SpringLayout.EAST, selectNoLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, selectNoText, -18, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, selectNoText, -312, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(selectNoText);
		selectNoText.setColumns(10);

		textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.WEST, selectNoLabel, 0, SpringLayout.WEST, textArea);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 0, SpringLayout.NORTH, frame.getContentPane());
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		springLayout.putConstraint(SpringLayout.WEST, textArea, 29, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(textArea);

		confirmBtn = new JButton("confirm");
		springLayout.putConstraint(SpringLayout.NORTH, confirmBtn, -4, SpringLayout.NORTH, selectNoLabel);
		springLayout.putConstraint(SpringLayout.WEST, confirmBtn, 6, SpringLayout.EAST, selectNoText);

		confirmBtn.setFont(new Font("Lucida Handwriting", Font.PLAIN, 16));
		frame.getContentPane().add(confirmBtn);

		JButton btnNewButton_2 = new JButton("exit");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_2, -8, SpringLayout.NORTH, selectNoLabel);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setFont(new Font("Lucida Handwriting", Font.PLAIN, 20));
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton = new JButton("exit");

		textArea.setText(
				"***********+ <dynamic> + ************\r\n1. print all info\r\n2. animal management\r\n3. plant management\r\n4. item management\r\n***********************************");

		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		springLayout.putConstraint(SpringLayout.NORTH, selectNoText, 18, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, selectNoLabel, 15, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -56, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);

		JButton btnNewButton_3 = new JButton("next day");
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_2, 6, SpringLayout.EAST, btnNewButton_3);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_3, -3, SpringLayout.NORTH, selectNoLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_3, 6, SpringLayout.EAST, confirmBtn);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day = farm.getDuration();
				day = day - 1;
				farm.setDuration(day);
				farm.updateFarm();
				if (day == 0) {
					textArea.setText("game over!");
					System.exit(0);
				} 
			}
		});
		btnNewButton_3.setFont(new Font("Lucida Handwriting", Font.PLAIN, 15));
		frame.getContentPane().add(btnNewButton_3);

		mainFun();
	}
}
