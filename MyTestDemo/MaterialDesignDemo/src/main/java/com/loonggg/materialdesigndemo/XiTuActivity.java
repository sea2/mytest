package com.loonggg.materialdesigndemo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaeger.library.StatusBarUtil;
import com.loonggg.materialdesigndemo.adapter.ViewPagerAdapter;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class XiTuActivity extends BaseActivity {
    private LinearLayout head_layout;
    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout root_layout;
    private ImageView tv_mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xi_tu);
        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        head_layout = (LinearLayout) findViewById(R.id.head_layout);
        tv_mb = (ImageView) findViewById(R.id.tv_mb);
        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("涩郎");
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });
        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));
        //tablayout和viewpager建立联系为什么不用下面这个方法呢？自己去研究一下，可能收获更多
        //toolbar_tab.setupWithViewPager(main_vp_container);
        loadBlurAndSetStatusBar();

        ImageView head_iv = (ImageView) findViewById(R.id.head_iv);
        Glide.with(this).load(R.mipmap.three).bitmapTransform(new RoundedCornersTransformation(this,
                90, 0)).into(head_iv);
    }

    /**
     * 设置毛玻璃效果和沉浸状态栏
     */
    private void loadBlurAndSetStatusBar() {
        StatusBarUtil.setTranslucent(XiTuActivity.this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        Glide.with(this).load(R.mipmap.three).bitmapTransform(new BlurTransformation(this, 80))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        head_layout.setBackground(resource);
                        root_layout.setBackground(resource);
                    }
                });


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.three);
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                toolbar_tab.setBackgroundColor(vibrant.getRgb());
                toolbar_tab.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                int[] colors = new int[2];
                colors[0] = colorBurn(vibrant.getRgb());
                colors[1] = colorBurn2(vibrant.getRgb());
                GradientDrawable drawable;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    drawable = new GradientDrawable();
                    drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    drawable.setColors(colors);
                } else {
                    drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
                }
                //drawable.setCornerRadii(radius);
                drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                tv_mb.setImageDrawable(drawable);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_xitu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.webview:
                msg += "博客跳转";
                break;
            case R.id.weibo:
                msg += "微博跳转";
                break;
            case R.id.action_settings:
                msg += "设置";
                break;
        }
        if (!msg.equals("")) {
            Toast.makeText(XiTuActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 颜色加深处理,透明度变化处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
//        red = (int) Math.floor(red * (1 - 0.1));
//        green = (int) Math.floor(green * (1 - 0.1));
//        blue = (int) Math.floor(blue * (1 - 0.1));
        alpha = 30;
        return Color.argb(alpha, red, green, blue);
    }

    //0表示透明，255 表示不透明
    private int colorBurn2(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
//        red = (int) Math.floor(red * (1 - 0.1));
//        green = (int) Math.floor(green * (1 - 0.1));
//        blue = (int) Math.floor(blue * (1 - 0.1));
        alpha = 200;
        return Color.argb(alpha, red, green, blue);
    }

}
