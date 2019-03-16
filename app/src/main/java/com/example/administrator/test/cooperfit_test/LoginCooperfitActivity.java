package com.example.administrator.test.cooperfit_test;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.example.administrator.test.common.Utils;
import com.google.gson.JsonObject;
import com.rba.ui.dialog.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.administrator.test.common.ConnectionDetector.isConnectedToInternet;

public class LoginCooperfitActivity extends AppCompatActivity implements GlobalConstants
{
    EditText et_userName, et_password;
    Button bt_login, bt_register;
    TextView tv_forgotPassword;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    Utils utils;

    SharedPreferenceHandle sharedPreferenceHandle;

    private String TAG = "LoginCooperfitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cooperfit);

        utils = new Utils();

        sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(this);

        if(sharedPreferenceHandle.getBoolean(LOGGED_IN,false))
        {
            login();
        }

        initializeViews();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        et_userName.setText("");
        et_password.setText("");
    }

    private void initializeViews()
    {
        et_userName = (EditText)findViewById(R.id.et_cfitUser);
        et_password = (EditText)findViewById(R.id.et_cfitPassword);
        bt_login = (Button)findViewById(R.id.bt_cfitLogin);
        bt_register = (Button)findViewById(R.id.bt_cfitRegister);
        tv_forgotPassword = (TextView)findViewById(R.id.tv_cfitForgotPassword);




        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_light_gray));
                utils.twoButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test","Are you sure you want to reset your password!!",true,new MaterialDialog.ButtonCallback()
                {
                    @Override
                    public void onPositive(MaterialDialog dialog)
                    {
                        super.onPositive(dialog);
                        forgotPassword();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog)
                    {
                        super.onNegative(dialog);
                    }
                });

            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginCooperfit();
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCooperfit();
            }
        });
    }

    private void registerCooperfit()
    {

    }

    private void loginCooperfit()
    {
        Log.d(TAG, "login process ");
        if(!isConnectedToInternet(this))
        {
            utils.singleButtonAlertDialog(this,"Cooperfit Test","Please connect to internet and try again!!");
            return;
        }

        String username = et_userName.getText().toString();
        String password = et_password.getText().toString();

        if(!isInputValid(username,password))
        {
            utils.singleButtonAlertDialog(this,"Cooperfit Test", "Invalid username/password ");
            return;
        }

        //Login

        String deviceToken = sharedPreferenceHandle.getString(DEVICE_TOKEN, EMPTY_STRING);

        Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,DONT_INCLUDE_AUTH_TOKENS,LoginCooperfitActivity.this).login(username,password,deviceToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(!response.isSuccessful())
                {
                    if(response.code() == 403)
                    {
                        utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit Test","Account not yet verified!!");
                    }
                    else
                    {
                        try
                        {
                            String msg = utils.convertStreamToString(response.errorBody().byteStream());
                            JSONObject object = new JSONObject(msg);
                            String message = object.getString(MESSAGE);
                            utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test",message);
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    return;
                }

                JsonObject responseBody = response.body();
                if(responseBody.has(RESULT_KEY))
                {
                    JsonObject result = responseBody.get(RESULT_KEY).getAsJsonObject();
                    if(result.has(SUCCESS_KEY))
                    {
                        if(responseBody.has(TOKENS_KEY))
                        {
                            JsonObject tokens = responseBody.get(TOKENS_KEY).getAsJsonObject();
                            String refreshToken = tokens.get(REFRESH_TOKEN).getAsString();
                            String authToken = tokens.get(AUTH_TOKEN).getAsString();
                            sharedPreferenceHandle.putString(AUTH_TOKEN,authToken);
                            sharedPreferenceHandle.putString(REFRESH_TOKEN,refreshToken);
                            sharedPreferenceHandle.putBoolean(LOGGED_IN,true);
                            login();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                utils.serviceCallFailermsg(t,LoginCooperfitActivity.this);
            }
        });
    }

    private void login()
    {
        Intent intent = new Intent(getApplicationContext(),ProfileCooperfitActivity.class);
        startActivity(intent);
    }

    private void forgotPassword()
    {
        if(!isConnectedToInternet(this))
        {
            utils.singleButtonAlertDialog(this,"Cooperfit Test","Please connect to internet and try again!!");
            tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
            return;
        }
        String user = et_userName.getText().toString();
        if(user.equals(""))
        {
            utils.singleButtonAlertDialog(this,"Cooperfit Test","Please enter the email address and then click on forgot password");
            tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
            return;
        }

        if(!user.matches(emailPattern))
        {
            utils.singleButtonAlertDialog(this,"Cooperfit Test","Please enter valid email address");
            tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
            return;
        }

        //Forgot Password

        Call<JsonObject> call = utils.getBaseClassService(EMPTY_STRING,DONT_INCLUDE_AUTH_TOKENS,this).forgetPassword(user);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                if(!response.isSuccessful())
                {
                    //utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test", "Unable to process request. " + response.code());

                    if(response.code() == 426)
                    {
                        utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test","You need to have the latest version to use the app");
                        tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
                        return;
                    }
                    else
                    {
                        try
                        {
                            String msg = utils.convertStreamToString(response.errorBody().byteStream());
                            JSONObject object = new JSONObject(msg);
                            String message = object.getString(MESSAGE);
                            utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test",message);
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
                    return;
                }
                utils.singleButtonAlertDialog(LoginCooperfitActivity.this,"Cooperfit test","An email has been sent to your registered email address to reset your password");
                tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                utils.serviceCallFailermsg(t,LoginCooperfitActivity.this);
                tv_forgotPassword.setTextColor(getResources().getColor(R.color.mdtp_white));
            }
        });

    }

    Boolean isInputValid(String user, String pass)
    {
        if(user.equals("") || pass.equals(""))
        {
            return false;
        }

        if(!user.matches(emailPattern))
        {
            return false;
        }
        return true;
    }
}
