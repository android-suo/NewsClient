package edu.feicui.news;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import base.MyBaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import edu.zx.slidingmenu.SlidingMenu;
import fragment.ContentFragment;
import fragment.LeftMenuFragment;
import fragment.RightMenuFragment;
public class MainActivity extends MyBaseActivity {
    SlidingMenu slidingMenu;//侧滑菜单，第三方框架
    @Bind(R.id.ibtn_home)
    ImageButton ibtnHome;
    @Bind(R.id.txt_title_content)
    TextView txtTitleContent;
    @Bind(R.id.ibtn_right)
    ImageButton ibtnRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置控件属性
        ibtnHome.setImageResource(R.mipmap.ic_title_home_default);
        ibtnRight.setImageResource(R.mipmap.ic_title_share_default);
        initEvent();
        mHandle.sendEmptyMessageDelayed(1, 5000);//
        //初始化SlidingMenu �
        initSlidingMenu();
        //给内容布局添加fragment
        addFragment(R.id.lyt_main, new ContentFragment());
        //设定左侧菜单内容
        addFragment(R.id.left_menu, new LeftMenuFragment());
        //设定右侧菜单内容
        addFragment(R.id.right_menu, new RightMenuFragment());
    }

    /**
     *
     * 按钮点击事件
     */
    private void initEvent() {
        ibtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
        ibtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRightMenu();
            }
        });
    }

    /**
     * 显示内容fragment
     */
    public void showContent() {
        slidingMenu.showContent();
    }

    /**
     * 显示菜单
     */
    public void showMenu() {
        slidingMenu.showMenu();
    }

    public void showRightMenu() {
        slidingMenu.showSecondaryMenu();
    }
    public void changeTitleContent(String string)
    {
        txtTitleContent.setText(string);
    }
    @Override
    public void handleMsg(Message msg) {
        Log.e("TAG", "msg==" + msg.what);
        cancelDialog();//关闭自定义对话框�
    }

    @Override
    public void onBackPressed() {//返回键按下
        if (slidingMenu.isMenuShowing()) {//如果侧滑菜单在显示，按下返回键后关闭侧滑菜单，显示内容
            slidingMenu.showContent();
        } else {
            super.onBackPressed();//结束App
        }
    }

    /**
     * 初始化slidingMenu
     */
    public void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);//创建侧滑菜单对象
        //设置侧滑菜单属性
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置左右侧菜单
        slidingMenu.setBehindOffset(200);//设置滑出后与边界的距离
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置触摸的方式来滑动
        //将侧滑菜单和activity关联，模式SLIDING_WINDOW：与窗口对齐；SLIDING_CONTENT：与内容对齐
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.lyt_left_menu);//设置一级菜单/底部菜单/左侧菜单
        slidingMenu.setSecondaryMenu(R.layout.lyt_right_menu);//设置二级菜单/上部菜单/右侧菜单
    }

    /**
     * 向布局中添加Fragment
     *
     * @param resId
     * @param fragment
     */
    public void addFragment(int resId, Fragment fragment) {
        //给指定布局设定fragment：设定左侧菜单的内容
        FragmentManager fragmentManager = this.getSupportFragmentManager();//获取管理器
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
        transaction.add(resId, fragment);//添加
        transaction.commit();//提交
    }

    /**
     * 替换布局中的fragment
     *
     * @param resId
     * @param fragment
     */
    public void replaceFragment(int resId, Fragment fragment) {
        //给指定布局设定fragment：设定左侧菜单的内容
        FragmentManager fragmentManager = this.getSupportFragmentManager();//获取管理器
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
        transaction.replace(resId, fragment);//添加
        transaction.commit();//提交
    }
}
