package fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.LoginlogAdapter;
import base.Constant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.feicui.news.AccountActivity;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import entity.NewsEntity;
import entity.UserCenterDetailInfo;
import entity.UserCenterInfo;
import manager.UserManager;
import utils.LogWrapper;
import utils.ParserUtils;
import utils.SharedPreUtils;

import static android.view.ViewGroup.*;
/**
 * Created by Administrator on 2016/7/26.
 */
public class AccountFragment extends Fragment {
    @Bind(R.id.img_user_logo)
    ImageView imgUserLogo;//头像
    @Bind(R.id.txt_account)
    TextView txtAccount;//用户名称
    @Bind(R.id.txt_integration)
    TextView txtIntegration;
    @Bind(R.id.txt_comment_num)
    TextView txtCommentNum;
    @Bind(R.id.lst_login_log)
    ListView lstLoginLog;
    @Bind(R.id.ibtn_back_account)
    ImageButton ibtnBackAccount;
    @Bind(R.id.txt_title_account)
    TextView txtTitleAccount;
    @Bind(R.id.btn_exit_login)
    Button btnExitLogin;
    LinearLayout photoTake;
    LinearLayout photoSelect;
    PopupWindow window;//头像设置窗口
    String portrait;//服务器返回头像地址
    AccountActivity activity;
    NewsEntity<UserCenterInfo> userInfo;//登录用户信息
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //从服务器获取用户信息
        activity = (AccountActivity) getActivity();
        getUserMessage();
        //设置头像的PopupWindow
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_popupwindow, null);
        window = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        photoTake = (LinearLayout) contentView.findViewById(R.id.photo_take);
        photoSelect = (LinearLayout) contentView.findViewById(R.id.photo_select);
        photoEvent();
    }
    /**
     * 照相和相片选择事件
     */
    private void photoEvent() {
        photoTake.setOnClickListener(new View.OnClickListener() {//跳到系统照相设备
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, 1);
            }
        });
        photoSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳到系统相片库
                Intent intent2 = getPhotoPickIntent();
                startActivityForResult(intent2, 2);
            }
        });
    }
    /**
     * 设置跳转返回图片的格式
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");//设置裁剪功能
        intent.putExtra("aspectX", 1); //宽高比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80); //宽高值
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true); //返回裁剪结果
        return intent;
    }
    /**
     * 用户中心数据请求
     */
    private void getUserMessage() {
        //        user_home?ver=版本号&imei=手机标识符&token =用户令牌
        String imei = UserManager.getIMEI(getActivity());//获取手机标识符
        LogWrapper.e("imei", "imei===" + imei);
        //获取登录令牌
        SharedPreUtils preUtils = SharedPreUtils.getSharedPreUtils(getActivity());
        String token = preUtils.getToken();
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("imei", imei);
        map.put("token", token);
        UserManager.getmUserManager(getActivity())
                .getUserMessage(Constant.METHOD_GET, Constant.PATH_USER_HOME, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userInfo = ParserUtils.getmParserUtils().parseUserMessage(jsonObject);
                        LogWrapper.e("Status()", "Status()====" + userInfo.getStatus());
                        if (userInfo.getStatus() == 0) {
//                            获取数据成功
                            UserCenterInfo centerInfo = userInfo.getData();
                            //存储用户中心数据
                            SharedPreUtils utils = SharedPreUtils.getSharedPreUtils(getActivity());
                            utils.saveUserInfo(centerInfo);
//                            刷新UI
                            refreshUI(centerInfo);
                        } else {
                            Toast.makeText(getActivity(), "请求服务器数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
    }
    /**
     * 获取用户中心数据后刷新UI
     *
     * @param centerInfo
     */
    private void refreshUI(UserCenterInfo centerInfo) {
        String uid = centerInfo.getUid();//用户名称
        portrait = centerInfo.getPortrait();//用户头像
        Picasso.with(getActivity()).load(portrait).into(imgUserLogo);
        String integration = centerInfo.getIntegration();//积分
        String comnum = centerInfo.getComnum();//评论数量
        List<UserCenterDetailInfo> loginlog = centerInfo.getLoginlog();//登录日志信息
        //更新UI
        txtAccount.setText(uid);
        txtCommentNum.setText(comnum);
        txtIntegration.setText(integration);
        LoginlogAdapter adapter = new LoginlogAdapter(getActivity());
        adapter.addNewData(loginlog);
        lstLoginLog.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.ibtn_back_account, R.id.btn_exit_login,
            R.id.img_user_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back_account://返回到新闻列表
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo",userInfo);
                activity.toActivity(MainActivity.class,bundle);
                break;
            case R.id.btn_exit_login://退出登录
                SharedPreUtils.getSharedPreUtils(getActivity()).clearToken();//清除登录帐户数据
                activity.toActivity(MainActivity.class);
                getActivity().finish();
                break;
            case R.id.img_user_logo://用户头像
//                    if (portrait.isEmpty()) {//服务器返回地址为空，上传头像
                        View lytAccount = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_account, null);
                        window.setBackgroundDrawable(new ColorDrawable());
                        window.setOutsideTouchable(true);
                        window.setFocusable(true);
                        window.setTouchInterceptor(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
                                    window.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                        window.showAtLocation(lytAccount, Gravity.BOTTOM, 0, 0);
//                    } else {//不为空，设置到imgview

//                    }
                break;
        }
    }
}
