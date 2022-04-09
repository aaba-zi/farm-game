package com.cn.farm;
/**  
* @Title: ViewTest.java  
*
* @Package com.cn.farm  
*
* @Description: TODO
*
* @author Fantasy  
*
* @date 2020年5月24日  
*
* @version V1.0  
*/

import java.awt.EventQueue;

import org.junit.Test;

import com.cn.farm.database.Database;
import com.cn.farm.view.FarmLand;

public class ViewTest {
	
	@Test
	public void farmLandTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Database.setCurrentFarm("fantasy");
					FarmLand window = new FarmLand();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

}
