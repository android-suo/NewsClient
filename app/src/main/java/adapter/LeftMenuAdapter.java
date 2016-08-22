package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import manager.MenuManager;

import base.MyBaseAdapter;
import edu.feicui.news.R;
import entity.MenuInfo;

/**
 * Created by Administrator on 2016/7/20.
 */
public class LeftMenuAdapter extends MyBaseAdapter{
    /**
     * ���췽�����������Ķ��󣬴�����Ⱦ��
     *
     * @param context
     */
    public LeftMenuAdapter(Context context) {
        super(context);
        getData();
    }
    public void getData()
    {
        this.addNewData(MenuManager.getMenuData());
    }
    @Override
    public View getMyView(int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.items_lst_menu,null);
            AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
            convertView.setLayoutParams(params);
            viewHolder.txtMenuTitle=(TextView) convertView.findViewById(R.id.txt_menu_title);
            viewHolder.txtMenuEngtitle=(TextView) convertView.findViewById(R.id.txt_menu_engtitle);
            viewHolder.imgMenuIcon=(ImageView) convertView.findViewById(R.id.img_menu_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        MenuInfo menuInfo=(MenuInfo) mData.get(position);
        viewHolder.txtMenuTitle.setText(menuInfo.getTitle());
        viewHolder.txtMenuEngtitle.setText(menuInfo.getEngTitle());
        viewHolder.imgMenuIcon.setImageResource(menuInfo.getIcon());
        return convertView;
    }
    class ViewHolder
    {
        ImageView imgMenuIcon;
        TextView txtMenuTitle;
        TextView txtMenuEngtitle;
    }
}
