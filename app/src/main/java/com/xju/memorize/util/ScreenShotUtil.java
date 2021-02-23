package com.xju.memorize.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.RelativeLayout;

public class ScreenShotUtil {

    public static Bitmap createBitmap(RelativeLayout v) {
        int width = v.getWidth();
        int height = v.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
