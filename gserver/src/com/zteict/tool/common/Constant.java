package com.zteict.tool.common;

import org.springframework.context.ApplicationContext;

public class Constant {

/**
 * 定义常量类	
 */
	/**登录成功 ；用户正常状态*/
	public static final int login_status = 1; //登录成功
	/**登录失败*/
	public static final int login_status_unsuces=2;//登录失败
	/**账号锁定；用户状态锁定*/
	public static final int login_status_lock=3;//账号锁定
	/**账号解锁*/
	public static final int login_status_unlock=4;//账号解锁
	/**登出*/
	public static final int logout_status = 0;//登出
	
	public static final String  localAuth = "1";//本地鉴权
	
	public static final String  IAMAuth = "2";//第三方鉴权
	
	public static final String SESSION_USER = "sessionUser"; //后台登录 session保存
	
	public static final String SESSION_HMUSER = "HMsessionUser"; //普通用户session session保存
	public static final String HMsessionID = "HMsessionID"; //普通用户session session保存
	
	
	public static final String NO_INTERCEPTOR_PATH = ".*/((loginin)|(logout)|(code)).*";	//不对匹配该值的访问路径拦截（正则）
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	
	/*第一次加解密秘钥*/
	public static final String FIRST_AES_KEY="b2xkcGhvdG9vbGRw";
	
	  /**
     * 处理OK的返回码
     */
    public static final String OKCode = "200";
    
    /**
     * 数据库访问异常错误码
     */
    public static final String DataBaseErrorCode = "201";
    
    /**
     * 失败返回码
     */
    public static final String FailCode = "201";
    
    /**
     * 字段校验返回码
     */
    public static final String CheckCode = "202";
    
    /**
     * 空值异常
     */
    public static final String NullErrorCode = "202";
    
    /**
     * 系统异常错误码
     */
    public static final String SystemError = "500";
}
