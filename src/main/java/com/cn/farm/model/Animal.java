package com.cn.farm.model;

import cn.hutool.core.date.DateUtil;
import com.cn.farm.database.Database;

/**
 * The type Animal.
 */
public class Animal extends BaseItem implements AnimalAction {
    // grow period
    private Integer durationHour;
    //happiness index
    private Integer happinessIndex;
    //health index
    private Integer healthIndex;
    //status
    private Integer status;
    private String updateTime;
    private String createTime;

    @Override
    public boolean feed(Feed feed) {
        // the amount of feed is 0
        if (feed.getCount() < 1) {
            System.out.println("fail to feed");
            return false;
        }
        healthIndex += feed.getHealthEffect();
        happinessIndex += feed.getHappinessEffect();
        feed.setCount(feed.getCount() - 1);
        System.out.println("successful feed!");
        return true;
    }
    
    @Override
    public boolean play() {
    	healthIndex += 2;
    	happinessIndex += 1;
    	return true;
    }
    

    @Override
    public boolean purchase() {
        Farm farm = Database.currentFarm;
        //initialize the animal data
        happinessIndex = 80;
        healthIndex = 80;
        createTime = DateUtil.date().toString();
        status = 0;
        updateTime = createTime;
        //change left time
        farm.setMoney(farm.getMoney() - purchasePrice);
        // add purchase animal into farm list
        farm.getAnimalList().add(this);
        farm.updateFarm();
        return true;
    }

    @Override
    public boolean sell() {
        Farm farm = Database.currentFarm;
        //change left amount of money
        farm.setMoney(farm.getMoney() + sellPrice);
        farm.updateFarm();
        return true;
    }

    @Override
    public String toString() {
        return "Animal{" +
                ", durationHour=" + durationHour +
                ", happinessIndex=" + happinessIndex +
                ", healthIndex=" + healthIndex +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", level=" + level +
                ", count=" + count +
                '}';
    }

    /**
     * Gets duration hour.
     *
     * @return the duration hour
     */
    public Integer getDurationHour() {
        return durationHour;
    }

    /**
     * Sets duration hour.
     *
     * @param durationHour the duration hour
     */
    public void setDurationHour(Integer durationHour) {
        this.durationHour = durationHour;
    }

    /**
     * Gets happiness index.
     *
     * @return the happiness index
     */
    public Integer getHappinessIndex() {
        return happinessIndex;
    }

    /**
     * Sets happiness index.
     *
     * @param happinessIndex the happiness index
     */
    public void setHappinessIndex(Integer happinessIndex) {
        this.happinessIndex = happinessIndex;
    }

    /**
     * Gets health index.
     *
     * @return the health index
     */
    public Integer getHealthIndex() {
        return healthIndex;
    }

    /**
     * Sets health index.
     *
     * @param healthIndex the health index
     */
    public void setHealthIndex(Integer healthIndex) {
        this.healthIndex = healthIndex;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets update time.
     *
     * @return the update time
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets update time.
     *
     * @param updateTime the update time
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
