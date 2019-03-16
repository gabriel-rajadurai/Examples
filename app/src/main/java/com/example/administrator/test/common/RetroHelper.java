package com.example.administrator.test.common;

import android.content.Context;
import android.util.Log;

import com.example.administrator.test.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 8/3/2017.
 */

public class RetroHelper implements GlobalConstants
{
    private static String TAG = "RetroHelper";
    public static Retrofit getAdapter(Context context, String serverUrl, HashMap<String, String> headers)
    {
        String url = "http://34.200.66.29/api/v1/" + serverUrl;
        Log.d("RetroHelper", "url : " + url);
        OkHttpClient client = getRequestResponseInterceptor(headers, context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    private static OkHttpClient getRequestResponseInterceptor(final HashMap<String, String> headers, final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);

        httpClient.interceptors().add(chain -> {

            Request.Builder requestBuilder = chain.request().newBuilder();
            Iterator<Map.Entry<String, String>> entrysList = headers.entrySet().iterator();
            while (entrysList.hasNext()) {
                Map.Entry<String, String> entry = entrysList.next();
                requestBuilder.addHeader(entry.getKey(), entry.getValue()).build();
                Log.d(TAG, "intercept: " + entry.getKey() + ":" + entry.getValue());
            }
            return chain.proceed(requestBuilder.build());
        });

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                MediaType contentType = response.body().contentType();
                String bodyString = response.body().string();

                Log.d(TAG, "intercept: " + response.code());
                ResponseBody body = ResponseBody.create(contentType, bodyString);

                updateTokens(bodyString);

                Log.d(TAG, "intercept: " + bodyString);
                return response.newBuilder().body(body).build();


            }

            private void updateTokens(String body) {

                try {
                    JSONObject jsonObject = new JSONObject(body);


                    JSONObject tokens = jsonObject.getJSONObject("token");

                    String refreshToken = tokens.getString("refreshToken");
                    String authToken = tokens.getString("authToken");

                    Log.d(TAG, "updateTokens: Auth Token" + authToken);
                    Log.d(TAG, "updateTokens: Refresh Token" + refreshToken);

                    SharedPreferenceHandle mSharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(context);
                    mSharedPreferenceHandle.putString(AUTH_TOKEN, authToken);
                    mSharedPreferenceHandle.putString(REFRESH_TOKEN, refreshToken);

                } catch (JSONException e) {
                    //  tokens section does not exist
                    Log.e(TAG, "updateTokens: " + e.toString());
                }
            }
        });
        return httpClient.build();
    }
}
