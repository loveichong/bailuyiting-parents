package com.bailuyiting.commons.until;
import java.math.BigDecimal;

public class MachinePerformanceUtils {

	/**
	 * 获取内存使用率(百分比)
	 * 
	 * @return
	 */
	public static String getUsedMemoryRate() {
		Runtime runtime = Runtime.getRuntime();
		BigDecimal totalMemory = new BigDecimal(runtime.totalMemory());
		BigDecimal useMemory = new BigDecimal(runtime.totalMemory() - runtime.freeMemory());
		useMemory = useMemory.multiply(new BigDecimal(100));
		BigDecimal percent = useMemory.divide(totalMemory, 1, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(percent.intValue());
	}

	public static String getUsedMemory() {
		BigDecimal useMemory = new BigDecimal(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		return String.valueOf(useMemory.divide(new BigDecimal(1048576), 1, BigDecimal.ROUND_HALF_UP));
	}

	public static String getTotalMemory() {
		BigDecimal totalMemory = new BigDecimal(Runtime.getRuntime().totalMemory());
		return String.valueOf(totalMemory.divide(new BigDecimal(1048576), 1, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 获取空闲内存(M)
	 * 
	 * @return
	 */
	public static String getFreeMemory() {
		BigDecimal freeMemory = new BigDecimal(Runtime.getRuntime().freeMemory());
		freeMemory = freeMemory.divide(new BigDecimal(1048576), 1, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(freeMemory.intValue());
	}

	public static void main(String[] args) {
		System.out.println(MachinePerformanceUtils.getUsedMemoryRate());
		System.out.println(MachinePerformanceUtils.getFreeMemory());
		System.out.println(MachinePerformanceUtils.getTotalMemory());
	}

}
