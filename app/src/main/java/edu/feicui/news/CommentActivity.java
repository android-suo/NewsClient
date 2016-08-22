package edu.feicui.news;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.CommentAdapter;
import base.Constant;
import base.MyBaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.CommentSendInfo;
import entity.CommentShowInfo;
import entity.NewsEntity;
import manager.UserManager;
import utils.ParserUtils;
import utils.SharedPreUtils;
import xlistview.XListView;

public class CommentActivity extends MyBaseActivity {

    @Bind(R.id.ibtn_home_comment)
    ImageButton ibtnHomeComment;
    @Bind(R.id.lst_comment)
    XListView lstComment;
    @Bind(R.id.edt_comment_edit)
    EditText edtCommentEdit;
    @Bind(R.id.ibtn_send_comment)
    ImageButton ibtnSendComment;
    CommentAdapter adapter;
    String newsNid;//该新闻的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        adapter = new CommentAdapter(this);
        Bundle bundle = getIntent().getExtras();
        newsNid = bundle.getString("bundle");
        CommentRequest();//请求服务器返回数据
        initXListView();//初始化xlistview
//        LogWrapper.e("newsNid ","newsNid ==="+newsNid);
    }

    /**
     * 评论请求
     */
    private void CommentRequest() {
        //cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("nid", newsNid);
        map.put("type", "1");
        map.put("stamp", "20140321");
        map.put("cid", "1");
        map.put("dir", "1");
        map.put("cnt", "20");
        UserManager.getmUserManager(this)
                .getComment(Constant.METHOD_GET, Constant.PATH_COMMENT_SHOW, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<List<CommentShowInfo>> commentShow = ParserUtils.getmParserUtils().parseCommentShow(jsonObject);
                        if (commentShow.getStatus() == 0) {
                            List<CommentShowInfo> showInfo = commentShow.getData();
                            adapter.addNewData(showInfo);
                            lstComment.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
    }

    /**
     * 设置下拉刷新的属性
     */
    private void initXListView() {
        //设置下拉和上拉的使能，默认的下拉刷性为true，上拉加载为false
        lstComment.setPullLoadEnable(true);//允许上拉加载
        String formatDate = getStringOfDate();
        lstComment.setRefreshTime(formatDate);
        lstComment.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {//下拉刷新数据
                CommentRequest();
                lstComment.stopRefresh();
            }

            @Override
            public void onLoadMore() {//上拉加载更多数据
                lstComment.stopLoadMore();
            }
        });
    }

    /**
     * 将当前事件的毫秒值转为String
     *
     * @return
     */
    private String getStringOfDate() {
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }

    @Override
    public void handleMsg(Message msg) {

    }

    @OnClick({R.id.ibtn_home_comment, R.id.ibtn_send_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_home_comment:
                finish();
                break;
            case R.id.ibtn_send_comment:
                sendComRequest();
                break;
        }
    }

    /**
     * 发布评论请求
     */
    private void sendComRequest() {
        //                cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
        String text = edtCommentEdit.getText().toString().trim();
        String token = SharedPreUtils.getSharedPreUtils(this).getToken();
        String imei = UserManager.getIMEI(this);
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("nid", newsNid);
        map.put("token", token);
        map.put("imei", imei);
        map.put("ctx", text);
        UserManager.getmUserManager(this)
                .getComment(Constant.METHOD_GET, Constant.PATH_COMMENT_SEND, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<CommentSendInfo> commentSend = ParserUtils.getmParserUtils().parseCommentSend(jsonObject);
                        if (commentSend.getStatus()==0) {
                            CommentSendInfo sendInfo = commentSend.getData();
                            if (sendInfo.getResult() == 0) {
                                Toast.makeText(CommentActivity.this, sendInfo.getExplain(), Toast.LENGTH_SHORT).show();
                            } else if (sendInfo.getResult()==-1) {
                                Toast.makeText(CommentActivity.this, "非法关键字", Toast.LENGTH_SHORT).show();

                            }else if (sendInfo.getResult()==-2) {
                                Toast.makeText(CommentActivity.this, "禁止评论", Toast.LENGTH_SHORT).show();
                            }else if (sendInfo.getResult()==-3) {
                                Toast.makeText(CommentActivity.this, "该用户已被禁言", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CommentActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
    }
}
