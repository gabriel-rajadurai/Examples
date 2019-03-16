package com.example.administrator.test.custom_toolbar;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.custom_toolbar.fragments.AbtUsFragment;
import com.example.administrator.test.custom_toolbar.fragments.Home_Fragment;
import com.example.administrator.test.custom_toolbar.fragments.ProfileFragment_cToolbar;
import com.example.administrator.test.custom_toolbar.fragments.SearchFragment;
import com.example.administrator.test.custom_toolbar.fragments.SettingsFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class CustomToolbarActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    private String TAG = "CustomToolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toolbar);
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        title = (TextView) findViewById(R.id.tv_title);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_close_24dp);
        setSupportActionBar(toolbar);


        //Set default fragment
        setFragment("Home");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search_item:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOptionsItemSelected: Search");
                setFragment("Search");
                return true;
            case R.id.home_item:
                Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOptionsItemSelected: Home");
                setFragment("Home");
                return true;
            case R.id.profile_item:
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOptionsItemSelected: Profile");
                setFragment("Profile");
                return true;
            case R.id.settings_item:
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                setFragment("Settings");
                return true;
            case R.id.aboutUs_item:
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                setFragment("About us");
                return true;
            case android.R.id.home:
                //Toast.makeText(this,"Exiting...",Toast.LENGTH_SHORT).show();
                finish();
                Log.d(TAG, "onOptionsItemSelected: Close");
                return true;
            default:
                Log.d(TAG, "onOptionsItemSelected: None");
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: "+getFragmentManager().getBackStackEntryCount());
        super.onBackPressed();
    }

    private void setFragment(String itemName)
    {
        title.setText(itemName);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            return;
        }

        Fragment fragment = new Fragment();
        switch (itemName)
        {
            case "Home":
                fragment = new Home_Fragment();
                break;
            case "Profile":
                fragment = new ProfileFragment_cToolbar();
                break;
            case "Search":
                fragment = new SearchFragment();
                break;
            case "Settings":
                fragment = new SettingsFragment();
                break;
            case "About us":
                fragment = new AbtUsFragment();
                break;
            default:
                break;
        }
        ft.add(R.id.fg_container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void updateToolbarTitle(@NonNull String title){
        if(this.title != null) this.title.setText(title.trim());
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
