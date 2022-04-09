package com.cn.farm.model;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.cn.farm.database.Database;

import java.util.Date;

/**
 * The type Plant.
 * @Description: Plant class
 */
public class Plant extends BaseItem implements PlantAction {
    //  grow period
    private Integer durationHour;
    // speed up the time to grow
    private Integer adjHour;
    private Integer status;
    // update the time
    private String updateTime;
    // last time we watering
    private String lastWatering;
    // create time
    private String createTime;

    @Override
    public boolean watering() {
        //if it is more than 1 day after last watering 
        if (DateUtil.between(DateUtil.parse(lastWatering), DateUtil.date(), DateUnit.DAY) > 1){
            adjHour += (Integer) Database.getGlobalParam("wateringHour");
            lastWatering = DateUtil.date().toString();
            System.out.println("successful watering");
            return true;
        }else {
            System.out.println("watering once per day, try it tommorow");
            return false;
        }
}

    @Override
    public boolean fertilize(Muck muck) {
        //no muck 
        if (muck.getCount() < 1) {
            System.out.println("muck is not enough!");
            return false;
        }
        adjHour += muck.getEffectHour();
        muck.setCount(muck.getCount() - 1);
        System.out.println("Successful fertilization");
        return true;
    }

    @Override
    public boolean purchase() {
        Farm farm = new Farm(true);
        //initialize the data
        createTime = DateUtil.date().toString();
        status = 0;
        adjHour = 0;
        lastWatering = createTime;
        updateTime = createTime;

        //change money
        farm.setMoney(farm.getMoney() - purchasePrice);
        //add into the farm list
        farm.getPlantList().add(this);
        farm.updateFarm();
        return true;
    }

    @Override
    public boolean sell() {
        Farm farm = new Farm(true);
        //predict harvest time
        Date endTime = DateUtil.offset(DateUtil.date(), DateField.HOUR, adjHour);
        //if it is mature
        if(DateUtil.between(DateUtil.parse(createTime), endTime, DateUnit.SECOND) < 1){
            status = 2;
            System.out.println("sell successful");
            return true;
        }else{
            System.out.println("The product is not available for sale");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Plant{" +
                ", durationHour=" + durationHour +
                ", adjHour=" + adjHour +
                ", status=" + status +
                ", updateTime='" + updateTime + '\'' +
                ", lastWatering='" + lastWatering + '\'' +
                ", createTime='" + createTime + '\'' +
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
     * Gets adj hour.
     *
     * @return the adj hour
     */
    public Integer getAdjHour() {
        return adjHour;
    }

    /**
     * Sets adj hour.
     *
     * @param adjHour the adj hour
     */
    public void setAdjHour(Integer adjHour) {
        this.adjHour = adjHour;
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
     * Gets last watering.
     *
     * @return the last watering
     */
    public String getLastWatering() {
        return lastWatering;
    }

    /**
     * Sets last watering.
     *
     * @param lastWatering the last watering
     */
    public void setLastWatering(String lastWatering) {
        this.lastWatering = lastWatering;
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
