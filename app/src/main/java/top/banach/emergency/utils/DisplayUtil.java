package top.banach.emergency.utils;

import android.content.Context;

/** 
 * dp、sp 转换为 px 的工具类 
 *  
 * @author 
 * 
 */  
public class DisplayUtil {  
    /** 
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *  
     * @param pxValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 将dip或dp值转换为px值，保证尺寸大小不变 
     *  
     * @param dipValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
  
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
    
    
//    /**
//     * 状态栏初始色
//     * @param activity
//     */
//    public static void initSystemBar(Activity activity) {
//
//    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//    		setTranslucentStatus(activity, true);
//
//    	}
//
//    		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//
//    		tintManager.setStatusBarTintEnabled(true);
//
//    		// 使用颜色资源
//
//    		tintManager.setStatusBarTintResource(R.color.status_color);
//
//    }
}  
