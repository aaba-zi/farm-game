package com.cn.farm.model;

import cn.hutool.core.date.DateUtil;
import com.cn.farm.database.Database;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Farm.
 * Farm Class
 */
public class Farm {
    private String name;
    // farm type
    private Integer type;
    // damaged or not
    private Integer fenceDamaged;
    private Integer duration;
    //available money 
    private Integer money;
    //remaining actions for farmer
    private Integer farmerRemainderCount;
    private String lastLoginTime;
    private String createTime;

    /**
     * The Animal list.
     */
    List<Animal> animalList;
    /**
     * The Plant list.
     */
    List<Plant> plantList;
    /**
     * The Muck list.
     */
    List<Muck> muckList;
    /**
     * The Feed list.
     */
    List<Feed> feedList;

    /**
     * Instantiates a new Farm.
     */
    public Farm() {
    }
    /**
     * Instantiates a new Farm.
     *
     * @param loadData load data or not
     */
    public Farm(boolean loadData) {
        if (loadData) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode farmJson = Database.currentFarmData;
            //initialize the basic infomation
            this.name = farmJson.get("name").toString().replace("\"", "");
            this.type = Integer.parseInt(farmJson.get("type").toString());
            this.fenceDamaged = Integer.parseInt(farmJson.get("fenceDamaged").toString());
            this.duration = Integer.parseInt(farmJson.get("duration").toString());
            this.money = Integer.parseInt(farmJson.get("money").toString());
            this.farmerRemainderCount = Integer.parseInt(farmJson.get("farmerRemainderCount").toString());
            this.createTime = farmJson.get("createTime").toString().replace("\"", "");
            ;

            try {
                // initialize animal
                //if this farm purchase this animal before
                if (!farmJson.get("animalList").isNull()) {
                    ArrayNode animalNode = (ArrayNode) farmJson.get("animalList");
                    animalList = mapper.readValue(animalNode.toString(), new TypeReference<List<Animal>>() {
                    });
                } else {
                    animalList = new ArrayList<>();
                }

                // initialize plant
                if (!farmJson.get("plantList").isNull()) {
                    ArrayNode plantNode = (ArrayNode) farmJson.get("plantList");
                    plantList = mapper.readValue(plantNode.toString(), new TypeReference<List<Plant>>() {
                    });
                } else {
                    plantList = new ArrayList<>();
                }

                //initialize muck
                if (!farmJson.get("muckList").isNull()) {
                    ArrayNode muckNode = (ArrayNode) farmJson.get("muckList");
                    muckList = mapper.readValue(muckNode.toString(), new TypeReference<List<Muck>>() {
                    });
                } else {
                    muckList = new ArrayList<>();
                }

                // initialize feed
                if (!farmJson.get("feedList").isNull()) {
                    ArrayNode feedNode = (ArrayNode) farmJson.get("feedList");
                    feedList = mapper.readValue(feedNode.toString(), new TypeReference<List<Feed>>() {
                    });
                } else {
                    feedList = new ArrayList<>();
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create farm.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void createFarm() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Set default data
        fenceDamaged = 0;
        farmerRemainderCount = 2;
        createTime = DateUtil.date().toString();
        lastLoginTime = createTime;
        animalList = new ArrayList<>();
        plantList = new ArrayList<>();
        muckList = Database.getGlobalMuck();
        feedList = Database.getGlobalFeed();
        money = Integer.parseInt(Database.getGlobalParam("initMoney").toString());
        String thisStr = mapper.writeValueAsString(this);
        Database.farmData.set(name, mapper.readTree(thisStr.replace("\\\"", "")));
        Database.updateData();
    }

    /**
     * Update farm.
     */
    public void updateFarm() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //store current status into the database
            String thisStr = mapper.writeValueAsString(this);
            Database.farmData.set(name, mapper.readTree(thisStr));
            Database.updateData();
            Database.setCurrentFarm(name);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Farm{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", fenceDamaged=" + fenceDamaged +
                ", duration=" + duration +
                ", money=" + money +
                ", farmerRemainderCount=" + farmerRemainderCount +
                ", createTime=" + createTime +
                '}';
    }

    /**
     * Gets last login time.
     *
     * @return the last login time
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets last login time.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * Gets animal list.
     *
     * @return the animal list
     */
    public List<Animal> getAnimalList() {
        return animalList;
    }

    /**
     * Sets animal list.
     *
     * @param animalList the animal list
     */
    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }

    /**
     * Gets plant list.
     *
     * @return the plant list
     */
    public List<Plant> getPlantList() {
        return plantList;
    }

    /**
     * Sets plant list.
     *
     * @param plantList the plant list
     */
    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    /**
     * Gets muck list.
     *
     * @return the muck list
     */
    public List<Muck> getMuckList() {
        return muckList;
    }

    /**
     * Sets muck list.
     *
     * @param muckList the muck list
     */
    public void setMuckList(List<Muck> muckList) {
        this.muckList = muckList;
    }

    /**
     * Gets feed list.
     *
     * @return the feed list
     */
    public List<Feed> getFeedList() {
        return feedList;
    }

    /**
     * Sets feed list.
     *
     * @param feedList the feed list
     */
    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets fence damaged.
     *
     * @return the fence damaged
     */
    public Integer getFenceDamaged() {
        return fenceDamaged;
    }

    /**
     * Sets fence damaged.
     *
     * @param fenceDamaged the fence damaged
     */
    public void setFenceDamaged(Integer fenceDamaged) {
        this.fenceDamaged = fenceDamaged;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets money.
     *
     * @return the money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * Sets money.
     *
     * @param money the money
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * Gets farmer remainder count.
     *
     * @return the farmer remainder count
     */
    public Integer getFarmerRemainderCount() {
        return farmerRemainderCount;
    }

    /**
     * Sets farmer remainder count.
     *
     * @param farmerRemainderCount the farmer remainder count
     */
    public void setFarmerRemainderCount(Integer farmerRemainderCount) {
        this.farmerRemainderCount = farmerRemainderCount;
    }

    /**
     * Gets create time.
     *
     * @return the create time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
