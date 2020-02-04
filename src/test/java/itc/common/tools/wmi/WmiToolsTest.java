package itc.common.tools.wmi;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class WmiToolsTest {
    Process process;

    @Test
    public void startApp() {
        try {
            WmiTools.startApp("E:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQScLauncher.exe", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stopApp() {
        try {
            WmiTools.stopApp("QQ.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stopApp1() {
        try {
            process.isAlive();
            process.waitFor();
            process.destroy();
            process.destroyForcibly();
            process.isAlive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void startServer() {
        try {
            WmiTools.startServer("getip-t");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stopServer() {
        try {
            WmiTools.stopServer("getip-t");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}