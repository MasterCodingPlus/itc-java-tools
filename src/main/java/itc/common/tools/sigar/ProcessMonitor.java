package itc.common.tools.sigar;

import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Project: itc-java-tools <br>
 * @Description: 监控进程CPU占用率与内存占用<br>
 * @CreateDate: 2019/8/21 <br>
 * @Author: yangyan
 **/
public class ProcessMonitor {
    public HashMap<String, HashMap<String, Object>> monitor(String processName) throws SigarException {
        Sigar sigar = new Sigar();
        long[] pids = sigar.getProcList();
        HashMap<String, HashMap<String, Object>> resultMap = new HashMap<>();
        List<String> applicationPids = new ArrayList();
        for (int i = 0; i < pids.length; i++) {
            ProcState procState;
            try {
                procState = sigar.getProcState(pids[i]);
            } catch (SigarException e) {
                continue;
            }
            if (!processName.equals(procState.getName())) {
                continue;
            }
            HashMap<String, Object> procMap = new HashMap<>();
            procMap.put("PID", pids[i]);
            procMap.put("PROC_MEM", sigar.getProcMem(pids[i]).getResident() / 1024L / 1024 + "M");
            //CPU占用率
            sigar.getProcCpu(pids[i]).getPercent();
            resultMap.put(pids[i] + "", procMap);
            applicationPids.add(pids[i] + "");
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String pid : applicationPids) {
            String s = new DecimalFormat("#.##%").format(sigar.getProcCpu(pid).getPercent());  // "0.0000"
//            ProcCpu procCpu = sigar.getProcCpu(pids[i]);  double percent = procCpu.getTotal()*100/((procCpu.getLastTime()-procCpu.getStartTime())*1.0);
            resultMap.get(pid).put("PROC_CPU_PERCENT", s);
        }
        return resultMap;
    }
}
