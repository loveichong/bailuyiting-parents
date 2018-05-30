package com.bailuyiting.commons.until;
/**
 * 服务器类型探测器
 * 
 * @author yezq
 * 
 */
public class ServerDetector {

	public static final String TOMCAT_CLASS = "/org/apache/catalina/startup/Bootstrap.class";

	public static final String WEBLOGIC_CLASS = "/weblogic/Server.class";

	public static final String WEBSPHERE_CLASS = "/com/ibm/websphere/product/VersionInfo.class";

	private static boolean _detect(String className) {
		try {
			ClassLoader.getSystemClassLoader().loadClass(className);

			return true;
		} catch (ClassNotFoundException cnfe) {

			Class c = ServerDetector.class;

			if (c.getResource(className) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 是否Tomcat服务器
	 * @return
	 */
	public static boolean isTomcat() {
		return _detect(TOMCAT_CLASS);
	}

	/**
	 * 是否WebLogic服务器
	 * @return
	 */
	public static boolean isWebLogic() {
		return _detect(WEBLOGIC_CLASS);
	}

	/**
	 * 是否WebSphere服务器
	 * @return
	 */
	public static boolean isWebSphere() {

		return _detect(WEBSPHERE_CLASS);
	}

}
