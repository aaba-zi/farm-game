package com.cn.farm.model;

/**
 * The type Feed.
 * feed class
 */
public class Feed extends BaseItem implements BaseAction{
    // increase health index
    private Integer healthEffect;
    // increase happiness index
    private Integer happinessEffect;

    /**
     * Gets health effect.
     *
     * @return the health effect
     */
    public Integer getHealthEffect() {
        return healthEffect;
    }

    /**
     * Sets health effect.
     *
     * @param healthEffect the health effect
     */
    public void setHealthEffect(Integer healthEffect) {
        this.healthEffect = healthEffect;
    }

    /**
     * Gets happiness effect.
     *
     * @return the happiness effect
     */
    public Integer getHappinessEffect() {
        return happinessEffect;
    }

    /**
     * Sets happiness effect.
     *
     * @param happinessEffect the happiness effect
     */
    public void setHappinessEffect(Integer happinessEffect) {
        this.happinessEffect = happinessEffect;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "healthEffect=" + healthEffect +
                ", happinessEffect=" + happinessEffect +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", level=" + level +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean purchase() {
        count += 1;
        return true;
    }

    @Override
    public boolean sell() {
        if (count < 1) {
            return false;
        }
        count -= 1;
        return true;
    }
}
