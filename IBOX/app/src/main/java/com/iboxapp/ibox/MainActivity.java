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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iboxapp.ibox.adapter.MyPagerAdapter;
import com.iboxapp.ibox.ui.MessageActivity;
import com.iboxapp.ibox.ui.SettingAboutActivity;
import com.iboxapp.ibox.ui.SettingCollectActivity;
import com.iboxapp.ibox.ui.SettingPhotosActivity;
import com.iboxapp.ibox.ui.SettingSystemActivity;
import com.iboxapp.ibox.ui.SettingTradeActivity;
import com.iboxapp.ibox.ui.SettingUserInfoActivity;

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
        /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        /*mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_message:
                        Toast.makeText(MainActivity.this, "toolbar_message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.toolbar_search:
                        Toast.makeText(MainActivity.this, "toolbar_search", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/

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
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动

        //设置NavigationView点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        //profile
        /*setUpProfile();*/


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
                            case R.id.navigation_item_userinfo:
                                switchToUserInfo();
                                break;
                            case R.id.navigation_item_photos:
                                switchToPhotos();
                                break;
                            case R.id.navigation_item_collection:
                                switchToCollection();
                                break;
                            case R.id.navigation_item_trade:
                                switchToTrade();
                                break;
                            case R.id.navigation_item_sys_set:
                                switchToSystemSet();
                                break;
                            case R.id.navigation_item_about:
                                switchToAbout();
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void switchToUserInfo() {
        Intent intent = new Intent(this, SettingUserInfoActivity.class);
        this.startActivity(intent);

    }

    private void switchToPhotos() {
        Intent intent = new Intent(this, SettingPhotosActivity.class);
        this.startActivity(intent);
    }

    private void switchToCollection() {
        Intent intent = new Intent(this, SettingCollectActivity.class);
        intent.putExtra("UserInfo", R.id.navigation_item_collection);
        this.startActivity(intent);
    }

    private void switchToTrade() {
        Intent intent = new Intent(this, SettingTradeActivity.class);
        this.startActivity(intent);
    }

    private void switchToSystemSet() {
        Intent intent = new Intent(this, SettingSystemActivity.class);
        this.startActivity(intent);
    }

    private void switchToAbout() {
        Intent intent = new Intent(this, SettingAboutActivity.class);
        this.startActivity(intent);
    }

    /*private void setUpProfile() {
        findViewById(R.id.profile_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToUserInfo();
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(1).setChecked(true);
            }
        });
    }*/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.toolbar_message:
                Toast.makeText(MainActivity.this, "toolbar_message", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MessageActivity.class);
                this.startActivity(intent);
                break;
            case R.id.toolbar_search:
                Toast.makeText(MainActivity.this, "toolbar_search", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
