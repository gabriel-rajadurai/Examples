package com.example.administrator.test.cooperfit_test;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.test.R;
import com.example.administrator.test.common.ConnectionDetector;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.example.administrator.test.common.Utils;
import com.example.administrator.test.fragments.RESTFragment;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileCooperfitActivity extends AppCompatActivity implements GlobalConstants {

    Button bt_viewProfile, bt_logout;
    Utils utils;
    SharedPreferenceHandle sharedPreferenceHandle;
    private String TAG = "ProfileCooperfitAct";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cooperfit);

        utils = new Utils();

        bt_viewProfile = (Button)findViewById(R.id.cfit_viewProfile);
        bt_logout = (Button)findViewById(R.id.cfit_logOut);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleButtonClicks(1);
            }
        });

        bt_viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClicks(0);
            }
        });

    }

    void handleButtonClicks(int buttonClicked)
    {
        switch (buttonClicked)
        {
            case 0:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Fragment fragment = new ProfileCooperfitFragment();
                ft.replace(R.id.cfit_Container,fragment);
                ft.commit();
                break;

            case 1:
                logoutCooperfit();
                break;
        }
    }

    private void logoutCooperfit()
    {
        if(!new ConnectionDetector(this).isConnectingToInternet())
        {

        }
        sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(this);
        String deviceToken = sharedPreferenceHandle.getString(DEVICE_TOKEN,EMPTY_STRING);
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("device_token",deviceToken);

        Call<JsonObject> call = utils.getBaseClassService("",true,this).logout(requestJson);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(response.isSuccessful())
                {
                    Log.d(TAG, "Log out:  " + response.raw());
                    sharedPreferenceHandle.clearSharedPreference();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                utils.serviceCallFailermsg(t,ProfileCooperfitActivity.this);
            }
        });
    }


}
