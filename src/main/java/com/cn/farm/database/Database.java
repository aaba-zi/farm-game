package com.cn.farm.database;

import com.cn.farm.model.*;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Database.
 */
public class Database {
    /**
     * Save all data of JSON file
     */
    public static ObjectNode dataJson;
    /**
     * save all farm data
     */
    public static ObjectNode farmData;
    /**
     * Current farm JSON format data
     */
    public static ObjectNode currentFarmData;
    /**
     * Current farm object
     */
    public static Farm currentFarm;
    // database file
    private static File file;
    // system separator
    private static String separator;

    static {
        separator = System.getProperty("file.separator");
        // Read database file
        file = new File(System.getProperty("user.dir") + separator + "database.json");
        // Converting database files
        ObjectMapper mapper = new ObjectMapper();

        try {
            //converting JSON into JAVA available JSON
            dataJson = (ObjectNode) mapper.readTree(file);
            farmData = (ObjectNode) dataJson.get("farm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the current farm
     */
    public static boolean setCurrentFarm(String name) {
    	/**
    	 * @param name of tha farm
    	 */
        if (farmData.has(name)) {
            currentFarmData = (ObjectNode) farmData.get(name);
            currentFarm = new Farm(true);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get the farm named name
     * @param personalise name
     * @return farm name
     */
    public static ObjectNode getFarmByName(String name) {

        return (ObjectNode) farmData.get(name);
    }

    /**
     * Press the key to get the global variable
     *
     * @param key variable
     * @return the object
     */
    public static Object getGlobalParam(String key) {
        return dataJson.get(key);
    }

    /**
     * Get all global animals
     *
     * @return the global animal
     */
    public static List<Animal> getGlobalAnimal() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode animalNode = (ArrayNode) Database.getGlobalParam("animal");
        List<Animal> animalList = null;
        try {
            animalList = mapper.readValue(animalNode.toString(), new TypeReference<List<Animal>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("jsonNode to list error");
            e.printStackTrace();
        }
        return animalList;
    }

    /**
     * Get all global plant
     *
     * @return the global plant
     */
    public static List<Plant> getGlobalPlant() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode plantNode = (ArrayNode) Database.getGlobalParam("plant");
        List<Plant> plantList = null;
        try {
            plantList = mapper.readValue(plantNode.toString(), new TypeReference<List<Plant>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("jsonNode to list error");
            e.printStackTrace();
        }
        return plantList;
    }

    /**
     * Get all global fertilizer
     *
     * @return the global muck
     */
    public static List<Muck> getGlobalMuck() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode muckNode = (ArrayNode) ((ObjectNode) Database.getGlobalParam("item")).get("muck");
        List<Muck> muckList = null;
        try {
            muckList = mapper.readValue(muckNode.toString(), new TypeReference<List<Muck>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("jsonNode to list error");
            e.printStackTrace();
        }
        return muckList;
    }

    /**
     * Get all global fodder
     *
     * @return the global feed
     */
    public static List<Feed> getGlobalFeed() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode feedNode = (ArrayNode) ((ObjectNode) Database.getGlobalParam("item")).get("feed");
        List<Feed> feedList = null;
        try {
            feedList = mapper.readValue(feedNode.toString(), new TypeReference<List<Feed>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("jsonNode to list error");
            e.printStackTrace();
        }
        return feedList;
    }

    /**
     * store data
     */
    public static void updateData() {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        dataJson.set("farm", farmData);
        try {
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(
                    file, JsonEncoding.UTF8);
            mapper.writeTree(jsonGenerator, dataJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
