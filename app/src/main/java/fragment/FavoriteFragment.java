package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import adapter.NewsAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import edu.feicui.news.WebActivity;
import entity.News;
import manager.NewsDBManager;
/**
 * Created by Administrator on 2016/7/20.
 */
public class FavoriteFragment extends Fragment {



    ListView lstNewsFavorite;
    NewsAdapter adapter;
    NewsDBManager dbManager;//数据库的管理对象
    List<News> data;//从本地服务器获取的数据集合
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        lstNewsFavorite = (ListView) view.findViewById(R.id.lst_news_favorite);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsAdapter(getActivity());
        dbManager = new NewsDBManager(getActivity());
        data = loadLocal();
        adapter.addNewData(data);
        lstNewsFavorite.setAdapter(adapter);
        lstNewsFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news= (News) parent.getItemAtPosition(position);//获取子条目的新闻对象
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
            }
        });
        lstNewsFavorite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                News news= (News) parent.getItemAtPosition(position);
                data.remove(position);
                dbManager.deleteDB(news);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /**
     * 从本地服务器下载收藏新闻
     * @return
     */
    public List<News> loadLocal()
    {
       return dbManager.queryFavoriteNews();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.lst_news_favorite)
    public void onClick() {
    }
}