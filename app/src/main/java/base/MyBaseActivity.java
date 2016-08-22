package base;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import view.ProDialog;

/**
 * activity�Ļ���
 * ��װһЩ�����ģ����õĹ��ܣ��� ��ת�����̵߳���ݴ��?�������
 * @author Administrator
 *
 */
public abstract class MyBaseActivity extends FragmentActivity {




	ProDialog pDialog;
	//����֮�����ת
	/**
	 * ����ݺ͵�ַ�Ľ���֮�����ת
	 * @param cla  ����Ҫ��ת���Ľ���
	 * @param bundle  ��ת�����Я��������bundle������
	 * @param uri  ��ʽ��ת��Ҫ�����Ŀ�����
	 */
	public void toActivity(Class cla,Bundle bundle,Uri uri)
	{
		Intent intent=new Intent(this, cla);
		if (bundle!=null) {
			intent.putExtras(bundle);
		}
		if (uri!=null) {
			intent.setData(uri);
		}
		startActivity(intent);
	}
	/**
	 * ����ݵ�û����ʽ��ת
	 * @param cla
	 * @param bundle
	 */
	public void toActivity(Class cla,Bundle bundle)
	{
		toActivity(cla, bundle, null);
	}
	/**
	 * ��ʾ��ת���������
	 * @param cla
	 */
	public void toActivity(Class cla)
	{
		toActivity(cla, null, null);
	}

	//Handler����
	public Handler mHandle=new Handler(){
		public void handleMessage(Message msg) {
			handleMsg(msg);
		};
	};
	/**
	 * ����handle�����ԣ������﷢�͵���Ϣ����������ս���?Ҳ����˵���ĸ����淢����ݣ����Ǹ����洦����
	 * ����ʹ�ó��󷽷����Ǹ�����̳л�activity���ͻ�ʵ������������ͻ��ڵ�ǰ���洦����
	 * @param msg
	 */
	public abstract void handleMsg(Message msg);
	//������Ի�����ʾ
	public void showDialog()
	{
		pDialog=new ProDialog(this);
		pDialog.show();
	}
	/**
	 * ȡ����ʾ�Ի���
	 */
	public void cancelDialog()
	{
		if (pDialog!=null) {
			pDialog.cancel();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
