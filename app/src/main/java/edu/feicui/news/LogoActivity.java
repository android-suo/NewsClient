package edu.feicui.news;

import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import base.MyBaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/22.
 */
public class LogoActivity extends MyBaseActivity {

    @Bind(R.id.img_logo)
    ImageView imgLogo;
    private Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            toActivity(MainActivity.class);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);
        Animation animation=AnimationUtils.loadAnimation(this, R.anim.anim_logo);
        animation.setAnimationListener(animationListener);
        imgLogo.setAnimation(animation);
    }

    @Override
    public void handleMsg(Message msg) {

    }
}
