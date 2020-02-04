package itc.common.tools.object;

import java.io.*;

/**
 * @description: 对象工具类
 * @vesion: 1.0.0
 * @author: Chen QiuXu
 * @date: 2019/4/4 9:48
 */
public class ObjectUtil {

    /**
     * 深拷贝
     *
     * @param oldValue  被拷贝对象
     * @return          拷贝后对象
     */
    public static Object deepCopy(Object oldValue) {
        Serializable newValue = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(oldValue);
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ois = new ObjectInputStream(bin);
            newValue = (Serializable) ois.readObject();
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }
        return newValue;
    }
}
