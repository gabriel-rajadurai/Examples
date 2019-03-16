package com.example.administrator.test.changemaker_test;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.test.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeMaker_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_maker_main);
        showIntro();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showIntro()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(ChangeMaker_main.this, IntroActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }
}
