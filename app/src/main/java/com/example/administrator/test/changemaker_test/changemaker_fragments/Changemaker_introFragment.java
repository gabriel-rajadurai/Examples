package com.example.administrator.test.changemaker_test.changemaker_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.test.R;
import com.example.administrator.test.changemaker_test.IntroPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Changemaker_introFragment extends Fragment
{

    private View view;
    ViewPager intro_viewPager;
    LinearLayout dotIndicator;
    ImageView[] dots;
    IntroPagerAdapter introPagerAdapter;

    Button next, previous;


    public Changemaker_introFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_changemaker_intro, container, false);

        //Initalize dot indicators
        dotIndicator = view.findViewById(R.id.intropage_dotIndicator);
        dots = new ImageView[4];
        for(int i=0;i<4;i++)
        {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            dotIndicator.addView(dots[i],params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        //Dot indicators initialization complete

        /*intro_viewPager = view.findViewById(R.id.intro_viewPager);

        introPagerAdapter = new IntroPagerAdapter(getContext(),dotIndicator);
        intro_viewPager.setAdapter(introPagerAdapter);*/

        //Initialize buttons
        next = view.findViewById(R.id.bt_next);
        previous = view.findViewById(R.id.bt_prev);

        setupViewPagerListener();
        setupButtonListener();


        return view;
    }

    private void setupButtonListener()
    {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(intro_viewPager.getCurrentItem()<4)
                {
                    intro_viewPager.setCurrentItem(intro_viewPager.getCurrentItem()+1);
                    setupIndicators(intro_viewPager.getCurrentItem());
                    previous.setVisibility(View.VISIBLE);
                }
                if(intro_viewPager.getCurrentItem()==3)
                {
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(intro_viewPager.getCurrentItem()>0)
                {
                    intro_viewPager.setCurrentItem(intro_viewPager.getCurrentItem()-1);
                    setupIndicators(intro_viewPager.getCurrentItem());
                    next.setVisibility(View.VISIBLE);
                }
                if(intro_viewPager.getCurrentItem()==0)
                {
                    previous.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setupViewPagerListener()
    {
        intro_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if(position==0)
                {
                    next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.INVISIBLE);
                }
                else if(position==3)
                {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }
                else
                {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                }
                setupIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupIndicators(int position)
    {
        for(int i=0;i<4;i++)
        {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

}
