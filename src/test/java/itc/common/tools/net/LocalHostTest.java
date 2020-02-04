package itc.common.tools.net;

import org.junit.Test;

import java.net.SocketException;

public class LocalHostTest {

    @Test
    public void getLocalIp() {
        System.out.println(LocalHost.getFirstLocalIp());
    }

    @Test
    public void getLocalIPList() {
        System.out.println(LocalHost.getLocalIPList());
    }

    @Test
    public void getFirstIp() throws SocketException {
        System.out.println(LocalHost.getLocalIP());
    }
}