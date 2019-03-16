package com.example.administrator.test.custom_toolbar.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.test.R;
import com.example.administrator.test.custom_toolbar.CustomToolbarActivity;


public class EditProfileFragment extends Fragment {


    CustomToolbarActivity cActivity;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        cActivity = (CustomToolbarActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cActivity.updateToolbarTitle("Profile");
    }
}
