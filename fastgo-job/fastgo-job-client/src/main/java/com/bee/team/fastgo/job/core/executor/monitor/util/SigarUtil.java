package com.bee.team.fastgo.job.core.executor.monitor.util;


import com.bee.team.fastgo.job.core.executor.monitor.entity.*;
import com.bee.team.fastgo.job.core.util.IpUtil;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.FormatUtil;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @desc: hyperic获取linux系统信息工具类
 * @auth: hjs
 * @date: 2020/7/21 0021
 */
public class SigarUtil {

    private static Logger logger = LoggerFactory.getLogger(SigarUtil.class);

    private static Sigar sigar = new Sigar();

    private static OperatingSystem OS = OperatingSystem.getInstance();

    private static Long ride = 1000L;
    

    /**
     * 获取操作系统信息
     *
     * @return
     * @throws SigarException
     */
    public static SystemInfo os() throws SigarException {
        SystemInfo systemInfo = new SystemInfo();
        // 系统信息
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        systemInfo.setServerIp(IpUtil.getIp());
        systemInfo.setCpuCoreNum(infos.length);
        if (infos.length > 0) {
            systemInfo.setCpuModel(infos[0].getModel());
        }
        systemInfo.setVersion(OS.getVersion());
        systemInfo.setSystemName(OS.getVendor() + " " + OS.getArch());
        systemInfo.setState(1);
        // 内存信息
        Mem mem = sigar.getMem();
        long total = mem.getTotal() / ride / ride / ride;
        systemInfo.setTotalMem(total + "G");
        return systemInfo;
    }


    /**
     * 获取内存使用信息
     *
     * @return
     * @throws SigarException
     */
    public static MemoryState memory() throws SigarException {
        MemoryState memState = new MemoryState();
        Mem mem = sigar.getMem();
        long total = mem.getTotal() / ride / ride;
        long used = mem.getUsed() / ride / ride;
        long free = mem.getFree() / ride / ride;
        double usePer = (double) used / (double) total;
        memState.setMemUsePer(usePer + "%");
        memState.setMemFree(new Double(free));
        memState.setMemUsed(new Double(used));
        memState.setMemTotal(new Double(total));
        memState.setServerIp(IpUtil.getIp());
        return memState;
    }


    /**
     * 获取cpu使用率，等待率，空闲率
     *
     * @return
     * @throws SigarException
     */
    public static CpuState cpu() throws SigarException {
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc[] cpuList = sigar.getCpuPercList();
        double sys = 0;
        double wait = 0;
        double idle = 0;
        for (int i = 0; i < infos.length; i++) {
            sys += Double.valueOf(delChar(CpuPerc.format(cpuList[i].getCombined())));
            wait += Double.valueOf(delChar(CpuPerc.format(cpuList[i].getWait())));
            idle += Double.valueOf(delChar(CpuPerc.format(cpuList[i].getIdle())));
        }
        CpuState cpuState = new CpuState();
        cpuState.setCpuUse(FormatUtil.toDouble(sys / infos.length, 1));
        cpuState.setCpuIdle(FormatUtil.toDouble(idle / infos.length, 1));
        cpuState.setCpuIoWait(FormatUtil.toDouble(wait / infos.length, 1));
        cpuState.setServerIp(IpUtil.getIp());
        return cpuState;
    }


    /**
     * 获取磁盘使用信息
     *
     * @throws Exception
     */
    public static List<DeskState> file() throws Exception {
        List<DeskState> list = new ArrayList<DeskState>();
        int usedSum = 0;
        int availSum = 0;
        int sizeSum = 0;
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            try {
                DeskState deskState = new DeskState();
                FileSystem fs = fslist[i];
                deskState.setFileSystem(fs.getDevName());
                deskState.setServerIp(IpUtil.getIp());
                FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                usedSum += (usage.getUsed() / 1024 / 1024);
                deskState.setUsed((usage.getUsed() / 1024 / 1024) + "G");
                availSum += (usage.getAvail() / 1024 / 1024);
                deskState.setAvail((usage.getAvail() / 1024 / 1024) + "G");
                sizeSum += (usage.getTotal() / 1024 / 1024);
                deskState.setSize((usage.getTotal() / 1024 / 1024) + "G");
                double usePercent = FormatUtil.toDouble(usage.getUsePercent() * 100D, 2);
                deskState.setUsePer(usePercent + "%");
                deskState.setCreateTime(new Date());
                list.add(deskState);
            } catch (SigarException e) {
                logger.error(e.toString());
            }
            DeskState deskStateSum = new DeskState();
            deskStateSum.setServerIp(IpUtil.getIp());
            deskStateSum.setAvail(availSum + "G");
            deskStateSum.setFileSystem("总计");
            deskStateSum.setSize(sizeSum + "G");
            double sumUsePercent = FormatUtil.toDouble(((double) usedSum / (double) sizeSum) * 100D, 2);
            deskStateSum.setUsePer(sumUsePercent + "%");
            deskStateSum.setUsed(usedSum + "G");
            deskStateSum.setCreateTime(getDateBefore(new Date(), 1));
            list.add(deskStateSum);
        }
        return list;
    }

    /**
     * 获取系统负载
     *
     * @return
     */
    public static SysLoadState getLoadState() {
        SysLoadState sysLoadState = new SysLoadState();
        try {
            double[] load = sigar.getLoadAverage();
            sysLoadState.setOneLoad(load[0]);
            sysLoadState.setServerIp(IpUtil.getIp());
            sysLoadState.setFiveLoad(load[1]);
            sysLoadState.setFifteenLoad(load[2]);
            return sysLoadState;
        } catch (SigarException e) {
            logger.error(DateUtils.getCurrentTime() + "获取系统负载异常", e);
        }
        return null;
    }

    /**
     * 获取进程信息
     *
     * @return
     */
    public static AppState getLoadPid(String pid) {
        try {
            AppState appState = new AppState();
            ProcCpu ProcCpu = sigar.getProcCpu(pid);
            ProcMem ProcMem = sigar.getProcMem(pid);
            appState.setCpuPer(FormatUtil.toDouble(ProcCpu.getPercent(), 2));
            appState.setMemPer(FormatUtil.toDouble((ProcMem.getResident() / 1024 / 1024), 2));
            return appState;
        } catch (SigarException e) {
            logger.error(DateUtils.getCurrentTime() + "获取进程信息错误", e);
        }
        return null;
    }

    /**
     * 网络设备的吞吐率
     *
     * @return
     * @throws Exception
     */
    public static NetIoState net() throws Exception {
        String ifNames[] = sigar.getNetInterfaceList();
        int rxBytesSum = 0;
        int txBytesSum = 0;
        int rxPackets = 0;
        int txPackets = 0;
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                logger.error("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            rxBytesSum += (ifstat.getRxBytes() / 1024);
            txBytesSum += (ifstat.getTxBytes() / 1024);
            rxPackets += ifstat.getRxPackets();
            txPackets += ifstat.getTxPackets();
        }
        NetIoState netIoState = new NetIoState();
        netIoState.setRxbyt(rxBytesSum + "");
        netIoState.setTxbyt(txBytesSum + "");
        netIoState.setRxpck(rxPackets + "");
        netIoState.setTxpck(txPackets + "");
        netIoState.setServerIp(IpUtil.getIp());
        return netIoState;
    }

    public static String delChar(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        str = str.replace("%", "");
        return str;
    }

    public static Date getDateBefore(Date datetimes, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(datetimes);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }


}