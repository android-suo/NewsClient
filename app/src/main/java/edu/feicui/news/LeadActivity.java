package edu.feicui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import adapter.LeadAdapter;
import base.MyBaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/22.
 */
public class LeadActivity extends MyBaseActivity {
    @Bind(R.id.pgb_lead)
    ViewPager pgbLead;
    @Bind(R.id.img_point1)
    ImageView imgPoint1;
    @Bind(R.id.img_point2)
    ImageView imgPoint2;
    @Bind(R.id.img_point3)
    ImageView imgPoint3;
    @Bind(R.id.img_point4)
    ImageView imgPoint4;
    private int[] data = {R.mipmap.welcome, R.mipmap.wy, R.mipmap.bd, R.mipmap.small};
    ImageView[] images;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        LeadAdapter adapter = new LeadAdapter(this, data);
        pgbLead.setAdapter(adapter);
        initFirstLogin();
        initEvent();
        images=getPoint();

    }

    /**
     * 点击事件
     */
    public void initEvent() {

        /*
        ViewPager页面改变事件
         */
        pgbLead.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i <images.length ; i++) {
                    if (i == position) {
                        images[i].setAlpha(1.0f);
                    } else {
                        images[i].setAlpha(0.1f);
                    }
                }
                if (position==3) {
                    toActivity(LogoActivity.class);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    /**
     * 设置第一次登录事件
     */
    private void initFirstLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences("Lead", Context.MODE_PRIVATE);
        boolean isFirst=sharedPreferences.getBoolean("isFirst",true);
        if (!isFirst) {//跳转到主界面
            toActivity(MainActivity.class);
            finish();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
    }

    /**
     * 获取指示页面的圆点
     * @return
     */
    public ImageView[] getPoint()
    {
        ImageView[] imageViews=new ImageView[4];
        imageViews[0]=imgPoint1;
        imageViews[0].setAlpha(1.0f);//设置为可见
        imageViews[1]=imgPoint2;
        imageViews[1].setAlpha(0.1f);
        imageViews[2]=imgPoint3;
        imageViews[2].setAlpha(0.1f);
        imageViews[3]=imgPoint4;
        imageViews[3].setAlpha(0.1f);
        return imageViews;

    }
    @Override
    public void handleMsg(Message msg) {

    }
}
