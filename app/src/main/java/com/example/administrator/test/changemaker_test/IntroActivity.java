package com.example.administrator.test.changemaker_test;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
{

    private ViewPager viewPager_Intro;
    private IntroPagerAdapter introPagerAdapter;
    private ImageView[] dots;
    private LinearLayout dotsIndicator, next_screen;
    private TextView prev_screen;
    private TextView tv_nxt;
    private ImageView img_nxt;
    int currentScreen;
    Utils utils;

    private String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initializeView();
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initializeView()
    {
        viewPager_Intro = findViewById(R.id.viewPager_intro);
        dotsIndicator = findViewById(R.id.introscreen_dotIndicator);
        next_screen = findViewById(R.id.next_screen);
        prev_screen = findViewById(R.id.prev_screen);
        utils = new Utils();
        tv_nxt = findViewById(R.id.bt_nxt);
        img_nxt = findViewById(R.id.img_next);

        next_screen.setClickable(true);
        next_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextScreen();
            }
        });

        prev_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevScreen();
            }
        });

        introPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager_Intro.setAdapter(introPagerAdapter);
        viewPager_Intro.addOnPageChangeListener(this);
        setupDotsIndicator();
    }

    private void nextScreen()
    {

        if(currentScreen<4)
        {
            currentScreen = currentScreen+1;
            viewPager_Intro.setCurrentItem(currentScreen);
            setDot(currentScreen);
            Log.d(TAG, "currentScreen: " + currentScreen);

        }
        else
        {
            Log.d(TAG, "nextScreen: ");
            utils.singleButtonAlertDialog(this,"Test","Click ok to continue");
        }
    }

    private void prevScreen()
    {

        if(currentScreen>0)
        {
            currentScreen = currentScreen-1;
            viewPager_Intro.setCurrentItem(currentScreen);
            setDot(currentScreen);
        }
    }



    private void setupDotsIndicator()
    {
        dots = new ImageView[5];
        for(int i=0;i<5;i++)
        {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            dotsIndicator.addView(dots[i],params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    void showIntroFragment()
    {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new Changemaker_introFragment();
        ft.add(R.id.intro_Container,fragment);
        ft.commit();*/
    }

    private void setDot(int position)
    {
        for(int i=0;i<5;i++)
        {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position)
    {
        currentScreen = position;
        setDot(position);
        if(position == 0)
        {
            tv_nxt.setText("Next");
            img_nxt.setVisibility(View.VISIBLE);
            prev_screen.setVisibility(View.INVISIBLE);
        }
        else if(position == 4)
        {
            tv_nxt.setText("Done");
            img_nxt.setVisibility(View.GONE);
            //next_screen.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv_nxt.setText("Next");
            img_nxt.setVisibility(View.VISIBLE);
            prev_screen.setVisibility(View.VISIBLE);
            //next_screen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
