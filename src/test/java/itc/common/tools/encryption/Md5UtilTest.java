package itc.common.tools.encryption;

import org.junit.Test;

public class Md5UtilTest {

    @Test
    public void getMd5Str() {
    }

    @Test
    public void getMd5() {
    }

    @Test
    public void testGetMd5() {
        byte[] md5 = Md5Util.getMd5(new byte[10000]);
        System.out.println(md5.length);
    }
}