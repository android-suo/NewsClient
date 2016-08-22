package utils;

import android.util.Log;

/**
 * ��־�ķ�װ��
 * ��־ÿ�δ�ӡ��������ڴ棬�����ڳ��������ɺ󣬾Ͳ���Ҫִ����Щ���
 * ���Կ���isDebug�����������Ƿ�ִ��
 * @author suo
 *
 */
public class LogWrapper {
	public static boolean isDebug=true;
	public static void e(String tag,String msg)
	{
		if (isDebug) {
			Log.e(tag, msg);
		}
	}
	public static void d(String tag,String msg)
	{
		if (isDebug) {
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg)
	{
		if (isDebug) {
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg)
	{
		if (isDebug) {
			Log.w(tag, msg);
		}
	}
}
