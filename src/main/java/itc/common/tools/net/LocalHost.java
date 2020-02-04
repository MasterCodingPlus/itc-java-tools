package itc.common.tools.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.net
 * @ClassName: LocalHost
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/16 3:48
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/16 3:48
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class LocalHost {
    /**
     * 获取网卡的第一个ip
     *
     * @return
     */
    public static String getFirstLocalIp() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            String localname = ia.getHostName();
            String localip = ia.getHostAddress();
            System.out.println("本机名称是：" + localname);
            System.out.println("本机的ip是 ：" + localip);
            return localip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取除回环ip的所有ip
     *
     * @return
     */
    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual()
                        || !networkInterface.isUp()) {
                    continue;
                }
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    /**
     * 获取本地真正的IP地址，即获得有线或者无线WiFi地址。
     * 过滤虚拟机、蓝牙等地址
     */
    public static String getLocalIP() {
        String hostAddress = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual()
                        || !networkInterface.isUp()) {
                    continue;
                }
                if (!networkInterface.getDisplayName().contains("Intel")
                        && !networkInterface.getDisplayName().contains("Realtek")) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        hostAddress = inetAddress.getHostAddress();
                        break;
                    }
                }
                if (hostAddress != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }
}
