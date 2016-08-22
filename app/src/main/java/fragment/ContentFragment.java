package fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.NewsAdapter;
import base.Constant;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.feicui.news.CommentActivity;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import edu.feicui.news.WebActivity;
import entity.News;
import entity.NewsEntity;
import entity.NewsType;
import entity.SubType;
import manager.NewsDBManager;
import manager.UserManager;
import utils.HttpWrapper;
import utils.LogWrapper;
import utils.ParserUtils;
import utils.SharedPreUtils;
import xlistview.XListView;
/**
 * Created by Administrator on 2016/7/20.
 */
public class ContentFragment extends Fragment {
    NewsAdapter adapter;
    List<News> newsInfos;
    NewsDBManager dbManager;
    MainActivity mainActivity;

    @Bind(R.id.lyt_hor_scoll_view)
    LinearLayout linearLayout;
    @Bind(R.id.lst_news_list)
    XListView lstNewsList;
    List<String> type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        adapter = new NewsAdapter(getActivity());
        dbManager = new NewsDBManager(getActivity());
        mainActivity.showDialog();//显示自定义进度条对话框
        newsRequest("1","1");//新闻数据请求
        initXListView();
//        if (dbManager.queryNewsType().size()==0) {//本地数据空为空
//            //从服务器获取新闻类型数据
//            newsTypeRequest();
//        } else {
//            //从本地数据库获得类型数据
//            dbManager.queryNewsType();
//        }
        initEvent();//新闻列表点击事件
        newsTypeRequest();
    }
    /**
     * 新闻类型请求
     */
    private void newsTypeRequest() {
        //news_sort?ver=版本号&imei=手机标识符
        String imei = UserManager.getIMEI(getActivity());
        Map<String, String> map = new HashMap<>();
        map.put("ver", "1");
        map.put("imei", imei);
        UserManager.getmUserManager(getActivity())
                .getComment(Constant.METHOD_GET, Constant.PATH_NEW_TYPE, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewsEntity<List<NewsType>> parseNewsType = ParserUtils.getmParserUtils().parseNewsType(jsonObject);
                        if (parseNewsType.getStatus()==0) {
//                            LogWrapper.e("-------","=========");
                            type=new ArrayList<>();
                            List<NewsType> newsTypes = parseNewsType.getData();
                            for (NewsType nType:newsTypes) {
                                List<SubType> subTypes = nType.getSubgrp();
//                                LogWrapper.e("nType","nType-----"+nType.getGroup());
                                for (SubType sub:subTypes) {
//                                    LogWrapper.e("Subgroup()","Subgroup()--"+sub.getSubgroup());
                                    type.add(sub.getSubgroup());
                                }
                            }
                            addView(type);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
    }
    /**
     * 初始化XListview
     */
    private void initXListView() {
        //设置下拉和上拉的使能，默认的下拉刷性为true，上拉加载为false
        lstNewsList.setPullLoadEnable(true);//允许上拉加载
        lstNewsList.setRefreshTime(getStringOfDate());
        lstNewsList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {//下拉刷新数据
                String firstShowId="1";
                //获取第一条新闻的id
                if (adapter.getCount()>0) {
                    firstShowId = adapter.getItem(0).getNid();
                }
                LogWrapper.e("nid","firstShowId---"+firstShowId);
                newsRequest("1",firstShowId);
                adapter.notifyDataSetChanged();
                lstNewsList.stopRefresh();
            }

            @Override
            public void onLoadMore() {//上拉加载更多数据
                if (lstNewsList.getCount()<=2) {
                    return;
                }
                //获取当前显示最后一条新闻的id
                String lastShowId=adapter.getItem(lstNewsList.getLastVisiblePosition()-2).getNid();
                HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mainActivity);
                Map<String, String> newsRequest = new HashMap<>();
                newsRequest.put("ver", "1");
                newsRequest.put("subid", "1");
                newsRequest.put("dir", "1");
                newsRequest.put("nid", lastShowId);
                newsRequest.put("stamp", "20140321");
                newsRequest.put("cnt", "20");
                httpWrapper.httpRequest(Constant.METHOD_GET, Constant.PATH_NEWS_LIST, newsRequest, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        ParserUtils parserUtils = ParserUtils.getmParserUtils();
                        NewsEntity<List<News>> newsData = parserUtils.parseNewsList(jsonObject);
                        String msg = newsData.getMessage();
                        int status = newsData.getStatus();
                        if (msg.equals("OK") && status == 0) {
                            List<News> news = newsData.getData();
                            newsInfos.addAll(news);//上拉加载后，增加集合中的内容
                            adapter.appendData(news);
                            adapter.notifyDataSetChanged();
//                            LogWrapper.e("adapter","adapter--"+adapter.getCount());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
                lstNewsList.stopLoadMore();
            }
        });
    }
    /**
     * 将当前事件的毫秒值转为String
     * @return
     */
    private String getStringOfDate() {
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }
    /**
     * 新闻列表请求
     */
    public void newsRequest(String dir,String nid) {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mainActivity);
        Map<String, String> newsRequest = new HashMap<>();
        newsRequest.put("ver", "1");
        newsRequest.put("subid", "1");
        newsRequest.put("dir", dir);
        newsRequest.put("nid", nid);
        newsRequest.put("stamp", "20140321");
        newsRequest.put("cnt", "20");
        httpWrapper.httpRequest(Constant.METHOD_GET, Constant.PATH_NEWS_LIST, newsRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                ParserUtils parserUtils = ParserUtils.getmParserUtils();
                NewsEntity<List<News>> newsData = parserUtils.parseNewsList(jsonObject);
                String msg = newsData.getMessage();
                int status = newsData.getStatus();
//                LogWrapper.e("Tag", "-----" + msg);
                if (msg.equals("OK") && status == 0) {
                    newsInfos = newsData.getData();
                    //保存新闻id
                    SharedPreUtils preUtils=SharedPreUtils.getSharedPreUtils(getActivity());
                    for (News newsInfo:newsInfos) {
                    String nid=newsInfo.getNid();
                    preUtils.saveNewsId(nid);
                    }
                    adapter.addNewData(newsInfos);
                    lstNewsList.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /**
     * 点击事件
     */
    public void initEvent() {
        lstNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),WebActivity.class);
                intent.putExtra("news",newsInfos.get(position));
                startActivity(intent);
            }
        });
    }
    /**
     * 在scollView中添加视图
     */
    public void addView(final List<String> type)
    {
        for (int i = 0; i <type.size() ; i++) {
//            LogWrapper.e("subTypes",type.size()+"---subTypes---"+type);
            TextView txt = new TextView(getActivity());
            txt.setTag(i);
            txt.setText(type.get(i)+"  ");
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setPadding(0,10,0,0);

//            LogWrapper.e("type.get","type.get+++"+type.get(i));
            txt.setTextSize(15.0f);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= (int) v.getTag();
                    TextView txt= (TextView) v;
                    for (int i = 0; i <8; i++) {
                        if (i == position) {
                            txt.setTextColor(Color.RED);
                            // TODO: 2016/7/26  更新点击后的视图
                        } else {
                            TextView t= (TextView) linearLayout.getChildAt(i);//布局中剩余的视图
                            t.setTextColor(Color.BLACK);
                        }
                    }
                }
            });
            linearLayout.addView(txt);
        }
    }

}