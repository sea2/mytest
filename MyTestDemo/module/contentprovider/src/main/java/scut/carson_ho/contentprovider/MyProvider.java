package scut.carson_ho.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Carson_Hoon 17/6/6.
 * 主要Binder机制+sqlLite实现，数据源可以使sqlLite,文件，xml
 */
public class MyProvider extends ContentProvider {

    private Context mContext;
    DataBaseHelper mDbHelper = null;
    SQLiteDatabase db = null;

    // 设置ContentProvider的唯一标识
    public static final String AUTOHORITY = "cn.scu.myprovider";

    public static final int User_Code = 1;

    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    static {
        // 初始化
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 若URI资源路径 = content://cn.scu.myprovider/tb_user ，则返回注册码User_Code
        mMatcher.addURI(AUTOHORITY, DataBaseManager.TABLE_USER_NAME, User_Code);
    }

    // 以下是ContentProvider的6个方法

    /**
     * 初始化ContentProvider
     */
    @Override
    public boolean onCreate() {

        mContext = getContext();
        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        DataBaseManager dbManager = new DataBaseManager(getContext());
        // 对于操作 = “增、删、改（更新）”，需获得 可"读 / 写"的权限：getWritableDatabase()
        // 对于操作 = “查询”，需获得 可"读 "的权限getReadableDatabase()
        if (dbManager.getDataBaseHelper() != null) {
            db = dbManager.getDataBaseHelper().getWritableDatabase();

            // 初始化两个表的数据(先清空两个表,再各加入一个记录)
            db.execSQL("delete from tb_user");
            db.execSQL("insert into tb_user values(1,'Carson');");
            db.execSQL("insert into tb_user values(2,'Kobe');");
        }
        return true;
    }

    /**
     * 添加数据
     */

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面


        // 向该表添加数据
        db.insert(DataBaseManager.TABLE_USER_NAME, null, values);

        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        return uri;
    }

    /**
     * 查询数据
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        // 查询数据
        return db.query(DataBaseManager.TABLE_USER_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    /**
     * 更新数据
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // 由于不展示,此处不作展开
        return db.update(DataBaseManager.TABLE_USER_NAME,values,selection,selectionArgs);
    }

    /**
     * 删除数据
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 由于不展示,此处不作展开
        return db.delete(DataBaseManager.TABLE_USER_NAME,selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {

        // 由于不展示,此处不作展开
        return null;
    }


}

