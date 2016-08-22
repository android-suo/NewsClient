package base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
	//Ϊ���ܿ��ʹ�ã������Ȩ��
	public List<T> mData;
	public LayoutInflater mInflater;
	/**
	 * ���췽�����������Ķ��󣬴�����Ⱦ��
	 */
	public MyBaseAdapter(Context context)
	{
		mInflater=LayoutInflater.from(context);
	}
	/**
	 * ����µ����
	 * @param data
	 */
	public void addNewData(List<T> data)
	{
		if (mData!=null) {//��ݲ�Ϊ�գ���ռ��ϣ�����������µ�
			mData.clear();
		}
		mData=data;
	}
	/**
	 * ��ԭ�����֮�ϼ���׷�����
	 * �����������ϻ������֮ǰ������
	 * @param data
	 */
	public void appendData(List<T> data)
	{
		mData.addAll(data);
	}
	/**
	 * ˢ��������
	 */
	public void updata()
	{
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return getMyView(arg0, arg1, arg2);
	}
	/**
	 * ���ó��󷽷���ȡ��ͼ��ÿ��ʵ�ַ����ͻ��ȡ��ͼ
	 */
	public abstract View getMyView(int arg0, View arg1, ViewGroup arg2);
	
		
	

}
