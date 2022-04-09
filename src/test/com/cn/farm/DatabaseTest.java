package com.cn.farm;

import com.cn.farm.database.Database;
import com.cn.farm.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Test;

import java.util.List;

/**
 * DatabaseTest
 **/

public class DatabaseTest {

	
    @Test
    public void arrayNodeToListTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode plantNode = (ArrayNode) Database.getGlobalParam("plant");
        List<Plant> plantList = mapper.readValue(plantNode.toString(), new TypeReference<List<Plant>>() { });
        System.out.println(plantList.get(0).toString());
    }

    @Test
    public void getGlobalAnimalTest(){
        List<Animal> animalList = Database.getGlobalAnimal();
        System.out.println(animalList.get(0).toString());
    }

    @Test
    public void getGlobalPlantTest(){
        List<Plant> plantList = Database.getGlobalPlant();
        System.out.println(plantList.get(0).toString());
    }

    @Test
    public void getGlobalMuckTest(){
        List<Muck> muckList = Database.getGlobalMuck();
        System.out.println(muckList.get(0).toString());
    }

    @Test
    public void getGlobalFeedTest(){
        List<Feed> feedList = Database.getGlobalFeed();
        System.out.println(feedList.get(0).toString());
    }

    @Test
    public void currentFarmTest(){
        Database.setCurrentFarm("yyy");
        Farm farm = Database.currentFarm;
        System.out.println(farm.getType());
        farm.setType(1);
        Database.currentFarm.updateFarm();
        System.out.println(Database.currentFarm.getType());
    }
}
