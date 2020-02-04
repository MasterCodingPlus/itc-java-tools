package itc.common.tools.wmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.wmi
 * @ClassName: WmiTools
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/16 16:31
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/16 16:31
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class WmiTools {

    /**
     * 开启进程
     *
     * @param path
     * @param paras
     */
    public static void startApp(String path, String[] paras) throws IOException {
        String cmd = "cmd /c" + path;
        Process exec = Runtime.getRuntime().exec(path, paras);
    }

    /**
     * 关闭进程
     *
     * @param appName
     */
    public static void stopApp(String appName) throws IOException {

//        String command = "kill /f /im " + appName;
//        Runtime.getRuntime().exec(command);

        String command = "taskkill /f /im " + appName;
        Runtime.getRuntime().exec(command);

    }

    /**
     * 开始服务
     *
     * @param serverName
     */
    public static void startServer(String serverName) throws IOException {
        String command = "cmd.exe /c net start \"" + serverName + "\"";
        Process exec = Runtime.getRuntime().exec(command.toUpperCase());
    }

    /**
     * 停止服务
     *
     * @param serverName
     */
    public static void stopServer(String serverName) throws IOException {
        String command = "cmd.exe /c net stop \"" + serverName + "\"";
        System.out.println(command.toUpperCase());
        Process exec = Runtime.getRuntime().exec(command.toUpperCase());
    }
}
