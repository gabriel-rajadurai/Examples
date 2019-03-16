package com.example.administrator.test.changemaker_test.changemaker_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment
{
    private View introView;
    private ImageView img_introScreen;
    private TextView tv_introLabel;

    int position;


    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(final int screenPosition)
    {
        IntroFragment introFragment = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(GlobalConstants.SCREEN_ID,screenPosition);

        introFragment.setArguments(b);
        return introFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        introView = inflater.inflate(R.layout.fragment_intro, container, false);

        img_introScreen = introView.findViewById(R.id.img_introScreen);
        tv_introLabel = introView.findViewById(R.id.tv_introLabel);


        if(getArguments()!=null)
        {
            position = getArguments().getInt(GlobalConstants.SCREEN_ID);
        }

        switch (position)
        {
            case 0:
                img_introScreen.setImageResource(R.mipmap.intro_one);
                tv_introLabel.setText("Be heroic\nBe a ChangeMaker");
                break;
            case 1:
                img_introScreen.setImageResource(R.mipmap.intro_two);
                tv_introLabel.setText("The world\n needs Leaders");
                break;
            case 2:
                img_introScreen.setImageResource(R.mipmap.intro_three);
                tv_introLabel.setText("Save the world for them.\n Earn for yourself");
                break;
            case 3:
                img_introScreen.setImageResource(R.mipmap.intro_four);
                tv_introLabel.setText("Kickback and watch your\n earnings grow");
                break;
            default:
                break;
        }

        return introView;
    }

}
