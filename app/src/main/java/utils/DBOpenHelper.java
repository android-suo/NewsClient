package utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/29.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        //在本地创建
        super(context, "newsdb.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建分类数据库的数据库表 表名为type
        //primary key autoincrement:主键并自动增长：表示独一无二，不允许重复
        db.execSQL("create table type(_id integer primary key autoincrement,subid integer,subgroup text)");
        db.execSQL("create table favorite(_id integer primary key autoincrement,type integer,nid text," +
                "stamp text, icon text,title text,summary text,link text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
