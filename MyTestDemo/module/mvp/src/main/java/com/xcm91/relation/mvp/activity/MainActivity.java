package com.xcm91.relation.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xcm91.relation.mvp.ILoginPresenter;
import com.xcm91.relation.mvp.LoginPresenter;
import com.xcm91.relation.mvp.ILoginView;
import com.xcm91.relation.mvp.R;


/**
 * 注意mvp使用的内存泄露
 */
public class MainActivity extends Activity implements ILoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        presenter = new LoginPresenter(this);

        getdat();


        for (int i = 0; i < 10; i++) {
            Log.i("tag", "--" + i);
        }
    }

    public void getdat() {
        for (int i = 0; i < 10; i++) {
            Log.i("tag", "--" + i);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        presenter = null;
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
// TODO       startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
    }


}
