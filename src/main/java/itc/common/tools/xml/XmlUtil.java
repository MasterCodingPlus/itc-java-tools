package itc.common.tools.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.xml
 * @ClassName: XmlUtil
 * @Description: XML工具类
 * @Author: MasterCoding
 * @CreateDate: 2018/09/20 15:45
 */
@Slf4j
public final class XmlUtil {

    /**
     * XML映射器
     */
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    /**
     * 防止被实例化
     */
    private XmlUtil() {
    }

    /**
     * 由XML字节数组实例化指定的类
     *
     * @param path  XML文件路径
     * @param clazz 指定的类
     * @param <T>   类型参数
     * @return 指定类的实例
     * @throws IOException 反序列化异常
     */
    public static <T> T from(Path path, Class<T> clazz) throws IOException {
        return XML_MAPPER.readValue(path.toFile(), clazz);
    }

    /**
     * 将对象实例序列化到指定的路径
     *
     * @param path   指定的路径
     * @param object 对象实例
     * @throws IOException 序列化异常
     */
    public static void to(Path path, Object object) throws IOException {
        XML_MAPPER.writeValue(path.toFile(), object);
    }

    /**
     * 从字符串转到实体类
     *
     * @param content   字符串
     * @param valueType 对象实例
     * @throws IOException 序列化异常
     */
    public static <T> T from(String content, Class<T> valueType) throws IOException {
        return XML_MAPPER.readValue(content, valueType);
    }

    /**
     * 从byte[]转到实体类
     *
     * @param bytes     字符串
     * @param valueType 对象实例
     * @throws IOException 序列化异常
     */
    public static <T> T from(byte[] bytes, Class<T> valueType) throws IOException {
        return XML_MAPPER.readValue(bytes, valueType);
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
            return XML_MAPPER.writeValueAsString(object);
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
            return XML_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("序列化异常[{}]", e);
            throw new IllegalStateException("序列化异常[" + object + "]", e);
        }
    }
}
