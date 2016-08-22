package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/22.
 */
public class LeadAdapter extends PagerAdapter {
    Context mContext;
    int[] datas;
    public LeadAdapter(Context context, int[] data)
    {
        this.mContext=context;
        this.datas=data;
    }
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        imageView.setImageResource(datas[position]);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
