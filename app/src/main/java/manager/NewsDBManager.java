package manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import entity.News;
import entity.SubType;
import utils.DBOpenHelper;
import utils.LogWrapper;

/**
 * 新闻数据库管理类
 * Created by Administrator on 2016/7/29.
 */
public class NewsDBManager {

    private DBOpenHelper dbHelper;
    private Context context;
    public NewsDBManager(Context context) {
        this.context = context;
        dbHelper = new DBOpenHelper(context);
    }
    /**
     *  保存新闻分类
     * @param types
     */
    public boolean saveNewsType(List<SubType> types){
        for(SubType type:types) {
            try {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                //"select * from type where subid="+type.getSubid()  找到子分类号对应的新闻信息（类型）
                Cursor cursor=db.rawQuery("select * from type where subid="+type.getSubid(),null);
                if(cursor.moveToFirst()){//没有内容显示，返回false
                    cursor.close();
                    return false;
                }
                //游标向下移动，准备添加数据
                cursor.close();
                //将新闻具体数据放入表中
                ContentValues values=new ContentValues();
                values.put("subid", type.getSubid());
                values.put("subgroup", type.getSubgroup());
                db.insert("type", null, values);
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    /**
     *  获取新闻分类 本地数据库
     * @return  新闻分类列表
     */
    public List<SubType> queryNewsType(){
        List<SubType> newsList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select * from type order by _id desc";
        Cursor cursor=db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int subId = cursor.getInt(cursor.getColumnIndex("subid"));
                String subGroup = cursor.getString(cursor.getColumnIndex("subgroup"));
                SubType subType = new SubType(subId, subGroup);
                newsList.add(subType);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return newsList;
    }
    /**
     *  收藏喜欢的新闻
     * @param news
     */
    public boolean saveFavoriteNews(News news){
        try {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor=db.rawQuery("select * from favorite where nid="+news.getNid(),null);
            LogWrapper.e("cursor","cursor---"+cursor);
            if(cursor.moveToFirst()){
                cursor.close();
                LogWrapper.e("cursor","cursor+++"+cursor);
                return false;
            }
            cursor.close();
            ContentValues values=new ContentValues();
            values.put("type", news.getType());
            values.put("nid", news.getNid());
            values.put("stamp", news.getStamp());
            values.put("icon", news.getIcon());
            values.put("title", news.getTitle());
            values.put("summary", news.getSummary());
            values.put("link", news.getLink());
            db.insert("favorite", null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     *  获取收藏新闻的列表
     * @return  新闻的列表
     */
    public List<News> queryFavoriteNews(){
        List<News> newsList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select * from favorite order by _id desc";
        Cursor cursor=db.rawQuery(sql, null);
        LogWrapper.e("queryFavoriteNews","cursor---"+cursor);
        if (cursor.moveToFirst()) {
            do {
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String nid = cursor.getString(cursor.getColumnIndex("nid"));
                String stamp =
                        cursor.getString(cursor.getColumnIndex("stamp"));
                String icon =
                        cursor.getString(cursor.getColumnIndex("icon"));
                String title =
                        cursor.getString(cursor.getColumnIndex("title"));
                String summary =
                        cursor.getString(cursor.getColumnIndex("summary"));
                String link =
                        cursor.getString(cursor.getColumnIndex("link"));
                News news = new News(type, nid, stamp, icon, title, summary, link);
                newsList.add(news);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return newsList;
    }

    /**
     * 删除数据库中的数据
     * @param news
     */
    public void deleteDB(News news)
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        db.delete("favorite", "nid=?", new String[]{news.getNid()});
    }
}
