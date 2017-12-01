/**
 * 系统配置参数实体
 */
package com.zteict.tool.common;

public class Config
{
	/**
	 * 这个类用来记录系统全局配置参数,在系统启动时进行加载 数据表 sys_parameter中的配置数据,常驻内存
	 * 定义方法: 
	 * 采用键值对形式配置,如 BEE2C_SERVER_NAME 对应数据库表中 param_name字段, BEE2C_SERVER_VALUE对应数据库表中的param_value字段
	 * 
	 */
    //商旅系统服务地址 参数名称
    public final static String BEE2C_SERVER_NAME = "BEE2C_SERVER_NAME";
    //商旅系统服务地址 参数值
    private static String BEE2C_SERVER_VALUE = "";
    
    //缓存服务器地址name
    public final static String MEMCACHED_SERVER_NAME = "MEMCACHED_SERVER_NAME";
    //缓存服务器地址 值
    private static String MEMCACHED_SERVER_VALUE = "";
    //缓存服务器端口
    public final static String MEMCACHED_SERVERPORT_NAME = "MEMCACHED_SERVER_PORT";
    //缓存服务器端口值
    private static String MEMCACHED_SERVERPORT_VALUE = "11211";
    
    //add by bigw at 2015-5-28 配置i信系统ID
    public final static String IXIN_SYSTEM_ID_NAME = "IXIN_SYSTEM_ID";
    private static String IXIN_SYSTEM_ID_VALUE = "";
    
    //xmpp登陆用户
    public final static String XMPP_ACCOUNT = "assistant001";
    //xmpp登陆密码
    public final static String XMPP_PASSWD = "123321";
    //xmpp主机
    public final static String HOST = "XmppHost";
    private static String XMPP_HOST = "";
    
    //上传路径
    public static String UPLOADFILE_PATH = "/usr/local/apache-tomcat-6.0.43/webapps/gaga_files/fileupload/upgradepackagefile/";
    //下载前缀路径
    public static String DOWN_PATH = "gaga_files/fileupload/upgradepackagefile/";
    
    //web访问路径
    public static String HTTP_DIR = "";
    
    //商务系统分配名称
    public final static String USERNAME = "USERNAME";
    //
    private static String USERNAME_VALUE = "";
    //商务系统分配KEY
    public final static String USERKEY = "USERKEY";
    //
    private static String USERKEY_VALUE = "";
    //存储时间
    public final static String SAVETIME = "SAVETIME";
    //
    private static String SAVETIME_VALUE = "";
    
    public static String EJABBER_DOMAIN = "EJABBER_DOMAIN";
    
    public static String IAM_WSDL = "";
    
    //iam是否同步开关  0：不同步  1：同步         add by fengsz at 2014/5/29
    public static String IAM_SYNC = "";
    
    public static String BMS_WSDL = "";
    //消息服务器ip add by fengsz at 2014/4/22
    public static String XMPP_HTTP_URL = "";
   
    public static String SERVERTYPE4COMPANY = "";
    
    //公有云im地址   add by fengsz at 2014/4/22
    public static String IM_SERVER = "";
    
    // Ejabberd域
    private static String EJABBER_DOMAIN_VALUE = "";
    
    private static String DOWN_PAGE = "";
    
    private static String UPLOAD_FILE_URL = "";
    
    
    //头像服务器地址    add by fengsz at 2014/10/24
    private static String HEAD_SERVER_URL;
    //文件服务器地址  add by fengsz at 2014/10/24
    private static String FILE_SERVER_URL;
    
    
    //客户端到OCS平台版超时时间(分钟)
    private static int TIMEOUTMINUTES = 1;
    
    
    
    //发送手机短信url   add by jinmingchun at 2014/10/20
    private static String SMS_URL = "";
    
    //发送手机短信账号   add by jinmingchun at 2014/10/20
    private static String SMS_SN = "";
    
    //发送手机短信密码   add by jinmingchun at 2014/10/20
    private static String SMS_PWD = "";
    
    //web下载地址（https）   add by pengqp at 2015/3/31
    private static String HTTPS_DIR = "";
    
    //add by zhuqing at 2015-04-20 平台版增加管理员设置 begin
    //IMweb服务器地址
    private static String IM_HTTP_SERVER;
    
    //OCS平台管理服务器地址(平台版)
    private static String OCS_HTTP_SERVER = "";
    
    public static String getIM_HTTP_SERVER() {
    	return IM_HTTP_SERVER;
    }
    public static void setIM_HTTP_SERVER(String im_http_server) {
    	IM_HTTP_SERVER = im_http_server;
    }
    public static String getOCS_HTTP_SERVER() {
    	return OCS_HTTP_SERVER;
    }
    public static void setOCS_HTTP_SERVER(String ocs_http_server) {
    	OCS_HTTP_SERVER = ocs_http_server;
    }
    //add by zhuqing at 2015-04-20 平台版增加管理员设置 end
    
