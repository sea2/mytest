package com.xcm91.relation.iosdialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.xcm91.relation.iosdialog.widget.ActionSheetDialog;
import com.xcm91.relation.iosdialog.widget.AlertDialog;
import com.xcm91.relation.iosdialog.widget.CommonDialog;
import com.xcm91.relation.iosdialog.widget.CustomActionSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class IosDialogActivity extends Activity implements OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                new ActionSheetDialog(IosDialogActivity.this).builder().setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？").setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("清空消息列表", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).show();
                break;
            case R.id.btn2:
                new ActionSheetDialog(IosDialogActivity.this).builder().setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("发送给好友", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem("转载到空间相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem("上传到群相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem("保存到手机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem("收藏", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem("查看聊天图片", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).show();
                break;
            case R.id.btn3:
                new ActionSheetDialog(IosDialogActivity.this).builder().setTitle("请选择操作").setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("条目一", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目二", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目三", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目四", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目五", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目六", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目七", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目八", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目九", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).addSheetItem("条目十", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Toast.makeText(IosDialogActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            case R.id.btn4:
                new AlertDialog(IosDialogActivity.this).builder().setTitle("退出当前账号").setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？").setPositiveButton("确认退出", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.btn5:
             /*   new AlertDialog(IosDialogActivity.this).builder().setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒").setNegativeButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();*/

                CommonDialog mCommonDialog = new CommonDialog(IosDialogActivity.this);
                mCommonDialog.setTitle("Material Design Dialog");
                mCommonDialog.setMessage("这是dialog的样式测试你鼻炎知道，这是对的事情这是dialog的样式测试你鼻炎知道，这是对的事情");
                mCommonDialog.setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mCommonDialog.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mCommonDialog.show();
                break;
            case R.id.btn6:

                 List<InvestDetialModel.OperationalItem> sheetItemList=new ArrayList<>();
                sheetItemList.add(new InvestDetialModel.OperationalItem("1","2","1122"));
                sheetItemList.add(new InvestDetialModel.OperationalItem("1","2","1122"));
                new CustomActionSheetDialog(IosDialogActivity.this).builder().setList(sheetItemList).show();

                break;
            default:
                break;
        }



    }

    public String TAG = this.getClass().getSimpleName();
    @Override
    protected void onStart() {
        super.onStart();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }


    @Override
    protected void onStop() {
        super.onStop();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
        Log.i(TAG, className + "-" + methodName + "-" + lineNumber);
    }
}
