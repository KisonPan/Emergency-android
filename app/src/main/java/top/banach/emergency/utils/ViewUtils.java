package top.banach.emergency.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ViewUtils {

	/**
	 * 设置中划线
	 * 
	 * @param tv
	 * @return
	 */
	public static TextView setMiddleLine(TextView tv) {
		tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

		return tv;

	}

	/**
	 * 
	 * @param c
	 *            界面
	 * @param v
	 *            控件
	 * @param b
	 *            宽的倍数
	 * @return
	 */
//	public static View formatView(Activity c, View v, float b) {
//		// int width = c.getWindowManager().getDefaultDisplay().getWidth();
//		int width = IshowApplication.getPoint().x;
//		android.view.ViewGroup.LayoutParams lp = v.getLayoutParams();
//
//		if (lp == null) {
//			lp = new android.view.ViewGroup.LayoutParams(width, (int) (width * b));
//		} else {
//			lp.width = width;
//			lp.height = (int) (width * b);
//		}
//
//		v.setLayoutParams(lp);
//		return v;
//	}

	/**
	 * DP转PX
	 * 
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}

//	public static View formatView(View v, float highPoint) {
//		android.view.ViewGroup.LayoutParams lp = v.getLayoutParams();
//		if (lp == null) {
//			lp = new android.view.ViewGroup.LayoutParams(IshowApplication.getPoint().x, (int) (Float.parseFloat(IshowApplication.getPoint().x + "") / highPoint));
//		} else {
//			lp.width = IshowApplication.getPoint().x;
//			lp.height = (int) (Float.parseFloat(IshowApplication.getPoint().x + "") / highPoint);
//		}
//		v.setLayoutParams(lp);
//		return v;
//	}
//	public static View formatView(View v, float highPoint, int cut) {
//		android.view.ViewGroup.LayoutParams lp = v.getLayoutParams();
//		int width = IshowApplication.getPoint().x;
//		width = width - width / 320 * cut;
//		if (lp == null) {
//			lp = new android.view.ViewGroup.LayoutParams(width, (int) (Float.parseFloat(width + "") / highPoint));
//		} else {
//			lp.width = width;
//			lp.height = (int) (Float.parseFloat(width + "") / highPoint);
//		}
//		v.setLayoutParams(lp);
//		return v;
//	}

	public static View formatView(View v, float width, float highPoint) {
		if (width > 0 && highPoint > 0) {
			android.view.ViewGroup.LayoutParams lp = v.getLayoutParams();
			lp.width = (int) width;
			lp.height = (int) highPoint;
			v.setLayoutParams(lp);
		}
		return v;
	}

	public static void closeKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
		// if (context.getCurrentFocus() != null)
		// ((InputMethodManager)
		// context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
		// InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void openKeyboard(Activity context, View v) {

		// (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		//
		// inputManager.showSoftInput(editText, 0);

		InputMethodManager imm = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
		// imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		imm.showSoftInput(v, 0);
		// InputMethodManager imm = (InputMethodManager)
		// (context.getSystemService(Context.INPUT_METHOD_SERVICE));
		// // 得到InputMethodManager的实例
		// if (!imm.isActive()) {
		// // 如果开启
		// imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
		// InputMethodManager.HIDE_NOT_ALWAYS);
		// // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		// }
	}

	/**
	 * 设置多颜色textview
	 * 
	 * @param textView
	 * @param actionText
	 * @param context
	 * @return
	 */
//	public static TextView setColorText(TextView textView, StringBuilder actionText, Context context) {
//		textView.setText(Html.fromHtml(actionText.toString(), new Html.ImageGetter() {
//			@Override
//			public Drawable getDrawable(String source) {
//				Drawable myDrawable = null;
//				return myDrawable;
//			}
//		}, null));
//		textView.setMovementMethod(LinkMovementMethod.getInstance());
//		CharSequence text = textView.getText();
//		int ends = text.length();
//		Spannable spannable = (Spannable) textView.getText();
//		URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
//		SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
//		stylesBuilder.clearSpans(); // should clear old spans
//		for (URLSpan url : urlspan) {
//			SayclickSpan myURLSpan = new SayclickSpan((SayInterface) context, url.getURL(), context, textView);
//			stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url), spannable.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		textView.setText(stylesBuilder);
//		return textView;
//	}
//	public static TextView setColorText(TextView textView, StringBuilder actionText, Context context,int start,int end) {
//		textView.setText(Html.fromHtml(actionText.toString(), new Html.ImageGetter() {
//			@Override
//			public Drawable getDrawable(String source) {
//				Drawable myDrawable = null;
//				return myDrawable;
//			}
//		}, null));
//		textView.setMovementMethod(LinkMovementMethod.getInstance());
//		CharSequence text = textView.getText(); 
//		Spannable spannable = (Spannable) textView.getText();
//		URLSpan[] urlspan = spannable.getSpans(start, end, URLSpan.class);
//		SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
//		stylesBuilder.clearSpans(); // should clear old spans
//		for (URLSpan url : urlspan) {
//			SayclickSpan myURLSpan = new SayclickSpan((SayInterface) context, url.getURL(), context, textView);
//			stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url), spannable.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		textView.setText(stylesBuilder);
//		return textView;
//	}

//	public static StringBuilder appendStr(StringBuilder actionText, String sayclickSpanInterfaceName, String text) {
//		if (sayclickSpanInterfaceName.equals(SayclickSpan.OVERDUL_DESC))
//			actionText.append(text);
//		else
//			actionText.append("<a style=\"text-decoration:none;\" href='" + sayclickSpanInterfaceName + "'>" + text + " </a>");
//		return actionText;
//	}

	public static TextView setBottomLine(TextView handleTv) {
		handleTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		handleTv.getPaint().setAntiAlias(true);// 抗锯齿
		return handleTv;
	}
	
	private static long lastClickTime;
	/**
	 * 防止按钮的重复点击 多次 造成多次操作
	 */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        //2秒内重复点击不作处理
        if ( time - lastClickTime < 2000) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
}
