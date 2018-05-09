package scut.carson_ho.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**参考：
 * https://www.jianshu.com/p/ea8bc4aaf057
 */
public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_provider);

        /**
         * 对user表进行操作
         */

        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/" + DataBaseManager.TABLE_USER_NAME);

        // 插入表中数据
        ContentValues values = new ContentValues();

        values.put("name", "version");


        // 获取ContentResolver
        ContentResolver resolver = getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        for (int i = 0; i < 10; i++)
            resolver.insert(uri_user, values);


        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"id", "name"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                System.out.println("query book:" + cursor.getInt(0) + " " + cursor.getString(1));
                // 将表中数据全部输出
            }
            // 关闭游标
            cursor.close();
        }


    }
}
