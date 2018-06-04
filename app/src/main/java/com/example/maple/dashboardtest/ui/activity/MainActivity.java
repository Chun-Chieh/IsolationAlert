package com.example.maple.dashboardtest.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.debug.DebugSurveyActivity;
import com.example.maple.dashboardtest.notification.NotificationUtility;
import com.example.maple.dashboardtest.service.NotificationForegroundService;
import com.example.maple.dashboardtest.ui.activity.survey.SurveyMainActivity;
import com.example.maple.dashboardtest.ui.adapter.MainPagerAdapter;

import static com.example.maple.dashboardtest.service.NotificationForegroundService.ACTION_NOTIFICATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected boolean isHomeAsUp = false;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private ViewPager mViewPager;
    private Intent intent;

    public static String PACKAGE_NAME;
    private boolean mBounded;
    private NotificationForegroundService mService;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity", "Service is disconnected");
            mBounded = false;
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity", "Service is connected");
            mBounded = true;
            NotificationForegroundService.LocalBinder mLocalBinder = (NotificationForegroundService.LocalBinder) service;
            mService = mLocalBinder.getService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        startForegroundService();

        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, NotificationForegroundService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_statistics:
                intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_recording_training:
                intent = new Intent(this, TrainingActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_survey:
                intent = new Intent(this, SurveyMainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_stop_service:
                Intent intent = new Intent(this, NotificationForegroundService.class);
                intent.setAction(NotificationForegroundService.ACTION_STOP_FOREGROUND_SERVICE);
                startService(intent);
                break;

            case R.id.nav_notification:
                new NotificationUtility(MainActivity.this).sendNotification();
                mService.initAlarm(ACTION_NOTIFICATION);
                break;

            case R.id.nav_db_survey:
                intent = new Intent(this, DebugSurveyActivity.class);
                startActivity(intent);
                break;

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initView() {
        // Setup the drawer layout (side menu)
        mDrawer = findViewById(R.id.drawer_dashboard);
        mToolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        // overwrite Navigation OnClickListener that is set by ActionBarDrawerToggle
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else if (isHomeAsUp) {
                    onBackPressed();
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            }
        });

        AppBarLayout appBarLayout = findViewById(R.id.appbar_dashboard);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    ViewCompat.setElevation(appBarLayout, 0);
                } else {
                    ViewCompat.setElevation(appBarLayout, 4);
                }
            }
        });


        // Setup the navigation view (side menu content)
        NavigationView navigationView = findViewById(R.id.nav_view_dashboard);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup the view pager (category)
        mViewPager = findViewById(R.id.view_pager_dashboard);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);

        // Setup the tabs (indicator)
        TabLayout tabLayout = findViewById(R.id.tab_dashboard);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager, true);
    }

    private void startForegroundService() {
        Intent serviceIntent = new Intent(this, NotificationForegroundService.class);
        startService(serviceIntent);
    }

}
