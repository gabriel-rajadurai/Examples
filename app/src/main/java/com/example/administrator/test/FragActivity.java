package com.example.administrator.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.test.expandable_recyclerview.ExpandableRecyclerFragment;
import com.example.administrator.test.fragments.CounterFragment;
import com.example.administrator.test.fragments.HeteroFragment;
import com.example.administrator.test.fragments.PickImageFragment;
import com.example.administrator.test.fragments.RESTFragment;
import com.example.administrator.test.fragments.RecycleViewFragment;
import com.example.administrator.test.fragments.RecyclerViewFragmentTwo;

public class FragActivity extends AppCompatActivity
{
    String buttonName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);

        //Get name of button clicked
        buttonName = getIntent().getStringExtra("buttonName");

        //Show Fragment
        if(savedInstanceState == null) {
            showFragment(buttonName);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }



    void showFragment(String buttonName)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment();
        switch (buttonName)
        {
            case "counter":
                fragment = new CounterFragment();
                break;
            case "rList":
                fragment = new RecycleViewFragment();
                break;
            case "cList":
                fragment = new RecyclerViewFragmentTwo();
                break;
            case "heteroView":
                fragment = new HeteroFragment();
                break;
            case "pickImageView":
                fragment = new PickImageFragment();
                break;
            case "restAPI":
                fragment = new RESTFragment();
                break;
            case "Expandable RecycleView":
                fragment = new ExpandableRecyclerFragment();
                break;
            default:
                break;
        }
        ft.add(R.id.container,fragment);
        ft.commit();

    }



}
