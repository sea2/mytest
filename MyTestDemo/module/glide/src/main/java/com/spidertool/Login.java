package com.spidertool;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.xcm91.relation.glide.R;

public class Login extends AppCompatActivity {

    private EditText etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.etpassword = (EditText) findViewById(R.id.et_password);

        etpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = etpassword.getText().toString();
                if (str.contains("2222")) {
                    Intent it = new Intent(Login.this, MainAct.class);
                    startActivity(it);
                    finish();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        applyPermissions();
    }


    private void applyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] strPermission = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            String[] strNeed = PermissionsUtil.getNeedApplyPermissions(strPermission);
            if (strNeed != null && strNeed.length > 0) {
                ActivityCompat.requestPermissions(this, strNeed, 100);
            }
        }
    }
}




/*        模块页码
<nav class="navigation pagination"role="navigation">
<h2 class="screen-reader-text">Posts navigation</h2>
<div class="nav-links"><span aria-current='page'class='page-numbers current'><span class="meta-nav screen-reader-text"></span>1<span class="meta-nav screen-reader-text"></span></span>
<a class='page-numbers'href='http://www.mzitu.com/tag/baoru/page/2/'><span class="meta-nav screen-reader-text"></span>2<span class="meta-nav screen-reader-text"></span></a>
<a class='page-numbers'href='http://www.mzitu.com/tag/baoru/page/3/'><span class="meta-nav screen-reader-text"></span>3<span class="meta-nav screen-reader-text"></span></a>
<a class='page-numbers'href='http://www.mzitu.com/tag/baoru/page/4/'><span class="meta-nav screen-reader-text"></span>4<span class="meta-nav screen-reader-text"></span></a>
<span class="page-numbers dots">&hellip;</span>
<a class='page-numbers'href='http://www.mzitu.com/tag/baoru/page/129/'><span class="meta-nav screen-reader-text"></span>129<span class="meta-nav screen-reader-text"></span></a>
<a class="next page-numbers"href="http://www.mzitu.com/tag/baoru/page/2/">下一页&raquo;</a></div>
</nav>

        个人页码
<div class="pagenavi">
<a href='http://www.mzitu.com/155855'><span>&laquo;上一组</span></a><span>1</span>
<a href='http://www.mzitu.com/155507/2'><span>2</span></a>
<a href='http://www.mzitu.com/155507/3'><span>3</span></a>
<a href='http://www.mzitu.com/155507/4'><span>4</span></a>
<span class='dots'>…</span><a href='http://www.mzitu.com/155507/60'><span>60</span></a>
<a href='http://www.mzitu.com/155507/2'><span>下一页&raquo;</span></a></div>*/

