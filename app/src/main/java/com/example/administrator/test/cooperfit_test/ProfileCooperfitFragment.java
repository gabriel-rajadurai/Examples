package com.example.administrator.test.cooperfit_test;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.ConnectionDetector;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.Utils;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCooperfitFragment extends Fragment implements GlobalConstants
{
    ImageView img_profilePic;

    TextView tv_name, tv_email, tv_dob, tv_gender;
    String name, email, dob, gender, profilePic_URL;

    ImageLoader imageLoader;
    DisplayImageOptions options;
    LoginCooperfitActivity activity;
    private String TAG = "ProfileCooperfitFragm";

    Utils utils;

    public ProfileCooperfitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_profile_cooperfit, container, false);
        initializeViews(currentView);

        //Initalize variables
        name = "Name : ";
        email = "Email : ";
        dob = "Date of birth : ";
        gender = "Gender : ";

        utils = new Utils();



        return currentView;
    }



    void initializeViews(View view)
    {
        img_profilePic = view.findViewById(R.id.img_cfitProfilePic);
        tv_name = view.findViewById(R.id.tv_cfitName);
        tv_email = view.findViewById(R.id.tv_cfitEmail);
        tv_dob = view.findViewById(R.id.tv_cfitDOB);
        tv_gender = view.findViewById(R.id.tv_cfitGender);
        showProfile();
    }

    void showProfile()
    {
        utils = new Utils();
        if(!(new ConnectionDetector(getContext()).isConnectingToInternet()))
        {
            utils.singleButtonAlertDialog(getContext(),"Cooperfit Test","Connection Lost!!");
            return;
        }


        Call<JsonObject> call = utils.getBaseClassService("",true,getActivity()).getMyLatestProfileDetails();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: Successful" + response.raw());
                    JsonObject responseBody = response.body();
                    if(responseBody.has(RESULT_KEY))
                    {
                        JsonObject result = responseBody.get(RESULT_KEY).getAsJsonObject();
                        if(result.has(FULL_NAME_KEY))
                        {
                            name += result.get(FULL_NAME_KEY).getAsString();
                        }
                        if(result.has(EMAIL_KEY))
                        {
                            email += result.get(EMAIL_KEY).getAsString();
                        }
                        if(result.has(DOB))
                        {
                            dob += result.get(DOB).getAsString();
                        }
                        if(result.has(GENDER))
                        {
                            gender += result.get(GENDER).getAsString();
                        }
                        if(result.has(PROFILE_PIC_KEY))
                        {
                            profilePic_URL = result.get(PROFILE_PIC_KEY).getAsString();
                            Log.d(TAG, "onResponse: " + profilePic_URL);
                        }

                        tv_name.setText(name);
                        tv_email.setText(email);
                        tv_gender.setText(gender);
                        tv_dob.setText(dob);

                        //Setup Image Loader
                        imageLoader = ImageLoader.getInstance();
                        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                                .cacheOnDisk(true).resetViewBeforeLoading(true)
                                .showImageForEmptyUri(R.drawable.user)
                                .showImageOnFail(R.drawable.user)
                                .showImageOnLoading(R.drawable.user).build();


                        //imageLoader.displayImage(profilePic_URL,img_profilePic,options);

                        imageLoader.loadImage(profilePic_URL, new SimpleImageLoadingListener()
                        {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                img_profilePic.setImageDrawable(getCircleBitmap(loadedImage));

                                Log.d(TAG, "onLoadingComplete: Rounded Image");
                            }
                        });



                    }
                }
            }

            private Drawable getCircleBitmap(Bitmap bitmap)
            {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCornerRadius(bitmap.getWidth());
                roundedBitmapDrawable.setCircular(true);
                return roundedBitmapDrawable;
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                utils.serviceCallFailermsg(t,getContext());
            }
        });



    }

}
