package fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import edu.feicui.news.AccountActivity;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import entity.NewsEntity;
import entity.UserCenterInfo;
import utils.SharedPreUtils;
import utils.UpdateUtils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class RightMenuFragment extends Fragment {


    MainActivity activity;
    NewsEntity<UserCenterInfo> userInfo;//用户中心传过来的用户信息
    @Bind(R.id.rgp_right_menu)
    RadioGroup rgpRightMenu;
    @Bind(R.id.ibtn_login_photo)
    ImageButton ibtnLoginPhoto;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    static  final int WEBCHAT=1;
    static  final int QQCHAT=2;
    static  final int WEBCHATMOMENTS=3;
    static  final int SINA=4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        SharedPreferences preferences=SharedPreUtils.getSharedPreUtils(getActivity()).getSharedPreferences();
        userInfo = (NewsEntity<UserCenterInfo> ) activity.getIntent().getSerializableExtra("userInfo");
        boolean firstShow = preferences.getBoolean("firstShow", true);
        if (!firstShow) {
            if (userInfo!=null&&userInfo.getStatus()==0) {
                Picasso.with(getActivity()).load(userInfo.getData().getPortrait()).into(ibtnLoginPhoto);
                btnLogin.setText(userInfo.getData().getUid());
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.toActivity(AccountActivity.class);
                    }
                });
            }
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstShow", false);
            editor.commit();
        }
        rgpRightMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_wechat:
                        showShare(WEBCHAT);
                        break;
                    case R.id.btn_qq:
                        showShare(QQCHAT);
                        break;
                    case R.id.btn_share:
                        showShare(WEBCHATMOMENTS);
                        break;
                    case R.id.btn_weibo:
                        showShare(SINA);
                        break;

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ibtn_login_photo, R.id.btn_login, R.id.btn_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_login_photo:
                activity.showContent();
                activity.changeTitleContent("用户登录");
                activity.replaceFragment(R.id.lyt_main, new LoginFragment());
                break;
            case R.id.btn_login:
                activity.showContent();
                activity.changeTitleContent("用户登录");
                activity.replaceFragment(R.id.lyt_main, new LoginFragment());
                break;
            case R.id.btn_update:
                UpdateUtils.download(getActivity());
                break;

        }
    }
    private void showShare(int platform) {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭 sso 授权
        oks.disableSSOWhenAuthorize();
        // title 标题，印象笔记、邮箱、信息、微信、人人网和 QQ 空间使用
        oks.setTitle(getString(R.string.ssdk_oks_share));
        // titleUrl 是标题的网络链接，仅在人人网和 QQ 空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text 是分享文本，所有平台都需要这个字段
        oks.setText("Tower 新闻客户端");
        // imagePath 是图片的本地路径，Linked-In 以外的平台都支持此参数
//         oks.setImagePath("/sdcard/test.jpg");
        // url 仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment 是我对这条分享的评论，仅在人人网和 QQ 空间使用
        oks.setComment("Tower 新闻客户端是一款好的新闻软件");
        switch (platform) {
            case WEBCHAT:
                oks.setPlatform(Wechat.NAME);
                break;
            case WEBCHATMOMENTS:
                oks.setPlatform(WechatMoments.NAME);
                break;
            case QQCHAT:
                oks.setPlatform(QQ.NAME);
                break;
            case SINA:
                oks.setPlatform(SinaWeibo.NAME);
                break;
        }
        //  启动分享 GUI
        oks.show(getActivity());
    }
}
