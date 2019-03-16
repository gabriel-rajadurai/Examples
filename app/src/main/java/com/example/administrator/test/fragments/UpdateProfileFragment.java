package com.example.administrator.test.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.Utils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends Fragment implements GlobalConstants
{
    EditText et_email, et_fName, et_dob, et_gender;
    String email, fName, dob, gender;
    Button bt_save, bt_cancel;
    private String TAG = "UpdateProfileFragment";
    Utils utils;


    public UpdateProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_update_profile, container, false);
        utils = new Utils();

        et_email = currentView.findViewById(R.id.et_updateEmail);
        et_fName = currentView.findViewById(R.id.et_updateFname);
        et_dob = currentView.findViewById(R.id.et_updateDOB);
        et_gender = currentView.findViewById(R.id.et_updateGender);

        bt_save = currentView.findViewById(R.id.bt_saveProfile);
        bt_cancel = currentView.findViewById(R.id.bt_cancelChanges);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelChanges();
            }
        });

        return currentView;
    }

    private void cancelChanges()
    {
        /*FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new ProfileFragment();
        ft.replace(R.id.container,fragment);
        ft.commit();*/
    }

    private void saveProfile()
    {
        Log.d(TAG, "saveProfile: ");
        if(isEnteredDataValid()==false)
        {
            utils.singleButtonAlertDialog(getContext(),"Test","Enter valid data");
            return;
        }

        Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,INCLUDE_AUTH_TOKENS,getContext()).updateProfile(email,fName,dob,gender);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(!response.isSuccessful())
                {
                    utils.singleButtonAlertDialog(getContext(),"Test", "Error code: " + response.code());
                    return;
                }
                Toast.makeText(getContext(),"Profile updated!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {

            }
        });

    }

    Boolean isEnteredDataValid()
    {
        email = et_email.getText().toString();
        fName = et_fName.getText().toString();
        dob = et_dob.getText().toString();
        gender = et_gender.getText().toString();

        if(email == "" || fName == "" || dob == "" || gender == "")
        {
            return false;
        }
        return true;
    }

}
