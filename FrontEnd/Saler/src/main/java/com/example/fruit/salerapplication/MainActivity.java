package com.example.fruit.salerapplication;

/**
 * Created by fruit on 2017/7/8.
 */
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button tabFruit, tabOrder, tabSetting, btnScan;
    private ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPage();
        bindView();
    }

    public void initView () {
        tabFruit = (Button)findViewById(R.id.btn_tab_fruit);
        tabOrder = (Button)findViewById(R.id.btn_tab_order);
        tabSetting = (Button)findViewById(R.id.btn_tab_setting);
        tabFruit.setSelected(true);
        btnScan = (Button)findViewById(R.id.btn_header_right);

        findViewById(R.id.btn_header_left).setVisibility(View.INVISIBLE);
    }

    public void initViewPage () {
        ((TextView) findViewById(R.id.text_title)).setText("修鲜水果管家");
        ((Button) findViewById(R.id.btn_header_right)).setText("入库");
        viewPager = (ViewPager)findViewById(R.id.fragment_container);
        fragments = new ArrayList<>();
        fragments.add(new FragmentFruit());
        fragments.add(new FragmentOrder());
        fragments.add(new FragmentSetting());
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new MainPageChangeListener());
    }

    public void bindView () {
        tabFruit.setOnClickListener(new MainClickListener(0));
        tabOrder.setOnClickListener(new MainClickListener(1));
        tabSetting.setOnClickListener(new MainClickListener(2));
        btnScan.setOnClickListener(new MainClickListener(3));
    }

    public void clearSelect () {
        tabFruit.setSelected(false);
        tabOrder.setSelected(false);
        tabSetting.setSelected(false);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return (fragments != null && fragments.size() != 0) ? fragments.get(position) : null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return (fragments == null) ? 0 : fragments.size();
        }
    }

    private class MainClickListener implements View.OnClickListener {
        private int index = 0;

        public MainClickListener (int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            switch (index) {
                case 0:
                    clearSelect();
                    tabFruit.setSelected(true);
                    viewPager.setCurrentItem(index);
                    break;
                case 1:
                    clearSelect();
                    tabOrder.setSelected(true);
                    viewPager.setCurrentItem(index);
                    break;
                case 2:
                    clearSelect();
                    tabSetting.setSelected(true);
                    viewPager.setCurrentItem(index);
                    break;
                case 3:
                    Intent intent = new Intent(MainActivity.this, Scan.class);
                    MainActivity.this.startActivity(intent);
                    break;
            }
        }
    }

    private class MainPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    clearSelect();
                    tabFruit.setSelected(true);
                    break;
                case 1:
                    clearSelect();
                    tabOrder.setSelected(true);
                    break;
                case 2:
                    clearSelect();
                    tabSetting.setSelected(true);
                    break;
            }
        }
    }
}
