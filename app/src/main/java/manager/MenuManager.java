package manager;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.news.R;
import entity.MenuInfo;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MenuManager {

    public static List<MenuInfo> getMenuData()
    {
        List<MenuInfo> infos=new ArrayList<>();


        MenuInfo menuInfo=new MenuInfo();
        menuInfo.setTitle("新闻");
        menuInfo.setEngTitle("NEWS");
        menuInfo.setIcon(R.mipmap.biz_navigation_tab_news);
        infos.add(menuInfo);

        MenuInfo menuInfofav=new MenuInfo();
        menuInfofav.setTitle("收藏");
        menuInfofav.setEngTitle("FAVORITE");
        menuInfofav.setIcon(R.mipmap.biz_navigation_tab_read);
        infos.add(menuInfofav);

        MenuInfo menuInfoLocal=new MenuInfo();
        menuInfoLocal.setTitle("本地");
        menuInfoLocal.setEngTitle("LOCAL");
        menuInfoLocal.setIcon(R.mipmap.biz_navigation_tab_local_news);
        infos.add(menuInfoLocal);

        MenuInfo menuInfoCom=new MenuInfo();
        menuInfoCom.setTitle("跟帖");
        menuInfoCom.setEngTitle("COMMENT");
        menuInfoCom.setIcon(R.mipmap.biz_navigation_tab_ties);
        infos.add(menuInfoCom);

        MenuInfo menuInfoPhoto=new MenuInfo();
        menuInfoPhoto.setTitle("图片");
        menuInfoPhoto.setEngTitle("PHOTO");
        menuInfoPhoto.setIcon(R.mipmap.biz_navigation_tab_pics);
        infos.add(menuInfoPhoto);
        return infos;
    }
}
