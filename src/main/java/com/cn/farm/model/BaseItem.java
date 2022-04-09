package com.cn.farm.model;

/**
 * @ClassName Item
 * Basic assist items
 **/

public abstract class BaseItem{
    //name
    public String name;
    // purchase price
    public Integer purchasePrice;
    //sell price
    public Integer sellPrice;
    // level of item(advance, medium, low)
    public Integer level;
    // remaining amount 
    public Integer count;
    //description
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
