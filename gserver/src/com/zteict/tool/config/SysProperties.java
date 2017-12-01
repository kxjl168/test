package com.zteict.tool.config;

import java.util.Properties;

/*

 * @date 2016-2-23
 * @author zj
 * 用于存储参数键值
 */
public final class SysProperties {
    private static SysProperties instance;
    private Properties initProperties = new Properties();
 
    private SysProperties() { }
 
    public static SysProperties getInstance() {
        if (instance == null) {
            instance = new SysProperties();
        }
        return instance;
    }
 
    /**
     * 获取对应的值
     * @param key String 提供的键（param-name）
     * @return String 键对应的值（param-value）
     */
    public String getProperty(String key)
    {
        return this.initProperties.getProperty(key);
    }
 
    /**
     * 检测是否包含该键
     * @param key String 键
     * @return boolean 该键存在返回 true，否则返回 false
     */
    public boolean containsKey(String key) {
        return this.initProperties.containsKey(key);
    }
 
    /**
     * 存储参数
     * @param key String param-name
     * @param object String param-value
     */
    public void put(String key, String object) {
        this.initProperties.put(key, object);
    }
}
 
