package net.jeebiz.admin.extras.monitor.utils;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import hitool.core.format.ByteUnitFormat;
import hitool.core.lang3.network.InetAddressUtils;
import net.jeebiz.admin.extras.monitor.web.vo.CpuInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.JvmInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.MemInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.ServerInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.SysDiskInfoVo;
import net.jeebiz.admin.extras.monitor.web.vo.SysInfoVo;
import net.jeebiz.boot.api.utils.Arith;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

public class OshiUtils {

    public static final int OSHI_WAIT_SECOND = 1000;
    public static final SystemInfo si = new SystemInfo();

    public static ServerInfoVo getServerInfo(ByteUnitFormat unit) {

        HardwareAbstractionLayer hal = si.getHardware();

        return ServerInfoVo
               .builder()
               .cpu(getCpuInfo(hal.getProcessor()))
               .mem(getMemInfo(hal.getMemory(), unit))
               .jvm(getJvmInfo())
               .sys(getSysInfo())
               .disks(getSysDisks(si.getOperatingSystem())).build();
    }

    /**
     * 获取CPU信息
     */
    public static CpuInfoVo getCpuInfo(CentralProcessor processor) {

        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        CpuInfoVo cpu = new CpuInfoVo();
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(Arith.round(Arith.mul(totalCpu, 100), 2));
        cpu.setSys(Arith.round(Arith.mul(cSys / totalCpu, 100), 2));
        cpu.setUser(Arith.round(Arith.mul(user / totalCpu, 100), 2));
        cpu.setWait(Arith.round(Arith.mul(iowait / totalCpu, 100), 2));
        cpu.setFree(Arith.round(Arith.mul(idle / totalCpu, 100), 2));

        return cpu;
    }

    /**
     * 获取内存信息
     */
    public static MemInfoVo getMemInfo(GlobalMemory memory, ByteUnitFormat unit) {

        MemInfoVo mem = new MemInfoVo();

        mem.setTotal(ByteUnitFormat.B.toDouble(unit, memory.getTotal()));
        mem.setUsed(ByteUnitFormat.B.toDouble(unit,memory.getTotal() - memory.getAvailable()));
        mem.setFree(ByteUnitFormat.B.toDouble(unit, memory.getAvailable()));  // Arith.div(free, (1024 * 1024 * 1024), 2)
        mem.setUsage(Arith.mul(Arith.div(memory.getTotal() - memory.getAvailable(), memory.getTotal(), 4), 100));

        return mem;
    }

    /**
     * 获取服务器信息
     */
    public static SysInfoVo getSysInfo()
    {
        SysInfoVo sys = new SysInfoVo();
        Properties props = System.getProperties();
        sys.setComputerName(InetAddressUtils.getHostName());
        sys.setComputerIp(InetAddressUtils.getHostAddress());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }


    /**
     * 获取Java虚拟机
     */
    public static JvmInfoVo getJvmInfo()  {
        return getJvmInfo(ByteUnitFormat.M);
    }
    /**
     * 获取Java虚拟机
     */
    public static JvmInfoVo getJvmInfo(ByteUnitFormat unit)  {

        Properties props = System.getProperties();
        JvmInfoVo jvm = new JvmInfoVo();
        
        long total = Runtime.getRuntime().totalMemory();
        long max = Runtime.getRuntime().maxMemory();
        long free = Runtime.getRuntime().freeMemory();

        jvm.setTotal(ByteUnitFormat.B.toDouble(unit, total));
        jvm.setMax(ByteUnitFormat.B.toDouble(unit, max));
        jvm.setFree(ByteUnitFormat.B.toDouble(unit, free));
        jvm.setUsage(Arith.mul(Arith.div(total - free, total, 4), 100));
        jvm.setName(ManagementFactory.getRuntimeMXBean().getVmName());
        jvm.setHome(props.getProperty("java.home"));
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setStartTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate()));
        jvm.setRunTime(DateUtils.getDatePoor(new Date(), DateUtils.getServerStartDate()));

        return jvm;
    }

    /**
     * 获取磁盘信息
     */
    public static List<SysDiskInfoVo> getSysDisks(OperatingSystem os)
    {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();

        List<SysDiskInfoVo> disks = new LinkedList<>();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;

            SysDiskInfoVo sysFile = new SysDiskInfoVo();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            disks.add(sysFile);
        }
        return disks;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size)
    {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb)
        {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb)
        {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb)
        {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else
        {
            return String.format("%d B", size);
        }
    }

}
