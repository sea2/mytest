package com.example.test.mytestdemo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.test.IAdditionService;
import com.example.test.mytestdemo.utils.StringUtils;
import com.example.test.mytestdemo.utils.ToastUtils;

import static com.example.test.mytestdemo.R.id.tv_name;

public class HelloSumAidlActivity extends AppCompatActivity {
    IAdditionService service;
    AdditionServiceConnection connection;
    TextView result;
    EditText value1;
    EditText value2;
    private TextView tvname;
    private TextView TextView01;
    private Button buttonCalc;
    String name = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_sum_aidl);
        this.buttonCalc = (Button) findViewById(R.id.buttonCalc);
        this.TextView01 = (TextView) findViewById(R.id.TextView01);
        this.tvname = (TextView) findViewById(tv_name);
        initService();

        Button buttonCalc = (Button) findViewById(R.id.buttonCalc);
        result = (TextView) findViewById(R.id.result);
        value1 = (EditText) findViewById(R.id.value1);
        value2 = (EditText) findViewById(R.id.value2);
        tvname.setText(name + "买豆");
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = false;
                String pwd = value1.getText().toString();
                String moneyStr = value2.getText().toString();
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtils.showShortToast("请输入密码");
                    return;
                }
                if (StringUtils.isEmpty(moneyStr)) {
                    ToastUtils.showShortToast("请输入购买金额");
                    return;
                }

                int money = Integer.parseInt(moneyStr);
                try {
                    res = service.callPay(name, pwd, money);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                result.setText(res ? "买" + money + "豆" : "买豆失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    class AdditionServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            // 获取中间人对象
            service = IAdditionService.Stub.asInterface((IBinder) boundService);
            Toast.makeText(HelloSumAidlActivity.this, "找到卖豆人", Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(HelloSumAidlActivity.this, "未找到卖豆人", Toast.LENGTH_LONG).show();
        }
    }


    private void initService() {
        connection = new AdditionServiceConnection();
        Intent i = new Intent(HelloSumAidlActivity.this, AdditionService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }


    private void releaseService() {
        unbindService(connection);
        connection = null;
    }
}

