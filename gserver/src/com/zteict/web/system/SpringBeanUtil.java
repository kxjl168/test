package com.zteict.web.system;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zteict.tool.utils.DateUtil;

public class SpringBeanUtil implements ApplicationContextAware{
	
	private static Logger logger=Logger.getLogger(SpringBeanUtil.class);
	
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtil.applicationContext = applicationContext;
	}

	public static Object getBeanByName(String beanName) {
		if (applicationContext == null){
			return null;
		}
		return applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}
	
	
	
	//private static SpiderLogDao sLog=null;
	/**
	 * 写入爬取日志
	 * @param info
	 */
	public static void OraLog(String info)
	{
		try {
			
			logger.info(info);
			//if(sLog==null)
			//	sLog=SpringBeanUtil.getBean(SpiderLogDao.class);
			
			//SpiderLog log=new SpiderLog();
			//log.setLogdate(DateUtil.getNowStr(""));
			//log.setInfo(info);
			//sLog.addSpiderLogInfo(log);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
	}

}