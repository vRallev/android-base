package net.vrallev.android.base.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Surface;
import android.view.WindowManager;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class DisplayHelper {

    private final Context mContext;
    private final WindowManager mWindowManager;

    public DisplayHelper(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public int getDisplayDegrees() {
        int rotation = mWindowManager.getDefaultDisplay().getRotation();
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

    public Point getScreenSize() {
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        return point;
    }

    public boolean isPortrait() {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
