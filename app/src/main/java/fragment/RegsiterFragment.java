package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import entity.NewsEntity;
import entity.RegisterInfo;
import manager.UserManager;
import utils.HttpWrapper;
import utils.LogWrapper;
import utils.ParserUtils;

/**
 * Created by Administrator on 2016/7/25.
 */
public class RegsiterFragment extends Fragment {

    @Bind(R.id.edt_user_mail)
    EditText edtUserMail;//用户邮箱
    @Bind(R.id.edt_user_called)
    EditText edtUserCalled;//用户昵称
    @Bind(R.id.edt_user_pwd_regsiter)
    EditText edtUserPwdRegsiter;//注册密码
    @Bind(R.id.btn_register_now)
    Button btnRegisterNow;//立即注册按钮
    @Bind(R.id.chb_check)
    CheckBox chbCheck;
    @Bind(R.id.txt_agree)
    TextView txtAgree;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
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


    @OnClick({R.id.btn_register_now, R.id.chb_check, R.id.txt_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_now:
                LogWrapper.e("btn_register_now","++++++++++++++"+chbCheck.isChecked());
                //判断是否同意协议
                if (!chbCheck.isChecked()) {
                    Toast.makeText(getActivity(), "没有同意协议条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userMail = edtUserMail.getText().toString().trim();
                String userCalled = edtUserCalled.getText().toString().trim();
                String userPwdReg = edtUserPwdRegsiter.getText().toString().trim();
                //判断邮箱，账号，密码是否全部符合规范
                if(TextUtils.isEmpty(userCalled) ){
                    Toast.makeText(getActivity(), "请输入用户昵称",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(userPwdReg.length() < 6 || userPwdReg.length() > 16 ){
                    Toast.makeText(getActivity(), "密码长度错误",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }
                Map<String, String> map = new HashMap<>();
//                ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
                map.put("ver", "1");
                map.put("uid", userCalled);
                map.put("email", userMail);
                map.put("pwd", userPwdReg);
                //发送请求
                UserManager userManager=UserManager.getmUserManager(getActivity());
                userManager.Register(Constant.METHOD_GET, Constant.PATH_REGSITER, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<RegisterInfo> regData = ParserUtils.getmParserUtils().parseRegister(jsonObject);
                        RegisterInfo registerInfo=regData.getData();
                        int  result;
                        LogWrapper.e("regData.getStatues()","--------"+regData.getStatus());
                        if (regData.getStatus() == 0) {
                            result=registerInfo.getResult();
//                        LogWrapper.e("result","---++++"+result);
                            //注册成功
                            if (result == 0) {
                                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                //更新UI
//                                activity.showContent();
//                                activity.replaceFragment(R.id.lyt_main_menu, new AccountFragment());
                                activity.toActivity(AccountActivity.class);
//                                Intent intent = new Intent(getActivity(), AccountActivity.class);
//                                startActivity(intent);
                            } else if (result==-1) {
                                Toast.makeText(getActivity(), "服务器用户已满", Toast.LENGTH_SHORT).show();
                            } else if (result == -2) {
                                Toast.makeText(getActivity(), "用户名重复", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "邮箱名重复", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "服务器响应异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.chb_check:
                break;
            case R.id.txt_agree:
                    if (chbCheck.isChecked()) {
                        chbCheck.setChecked(false);
                    } else {
                        chbCheck.setChecked(true);

                    }
                break;
        }
    }
}
