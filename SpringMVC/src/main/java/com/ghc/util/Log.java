/**
 * Log.java
 *
 *	
 */
package com.ghc.util;

import org.apache.log4j.Logger;

/**
 * 
 */
public class Log {
	private static boolean mEnableLog = true;
	private static final Logger sLog = Logger.getLogger(Log.class);

	private Log() {

	}

	public static void setEnableLog(boolean enableLog) {
		mEnableLog = enableLog;
	}

	public static void debug(String message) {
		debug(message, "");
	}

	public static void debug(String message, Object... args) {
		if (mEnableLog) {
			String format = String.format(message, args);
			sLog.debug(format);
		}
	}
}
