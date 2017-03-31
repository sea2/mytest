package com.example.test.mytestdemo.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mytestdemo.app.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**  权限申请，通讯录，短信，通讯记录
 * Created by lhy on 2017/3/29.
 */

public class PermissionsNewActivity extends BaseActivity {


    private static final int REQUEST_READ_LOG = 1;

    @Override
    protected void onCreate(Bundle savedIstanceState) {
        super.onCreate(savedIstanceState);
        TextView tv = new TextView(this);
        setContentView(tv);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG}, REQUEST_READ_LOG);
            } else openSetting();
            return;
        }


        ContentResolver contentResolver = getContentResolver(); //创建ContentProvider客服端
        //获取通讯录
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                //获取通信录姓名
                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                //获取该条目录下的所有电话
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                StringBuilder number = new StringBuilder();
                if (phone != null) {
                    while (phone.moveToNext()) {
                        number.append(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))).append(" ");
                    }
                    phone.close();
                }

                String content = name + ":" + number;
                Log.e("Content", content);
            }
            cursor.close();
        }

        Cursor cursorSms = contentResolver.query(Uri.parse("content://sms/"), null, null, null, null);
        if (cursorSms != null) {
            while (cursorSms.moveToNext()) {
                String number = cursorSms.getString(cursorSms.getColumnIndex("address"));//手机号
                String body = cursorSms.getString(cursorSms.getColumnIndex("body"));//短信内容

                Log.e("Content", number + body);
            }
            cursorSms.close();
        }


        Cursor cursorCallLog = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursorCallLog != null) {
            while (cursorCallLog.moveToNext()) {
                //号码
                String number = cursorCallLog.getString(cursorCallLog.getColumnIndex(CallLog.Calls.NUMBER));
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursorCallLog.getString(cursorCallLog.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";//应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursorCallLog.getString(cursorCallLog.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                //呼叫时间
                String time = sfd.format(date);
                //联系人
                String name = cursorCallLog.getString(cursorCallLog.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                //通话时间,单位:s
                String duration = cursorCallLog.getString(cursorCallLog.getColumnIndexOrThrow(CallLog.Calls.DURATION));

                String content = time + "  号码:" + number + "  " + type + "  通话时间（s）： " + duration;
                Log.e("xx", content);
            }
            cursorCallLog.close();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_LOG:
                List list = new ArrayList();


                for (int intInfo : grantResults) {
                    if (intInfo == PackageManager.PERMISSION_GRANTED) {
                        list.add(permissions[intInfo]);
                    }
                }
                String[] arr = new String[0];
                if (list.size() > 0) arr = (String[]) list.toArray(new String[list.size()]);

                Toast.makeText(PermissionsNewActivity.this, "权限已获取" + arr.length, Toast.LENGTH_SHORT).show();


                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * 应用设置
     */
    public void openSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}
