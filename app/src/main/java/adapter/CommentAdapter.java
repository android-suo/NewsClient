package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import base.MyBaseAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import edu.feicui.news.R;
import entity.CommentShowInfo;
import utils.LogWrapper;

/**
 * Created by Administrator on 2016/7/27.
 */
public class CommentAdapter extends MyBaseAdapter {
    Context mContext;

    /**
     * ���췽�����������Ķ��󣬴�����Ⱦ��
     *
     * @param context
     */
    public CommentAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lst_comment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommentShowInfo info = (CommentShowInfo) mData.get(position);
        //设定图片的大小和缓存
        String portrait = info.getPortrait();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        viewHolder.imgCommentIcon.setLayoutParams(params);
        Picasso picasso = Picasso.with(mContext);
        RequestCreator creator = picasso.load(portrait);
        creator.into(viewHolder.imgCommentIcon);
        viewHolder.txtCommentName.setText(info.getUid());
        viewHolder.txtCommentTime.setText(info.getStamp());
        viewHolder.txtCommentContent.setText(info.getContent());
        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.img_comment_icon)
        ImageView imgCommentIcon;
        @Bind(R.id.txt_comment_name)
        TextView txtCommentName;
        @Bind(R.id.txt_comment_time)
        TextView txtCommentTime;
        @Bind(R.id.txt_comment_content)
        TextView txtCommentContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
