package view;

import edu.feicui.news.R;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/**
 * �Զ���Dialog����������������ʾ
 * @author Administrator
 *
 */
public class ProDialog extends Dialog {
	ImageView mImgPgbDialog;
	public ProDialog(Context context) {
		super(context,R.style.ProgressDialog);//��style�иı�Ի������״����������
		setContentView(R.layout.progress_dialog);
		//��Ի����е�ͼƬ���ö���
		mImgPgbDialog=(ImageView) this.findViewById(R.id.img_progress);
		Animation animation=AnimationUtils.loadAnimation(context, R.anim.progress_dialog_anim);
		mImgPgbDialog.setAnimation(animation);
	}

}
