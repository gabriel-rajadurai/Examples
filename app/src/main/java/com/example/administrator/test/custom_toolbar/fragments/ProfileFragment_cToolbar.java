package com.example.administrator.test.custom_toolbar.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.custom_toolbar.CustomToolbarActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment_cToolbar extends Fragment {

    private Button bt_edtProfile;
    private CustomToolbarActivity mActivity;

    private String TAG = "PrfleFrgmnt_cToolbar";

    public ProfileFragment_cToolbar() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (CustomToolbarActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment__ctoolbar, container, false);
        bt_edtProfile = view.findViewById(R.id.bt_edtProfile);

        bt_edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtProfile();
            }
        });
        return view;
    }



    private void edtProfile() {
        mActivity.updateToolbarTitle("EditProfile");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new EditProfileFragment();
        ft.add(R.id.fg_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
        Log.d(TAG, "edtProfile: "+fragmentManager.getBackStackEntryCount());
    }

}
