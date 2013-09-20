package net.vrallev.android.base.util;

import android.os.Debug;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inspired from Timber: https://github.com/JakeWharton/timber
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class Cat {

    private static final Cat RELEASE = new Cat(null) {

        @Override
        public void de(String message) {

        }

        @Override
        public void de(String message, Object... args) {

        }

        @Override
        public void de(Throwable t, String message, Object... args) {

        }

        @Override
        public void in(String message) {

        }

        @Override
        public void in(String message, Object... args) {

        }

        @Override
        public void in(Throwable t, String message, Object... args) {

        }

        @Override
        public void wa(String message, Object... args) {

        }

        @Override
        public void wa(Throwable t, String message, Object... args) {

        }

        @Override
        public void er(Throwable t) {

        }

        @Override
        public void er(String message, Object... args) {

        }

        @Override
        public void er(Throwable t, String message, Object... args) {

        }

        @Override
        public void ve(Throwable t, String message, Object... args) {

        }

        @Override
        public void ve(String message, Object... args) {

        }
    };

    private static Cat instance = Debug.isDebuggerConnected() ? new Cat(null) : RELEASE;

    /** Log a debug message with optional format args. */
    public static void d(String message) {
        instance.de(message);
    }

    /** Log a debug message with optional format args. */
    public static void d(String message, Object... args) {
        instance.de(message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(Throwable t, String message, Object... args) {
        instance.de(t, message, args);
    }

    /** Log an info message with optional format args. */
    public static void i(String message) {
        instance.in(message);
    }

    /** Log an info message with optional format args. */
    public static void i(String message, Object... args) {
        instance.in(message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(Throwable t, String message, Object... args) {
        instance.in(t, message, args);
    }

    /** Log a warning message with optional format args. */
    public static void w(String message, Object... args) {
        instance.wa(message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(Throwable t, String message, Object... args) {
        instance.wa(t, message, args);
    }

    /** Log an error message with optional format args. */
    public static void e(Throwable t) {
        instance.er(t);
    }

    /** Log an error message with optional format args. */
    public static void e(String message, Object... args) {
        instance.er(message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(Throwable t, String message, Object... args) {
        instance.er(t, message, args);
    }

    /** Log a verbose message with optional format args. */
    public static void v(String message, Object... args) {
        instance.ve(message, args);
    }

    /** Log a verbose message with optional format args. */
    public static void v(Throwable t, String message, Object... args) {
        instance.ve(t, message, args);
    }

    public static Cat create(String tag) {
        return new Cat(tag);
    }

    public static void setDefaultInstance(Cat cat) {
        instance = cat;
    }

    public static void setDefaultInstance(boolean debug) {
        instance = debug ? new Cat(null) : RELEASE;
    }



    private final Pattern mAnonymousClass = Pattern.compile("\\$\\d+$");
    private String mTag;

    private Cat(String tag) {
        mTag = tag;
    }

    private String getTag() {
        String tag = mTag;
        if (tag != null) {
            mTag = null;
            return tag;
        }

        tag = Thread.currentThread().getStackTrace()[5].getClassName();
        Matcher m = mAnonymousClass.matcher(tag);
        if (m != null && m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }

    public void de(String message) {
        if (message == null) {
            message = "null";
        }
        Log.d(getTag(), message);
    }

    public void de(String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.d(getTag(), String.format(message, args));
    }

    public void de(Throwable t, String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.d(getTag(), String.format(message, args), t);
    }

    public void in(String message) {
        if (message == null) {
            message = "null";
        }
        Log.i(getTag(), message);
    }

    public void in(String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.i(getTag(), String.format(message, args));
    }

    public void in(Throwable t, String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.i(getTag(), String.format(message, args), t);
    }

    public void wa(String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.w(getTag(), String.format(message, args));
    }

    public void wa(Throwable t, String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.w(getTag(), String.format(message, args), t);
    }

    public void er(Throwable t) {
        if (t == null) {
            t = new Exception("null exception logged");
        }
        Log.e(getTag(), t.getMessage(), t);
    }

    public void er(String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.e(getTag(), String.format(message, args));
    }

    public void er(Throwable t, String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.e(getTag(), String.format(message, args), t);
    }

    public void ve(String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.v(getTag(), String.format(message, args));
    }

    public void ve(Throwable t, String message, Object... args) {
        if (message == null) {
            message = "null";
            args = new Object[0];
        }
        Log.v(getTag(), String.format(message, args), t);
    }
}