	public static String getEJABBER_DOMAIN_VALUE() {
		return EJABBER_DOMAIN_VALUE;
	}
	public static void setEJABBER_DOMAIN_VALUE(String ejabber_domain_value) {
		EJABBER_DOMAIN_VALUE = ejabber_domain_value;
	}
	public static String getBEE2C_SERVER_VALUE() {
		return BEE2C_SERVER_VALUE;
	}
	public static void setBEE2C_SERVER_VALUE(String bee2c_server_value) {
		BEE2C_SERVER_VALUE = bee2c_server_value;
	}
	public static String getMEMCACHED_SERVER_VALUE() {
		return MEMCACHED_SERVER_VALUE;
	}
	public static void setMEMCACHED_SERVER_VALUE(String memcached_server_value) {
		MEMCACHED_SERVER_VALUE = memcached_server_value;
	}
	public static String getMEMCACHED_SERVERPORT_VALUE() {
		return MEMCACHED_SERVERPORT_VALUE;
	}
	public static void setMEMCACHED_SERVERPORT_VALUE(
			String memcached_serverport_value) {
		MEMCACHED_SERVERPORT_VALUE = memcached_serverport_value;
	}
	public static String getSAVETIME_VALUE() {
		return SAVETIME_VALUE;
	}
	public static void setSAVETIME_VALUE(String savetime_value) {
		SAVETIME_VALUE = savetime_value;
	}
	public static String getUSERKEY_VALUE() {
		return USERKEY_VALUE;
	}
	public static void setUSERKEY_VALUE(String userkey_value) {
		USERKEY_VALUE = userkey_value;
	}
	public static String getUSERNAME_VALUE() {
		return USERNAME_VALUE;
	}
	public static void setUSERNAME_VALUE(String username_value) {
		USERNAME_VALUE = username_value;
	}
	public static String getXMPP_HOST() {
		return XMPP_HOST;
	}
	public static void setXMPP_HOST(String xMPPHOST) {
		XMPP_HOST = xMPPHOST;
	}
	public static String getUPLOADFILE_PATH() {
		return UPLOADFILE_PATH;
	}
	public static void setUPLOADFILE_PATH(String uPLOADFILEPATH) {
		UPLOADFILE_PATH = uPLOADFILEPATH;
	}
	public static String getHTTP_DIR() {
		return HTTP_DIR;
	}
	public static void setHTTP_DIR(String hTTPDIR) {
		HTTP_DIR = hTTPDIR;
	}
	public static String getIAM_WSDL() {
		return IAM_WSDL;
	}
	public static void setIAM_WSDL(String iam_wsdl) {
		IAM_WSDL = iam_wsdl;
	}
	public static String getBMS_WSDL() {
		return BMS_WSDL;
	}
	public static void setBMS_WSDL(String bms_wsdl) {
		BMS_WSDL = bms_wsdl;
	}
	public static String getXMPP_HTTP_URL() {
		return XMPP_HTTP_URL;
	}
	public static void setXMPP_HTTP_URL(String xMPP_HTTP_URL) {
		XMPP_HTTP_URL = xMPP_HTTP_URL;
	}
	public static String getSERVERTYPE4COMPANY() {
		return SERVERTYPE4COMPANY;
	}
	public static void setSERVERTYPE4COMPANY(String sERVERTYPE4COMPANY) {
		SERVERTYPE4COMPANY = sERVERTYPE4COMPANY;
	}
	public static String getIM_SERVER() {
		return IM_SERVER;
	}
	public static void setIM_SERVER(String iM_SERVER) {
		IM_SERVER = iM_SERVER;
	}
	public static String getIAM_SYNC() {
		return IAM_SYNC;
	}
	public static void setIAM_SYNC(String iAM_SYNC) {
		IAM_SYNC = iAM_SYNC;
	}
	public static String getDOWN_PAGE() {
		return DOWN_PAGE;
	}
	public static void setDOWN_PAGE(String down_page) {
		DOWN_PAGE = down_page;
	}
	public static String getUPLOAD_FILE_URL() {
		return UPLOAD_FILE_URL;
	}
	public static void setUPLOAD_FILE_URL(String uPLOAD_FILE_URL) {
		UPLOAD_FILE_URL = uPLOAD_FILE_URL;
	}
	public static String getHEAD_SERVER_URL() {
		return HEAD_SERVER_URL;
	}
	public static void setHEAD_SERVER_URL(String hEAD_SERVER_URL) {
		HEAD_SERVER_URL = hEAD_SERVER_URL;
	}
	public static String getFILE_SERVER_URL() {
		return FILE_SERVER_URL;
	}
	public static void setFILE_SERVER_URL(String fILE_SERVER_URL) {
		FILE_SERVER_URL = fILE_SERVER_URL;
	}
	public static String getSMS_URL() {
		return SMS_URL;
	}
	public static void setSMS_URL(String sms_url) {
		SMS_URL = sms_url;
	}
	public static String getSMS_SN() {
		return SMS_SN;
	}
	public static void setSMS_SN(String sms_sn) {
		SMS_SN = sms_sn;
	}
	public static String getSMS_PWD() {
		return SMS_PWD;
	}
	public static void setSMS_PWD(String sms_pwd) {
		SMS_PWD = sms_pwd;
	}
	
	public static String getHTTPS_DIR() {
		return HTTPS_DIR;
	}
	public static void setHTTPS_DIR(String hTTPS_DIR) {
		HTTPS_DIR = hTTPS_DIR;
    }
	
	public static int getTIMEOUTMINUTES() {
		return TIMEOUTMINUTES;
	}
	public static void setTIMEOUTMINUTES(int timeoutminutes) {
		TIMEOUTMINUTES = timeoutminutes;
	}
	public static String getIXIN_SYSTEM_ID_VALUE() {
		return IXIN_SYSTEM_ID_VALUE;
	}
	public static void setIXIN_SYSTEM_ID_VALUE(String ixin_system_id_value) {
		IXIN_SYSTEM_ID_VALUE = ixin_system_id_value;
	}
	
}
