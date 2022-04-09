package com.cn.farm.controller;

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
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * The type Application.
 *  Application
 * application
 */
public class Application {
    /**
     * Print store animal.
     * @return global animal info
     */
	
    public String printGlobalAnimal() {
        List<Animal> animalList = Database.getGlobalAnimal();
        String result = "";
        for (int i = 0; i < animalList.size(); i++) {
            Animal animal = animalList.get(i);
            System.out.println("------------------------No." + i + "-------------------------");
            System.out.println("\t name:\t" + animal.getName());
            System.out.println("\t purchase price:\t" + animal.getPurchasePrice());
            System.out.println("\t description:\t" + animal.getDescription());
            System.out.println();
            result += "------------------------No." + i + "-------------------------";
            result += "\t name:\t" + animal.getName();
            result += "\t purchase price:\t" + animal.getPurchasePrice();
            result += "\t description:\t" + animal.getDescription();
        }
        return result;
    }

    /**
     * Print store plant.
     */
    public void printGlobalPlant() {
        List<Plant> plantList = Database.getGlobalPlant();
        for (int i = 0; i < plantList.size(); i++) {
            Plant plant = plantList.get(i);
            System.out.println("------------------------No." + i + "-------------------------");
            System.out.println("\t name:\t" + plant.getName());
            System.out.println("\t purchase price:\t" + plant.getPurchasePrice());
            System.out.println("\t sell price:\t" + plant.getSellPrice());
            System.out.println("\t grow period:\t" + plant.getDurationHour());
            System.out.println("\t description:\t" + plant.getDescription());
            System.out.println();
        }
    }

    /**
     * Print global farm type.
     */
    public void printGlobalFarmType() {
        ArrayNode farmTypeNode = (ArrayNode) Database.getGlobalParam("farmType");
        System.out.println("=============================================");
        for (JsonNode farmType : farmTypeNode) {
            System.out.println("\t" + farmType.get("key") + ". \t" + farmType.get("value").toString());
        }
        System.out.println("=============================================");
    }

    /**
     * Print farm info.
     */
    public void printFarmInfo() {
    	String result = "";
        Farm farm = Database.currentFarm;
        System.out.println("======================Farm=======================");
        System.out.println("\t name:\t" + farm.getName());
        System.out.println("\t type:\t" + farm.getType());
        System.out.println("\t damaged or not:\t" + farm.getFenceDamaged());
        System.out.println("\t money:\t" + farm.getMoney());
        System.out.println("\t remaining actions:\t" + farm.getFarmerRemainderCount());
        System.out.println("\t create time:\t" + farm.getCreateTime());
        System.out.println("\t game time:\t" + farm.getDuration() + "day");
        System.out.println("====================================================");
        
    }

    /**
     * Print all animal info.
     */
    public void printAnimalInfo() {
        Farm farm = Database.currentFarm;
        List<Animal> animalList = farm.getAnimalList();
        if (animalList.size() < 1) {
            System.out.println("You didn't buy animal.");
        }
        for (int i = 0; i < animalList.size(); i++) {
            Animal animal = animalList.get(i);
            System.out.println("------------------------No." + i + " Animal-------------------------");
            System.out.println("\t name:\t" + animal.getName());
            System.out.println("\t purchase price:\t" + animal.getPurchasePrice());
            System.out.println("\t description:\t" + animal.getDescription());
            System.out.println("\t health index:\t" + animal.getHealthIndex());
            System.out.println("\t happiness index:\t" + animal.getHappinessIndex());
            System.out.println("\t create time:\t" + animal.getCreateTime());
            System.out.println();
        }
    }

    /**
     * Pursue animal.
     */
    public void pursueAnimal() {
        Scanner sc = new Scanner(System.in);
        List<Animal> animalList = Database.getGlobalAnimal();
        Farm farm = Database.currentFarm;
        int operate;
        System.out.println("Find animal list below: ");
        printGlobalAnimal();
        System.out.println("Your current available balance:" + farm.getMoney());
        while (true) {
            System.out.println("Please enter the number of the animal you want to buy (enter - 1 to exit):");
            operate = sc.nextInt();
            if (operate == -1) {
                return;
            } else if (operate >= 0 && operate < animalList.size()) {
                if (animalList.get(operate).purchase()) {
                    System.out.println("successful purchase.");
                    return;
                } else {
                    System.out.println("purchase failed.");
                }
            } else {
                System.out.println("type error, please select again");
            }
        }
    }

