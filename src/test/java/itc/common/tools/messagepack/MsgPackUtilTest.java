package itc.common.tools.messagepack;

import itc.common.tools.struct.User;
import org.junit.Test;

import java.io.IOException;

public class MsgPackUtilTest {

    @Test
    public void pack() throws IOException {
        User user = new User();
        user.setAge(12);
        user.setName(11);
        byte[] hellos = MsgPackUtil.pack(User.class, user);
        System.out.println(hellos);
    }

    @Test
    public void unpack() throws IOException {
        User user = new User();
        user.setAge(12);
        user.setName(11);
        byte[] hellos = MsgPackUtil.pack(User.class, user);
        User user1 = MsgPackUtil.unpack(User.class, hellos);
        System.out.println(user1);
    }
}