package itc.common.tools.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.json
 * @ClassName: JsonUtil
 * @Description: JSON工具类
 * @Author: MasterCoding
 * @CreateDate: 9/6/18 4:17 PM
 * Copyright: Copyright (c) 2018
 **/
@Slf4j
public final class JsonUtil {

    /**
     * 对象映射器
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    /**
     * 进入时设置Jackson支持JDK1.8的LocalDateTime
     */
    static {
        //OBJECT_MAPPER.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
    }

    /**
     * 序列化指定对象为JSON字符串
     *
     * @param object 对象
     * @param <T>    类型参数
     * @return JSON字符串
     */
    public static <T> String toString(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("序列化异常[{}]", e);
            throw new IllegalStateException("序列化异常[" + object + "]", e);
        }
    }

    /**
     * 序列化指定对象为JSON字节数组
     *
     * @param object 对象
     * @param <T>    类型参数
     * @return JSON字节数组
     */
    public static <T> byte[] toBytes(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("序列化异常[{}]", e);
            throw new IllegalStateException("序列化异常[" + object + "]", e);
        }
    }

    /**
     * 由JSON字节数组实例化指定的类
     *
     * @param bytes JSON字节数组
     * @param clazz 指定的类
     * @param <T>   类型参数
     * @return 指定类的实例
     * @throws IOException 反序列化异常
     */
    public static <T> T from(byte[] bytes, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(bytes, clazz);
    }

    /**
     * 由JSON字节数组实例化指定的类
     *
     * @param jsonValue JSON字节数组
     * @param clazz     指定的类
     * @param <T>       类型参数
     * @return 指定类的实例
     * @throws IOException 反序列化异常
     */
    public static <T> T from(String jsonValue, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(jsonValue, clazz);
    }


    /**
     * object转JavaBean
     *
     * @param o     需要被转换的Object
     * @param clazz 需要转换成的JavaBean
     * @param <T>   类型参数
     * @return 指定的JavaBean实例
     * @throws IllegalArgumentException 反序列异常
     */
    public static <T> T obj2Bean(Object o, Class<T> clazz) throws IllegalArgumentException {
        return OBJECT_MAPPER.convertValue(o, clazz);
    }

    /**
     * 转换成JSONObject
     *
     * @param jsonStr
     * @return
     */
    public static JSONObject toJsonObject(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject;
    }

    /**
     * 字符串转json数组
     *
     * @param jsonStr
     * @return
     */
    public static JSONArray toJsonArray(String jsonStr) {
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        return jsonArray;
    }

    /**
     * @param jsonBytes
     * @return
     */
    public static JSONObject toJsonObject(byte[] jsonBytes) {
        final Object obj = JSON.parse(jsonBytes);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        try {
            return (JSONObject) JSON.toJSON(obj);
        } catch (RuntimeException e) {
            throw new JSONException("can not cast to JSONObject.", e);
        }
    }

    /**
     * @param jsonBytes
     * @return
     */
    public static JSONArray toJsonArray(byte[] jsonBytes) {
        final String json = new String(jsonBytes, StandardCharsets.UTF_8);
        return toJsonArray(json);
    }
}
