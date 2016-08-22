package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import edu.feicui.news.R;
import entity.NewsEntity;
import entity.PwdInfo;
import manager.UserManager;
import utils.ParserUtils;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PwdFindFragment extends Fragment {

    @Bind(R.id.edt_pwd_find)
    EditText edtPwdFind;
    @Bind(R.id.btn_conform)
    Button btnConform;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_find, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    //user_forgetpass?ver=版本号&email=邮箱
    @OnClick(R.id.btn_conform)
    public void onClick() {
        String email = edtPwdFind.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("email", email);
        UserManager.getmUserManager(getActivity())
                .getPasswordBack(Constant.METHOD_GET, Constant.PATH_PWD_FIND, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<PwdInfo> password = ParserUtils.getmParserUtils().parsePassword(jsonObject);
                        PwdInfo pwdInfo = password.getData();
                        if (pwdInfo.getResult() == 0) {//请求成功
                            String explain = pwdInfo.getExplain();
                            Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "请求服务器失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

    }
}
