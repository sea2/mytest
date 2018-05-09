package scut.carson_ho.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lhy on 2018/5/7.
 */
public class DataBaseManager {


    // 表名
    public static final String TABLE_USER_NAME = "tb_user";
    public SQLiteDatabase db = null;
    DataBaseHelper dataBaseHelper = null;

    DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context, TABLE_USER_NAME);
        if (db == null)
            db = dataBaseHelper.getWritableDatabase();
    }


    public DataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }


    /**
     * 插入
     *
     * @param values
     * @param isClose
     * @return 例：
     * ContentValues values = new ContentValues();
     * values.put("id", 1);
     * values.put("name", "carson");
     */
    public void insert(ContentValues values, boolean isClose) {
        /**
         *  操作1：插入数据 = insert()
         */
       /* // a. 创建ContentValues对象
        ContentValues values = new ContentValues();
        // b. 向该对象中插入键值对
        values.put("id", 1);
        values.put("name", "carson");*/

        // c. 插入数据到数据库当中：insert()
        if (null != db && values != null)
            db.insert(TABLE_USER_NAME, null, values);
        if (isClose) closeDataBase();
    }


    /**
     * 更新
     *
     * @param values
     * @param whereClause
     * @param whereArgs
     * @param isClose     db.update("tb_user","id=?" , new String[]{"1","5"});表示更新tb_user表里面id=1和id=5的数据
     *                    db.update("tb_user","id=" + id, null);表示更新tb_user表里面id=1的数据，这种情况只能更新一条数据，如果只更新一条数据，这种拼接的方式更便捷。
     */
    public void update(ContentValues values, String whereClause, String[] whereArgs, boolean isClose) {
        /**
         *  操作1：插入数据 = insert()
         */
       /* // a. 创建ContentValues对象
        ContentValues values = new ContentValues();
        // b. 向该对象中插入键值对
        values.put("id", 1);
        values.put("name", "carson");*/

        // c. 插入数据到数据库当中：insert()
        if (null != db && values != null)
            db.update(TABLE_USER_NAME, values, whereClause, whereArgs);
        if (isClose) closeDataBase();
    }


    /**
     * @param values
     * @param whereClause
     * @param whereArgs
     * @param isClose     * db.delete("tb_user","id=?" , new String[]{"1","5"});表示删除tb_user表里面id=1和id=5的数据
     *                    db.delete("tb_user","id=" + id, null);表示删除tb_user表里面id=1的数据，这种情况只能更新一条数据，如果只删除一条数据，这种拼接的方式更便捷。
     */
    public void delete(ContentValues values, String whereClause, String[] whereArgs, boolean isClose) {
        if (null != db && values != null)
            db.delete(TABLE_USER_NAME, whereClause, whereArgs);
        if (isClose) closeDataBase();
    }


    /**
     * 查询
     * <p>
     * // 方法说明
     * // 参数说明
     * // table：要操作的表
     * // columns：查询的列所有名称集
     * // selection：WHERE之后的条件语句，可以使用占位符
     * // groupBy：指定分组的列名
     * // having指定分组条件，配合groupBy使用
     * // orderBy指定排序的列名
     * // limit指定分页参数
     * // distinct可以指定“true”或“false”表示要不要过滤重复值
     * db.query(String table, String[]columns, String selection, String[]selectionArgs, String groupBy, String having, String orderBy);
     * db.query(String table, String[]columns, String selection, String[]selectionArgs, String groupBy, String having, String
     * orderBy, String limit);
     * db.query(String distinct, String table, String[]columns, String selection, String[]selectionArgs, String groupBy, String
     * having, String orderBy, String limit);
     * <p>
     * cursor        1 c.move(int offset); //以当前位置为参考,移动到指定行
     * 2 c.moveToFirst();    //移动到第一行
     * 3 c.moveToLast();     //移动到最后一行
     * 4 c.moveToPosition(int position); //移动到指定行
     * 5 c.moveToPrevious(); //移动到前一行
     * 6 c.moveToNext();     //移动到下一行
     * 7 c.isFirst();        //是否指向第一条
     * 8 c.isLast();     //是否指向最后一条
     * 9 c.isBeforeFirst();  //是否指向第一条之前
     * 10 c.isAfterLast();    //是否指向最后一条之后
     * 11 c.isNull(int columnIndex);  //指定列是否为空(列基数为0)
     * 12 c.isClosed();       //游标是否已关闭
     * 13 c.getCount();       //总数据项数
     * 14 c.getPosition();    //返回当前游标所指向的行数
     * 15 c.getColumnIndex(String columnName);//返回某列名对应的列索引值，如果不存在返回-1
     * 16 c.getString(int columnIndex);   //返回当前行指定列的值
     * 17 c·getColumnIndexOrThrow(String columnName)——从零开始返回指定列名称，如果不存在将抛出IllegalArgumentException 异常。
     * 18 c.close()——关闭游标，释放资源
     */
    public void query(String[] columns, String selection, String[] selectionArgs, boolean isClose) {
        if (null != db) {
            Cursor cursor = db.query(TABLE_USER_NAME, columns, selection, selectionArgs, null, null, null);
            //示例  Cursor cursor = db.query(TABLE_USER_NAME, new String[]{"id", "name"}, "id=?", new String[]{"1"}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    //遍历Cursor对象，取出数据并打印
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    Log.i("MainActivity", "book name is" + name);
                    Log.i("MainActivity", "book price is" + id);
                }
            }

            if (cursor != null)
                cursor.close();
        }
        if (isClose) closeDataBase();
    }


    /**
     * 执行sql
     *
     * @param sql
     * @param isClose 1，数据类型有：
     *                1).  NULL，值是NULL
     *                2).  INTEGER，值是有符号整形，根据值的大小以1,2,3,4,6或8字节存放
     *                3).  REAL，值是浮点型值，以8字节IEEE浮点数存放
     *                4).  TEXT，值是文本字符串，使用数据库编码（UTF-8，UTF-16BE或者UTF-16LE）存放
     *                5).  BLOB，只是一个数据块，完全按照输入存放（即没有准换）
     *                实际也支持我们觉的Integer,varchar,time，double等类型。
     *                在使用数据库时，我们通常要使用到的sql语句有：
     *                1，创建表
     *                create table 表名（字段名称 数据类型 约束，字段名称 数据类型 约束......）
     *                例如创建一个学生表student：
     *                create table student(_id Integer primary key,name varchar(10) not null,age Integer,scroe float)
     *                2，删除表
     *                drop table 表名
     *                例如删除刚刚创建的student表：
     *                drop table student
     *                3，表中添加（插入数据）
     *                insert into 表名（字段1，字段2...）values (值1，值2...)
     *                或者：
     *                insert into 表名 values(值1，值2...值n) 这种方式添加数据要求后面的值与数据表中字段一一对应且不能空缺字段
     *                例如给student表中添加一条数据：
     *                insert into student (_id,name,age,scroe) values(1,'张三',13,85.6)
     *                4,表中删除数据
     *                delete from 表名 where 删除的条件
     *                比如删除student表中姓名为张三的数据：
     *                delete from student where name="张三"
     *                5,修改表中的数据
     *                update 表名 set 字段=新值 where 修改的条件
     *                例如，修改student表中张三的成绩为90分
     *                update student set score=90 where _id=1
     *                6,查询表中的数据
     *                1，查找全部数据
     *                select * from 表名
     *                例如查的student表中所有数据：
     *                select * from student
     *                2，查找指定数据：
     *                select 字段名 from 表名 where 查询条件 group by 分组字段 having 筛选条件 order by 排序字段
     *                例如查询学生表student中姓名为张三的成绩：
     *                select scroe from student where name='张三'
     */
    public void execSQL(String sql, boolean isClose) {
        if (null != db && !TextUtils.isEmpty(sql))
            db.execSQL(sql);
        if (isClose) closeDataBase();
    }


    /**
     * 关闭 db
     */
    public void closeDataBase() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
    }
}
