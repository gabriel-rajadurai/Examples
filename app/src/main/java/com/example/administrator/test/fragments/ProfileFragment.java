package com.example.administrator.test.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.ConnectionDetector;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.example.administrator.test.common.Utils;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment implements GlobalConstants
{
    String user;
    TextView welcome, username, email;
    ImageView profile_pic;
    Button bt_Logout, bt_updateProfile;
    Utils utils;

    SharedPreferenceHandle sharedPreferenceHandle;

    private static String TAG = "Profile";
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public ProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_profile, container, false);

        Log.d(TAG, "Profile Fragment: ");

        //Initialize image view
        profile_pic = currentView.findViewById(R.id.img_profilePic);

        //Set Welcome text
        welcome = currentView.findViewById(R.id.tv_welcome);
        user = getArguments().getString("User");
        welcome.setText("You are logged in as " + user);

        username = currentView.findViewById(R.id.tv_username);
        email = currentView.findViewById(R.id.tv_email);
        bt_Logout = currentView.findViewById(R.id.bt_Logout);
        bt_updateProfile = currentView.findViewById(R.id.bt_updateProfile);

        bt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        bt_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });


        //Setup Image Loader
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.one)
                .showImageOnFail(R.drawable.one)
                .showImageOnLoading(R.drawable.one).build();




        utils = new Utils();

        retrieveProfile();

        return currentView;
    }

    private void updateProfile()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new UpdateProfileFragment();
        ft.add(R.id.container,fragment);
        ft.commit();
    }

    public void retrieveProfile()
    {
        if(new ConnectionDetector(getContext()).isConnectingToInternet())
        {
            Call<JsonObject> call = utils.getBaseClassService("",true,getContext()).getMyLatestProfileDetails();
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                {
                    if(response.isSuccessful())
                    {
                        Log.d(TAG, "onResponse: " + response.raw());
                        JsonObject jresponse = response.body();
                        if(jresponse.has(RESULT_KEY))
                        {
                            JsonObject result = jresponse.get(RESULT_KEY).getAsJsonObject();
                            Log.d(TAG, "onResponse: " + result.toString());
                            String name = "User:  ";
                            String emailid = "Email: ";
                            if(result.has(FULL_NAME_KEY))
                            {
                                name += result.get(FULL_NAME_KEY).getAsString();
                            }
                            if(result.has(EMAIL_KEY))
                            {
                                emailid += result.get(EMAIL_KEY).getAsString();
                            }
                            if(result.has(PROFILE_PIC_KEY))
                            {
                                imageLoader.displayImage(result.get(PROFILE_PIC_KEY).getAsString(),profile_pic,options);

                            }
                            username.setText(name);
                            email.setText(emailid);
                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
    }



    public  void logout()
    {
        if(new ConnectionDetector(getContext()).isConnectingToInternet())
        {
            sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(getContext());
            String deviceToken = sharedPreferenceHandle.getString(DEVICE_TOKEN,EMPTY_STRING);
            JsonObject requestJson = new JsonObject();
            requestJson.addProperty("device_token",deviceToken);

            Call<JsonObject> call = utils.getBaseClassService("",true,getContext()).logout(requestJson);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                {
                    if(response.isSuccessful())
                    {
                        Log.d(TAG, "Log out:  " + response.raw());
                        sharedPreferenceHandle.clearSharedPreference();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        Fragment fragment = new RESTFragment();
                        ft.replace(R.id.container,fragment);
                        ft.commit();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
    }



}
