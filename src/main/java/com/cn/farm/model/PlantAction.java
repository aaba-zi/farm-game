package com.cn.farm.model;

public interface PlantAction extends BaseAction {
    //watering
    public boolean watering();
    //fertilization
    public boolean fertilize(Muck muck);
}