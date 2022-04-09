package com.cn.farm;

import com.cn.farm.controller.Application;
import org.junit.Test;

/**
 * ApplicationTest
 **/

public class ApplicationTest {

    @Test
    public void printGlobalFarmTypeTest() {
        Application application = new Application();
        application.printGlobalFarmType();
    }
    
    @Test
    public void printGlobalAnimalTest() {
        Application application = new Application();
        application.printGlobalAnimal();
    }
    
    @Test
    public void printGlobalPlantTest() {
        Application application = new Application();
        application.printGlobalPlant();
    }
    
}
