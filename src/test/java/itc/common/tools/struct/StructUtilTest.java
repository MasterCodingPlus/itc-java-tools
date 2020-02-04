package itc.common.tools.struct;

import itc.common.tools.json.JsonUtil;
import itc.common.tools.messagepack.JacksonMsgPackUtil;
import org.junit.Test;
import struct.StructException;

import java.io.IOException;

public class StructUtilTest {

    @Test
    public void unpack() {
        User user = new User();
        user.setName(456);
        user.setAge(17);

        try {
            byte[] pack = StructUtil.pack(user);
            byte[] bytes = JsonUtil.toBytes(user);
            byte[] bytes2 = JacksonMsgPackUtil.toBytes(user);

            User unpack = StructUtil.unpack(User.class, pack);
            User user1 = JacksonMsgPackUtil.from(bytes2, User.class);
            System.out.println(unpack);
            System.out.println(user1);
        } catch (StructException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void unpackBig() {
    }

    @Test
    public void pack() {
        User user = new User();
        user.setName(789);
        user.setAge(17);
        try {
            byte[] pack = StructUtil.pack(user);
            byte[] bytes = JsonUtil.toBytes(user);
            byte[] bytes2 = JacksonMsgPackUtil.toBytes(user);
            System.out.println(new String(pack));
        } catch (StructException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void packBig() {
    }
}