package com.zteict.tool.config;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/*

 * @date 2016-2-23
 * @author zj
 *
 * 系统监听，程序启动时初始化并存储相关参数
 */
public class SystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initContextParam(servletContextEvent);
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) { }
 
    private void initContextParam(ServletContextEvent event) {
        Enumeration<String> names = event.getServletContext().getInitParameterNames();
        while (names.hasMoreElements())
        {
            String name = names.nextElement();
            String value = event.getServletContext().getInitParameter(name);
            SysProperties.getInstance().put(name, value);
        }
        
    	String sysFilePath= ConfigReader.getConfigPath();
        
        configWarthDog dog=new configWarthDog(sysFilePath);
        dog.setDelay(10000);
        dog.start();
        
    }
 
}