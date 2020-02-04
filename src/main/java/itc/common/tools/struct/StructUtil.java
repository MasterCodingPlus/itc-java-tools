package itc.common.tools.struct;

import struct.JavaStruct;
import struct.StructException;

import java.nio.ByteOrder;

/**
 * @program: java-tools
 * @description: Struct帮助类
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-08-22 11:55
 **/
public class StructUtil {

    /**
     * 小端解包
     * @param clazz                         解包后的实体类
     * @param bytes                         待解包的字节流
     * @param <T>                           范型
     * @return                              解包后对象
     * @throws StructException              解包异常
     * @throws IllegalAccessException       构造对象异常
     * @throws InstantiationException       构造对象异常
     */
    public static <T> T unpack(Class<T> clazz, byte[] bytes) throws StructException, IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        JavaStruct.unpack(t, bytes);
        return t;
    }

    /**
     * 大端解包
     * @param clazz                         解包后的实体类
     * @param bytes                         待解包的字节流
     * @param <T>                           范型
     * @return                              解包后对象
     * @throws StructException              解包异常
     * @throws IllegalAccessException       构造对象异常
     * @throws InstantiationException       构造对象异常
     */
    public static <T> T unpackBig(Class<T> clazz, byte[] bytes) throws StructException, IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        JavaStruct.unpack(t, bytes, ByteOrder.BIG_ENDIAN);
        return t;
    }

    /**
     * 小端封包
     * @param o                         待封装对象
     * @return                          封装后字节流
     * @throws StructException          封包异常
     */
    public static byte[] pack(Object o) throws StructException {
        return JavaStruct.pack(o);
    }

    /**
     * 大端封包
     * @param o                         待封装对象
     * @return                          封装后字节流
     * @throws StructException          封包异常
     */
    public static byte[] packBig(Object o) throws StructException {
        return JavaStruct.pack(o, ByteOrder.BIG_ENDIAN);
    }
}
