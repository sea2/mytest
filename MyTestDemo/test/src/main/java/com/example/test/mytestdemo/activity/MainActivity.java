package com.example.test.mytestdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.adapter.FragmentAdapter;
import com.example.test.mytestdemo.app.BaseActivity;
import com.example.test.mytestdemo.event.BaseEvent;
import com.example.test.mytestdemo.fragment.FourFragment;
import com.example.test.mytestdemo.fragment.OneFragment;
import com.example.test.mytestdemo.fragment.ThreeFragment;
import com.example.test.mytestdemo.fragment.TwoFragment;
import com.example.test.mytestdemo.ui.MyDialog;
import com.example.test.mytestdemo.util.DensityUtils;
import com.example.test.mytestdemo.util.PermissionsUtil;
import com.example.test.mytestdemo.utils.FileUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private final String ONE_FRAGMENT_KEY = "one_fragment__key";
    private final String TWO_FRAGMENT_KEY = "two__fragment_key";
    private final String THREE_FRAGMENT_KEY = "three__fragment_key";
    private final String FOUR_FRAGMENT_KEY = "four__fragment_key";
    private static int currIndex = 0;
    private RadioGroup group;
    //fragment
    private Fragment mFragmentOne, mFragmentTwo, mFragmentThree, mFragmentFour;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> fragmentTags;
    private FragmentAdapter homePageFragmentAdapter;
    private ViewPager viewpager_container;
    private final int READ_PHONE_STATE_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i("size-----" + DensityUtils.getEndSize(20, 720));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);
        }

        initView();
        initData();


        //文件路径测试
        FileUtils.getApplicationDirectories(this);
        FileUtils.getEnvironmentDirectories();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
        outState.putStringArrayList(FRAGMENT_TAGS, fragmentTags);


    }



    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        viewpager_container = (ViewPager) findViewById(R.id.viewpager_container);
        if (mFragmentOne == null) mFragmentOne = new OneFragment();
        if (mFragmentTwo == null) mFragmentTwo = new TwoFragment();
        if (mFragmentThree == null) mFragmentThree = new ThreeFragment();
        if (mFragmentFour == null) mFragmentFour = new FourFragment();
        mFragmentList.add(mFragmentOne);
        mFragmentList.add(mFragmentTwo);
        mFragmentList.add(mFragmentThree);
        mFragmentList.add(mFragmentFour);
        homePageFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager_container.setOffscreenPageLimit(2);
        viewpager_container.setAdapter(homePageFragmentAdapter);


        viewpager_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        group.check(R.id.foot_bar_home);
                        break;
                    case 1:
                        group.check(R.id.foot_bar_im);
                        break;
                    case 2:
                        group.check(R.id.foot_bar_interest);
                        break;
                    case 3:
                        group.check(R.id.main_footbar_user);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        viewpager_container.setCurrentItem(0, false);
                        break;
                    case R.id.foot_bar_im:
                        viewpager_container.setCurrentItem(1, false);
                        break;
                    case R.id.foot_bar_interest:
                        viewpager_container.setCurrentItem(2, false);
                        break;
                    case R.id.main_footbar_user:
                        viewpager_container.setCurrentItem(3, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void initData() {

        viewpager_container.setCurrentItem(currIndex);
        fragmentTags = new ArrayList<>(Arrays.asList("OneFragment", "TwoFragment", "ThreeFragment", "FourFragment"));

        appPermissionApply();
    }



    /**
     * 权限申请
     */
    private void appPermissionApply() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] strPermission = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            String[] strNeed = PermissionsUtil.getNeedApplyPermissions(strPermission);
            if (strNeed != null && strNeed.length > 0) {
                ActivityCompat.requestPermissions(this, strNeed, READ_PHONE_STATE_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PHONE_STATE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.i("Main+onRequestPermissionsResult PERMISSION_GRANTED"+grantResults.length);
            } else {
                Logger.i("Main+onRequestPermissionsResult"+grantResults.length);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);  //退到后台
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Subscribe
    public void onEvent(BaseEvent event) {
        MyDialog dialog = new MyDialog(MainActivity.this);
        dialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("Main+onNewIntent");
    }
}
