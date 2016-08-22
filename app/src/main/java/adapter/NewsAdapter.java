package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import base.MyBaseAdapter;
import base.MyRequest;
import edu.feicui.news.R;
import entity.News;
import utils.LogWrapper;

/**
 * ��������������б��������
 * @author Administrator
 *
 */
public class NewsAdapter extends MyBaseAdapter<News> {

	Context mContext;
	Bitmap iconBitmap;
	ImageView imgNewsIcon;
	public NewsAdapter(Context context) {
		super(context);
		mContext=context;
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup arg2)
	{
		View view=mInflater.inflate(R.layout.items_news_list, null);
		TextView txtNewsTitle=(TextView) view.findViewById(R.id.txt_news_title);
		TextView txtNewsSummary=(TextView) view.findViewById(R.id.txt_news_summary);
		TextView txtNewsDate=(TextView) view.findViewById(R.id.txt_news_date);
		imgNewsIcon=(ImageView) view.findViewById(R.id.img_news_icon);
		txtNewsTitle.setText(mData.get(position).getTitle());
		txtNewsSummary.setText(mData.get(position).getSummary());
		txtNewsDate.setText(mData.get(position).getStamp());
		String pathName=mData.get(position).getIcon();
//		MyTask iconTask=new MyTask();
//		iconTask.execute(pathName);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(220,220);
		imgNewsIcon.setLayoutParams(params);
		//使用Picsso缓存机制，防止内存溢出
		Picasso picasso=Picasso.with(mContext);
		RequestCreator requestCreator=picasso.load(pathName);
		requestCreator.placeholder(R.mipmap.defaultpic);//设定加载失败时替换的照片
		requestCreator.into(imgNewsIcon);
//		LogWrapper.e("icon", "+++++"+pathName);
		return view;
	}
}
