package net.vrallev.android.base.util;

import android.util.Log;
import net.vrallev.android.base.App;

/**
 * Helper class to ease logging. 
 * 
 * @author Ralf Wondratschek
 *
 */
@Deprecated
public final class Lo {
	
	private static Lo instance = new Lo("DEFAULT");
	
	public static void setInstance(Lo instance) {
		Lo.instance = instance;
	}
	
	private final boolean mDebuggable;
	private final String mTag;
	
	public Lo(Class<?> tag) {
		this(tag.getSimpleName());
	}
	
	public Lo(String tag) {
		this(tag, App.DEBUG_CONNECTED);
	}
	
	public Lo(String tag, boolean debuggable) {
		mDebuggable = debuggable;
		mTag = tag;
	}
	
	private void println(int priority, String msg) {
		if (mDebuggable)
			Log.println(priority, mTag, msg);
	}
	
	private void println(int priority, String msg, Throwable e) {
		if (mDebuggable)
			Log.println(priority, mTag, msg + '\n' + Log.getStackTraceString(e));
	}
	
	private void println(int priority, String msg1, Object msg2) {
		if (mDebuggable)
			Log.println(priority, mTag, msg1 + msg2);
	}
	
	private void println(int priority, String msg1, Object msg2, Object msg3) {
		if (mDebuggable)
			Log.println(priority, mTag, msg1 + msg2 + msg3);
	}
	
	private void println(int priority, String msg1, Object... msgs) {
		if (mDebuggable) {
			StringBuilder builder = new StringBuilder(msg1);
			for (Object object : msgs) {
				if (object != null) {
					builder.append(object);
				}
			}
			
			Log.println(priority, mTag, builder.toString());
		}
	}
	
	
	
	public static void debug(String msg) {
		instance.d(msg);
	}
	
	public void d(String msg) {
		println(Log.DEBUG, msg);
	}
	
	public void d(String msg, Throwable e) {
		println(Log.DEBUG, msg, e);
	}
	
	public void d(String msg1, Object msg2) {
		println(Log.DEBUG, msg1, msg2);
	}
	
	public void d(String msg1, Object msg2, Object msg3) {
		println(Log.DEBUG, msg1, msg2, msg3);
	}
	
	public void d(String msg1, Object... msgs) {
		println(Log.DEBUG, msg1, msgs);
	}
	
	
	
	public static void error(String msg) {
		instance.e(msg);
	}

	public static void error(Throwable e) {
		instance.e(e);
	}
	
	public void e(String msg) {
		println(Log.ERROR, msg);
	}
	
	public void e(Throwable e) {
		println(Log.ERROR, e.getMessage(), e);
	}
	
	public void e(String msg, Throwable e) {
		println(Log.ERROR, msg, e);
	}
	
	public void e(String msg1, Object msg2) {
		println(Log.ERROR, msg1, msg2);
	}
	
	public void e(String msg1, Object msg2, Object msg3) {
		println(Log.ERROR, msg1, msg2, msg3);
	}
	
	public void e(String msg1, Object... msgs) {
		println(Log.ERROR, msg1, msgs);
	}
	
	
	
	public static void info(String msg) {
		instance.i(msg);
	}
	
	public void i(String msg) {
		println(Log.INFO, msg);
	}
	
	public void i(String msg, Throwable e) {
		println(Log.INFO, msg, e);
	}
	
	public void i(String msg1, Object msg2) {
		println(Log.INFO, msg1, msg2);
	}
	
	public void i(String msg1, Object msg2, Object msg3) {
		println(Log.INFO, msg1, msg2, msg3);
	}
	
	public void i(String msg1, Object... msgs) {
		println(Log.INFO, msg1, msgs);
	}
	
	
	
	public static void verbose(String msg) {
		instance.v(msg);
	}
	
	public void v(String msg) {
		println(Log.VERBOSE, msg);
	}
	
	public void v(String msg, Throwable e) {
		println(Log.VERBOSE, msg, e);
	}
	
	public void v(String msg1, Object msg2) {
		println(Log.VERBOSE, msg1, msg2);
	}
	
	public void v(String msg1, Object msg2, Object msg3) {
		println(Log.VERBOSE, msg1, msg2, msg3);
	}
	
	public void v(String msg1, Object... msgs) {
		println(Log.VERBOSE, msg1, msgs);
	}
	
	
	
	public static void warn(String msg) {
		instance.w(msg);
	}
	
	public void w(String msg) {
		println(Log.WARN, msg);
	}
	
	public void w(String msg, Throwable e) {
		println(Log.WARN, msg, e);
	}
	
	public void w(String msg1, Object msg2) {
		println(Log.WARN, msg1, msg2);
	}
	
	public void w(String msg1, Object msg2, Object msg3) {
		println(Log.WARN, msg1, msg2, msg3);
	}
	
	public void w(String msg1, Object... msgs) {
		println(Log.WARN, msg1, msgs);
	}
}