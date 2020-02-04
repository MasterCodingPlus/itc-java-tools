package itc.common.tools.sigar;

import itc.common.tools.sigar.utils.SystemProperty;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Project: itc-java-tools <br>
 * @Description: <br>
 * @CreateDate: 2019/8/22 <br>
 * @Author: yangyan
 **/
public class SigarUtil {
    protected static Sigar sigar;

    static {
        sigar = new Sigar();
    }

    /**
     * CPU使用情况
     *
     * @return
     * @throws SigarException
     */
    public CpuPerc[] getCpuPercList() throws SigarException {
        return sigar.getCpuPercList();
    }

    public CpuPerc getCpuPerc() throws SigarException {
        return sigar.getCpuPerc();
    }

    /**
     * cpu信息
     *
     * @return
     * @throws SigarException
     */
    public CpuInfo[] getCpuInfoList() throws SigarException {
        return sigar.getCpuInfoList();
    }

    /**
     * 内存使用信息
     *
     * @return
     * @throws SigarException
     */
    public Mem getMem() throws SigarException {
        return sigar.getMem();
    }

    /**
     * 操作系统信息
     *
     * @return
     */
    public OperatingSystem getOs() {
        OperatingSystem os = OperatingSystem.getInstance();
        return os;
    }

    /**
     * 用户信息
     *
     * @return
     * @throws SigarException
     */
    public Who[] getWhoList() throws SigarException {
        return sigar.getWhoList();
    }

    /**
     * 文件系统信息
     *
     * @return
     * @throws SigarException
     */
    public HashMap<String, Object> getFileSystemMap() throws SigarException {
        return sigar.getFileSystemMap();
    }

    /**
     * 文件系统使用情况
     *
     * @param dirName 文件系统路径
     * @return
     */
    public FileSystemUsage getFileSystemUsage(String dirName) throws SigarException {
        return sigar.getFileSystemUsage(dirName);
    }

    /**
     * 获取指定盘符的磁盘使用信息
     *
     * @param devName 盘符
     * @return
     * @throws SigarException
     */
    public FileInfo getFileSystemInfo(String devName) throws SigarException {
        return sigar.getFileInfo(devName);
    }

    /**
     * 网络接口名称
     *
     * @return
     * @throws SigarException
     */
    public String[] getNetInterfaceList() throws SigarException {
        return sigar.getNetInterfaceList();
    }

    /**
     * 获取指定接口的网络配置
     *
     * @param name 网络接口名称
     * @return
     * @throws SigarException
     */
    public NetInterfaceConfig getNetInterfaceConfig(String name) throws SigarException {
        return sigar.getNetInterfaceConfig(name);
    }

    public NetInterfaceConfig getNetInterfaceConfig() throws SigarException {
        return sigar.getNetInterfaceConfig();
    }

    /**
     * 获取指定网络接口的状态信息
     *
     * @param name
     * @return
     * @throws SigarException
     */
    public NetInterfaceStat getNetInterfaceState(String name) throws SigarException {
        return sigar.getNetInterfaceStat(name);
    }

    /**
     * 网络路由信息
     *
     * @return
     * @throws SigarException
     */
    public NetRoute[] getNetRouteList() throws SigarException {
        return sigar.getNetRouteList();
    }

    /**
     * 系统信息
     *
     * @return
     * @throws UnknownHostException
     */
    public Map<String, String> getSystemInfo() throws UnknownHostException {
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        Map<String, String> map = System.getenv();
        map.put(SystemProperty.IP, addr.getHostAddress());
        map.put(SystemProperty.HOST_NAME, addr.getHostName());
        return map;
    }

    /**
     * jvm信息
     *
     * @return
     */
    public Properties getJvmInfo() {
        Properties props = System.getProperties();
        return props;
    }
}
