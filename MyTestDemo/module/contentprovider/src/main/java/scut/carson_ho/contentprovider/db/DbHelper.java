package scut.carson_ho.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by long on 2016/3/14.
 * 数据库帮助类
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "tb_file_info";
    private static final String CREATE_TABLE = "create table if not exists file_info ( "
            + "id integer primary key autoincrement, "
            + "url text, "
            + "name text, "
            + "path text, "
            + "loadBytes integer, "
            + "totalBytes integer, "
            + "status integer)";

    //删除表
    private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除表，执行最新的sql
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
