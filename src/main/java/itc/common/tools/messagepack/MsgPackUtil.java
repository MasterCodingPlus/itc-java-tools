package itc.common.tools.messagepack;

import org.msgpack.MessagePack;
import java.io.IOException;

/**
 * @program: java-tools
 * @description: 原生MessagePack工具类
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-08-22 15:16
 **/
public class MsgPackUtil {

   final private static MessagePack MESSAGE_PACK = new MessagePack();

    /**
     * 转对象为MessagePack的字节流
     * @param clazz             待转对象类
     * @param o                 待转对象
     * @param <T>               范型
     * @return                  字节流
     * @throws IOException      序列化异常
     */
    public static <T> byte[] pack(Class<T> clazz, Object o) throws IOException {
        MESSAGE_PACK.register(clazz);
        return MESSAGE_PACK.write(o);
    }

    /**
     * 转MessagePack的字节流为对象
     * @param clazz             待转对象类
     * @param bytes             待转字节流
     * @param <T>               范型
     * @return                  序列化后对象
     * @throws IOException      序列化异常
     */
    public static <T> T unpack(Class<T> clazz, byte[] bytes) throws IOException {
        MESSAGE_PACK.register(clazz);
        return MESSAGE_PACK.read(bytes, clazz);
    }
}
