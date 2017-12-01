package com.zteict.tool.config;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.FileWatchdog;

/*

 * @date 2016-2-23
 * @author zj
 */
public class configWarthDog extends FileWatchdog {

	
	
	protected configWarthDog(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doOnChange() {
	
		
		System.out.println("===========configFile changed! reloading...======");
		ConfigReader.getInstance().reload();
		
	}
	
}
