package com.example.administrator.test.changemaker_test;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.test.R;
import com.example.administrator.test.changemaker_test.changemaker_fragments.IntroFragment;
import com.example.administrator.test.changemaker_test.changemaker_fragments.IntroFragment2;

/**
 * Created by Administrator on 8/10/2017.
 */

public class IntroPagerAdapter extends FragmentPagerAdapter
{
    private int[] mImageResources = {R.mipmap.intro_one,R.mipmap.intro_two,R.mipmap.intro_three,R.mipmap.intro_four};


    public IntroPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment introFragment;
        introFragment = IntroFragment.newInstance(position);
        if(position==4)
        {
            introFragment = IntroFragment2.newInstance(position);
        }
        return introFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }






}
