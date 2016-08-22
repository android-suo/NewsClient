package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import adapter.LeftMenuAdapter;
import edu.feicui.news.MainActivity;
import edu.feicui.news.R;
import utils.LogWrapper;

/**
 * Created by Administrator on 2016/7/20.
 */
public class LeftMenuFragment extends Fragment{
    ListView mLst;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left_menu,null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity= (MainActivity) getActivity();
        mLst=(ListView) getView().findViewById(R.id.lst_left_menu);
        mLst.setDividerHeight(0);//去掉Listview子条目的分割线
        LeftMenuAdapter adapter=new LeftMenuAdapter(this.getActivity());
        mLst.setAdapter(adapter);
        mLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i <5; i++) {
                    if (i == position) {
                        view.setAlpha(1.0f);
                    }else {
                        LogWrapper.e("TAG","++++");
                        parent.getChildAt(i).setAlpha(0.5f);
                    }
                }
                switch (position)
                {
                    case 0:
                        mainActivity.showContent();
                        mainActivity.changeTitleContent("资讯");
                        mainActivity.mHandle.sendEmptyMessageDelayed(2,2000);
                        mainActivity.replaceFragment(R.id.lyt_main,new ContentFragment());
                        break;
                    case 1:
                        mainActivity.showContent();
                        mainActivity.changeTitleContent("收藏");
                        mainActivity.replaceFragment(R.id.lyt_main,new FavoriteFragment());
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "更多内容，敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "更多内容，敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "更多内容，敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

}
