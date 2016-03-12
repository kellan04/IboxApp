package com.iboxapp.ibox;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iboxapp.ibox.adapter.MyPagerAdapter;
import com.iboxapp.ibox.ui.DrawerActivity;

public class MainActivity extends AppCompatActivity implements BackHandledFragment.BackHandledInterface{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private BackHandledFragment selectedFragment;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_main_title));
        setSupportActionBar(mToolbar);

        //设置ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        //给TabLayout增加Tab, 并关联ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Ibox"));
        tabLayout.addTab(tabLayout.newTab().setText("Ishow"));
        tabLayout.addTab(tabLayout.newTab().setText("Ibuy"));
        tabLayout.setupWithViewPager(mViewPager);

        //设置DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置NavigationView点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        //profile
        setUpProfile();


    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(IboxFragment.newInstance("this is first fragment"),"Ibox");
        adapter.addFragment(IshowFragment.newInstance("this is second fragment"),"Ishow");
        adapter.addFragment(IbuyFragment.newInstance("this is third fragment"),"Ibuy");
        mViewPager.setAdapter(adapter);
    }


    //设置NavigationView点击事件
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_collection:
                                switchToCollection();
                                break;
//                            case R.id.navigation_item_blog:
//                                switchToBlog();
//                                break;
//                            case R.id.navigation_item_about:
//                                switchToAbout();
//                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void switchToUserInfo() {
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra("UserInfo", R.id.navigation_item_collection);
        this.startActivity(intent);

    }

    private void switchToCollection() {
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra("UserInfo", R.id.navigation_item_collection);
        this.startActivity(intent);
    }

    private void setUpProfile() {
        findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToUserInfo();
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(1).setChecked(true);
            }
        });
    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }


    @Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                doExitApp();
            }
        }

    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
