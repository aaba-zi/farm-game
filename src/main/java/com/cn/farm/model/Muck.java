package com.cn.farm.model;

/**
 * The type Muck.
 * Muck class
 */
public class Muck extends BaseItem implements BaseAction {

    private Integer effectHour;

    @Override
    public String toString() {
        return "Muck{" +
                "effectHour=" + effectHour +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", level=" + level +
                ", count=" + count +
                '}';
    }

    /**
     * Gets effect hour.
     *
     * @return the effect hour
     */
    public Integer getEffectHour() {
        return effectHour;
    }

    /**
     * Sets effect hour.
     *
     * @param effectHour the effect hour
     */
    public void setEffectHour(Integer effectHour) {
        this.effectHour = effectHour;
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
        return  true;
    }
}
