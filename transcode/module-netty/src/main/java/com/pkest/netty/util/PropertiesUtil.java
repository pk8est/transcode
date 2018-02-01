package com.pkest.netty.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wuzhonggui on 2018/2/1.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class PropertiesUtil {

    protected static Properties properties;
    protected static Map<String, Object> defaultValues = new HashMap();

    static {
        init("netty-setting.properties");
    }

    public static void init(String propertieFile){
        try {
            properties = new Properties();
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertieFile));
            Set<String> keySet = properties.stringPropertyNames();

            for (String key: keySet){
                Object value = properties.get(key);
                defaultValues.put(key, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Update(String key, String value){
        defaultValues.put(key, value);
    }

    public static Object get(String key){
        return get(key, null);
    }

    public static Object get(String key, Object defaultValue){
        if(System.getenv(key) != null){
            return System.getenv(key);
        }else if(defaultValues.containsKey(key)){
            return defaultValues.get(key);
        }else{
            return defaultValue;
        }
    }

    public static void set(String key, Object value){
        defaultValues.put(key, value);
    }

    public static Map<String, Object> getAll(){
        return defaultValues;
    }

    public static void setAll(Map<String, Object> map){
        for(Map.Entry<String, Object> entry: map.entrySet()){
            set(entry.getKey(), entry.getValue());
        }
    }

    public static String getScanProtocolPacket(){
        return (String) get("scan.protocol.packet");
    }

}
