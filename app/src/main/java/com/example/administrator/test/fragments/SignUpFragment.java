package com.example.administrator.test.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.test.R;
import com.example.administrator.test.common.ConnectionDetector;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.example.administrator.test.common.Utils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;

import static android.content.ContentValues.TAG;
import static com.example.administrator.test.common.ConnectionDetector.isConnectedToInternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements GlobalConstants
{

    EditText et_email, et_password, et_dob, et_groupID, et_fName;
    Button bt_createAccount;
    Utils utils;
    SharedPreferenceHandle sharedPreferenceHandle;

    final Calendar calendar = Calendar.getInstance();

    private static String TAG = "SignUpFragment";


    public SignUpFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        utils = new Utils();

        sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(getContext());

        et_email = currentView.findViewById(R.id.et_email);
        et_password = currentView.findViewById(R.id.et_pass);
        et_dob = currentView.findViewById(R.id.et_dob);
        et_groupID = currentView.findViewById(R.id.et_groupID);
        et_fName = currentView.findViewById(R.id.et_fName);

        bt_createAccount = currentView.findViewById(R.id.bt_createAccount);
        bt_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                registerAccount();
            }
        });

        return currentView;
    }

    void setupDatePicker()
    {

    }


    public void registerAccount()
    {
        Log.d(TAG, "registering Account: ");
        UserDetails userDetails = new UserDetails();

        userDetails.fullName = et_fName.getText().toString();
        userDetails.email = et_email.getText().toString();
        userDetails.dob = et_dob.getText().toString();
        userDetails.gender = "Male";
        userDetails.groupId = et_groupID.getText().toString();
        userDetails.password = et_password.getText().toString();

        if(!isConnectedToInternet(getContext()))
        {
            utils.singleButtonAlertDialog(getContext(),"Sign Up","Please check your Internet connection and try again.");
            return;
        }


        //Sign up process
        Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,DONT_INCLUDE_AUTH_TOKENS,getContext())
                .signup(userDetails.email,userDetails.password,userDetails.groupId,userDetails.dob,userDetails.gender,userDetails.fullName);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(!response.isSuccessful())
                {
                    Log.d(TAG, "Unsuccessful: " + response.raw());
                    return;
                }

                JsonObject signUpResponse = response.body();
                if(signUpResponse.has(RESULT_KEY))
                {
                    Log.d(TAG, "Result");
                    JsonObject result = signUpResponse.get(RESULT_KEY).getAsJsonObject();
                    Log.d(TAG, "onResponse: " + result.get(MESSAGE).getAsString());
                    if(signUpResponse.has(VERIFIED_KEY)&&result.get(VERIFIED_KEY).getAsBoolean())
                    {
                        Log.d(TAG, "Verified");
                        if(result.has(TOKENS_KEY))
                        {
                            JsonObject tokens = signUpResponse.get(TOKENS_KEY).getAsJsonObject();

                            String refreshToken = tokens.get(REFRESH_TOKEN).getAsString();
                            String authToken = tokens.get(AUTH_TOKEN).getAsString();

                            sharedPreferenceHandle.putString(REFRESH_TOKEN,refreshToken);
                            sharedPreferenceHandle.putString(AUTH_TOKEN,authToken);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {

            }
        });

    }


    /**
     * UserDetails holder class holds user entered data. Avoids multiple parameters in a method.
     */
    class UserDetails {

        String email;
        String password;
        String dob;
        String groupId;
        String fullName;
        String gender;

        UserDetails(){}
    }

}
