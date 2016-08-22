package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import base.Constant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.feicui.news.AccountActivity;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import entity.LoginInfo;
import entity.NewsEntity;
import manager.UserManager;
import utils.LogWrapper;
import utils.ParserUtils;
import utils.SharedPreUtils;

/**
 * Created by Administrator on 2016/7/25.
 */
public class LoginFragment extends Fragment {

    @Bind(R.id.edt_user_name)
    EditText edtUserName;
    @Bind(R.id.edt_user_pwd)
    EditText edtUserPwd;
    @Bind(R.id.btn_register_in)
    Button btnRegisterIn;
    @Bind(R.id.btn_forgot_pwd)
    Button btnForgotPwd;
    @Bind(R.id.btn_login_in)
    Button btnLoginIn;
    MainActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_register_in, R.id.btn_forgot_pwd, R.id.btn_login_in})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_register_in:
                    activity.showContent();
                    activity.changeTitleContent("用户注册");
                    activity.replaceFragment(R.id.lyt_main,new RegsiterFragment());
                    break;
                case R.id.btn_forgot_pwd:
                    activity.showContent();
                    activity.changeTitleContent("忘记密码");
                    activity.replaceFragment(R.id.lyt_main,new PwdFindFragment());
                    break;
                case R.id.btn_login_in:
                    userLogin();//登录请求
                    break;
            }
    }

    /**
     * 登录
     */
    private void userLogin() {
        String userName=edtUserName.getText().toString().trim();
        String userPwd=edtUserPwd.getText().toString().trim();
        //判断输入的信息格式是否合法
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (TextUtils.isEmpty(userPwd)) {
            Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPwd.length()<6||userPwd.length()>12) {
            Toast.makeText(getActivity(),"密码长度错误",Toast.LENGTH_SHORT).show();
        }
        Map<String,String> map=new HashMap<>();
        map.put("ver","1");
        map.put("uid",userName);
        map.put("pwd",userPwd);
        map.put("device","0");
        UserManager userManager = UserManager.getmUserManager(getActivity());
        userManager.login(Constant.METHOD_GET, Constant.PATH_LOGIN, map, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                //得到服务器返回的信息
                NewsEntity<LoginInfo> loginData= ParserUtils.getmParserUtils().parseLogin(jsonObject);
                //获得共享参数工具类的对象
                SharedPreUtils preUtil=SharedPreUtils.getSharedPreUtils(getActivity());
                //存储获得的用户信息

                if (loginData.getStatus() == 0) {//登录成功
                    if (loginData.getData().getResult()==0) {
                        //存储登录信息到共享参数
                        preUtil.save(loginData.getData().getToken(), loginData.getData().getExplain());
                        //跳转到用户帐户界面
//                                    activity.replaceFragment(R.id.lyt_main_menu,new AccountFragment());
//                                    activity.showContent();
                        activity.toActivity(AccountActivity.class);
//                                    Intent intent = new Intent(getActivity(), AccountActivity.class);
//                                    startActivity(intent);
                        Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                    }

                } else {//登录失败
                    Toast.makeText(getActivity(),"登录失败，请重新输入",Toast.LENGTH_SHORT).show();
                    activity.replaceFragment(R.id.lyt_main,new LoginFragment());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }
}
