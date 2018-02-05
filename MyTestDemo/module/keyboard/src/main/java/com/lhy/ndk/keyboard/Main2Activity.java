package com.lhy.ndk.keyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhy.ndk.keyboard.custom.Keyboard1Activity;
import com.lhy.ndk.keyboardlibrary.KeyBoardDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.btn_custom_keyboard)
    Button btnCustomKeyboard;
    @BindView(R.id.btn_list_test)
    Button btnListTest;
    @BindView(R.id.iv_top_bar)
    ImageView ivTopBar;
    @BindView(R.id.video_player)
    ImageView videoPlayer;
    @BindView(R.id.tv_title_info)
    TextView tvTitleInfo;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.sb_send)
    Button sbSend;
    @BindView(R.id.iv_video_download)
    ImageView ivVideoDownload;
    @BindView(R.id.ll_operate)
    LinearLayout llOperate;
    @BindView(R.id.et_custom_keyboard_safe)
    EditText etCustomKeyboardSafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);



        KeyBoardDialogUtils keyBoardDialogUtils = new KeyBoardDialogUtils(Main2Activity.this);
        keyBoardDialogUtils.show(etCustomKeyboardSafe);

        findViewById(R.id.btn_list_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Main2Activity.this, MainListActivity.class);
                startActivity(it);
            }
        });
        btnCustomKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Main2Activity.this, Keyboard1Activity.class);
                startActivity(it);
            }
        });
        etCustomKeyboardSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardDialogUtils keyBoardDialogUtils = new KeyBoardDialogUtils(Main2Activity.this);
                keyBoardDialogUtils.show(etCustomKeyboardSafe);
            }
        });
    }
}
