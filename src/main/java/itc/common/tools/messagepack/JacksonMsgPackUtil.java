package itc.common.tools.messagepack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

/**
 * @program: java-tools
 * @description: MessagePack帮助类
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-08-22 11:21
 **/
@Slf4j
public class JacksonMsgPackUtil {

    /**
     * 对象映射器
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper(new MessagePackFactory());


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
            log.error("序列化异常[{}]", e.toString());
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
     * byte转JsonNode
     * @param bytes             字节流
     * @return                  JsonNode
     * @throws IOException      反序列化异常
     */
    public static JsonNode byte2JsonNode(byte[] bytes) throws IOException {
        return OBJECT_MAPPER.readTree(bytes);
    }

}
