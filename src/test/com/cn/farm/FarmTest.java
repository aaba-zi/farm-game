package com.cn.farm;

import com.cn.farm.database.Database;
import com.cn.farm.model.Farm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * FarmTest
 **/

public class FarmTest {

    @Test
    public void autoFillJsonToFarm() throws JsonProcessingException {
        Database.setCurrentFarm("test");
        ObjectMapper mapper = new ObjectMapper();
        Farm farm = new Farm(true);
        System.out.println(mapper.writeValueAsString(farm));
    }
    
    
    @Test
    public void reflectTest() throws JsonProcessingException {
        Database.setCurrentFarm("fantasy");
        Farm farm = new Farm(true);
        try {
			String  name = (String) farm.getClass().getMethod("getName").invoke(farm);
			System.out.println(name);
			name = (String) Farm.class.getMethod("getName").invoke(new Farm());
			System.out.println(name);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Test
    public void farmToJson() throws JsonProcessingException {
        Database.setCurrentFarm("yyy");
        ObjectMapper mapper = new ObjectMapper();
        Farm farm = new Farm(true);
        //farm.updateFarm();
        System.out.println(farm.getName());
        String farmStr = mapper.writeValueAsString(farm);
        System.out.println(farmStr);
        System.out.println(farmStr.replace("\\\"", ""));
        System.out.println(mapper.readTree(farmStr.replace("\\\"", "")));

    }
    @Test
    public void autoFillJsonToFarm1() throws JsonProcessingException {
        Database.setCurrentFarm("test");
        ObjectMapper mapper = new ObjectMapper();
        Farm farm = new Farm(true);
        System.out.println(mapper.writeValueAsString(farm));
    }

    @Test
    public void farmToJson1() throws JsonProcessingException {
        Database.setCurrentFarm("test");
        ObjectMapper mapper = new ObjectMapper();
        Farm farm = new Farm(true);
        //farm.updateFarm();
        System.out.println(farm.getName());
        String farmStr = mapper.writeValueAsString(farm);
        System.out.println(farmStr);
        System.out.println(farmStr.replace("\\\"", ""));
        System.out.println(mapper.readTree(farmStr.replace("\\\"", "")));

    }
    
}
