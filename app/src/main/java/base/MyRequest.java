package base;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * ���������װ��
 * �趨����
 * @author Administrator
 *
 */
public class MyRequest {

	/**
	 * �������󣬷������磬��ȡ���
	 * @param context
	 * @param request  ��������
	 */
	public static <T> void setRequest(Context context,Request<T> request)
	{
		RequestQueue queue=Volley.newRequestQueue(context);
		queue.add(request);
	}
}
