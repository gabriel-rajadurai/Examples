package com.example.administrator.test.navdrawer_test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View navHeaderLayout;
    private ImageView img_profilePic;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String profilePic_URL = "http://media.glassdoor.com/sqll/831870/rapidbizapps-squarelogo-1415068388416.png";

    private String TAG = "NavDrawer";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        initViews();
    }

    private void initViews()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drwr_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        //Custom toggle icon
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerVisible(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else
                {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderLayout = navigationView.getHeaderView(0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new NavWelcomeFragment();
        ft.replace(R.id.navContainer,fragment);
        ft.commit();
        setupProfilePic();
    }

    private void setupProfilePic()
    {
        img_profilePic = navHeaderLayout.findViewById(R.id.nav_profilePic);
        Log.d(TAG, "setupProfilePic: " + img_profilePic);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
                .showImageOnLoading(R.drawable.user).build();

        imageLoader.loadImage(profilePic_URL, new SimpleImageLoadingListener()
        {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                img_profilePic.setImageDrawable(getCircleBitmap(loadedImage));
            }
        });
    }

    private Drawable getCircleBitmap(Bitmap bitmap)
    {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCornerRadius(bitmap.getWidth());
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.hme_item:
                Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
                openHome();
                break;
            case R.id.prfle_item:
                openProfile();
                break;
            case R.id.shre_item:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openProfile()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new NavProfileFragment();
        ft.replace(R.id.navContainer,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void openHome()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new HomeFragment();
        ft.replace(R.id.navContainer,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }




}
