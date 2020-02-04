package itc.common.tools.jvm;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.jvm
 * @ClassName: RuntimeUtils
 * @Description: (用一句话描述该文件做什么)
 * @Author: Mastercoding
 * @CreateDate: 2019/9/12 10:03
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/9/12 10:03
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class RuntimeUtils {
    public static Boolean isUnder25Percent() {
        // 虚拟机级内存情况查询
        double vmFree = 0;
        double vmUse = 0;
        double vmTotal = 0;
        double vmMax = 0;
        double byteToMb = 1024 * 1024;
        Runtime rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        return vmTotal / vmFree < 0.25;
    }
}
