package com.example.test.mytestdemo.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.test.mytestdemo.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private RadioGroup group;
    //fragment
    private Fragment mFragmentOne, mFragmentTwo, mFragmentThree, mFragmentFour;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> fragmentTags;
    private FragmentAdapter homePageFragmentAdapter;
    private ViewPager viewpager_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            initView();
            initData();
        } else {
            initFromSavedInstantsState(savedInstanceState);
        }

        FileUtils.getApplicationDirectories(this);
        FileUtils.getEnvironmentDirectories();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
        outState.putStringArrayList(FRAGMENT_TAGS, fragmentTags);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initFromSavedInstantsState(savedInstanceState);
    }

    private void initFromSavedInstantsState(Bundle savedInstanceState) {
        currIndex = savedInstanceState.getInt(CURR_INDEX);
        fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);

    }

    private void initData() {
        currIndex = 0;
        fragmentTags = new ArrayList<>(Arrays.asList("OneFragment", "TwoFragment", "ThreeFragment", "FourFragment"));

        mFragmentOne = new OneFragment();
        mFragmentTwo = new TwoFragment();
        mFragmentThree = new ThreeFragment();
        mFragmentFour = new FourFragment();
        mFragmentList.add(mFragmentOne);
        mFragmentList.add(mFragmentTwo);
        mFragmentList.add(mFragmentThree);
        mFragmentList.add(mFragmentFour);


        homePageFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager_container.setOffscreenPageLimit(3);
        viewpager_container.setAdapter(homePageFragmentAdapter);
    }

    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        viewpager_container = (ViewPager) findViewById(R.id.viewpager_container);
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
        Intent it = new Intent();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
