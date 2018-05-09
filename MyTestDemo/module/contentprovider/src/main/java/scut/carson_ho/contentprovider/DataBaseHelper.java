package scut.carson_ho.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carson_Ho on 17/6/6.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // 数据库名
    private static final String DATABASE_NAME = "my_db.db";

    // 表名
    private final String TABLE_NAME;


    //数据库版本号
    private static final int DATABASE_VERSION = 1;



    public DataBaseHelper(Context context, String tb_name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        TABLE_NAME = tb_name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建两个表格:用户表 和职业表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id integer PRIMARY KEY AUTOINCREMENT, name text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id integer PRIMARY KEY AUTOINCREMENT, name text, age integer)");
        }
    }




}
