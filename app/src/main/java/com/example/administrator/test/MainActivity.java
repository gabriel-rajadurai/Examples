package com.example.administrator.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.test.changemaker_test.ChangeMaker_main;
import com.example.administrator.test.contacts_sql.ContactsMainActivity;
import com.example.administrator.test.cooperfit_test.LoginCooperfitActivity;
import com.example.administrator.test.custom_toolbar.CustomToolbarActivity;
import com.example.administrator.test.navdrawer_test.NavDrawerActivity;
import com.example.administrator.test.scroll_layout_test.ScrollLayoutActivity;
import com.example.administrator.test.sqlite_test.SqlActivity;

public class MainActivity extends AppCompatActivity
{
    Button counter, rList, cList, heteroView, pickImg, expRecycleView;
    Button sqlite, restAPI, cooperfit, changemaker, c_toolbar, navDrawer, scrl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the buttons
        counter = (Button)findViewById(R.id.counter);
        rList = (Button)findViewById(R.id.rView);
        cList = (Button)findViewById(R.id.cView);
        heteroView = (Button)findViewById(R.id.heteroView);
        pickImg = (Button)findViewById(R.id.pickImageView);
        restAPI = (Button)findViewById(R.id.restAPI);
        cooperfit = (Button)findViewById(R.id.cooperfit);
        changemaker = (Button)findViewById(R.id.changemaker);
        c_toolbar = (Button)findViewById(R.id.toolbar_custom);
        navDrawer = (Button)findViewById(R.id.nav_drawer);
        scrl_layout = (Button)findViewById(R.id.scroll_layout) ;
        sqlite = (Button)findViewById(R.id.sql_queries);
        expRecycleView = (Button)findViewById(R.id.exp_recycleview);

        initListeners();
    }


    private void initListeners()
    {
        //Initialize the OnClickListeners for the buttons
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicks("counter");
            }
        });

        rList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks("rList");
            }
        });

        cList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks("cList");
            }
        });

        heteroView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks("heteroView");
            }
        });

        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks("pickImageView");
            }
        });

        restAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks("restAPI");
            }
        });

        cooperfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCooperfit();
            }
        });

        changemaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangemaker();
            }
        });

        c_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomToolbarActivity();
            }
        });

        navDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDrawerActivity();
            }
        });

        scrl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScrollLayoutActivity();
            }
        });

        sqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensqliteActivity();
            }
        });

        expRecycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicks("Expandable RecycleView");
            }
        });
    }

    private void opensqliteActivity()
    {
        Intent intent = new Intent(this, ContactsMainActivity.class);
        startActivity(intent);
    }

    private void openScrollLayoutActivity()
    {
        Intent intent = new Intent(this, ScrollLayoutActivity.class);
        startActivity(intent);
    }

    private void openNavDrawerActivity()
    {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
    }

    private void openCustomToolbarActivity()
    {
        Intent intent = new Intent(this, CustomToolbarActivity.class);
        startActivity(intent);
    }

    private void openChangemaker()
    {
        Intent intent = new Intent(this, ChangeMaker_main.class);
        startActivity(intent);
    }

    private void openCooperfit()
    {
        Intent intent = new Intent(this, LoginCooperfitActivity.class);
        startActivity(intent);
    }

    //Method to go to next Activity
    void handleButtonClicks(String buttonName)
    {

        // This is an awesome comment.
        Intent intent = new Intent(this, FragActivity.class);
        intent.putExtra("buttonName",buttonName);
        startActivity(intent);
    }

}