    /**
     * Care animal.
     */
    public void careAnimal() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Animal> animalList = farm.getAnimalList();
        List<Feed> feedList = farm.getFeedList();
        int operate, animalNum, feedNum;
        while (true) {
            System.out.println("********************Animal Care*************************");
            System.out.println("*\t1. \tFeed");
            System.out.println("*\t2. \tPlay");
            System.out.println("*\t0. \t Go back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select actions: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
                    return;
                case 1:
                    printAnimalInfo();
                    System.out.println("choose animal you want to feed: ");
                    animalNum = sc.nextInt();
                    sc.nextLine();
                    printFeedInfo();
                    System.out.println("please choose food to feed:");
                    feedNum = sc.nextInt();
                    sc.nextLine();
                    animalList.get(animalNum).feed(feedList.get(feedNum));
                    break;
                case 2:
                    printAnimalInfo();
                    System.out.println("choose animal you want to play with: ");
                    animalNum = sc.nextInt();
                    sc.nextLine();
                    printFeedInfo();
                    sc.nextLine();
                    animalList.get(animalNum).play();
                    break;
                default:
                    break;
            }
            farm.updateFarm();
        }
    }

    /**
     * Animal manage.
     */
    public void animalManage() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        int operate;
        while (true) {
            System.out.println("********************Animal Management*************************");
            System.out.println("*\t1. \t print all animal info");
            System.out.println("*\t2. \t purchase new animal");
            System.out.println("*\t3. \t manage animal");
            System.out.println("*\t0. \t go back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select actions");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
                    return;
                case 1:
                    printAnimalInfo();
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
    }

    /**
     * Print plant info.
     */
    public void printPlantInfo() {
        Farm farm = Database.currentFarm;
        List<Plant> plantList = farm.getPlantList();
        if (plantList.size() < 1) {
            System.out.println("you didn't purchase plant.");
        }
        for (int i = 0; i < plantList.size(); i++) {
            Plant plant = plantList.get(i);
            System.out.println("------------------------No." + i + " plant-------------------------");
            System.out.println("\t Name:\t" + plant.getName());
            System.out.println("\t purchase price:\t" + plant.getPurchasePrice());
            System.out.println("\t sell price:\t" + plant.getSellPrice());
            System.out.println("\t description:\t" + plant.getDescription());
            System.out.println("\t last time watering:\t" + plant.getCreateTime());
            System.out.println("\t Feed:\t" + plant.getLastWatering());
            Date start = DateUtil.parse(plant.getCreateTime());
            Date end = DateUtil.offset(start, DateField.HOUR, plant.getDurationHour() - plant.getAdjHour());
            System.out.println("\t time to grow:\t" + FormatTool.dateBetweenParse(start, end));
            System.out.println("\t status:\t" + plant.getStatus());
            System.out.println();
        }
    }

    /**
     * Pursue plant.
     */
    public void pursuePlant() {
        ObjectMapper mapper = new ObjectMapper();
        Scanner sc = new Scanner(System.in);
        List<Plant> plantList = Database.getGlobalPlant();
        Farm farm = Database.currentFarm;
        int operate;
        System.out.println("below it animal list:");
        printGlobalPlant();
        System.out.println("your balance is " + farm.getMoney());
        while (true) {
            System.out.println("Please enter the number of the plant you want to buy (enter - 1 to exit):");
            operate = sc.nextInt();
            if (operate == -1) {
                return;
            } else if (operate >= 0 && operate < plantList.size()) {
                if (plantList.get(operate).purchase()) {
                    System.out.println("successful purchase.");
                    return;
                } else {
                    System.out.println("purchase failed.");
                }
            } else {
                System.out.println("type error, please select again.");
            }
        }
    }

    /**
     * Care plant.
     */
    public void carePlant() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Plant> plantList;
        List<Muck> muckList;
        int operate, plantNum, muckNum;
        while (true) {
            plantList = farm.getPlantList();
            muckList = farm.getMuckList();
            System.out.println("********************Plant Management*************************");
            System.out.println("*\t1. \t Fertilizer");
            System.out.println("*\t2. \t watering");
            System.out.println("*\t3. \t sell");
            System.out.println("*\t0. \t back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
                    return;
                case 1:
                    printPlantInfo();
                    System.out.println("please select plant to fertilize: ");
                    plantNum = sc.nextInt();
                    sc.nextLine();
                    printMuckInfo();
                    muckNum = sc.nextInt();
                    sc.nextLine();
                    plantList.get(plantNum).fertilize(muckList.get(muckNum));
                    break;
                case 2:
                    printPlantInfo();
                    System.out.println("please select plant to watering: ");
                    plantNum = sc.nextInt();
                    sc.nextLine();
                    plantList.get(plantNum).watering();
                    break;
                case 3:
                    printPlantInfo();
                    System.out.println("please select plant to sell: ");
                    plantNum = sc.nextInt();
                    sc.nextLine();
                    if (plantList.get(plantNum).sell()) {
                        plantList.remove(plantNum);
                        farm.updateFarm();
                    }
                    break;
                default:
                    break;
            }
            farm.updateFarm();
        }
    }

    /**
     * Plant manage.
     */
    public void plantManage() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Plant> plantList;
        int operate, plantNum;
        while (true) {
            plantList = farm.getPlantList();
            System.out.println("********************plant management*************************");
            System.out.println("*\t1. \t print all plant info");
            System.out.println("*\t2. \t purchase new palnt");
            System.out.println("*\t3. \t manage plant");
            System.out.println("*\t4. \t sell plant");
            System.out.println("*\t0. \t back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
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
                    plantNum = sc.nextInt();
                    sc.nextLine();
                    if (plantList.get(plantNum).sell()) {
                        farm.setMoney(farm.getMoney() + plantList.get(plantNum).getSellPrice());
                        plantList.remove(plantNum);
                        farm.updateFarm();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Print muck info.
     */
    public void printMuckInfo() {
        Farm farm = Database.currentFarm;
        List<Muck> muckList = farm.getMuckList();
        for (int i = 0; i < muckList.size(); i++) {
            Muck muck = muckList.get(i);
            System.out.println("------------------------No." + i + "Fertilizer-------------------------");
            System.out.println("\t Name:\t" + muck.getName());
            System.out.println("\t purchase price:\t" + muck.getPurchasePrice());
            System.out.println("\t sell price:\t" + muck.getSellPrice());
            System.out.println("\t Fertilizer level:\t" + muck.getLevel());
            System.out.println("\t speed up(hours):\t" + muck.getEffectHour());
            System.out.println("\t remaining hours:\t" + muck.getCount());
            System.out.println();
        }
    }

    /**
     * Pursue muck.
     */
    public void pursueMuck() {
        ObjectMapper mapper = new ObjectMapper();
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Muck> muckList = farm.getMuckList();
        int muckNum;
        System.out.println("please find fertilizer list below: ");
        for (int i = 0; i < muckList.size(); i++) {
            Muck muck = muckList.get(i);
            System.out.println("------------------------No." + i + "-------------------------");
            System.out.println("\t Name:\t" + muck.getName());
            System.out.println("\t purchase price:\t" + muck.getPurchasePrice());
            System.out.println("\t sell price:\t" + muck.getSellPrice());
            System.out.println("\t Fertilizer level:\t" + muck.getLevel());
            System.out.println("\t speed up(hours):\t" + muck.getEffectHour());
            System.out.println("\t description:\t" + muck.getDescription());
            System.out.println();
        }
        System.out.println("your current balance is :" + farm.getMoney());
        while (true) {
            System.out.println("Please enter the number of the Fertilier you want to buy (enter - 1 to exit):");
            muckNum = sc.nextInt();
            if (muckNum == -1) {
                return;
            } else if (muckNum >= 0 && muckNum < muckList.size()) {
                if (muckList.get(muckNum).purchase()) {
                    farm.setMoney(farm.getMoney() - muckList.get(muckNum).getPurchasePrice());
                    farm.updateFarm();
                    System.out.println("successful purchase.");
                    return;
                } else {
                    System.out.println("purchase failed.");
                }
            } else {
                System.out.println("type error, please select again.");
            }
        }
    }

    /**
     * Muck manage.
     */
    public void muckManage() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Muck> muckList = farm.getMuckList();
        int operate, muckNum;
        while (true) {
            System.out.println("********************Fertilizer Management*************************");
            System.out.println("*\t1. \t print all fertilizer info");
            System.out.println("*\t2. \t purchase fertilizer");
            System.out.println("*\t3. \t sell fertilizer");
            System.out.println("*\t0. \t go back to upper level");
            System.out.println("****************************************************");
            System.out.println("plase select action: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
                    return;
                case 1:
                    printMuckInfo();
                    break;
                case 2:
                    pursueMuck();
                    break;
                case 3:
                    printMuckInfo();
                    System.out.println("please select fertilizer you want to sell");
                    muckNum = sc.nextInt();
                    sc.nextLine();
                    if (muckList.get(muckNum).sell()) {
                        farm.setMoney(farm.getMoney() + muckList.get(muckNum).getSellPrice());
                        farm.updateFarm();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Print feed info.
     */
    public void printFeedInfo() {
        Farm farm = Database.currentFarm;
        List<Feed> feedList = farm.getFeedList();
        for (int i = 0; i < feedList.size(); i++) {
            Feed feed = feedList.get(i);
            System.out.println("------------------------No." + i + "Feed-------------------------");
            System.out.println("\t Name:\t" + feed.getName());
            System.out.println("\t purchase price:\t" + feed.getPurchasePrice());
            System.out.println("\t sell price:\t" + feed.getSellPrice());
            System.out.println("\t increase health index:\t" + feed.getHealthEffect());
            System.out.println("\t increase happiness index:\t" + feed.getHappinessEffect());
            System.out.println("\t feed level:\t" + feed.getLevel());
            System.out.println("\t remaining amount:\t" + feed.getCount());
            System.out.println("\t description:\t" + feed.getDescription());
            System.out.println();
        }
    }

    /**
     * Pursue feed.
     */
    public void pursueFeed() {
        ObjectMapper mapper = new ObjectMapper();
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Feed> feedList = farm.getFeedList();
        int feedNum;
        System.out.println("please find Feed list below: ");
        for (int i = 0; i < feedList.size(); i++) {
            Feed feed = feedList.get(i);
            System.out.println("------------------------NO." + i + "-------------------------");
            System.out.println("\t Name:\t" + feed.getName());
            System.out.println("\t purchase price:\t" + feed.getPurchasePrice());
            System.out.println("\t sell price:\t" + feed.getSellPrice());
            System.out.println("\t feed level:\t" + feed.getLevel());
            System.out.println("\t increase health index:\t" + feed.getHealthEffect());
            System.out.println("\t increase happiness index:\t" + feed.getHappinessEffect());
            System.out.println("\t description:\t" + feed.getDescription());
            System.out.println();
        }
        System.out.println("your current balance is: " + farm.getMoney());
        while (true) {
            System.out.println("Please enter the number of the feed you want to buy (enter - 1 to exit):");
            feedNum = sc.nextInt();
            if (feedNum == -1) {
                return;
            } else if (feedNum >= 0 && feedNum < feedList.size()) {
                if (feedList.get(feedNum).purchase()) {
                    farm.setMoney(farm.getMoney() - feedList.get(feedNum).getPurchasePrice());
                    farm.updateFarm();
                    System.out.println("successful purchase.");
                    return;
                } else {
                    System.out.println("purchase failed.");
                }
            } else {
                System.out.println("type error, please select again.");
            }
        }
    }

    /**
     * Feed manage.
     */
    public void feedManage() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        List<Feed> feedList = farm.getFeedList();
        int operate, feedNum;
        while (true) {
            System.out.println("********************Feed Management*************************");
            System.out.println("*\t1. \t print all fodder info");
            System.out.println("*\t2. \t purchase new fodder");
            System.out.println("*\t3. \t sell fodder");
            System.out.println("*\t0. \t back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
                    return;
                case 1:
                    printFeedInfo();
                    break;
                case 2:
                    pursueFeed();
                    break;
                case 3:
                    printMuckInfo();
                    System.out.println("Please select which fertilizzer to sell: ");
                    feedNum = sc.nextInt();
                    sc.nextLine();
                    if (feedList.get(feedNum).sell()) {
                        farm.setMoney(farm.getMoney() + feedList.get(feedNum).getSellPrice());
                        farm.updateFarm();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Item manage.
     */
    public void itemManage() {
        Scanner sc = new Scanner(System.in);
        Farm farm = Database.currentFarm;
        int operate;
        while (true) {
            System.out.println("********************Item Management*************************");
            System.out.println("*\t1. \t fodder management");
            System.out.println("*\t2. \t fertilizer management");
            System.out.println("*\t0. \t back to upper level");
            System.out.println("****************************************************");
            System.out.println("please select: ");
            operate = sc.nextInt();
            sc.nextLine();
            switch (operate) {
                case 0:
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
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        Application application = new Application();
        Scanner sc = new Scanner(System.in);
        System.out.println("welcome to the game, please select: ");
        String farmNameStr;
        while (true) {
            System.out.println("\t1: login\n\t2: create");
            int haveFarm = sc.nextInt();
            sc.nextLine();
            // login
            if (haveFarm == 1) {
                System.out.println("please input your farm name.");
                farmNameStr = sc.nextLine();
                // check if it is in the database.
                if (Database.setCurrentFarm(farmNameStr)) {
                    Farm farm = Database.currentFarm;
                    Date start = DateUtil.parse(farm.getCreateTime());
                    //if game is over
                    if (DateUtil.between(start, DateUtil.date(), DateUnit.DAY) >= farm.getDuration()) {
                        System.out.println("game over, there is your result.");
                        System.out.println("======================farm info=======================");
                        System.out.println("\t Name:\t" + farm.getName());
                        System.out.println("\t type:\t" + farm.getType());
                        if (farm.getType() == 4) {
                            System.out.println("\t current balance:\t" + farm.getMoney() * 1.1);
                        } else {
                            System.out.println("\t current balance:\t" + farm.getMoney());
                        }
                        System.out.println("\t game time:\t" + farm.getDuration() + "day");
                        System.out.println("====================================================");
                        return;
                    }
                    int operate;
                    System.out.println("welcome back!!!");
                    while (true) {
                        System.out.println("****************************************************");
                        System.out.println("*\t1. \t print all info");
                        System.out.println("*\t2. \t animal management");
                        System.out.println("*\t3. \t plant management");
                        System.out.println("*\t4. \t item management");
                        System.out.println("*\t0. \t exit");
                        System.out.println("****************************************************");
                        System.out.println("please select: ");
                        operate = sc.nextInt();
                        sc.nextLine();
                        switch (operate) {
                            case 0:
                                return;
                            case 1:
                                application.printFarmInfo();
                                break;
                            case 2:
                                application.animalManage();
                                break;
                            case 3:
                                application.plantManage();
                                break;
                            case 4:
                                application.itemManage();
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    System.out.println("farm is not exist, try again.");
                }
                //create a farm
            } else if (haveFarm == 2) {
                System.out.println("please input farm name: ");
                Farm farm = new Farm();
                farmNameStr = sc.nextLine();
                if (ObjectUtil.isNotEmpty(Database.getFarmByName(farmNameStr))) {
                    System.out.println("the farm is already exist, please change a name or login");
                    continue;
                }
                farm.setName(farmNameStr);
                System.out.println("please choose a type");
                application.printGlobalFarmType();
                farm.setType(sc.nextInt());
                System.out.println("how many days do you want?");
                farm.setDuration(sc.nextInt());
                int initMoney = Integer.parseInt(Database.getGlobalParam("initMoney").toString());
                if (farm.getType() == 1) {
                    farm.setMoney((int) Math.ceil(initMoney * 1.3));
                } else {
                    farm.setMoney(initMoney);
                }
                System.out.println("farm is created. please login!");
                farm.createFarm();
            }
        }
    }
}
