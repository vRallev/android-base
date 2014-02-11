package net.vrallev.android.base.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Surface;

/**
 * 
 * @author Ralf Wondratschek
 *
 */
@SuppressWarnings("UnusedDeclaration")
@Deprecated
public final class DisplayUtils {

    private static Context context;
	
	private DisplayUtils() {

	}

    @Deprecated
    public static void init(Context context) {
        //noinspection deprecation
        DisplayUtils.context = context;
    }

    @Deprecated
	public static int getDisplayDegrees() {
        //noinspection deprecation
		int rotation = AndroidServices.getWindowManager().getDefaultDisplay().getRotation();
		switch (rotation) {
			case Surface.ROTATION_0:
				return 0;
			case Surface.ROTATION_90:
				return 90;
			case Surface.ROTATION_180:
				return 180;
			case Surface.ROTATION_270:
				return 270;
		}
		
		return -1;
	}

    @Deprecated
	public static Point getScreenSize() {
		Point point = new Point();
        //noinspection deprecation
        AndroidServices.getWindowManager().getDefaultDisplay().getSize(point);
		return point;
	}

    @Deprecated
	public static boolean isPortrait() {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}
}
