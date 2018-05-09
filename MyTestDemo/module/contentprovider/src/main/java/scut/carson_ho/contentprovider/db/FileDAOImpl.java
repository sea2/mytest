package scut.carson_ho.contentprovider.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import scut.carson_ho.contentprovider.entity.FileInfo;

/**
 * Created by long on 2016/5/26.
 * 数据库访问实现类
 */
public class FileDAOImpl implements FileDAO {

    private static final String DB_NAME = "FileDownloader.db";
    private static final int DB_VERSION = 1;
    private DbHelper mDbHelper = null;


    private FileDAOImpl(Context context) {
        // 创建数据库
        mDbHelper = new DbHelper(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void insert(FileInfo info) {
        if (mDbHelper != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            if (db != null) {
                db.insert(DbHelper.TABLE_NAME, null, info.toValues());
                db.close();
            }
        }
    }

    @Override
    public void delete(String url) {
        if (mDbHelper != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            if (db != null) {
                db.delete(DbHelper.TABLE_NAME, "url = ?", new String[]{url});
            }
        }
    }

    @Override
    public void update(FileInfo info) {
        if (mDbHelper != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            if (db != null)
                db.update(DbHelper.TABLE_NAME, info.toValues(), "url = ?", new String[]{info.getUrl()});
        }
    }

    @Override
    public FileInfo query(String fileUrl) {
        FileInfo fileInfo = null;
        if (mDbHelper != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Cursor cursor = db.query(DbHelper.TABLE_NAME, null, "url = ?", new String[]{fileUrl}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int loadBytes = cursor.getInt(cursor.getColumnIndex("loadBytes"));
                int totalBytes = cursor.getInt(cursor.getColumnIndex("totalBytes"));
                fileInfo = new FileInfo(url, name);
                fileInfo.setLoadBytes(loadBytes);
                fileInfo.setTotalBytes(totalBytes);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileInfo;
    }

    public FileInfo queryPkg(String pkgName) {
        FileInfo fileInfo = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, "name = ?", new String[]{pkgName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int loadBytes = cursor.getInt(cursor.getColumnIndex("loadBytes"));
            int totalBytes = cursor.getInt(cursor.getColumnIndex("totalBytes"));
            fileInfo = new FileInfo(url, name);
            fileInfo.setLoadBytes(loadBytes);
            fileInfo.setTotalBytes(totalBytes);
        }
        if (cursor != null) {
            cursor.close();
        }
        return fileInfo;
    }

    @Override
    public boolean isExists(String url) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME, null, "url = ?", new String[]{url}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }
}
