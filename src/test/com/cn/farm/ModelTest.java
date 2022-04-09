package com.cn.farm;

import com.cn.farm.model.Animal;
import com.cn.farm.model.Farm;
import com.cn.farm.model.Feed;
import com.cn.farm.model.Muck;
import com.cn.farm.model.Plant;

import org.junit.Test;

/**
 * ModelTest
 **/

public class ModelTest {

    @Test
    public void FarmTest(){
        Farm farm = new Farm();
        System.out.println(farm.toString());
    }

    @Test
    public void AnimalTest(){
        Animal animal = new Animal();
        animal.setName("fantasy");
        System.out.println(animal.toString());
    }
    @Test
    public void PlantTest(){
        Plant plant = new Plant();
        plant.setName("fantasy");
        System.out.println(plant.toString());
    }
    
    @Test
    public void FeedTest(){
    	Feed feed = new Feed();
    	feed.setName("a");
        System.out.println(feed.toString());
    }
    
    @Test
    public void MuckTest(){
    	Muck muck = new Muck();
    	muck.setName("a");
        System.out.println(muck.toString());
    }
}
