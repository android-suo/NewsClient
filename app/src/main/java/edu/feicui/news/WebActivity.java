package edu.feicui.news;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import base.Constant;
import base.MyBaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.News;
import entity.NewsEntity;
import manager.NewsDBManager;
import manager.UserManager;
import utils.LogWrapper;
import utils.ParserUtils;

public class WebActivity extends MyBaseActivity {

    @Bind(R.id.web_news)
    WebView webNews;
    @Bind(R.id.ibtn_home_web)
    ImageButton ibtnHomeWeb;
    @Bind(R.id.txt_title_web)
    TextView txtTitleWeb;
    @Bind(R.id.btn_comment_web)
    Button btnCommentWeb;
    @Bind(R.id.ibtn_right_web)
    ImageButton ibtnRightWeb;
    @Bind(R.id.ibtn_share_web)
    ImageButton ibtnShareWeb;
    @Bind(R.id.ibtn_com_edit)
    ImageButton ibtnComEdit;
    @Bind(R.id.txt_com)
    TextView txtCom;
    @Bind(R.id.btn_reflect_comment)
    Button btnReflectComment;
    News news;
    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        //初始化webview
        WebSettings setting = webNews.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        //获得界面跳转传过来的网址信息
        news = (News) getIntent().getSerializableExtra("news");
        webNews.loadUrl(news.getLink());
//        LogWrapper.e("Nid","Nid==="+news.getNid());
        ibtnHomeWeb.setImageResource(R.mipmap.back);
        CommentNum();//设置跟帖数量

    }

    /**
     * 设置跟帖数量
     */
    private void CommentNum() {
        //        cmt_num?ver=版本号& nid=新闻编号
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("nid", news.getNid());
        UserManager.getmUserManager(this)
                .getComment(Constant.METHOD_GET, Constant.PATH_COMMENT_NUM, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<String> numEntry = ParserUtils.getmParserUtils().parseCommentNum(jsonObject);
                        String commentNum = numEntry.getData();
                        btnCommentWeb.setText(commentNum+" 跟帖");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
    }

    @OnClick({R.id.ibtn_home_web, R.id.btn_comment_web, R.id.ibtn_right_web,
            R.id.ibtn_share_web,R.id.ibtn_com_edit,R.id.txt_com,R.id.btn_reflect_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_home_web://返回新闻列表
                finish();
                break;
            case R.id.btn_comment_web://跳转到评论界面
                Bundle bundledd = new Bundle();
                bundledd.putString("bundle",news.getNid());
                toActivity(CommentActivity.class,bundledd);
                break;
            case R.id.ibtn_right_web:
                showPopView();//加入收藏窗口
                break;
            case  R.id.ibtn_share_web://分享新闻
                break;
            case  R.id.ibtn_com_edit://写评论
                Bundle bundle = new Bundle();
                bundle.putString("bundle",news.getNid());
                toActivity(CommentActivity.class,bundle);
                break;
            case  R.id.txt_com://跟帖写评论
                Bundle bundled = new Bundle();
                bundled.putString("bundle",news.getNid());
                toActivity(CommentActivity.class,bundled);
                break;
            case  R.id.btn_reflect_comment://意见反馈
                break;
        }
    }

    private void showPopView() {
        //显示 R.layout.item_pop_save 界面
        View popview = getLayoutInflater().inflate(R.layout.item_popup_save_news, null);
        //对弹出窗口进行设置
        popupWindow = new PopupWindow(popview, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        TextView saveNews = (TextView) popview.findViewById(R.id.save_news);
        //设置点击监听，点击后添加新闻到数据库
        saveNews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                NewsDBManager manager = new NewsDBManager(WebActivity.this);
                if (manager.saveFavoriteNews(news)) {
                    Toast.makeText(WebActivity.this, "收藏成功！\n" +
                            " 在主界面侧滑菜单中查看", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(WebActivity.this, "已经收藏过这条新闻了！\n" +
                            " 在主界面侧滑菜单中查看", Toast.LENGTH_SHORT).show();
                }
            }
        });
        popupWindow.showAsDropDown(ibtnRightWeb, 0,0);
    }

    @Override
    public void handleMsg(Message msg) {

    }
}
