package com.example.administrator.test.changemaker_test.changemaker_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment2 extends Fragment {


    public IntroFragment2()
    {
        // Required empty public constructor
    }


    public static IntroFragment2 newInstance(final int screenPosition)
    {
        IntroFragment2 introFragment2 = new IntroFragment2();
        Bundle b = new Bundle();
        b.putInt(GlobalConstants.SCREEN_ID,screenPosition);

        introFragment2.setArguments(b);
        return introFragment2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment2, container, false);
    }

}
