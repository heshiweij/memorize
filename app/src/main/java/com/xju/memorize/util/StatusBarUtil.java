package com.xju.memorize.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.xju.memorize.constant.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class StatusBarUtil {
    //修改状态栏为全透明
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //修改状态栏颜色
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
    }

    /**
     * 需要MIUIV6以上
     *
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    //需要MIUIV6以上
    private static void MIUISetStatusBarLightMode(Object object, boolean dark) {
        Window window = null;
        if (object instanceof Activity) {
            window = ((Activity) object).getWindow();
        } else if (object instanceof Window) {
            window = (Window) object;
        }

        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtil.isMiUIV7OrAbove()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }

            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }


    //true 字体颜色为黑色，false为白色
    //是否在全屏模式下
    public static void setLightStatusBar(final Activity activity, final boolean dark, boolean isFullMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Constant.DEVICE_FIRM == -1) {
                Constant.DEVICE_FIRM = RomUtil.getLightStatusBarAvailableRomType();
            }
            switch (Constant.DEVICE_FIRM) {
                case RomUtil.AvailableRomType.MIUI:
                    MIUISetStatusBarLightMode(activity, dark);
                    break;

                case RomUtil.AvailableRomType.FLYME:
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        setAndroidNativeLightStatusBar(activity, dark, isFullMode);
                    } else {
                        setFlymeLightStatusBar(activity, dark);
                    }
                    break;

                case RomUtil.AvailableRomType.ANDROID_NATIVE:
                    setAndroidNativeLightStatusBar(activity, dark, isFullMode);
                    break;

                case RomUtil.AvailableRomType.NA:
                    // N/A do nothing
                    break;
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setLightStatusBar(final Window window, final boolean dark, boolean isFullMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            switch (RomUtil.getLightStatusBarAvailableRomType()) {
                case RomUtil.AvailableRomType.MIUI:
                    MIUISetStatusBarLightMode(window, dark);
                    break;

                case RomUtil.AvailableRomType.FLYME:
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        setAndroidNativeLightStatusBar(window, dark, isFullMode);
                    } else {
                        setFlymeLightStatusBar(window, dark);
                    }
                    break;

                case RomUtil.AvailableRomType.ANDROID_NATIVE:
                    setAndroidNativeLightStatusBar(window, dark, isFullMode);
                    break;

                case RomUtil.AvailableRomType.NA:
                    // N/A do nothing
                    break;
            }
        }


    }

    private static boolean setFlymeLightStatusBar(Object obj, boolean dark) {
        boolean result = false;
        Window window = null;
        if (obj instanceof Activity) {
            window = ((Activity) obj).getWindow();
        } else if (obj instanceof Window) {
            window = ((Window) obj);
        }

        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void setAndroidNativeLightStatusBar(Object obj, boolean dark, boolean isFullMode) {
        View decor = null;
        if (obj instanceof Activity) {
            decor = ((Activity) obj).getWindow().getDecorView();
        } else if (obj instanceof Window) {
            decor = ((Window) obj).getDecorView();
        }

        if (decor == null) {
            return;
        }

        if (dark) {
            if (isFullMode) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            // We want to change tint color to white again.
            // You can also record the flags in advance so that you can turn UI back completely if
            // you have set other flags before, such as translucent or full screen.
            if (isFullMode) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    //获得通知栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    public static void clearActionBarShadow(Activity activity, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //占位状态栏
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                statusBarView.setBackground(drawable);
            } else {
                statusBarView.setBackgroundDrawable(drawable);
            }
        }
    }

    /**
     * 设置状态栏渐变颜色
     *
     * @param activity 目标activity
     * @param view     目标View
     */
    //设置状态栏渐变颜色
    public static void setGradientColor(@NonNull Activity activity, View view) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(android.R.id.custom);
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView);
        }
        setRootView(activity, false);
        setTransparentForWindow(activity);
        setPaddingTop(activity, view);
    }

    /**
     * 设置根布局参数
     *
     * @param activity         目标activity
     * @param fitSystemWindows 是否预留toolbar的高度
     */
    // 设置根布局参数
    private static void setRootView(Activity activity, boolean fitSystemWindows) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(fitSystemWindows);
                ((ViewGroup) childView).setClipToPadding(fitSystemWindows);
            }
        }
    }

    /**
     * 设置透明状态栏
     *
     * @param activity 目标界面
     */
    //设置透明状态栏
    public static void setTransparentForWindow(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     *
     * @param context 目标Context
     * @param view    需要增高的View
     */
    //增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
    public static void setPaddingTop(Context context, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0 && view.getPaddingTop() == 0) {
                lp.height += getStatusBarHeight(context);
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }

}
