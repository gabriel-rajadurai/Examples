package com.example.administrator.test.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.google.gson.JsonObject;
import  com.example.administrator.test.common.Utils;
import com.rba.ui.dialog.MaterialDialog;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.administrator.test.common.ConnectionDetector.isConnectedToInternet;

import static com.example.administrator.test.common.Utils.isNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RESTFragment extends Fragment implements GlobalConstants {

    Button bt_Register, bt_Login;
    EditText user,pass;
    TextView tv_result;
    String jsonData;
    com.example.administrator.test.common.Utils utils;

    SharedPreferenceHandle sharedPreferenceHandle;

    private final String TAG = "RESTFragment";

    public RESTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_rest, container, false);

        tv_result = currentView.findViewById(R.id.tv_result);
        bt_Register = currentView.findViewById(R.id.bt_Register);
        bt_Login = currentView.findViewById(R.id.bt_Login);
        user = currentView.findViewById(R.id.et_user);
        pass = currentView.findViewById(R.id.et_password);

        bt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                register();
            }
        });
        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginProcess();
            }
        });


        utils = new Utils();

        return currentView;
    }




    public void register()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new SignUpFragment();
        ft.replace(R.id.container,fragment);
        ft.commit();
    }


    //Login Process
    public  void loginProcess()
    {
        Log.d(TAG, "loginProcess: ");
        if(!isConnectedToInternet(getActivity()))
        {
            Log.d(TAG, "Check your internet connection ");
            return;
        }

        String username = user.getText().toString();
        String password = pass.getText().toString();

        if(!isInputValid(username,password))
        {
            Log.d(TAG, "Invalid user name/password ");
            return;
        }

        //Login process

        sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(getContext());
        String deviceToken = sharedPreferenceHandle.getString(DEVICE_TOKEN, EMPTY_STRING);

        Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,DONT_INCLUDE_AUTH_TOKENS,getActivity()).login(username, password, deviceToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(!response.isSuccessful())
                {
                    if(response.code() == 403)
                    {
                        //utils.singleButtonAlertDialog(getContext(), " Login " ,"Error code: " + response.code());
                        handleVerificationEmailResponse(response.errorBody(),username);
                    }
                    return;
                }

                Log.d(TAG, "onResponse: success");

                //Process response
                JsonObject loginResponse = response.body();

                if(loginResponse.has(RESULT_KEY))
                {
                    Log.d(TAG, "Result: ");
                    JsonObject result = loginResponse.get(RESULT_KEY).getAsJsonObject();
                    if(result.has(SUCCESS_KEY))
                    {
                        Log.d(TAG, "Success: ");
                        if(loginResponse.has(TOKENS_KEY))
                        {
                            Log.d(TAG, "Tokens: ");
                            JsonObject tokens = loginResponse.get(TOKENS_KEY).getAsJsonObject();
                            String refreshToken = tokens.get(REFRESH_TOKEN).getAsString();
                            String authToken = tokens.get(AUTH_TOKEN).getAsString();
                            sharedPreferenceHandle.putString(AUTH_TOKEN,authToken);
                            sharedPreferenceHandle.putString(REFRESH_TOKEN,refreshToken);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            Fragment fragment = new ProfileFragment();
                            Bundle args = new Bundle();
                            args.putString("User",username);
                            fragment.setArguments(args);
                            ft.replace(R.id.container,fragment);
                            ft.commit();
                        }
                     }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                Log.d(TAG, "Unsuccesful login ");
            }
        });
    }

    private void handleVerificationEmailResponse(ResponseBody responseBody, String email)
    {
        utils.twoButtonAlertDialog(getContext(), "Test", "Account not verified!! Resend verification mail?", true, new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog)
            {
                super.onPositive(dialog);

                //Resend verification mail
                JsonObject requestObject = new JsonObject();
                requestObject.addProperty("email",email);

                Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,DONT_INCLUDE_AUTH_TOKENS,getContext()).resendVerifyMail(requestObject);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                    {
                        if(!response.isSuccessful())
                        {

                            utils.singleButtonAlertDialog(getContext(),"Test","Error!");
                        }
                        Log.d(TAG, "onResponse: " + response.raw());

                        utils.singleButtonAlertDialog(getContext(),"Test","Verification mail sent. Please verify and log in again");
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNegative(MaterialDialog dialog)
            {
                super.onNegative(dialog);
            }
        });
    }
    //Login process ends here


    private boolean isInputValid(String username, String password) {

        username = username.toLowerCase().trim();
        password = password.trim();


        String userNameFieldError = null;
        String passwordFieldError = null;

        if (isNull(userNameFieldError) && username.length() == 0) {

            return false;
        }

        if (isNull(userNameFieldError) && !utils.emailValidation(username) ) {

            return false;
        }

        if (isNull(passwordFieldError) && password.length() == 0) {

            return false;
        }

        if (isNull(userNameFieldError) && isNull(passwordFieldError)) {
            // Input is valid
            return true;
        }
        else {

            return false;
        }

    }








}
