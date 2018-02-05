package com.example.test.mytestdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.mytestdemo.app.BaseActivity;

/**
 * 权限动态申请test
 */
public class PermissionsActivity extends BaseActivity {

    private android.widget.Button btnshen;
    final int REQUEST_CODE_ASK_CALL_PHONE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnshen = new Button(this);
        btnshen.setText("申请权限");
        setContentView(btnshen);

        //申请权限
        btnshen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(PermissionsActivity.this, Manifest.permission.CALL_PHONE);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PermissionsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_CALL_PHONE);
                        return;
                    } else {
                        //权限已有
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PermissionsActivity.this, "权限已获取", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionsActivity.this, "权限已禁止", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
