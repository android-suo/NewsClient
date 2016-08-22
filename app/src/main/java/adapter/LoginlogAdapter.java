package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import base.MyBaseAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import edu.feicui.news.R;
import entity.UserCenterDetailInfo;

/**
 * Created by Administrator on 2016/7/26.
 */
public class LoginlogAdapter extends MyBaseAdapter {
    Context mContext;

    /**
     * ���췽�����������Ķ��󣬴�����Ⱦ��
     *
     * @param context
     */
    public LoginlogAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        if (convertView==null) {
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item_lst_login_log, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserCenterDetailInfo detailInfo= (UserCenterDetailInfo) mData.get(position);
        holder.txtTime.setText(detailInfo.getTime());
        holder.txtAddress.setText(detailInfo.getAddress());
        holder.txtDevice.setText(getDevice(detailInfo.getDevice()));
        return convertView;
    }
    public String getDevice(int device)
    {
        switch (device) {
            case 0:
                return "移动端设备";
            case 1:
                return "PC端数据";
            default:
                return "未知设备";
        }
    }
     class ViewHolder {
        @Bind(R.id.txt_time)
        TextView txtTime;
        @Bind(R.id.txt_address)
        TextView txtAddress;
        @Bind(R.id.txt_device)
        TextView txtDevice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
