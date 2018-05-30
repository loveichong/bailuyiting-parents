package com.bailuyiting.commons.until;
import java.math.BigDecimal;

public class DoubleUtils {
	public static Double toPoint2(Double d) {
		if (d != null&&(!d.isNaN())) {
			BigDecimal bg = new BigDecimal(d);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}

		return null;
	}
}
