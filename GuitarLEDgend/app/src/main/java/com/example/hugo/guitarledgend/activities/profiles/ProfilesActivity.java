package com.example.hugo.guitarledgend.activities.profiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.hugo.guitarledgend.R;
import com.example.hugo.guitarledgend.activities.HelpActivity;
import com.example.hugo.guitarledgend.activities.SettingsActivity;
import com.example.hugo.guitarledgend.databases.users.Profile;

public class ProfilesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static Profile user;

    public static Activity pa;

    public static Profile getUser() {
        return user;
    }

    public static void setUser(Profile user) {
        ProfilesActivity.user = user;
    }

    private static ProfilesAdapter mFragmentPagerAdapter;
    private static ViewPager mViewPager;

    public static ViewPager getmViewPager() {
        return mViewPager;
    }

    public static ProfilesAdapter getmFragmentPagerAdapter() {
        return mFragmentPagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pa = this;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mFragmentPagerAdapter = new ProfilesAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

/*        TextView next_text_view = (TextView) findViewById(R.id.next_button);
        Typeface century_bold = Typeface.createFromAsset(getAssets(), "fonts/Century Gothic Bold.ttf");
        next_text_view.setTypeface(century_bold);
*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(ProfilesActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_help) {
            Intent intent = new Intent(ProfilesActivity.this, HelpActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
