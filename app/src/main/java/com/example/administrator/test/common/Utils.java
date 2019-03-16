package com.example.administrator.test.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.administrator.test.R;
import com.example.administrator.test.RESTHelper;
import com.rba.ui.dialog.MaterialDialog;
import com.rba.ui.dialog.Theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Pattern;

//import okhttp3.ResponseBody;
//import rapidbiz.cooperfit.R;


public class Utils implements GlobalConstants {
    private static final String TAG = "Utils";

    MaterialDialog ringProgressDialog = null;
    MaterialDialog networkDialog = null;



    private static Utils Instance;

    public static Utils getInstance() {
        if (Instance == null){
            Instance = new Utils();
        }
        return Instance;
    }

    public void showProgressDialog(final Context ctx) {
        if (ringProgressDialog == null) {
            ringProgressDialog = new MaterialDialog.Builder(ctx)
                    //.title(ctx.getResources().getString(R.string.app_name))
                    .content("Please wait.... ")
                    .progress(true, 0)
                    .theme(Theme.LIGHT)
                    .cancelable(false)
                    .show();
            ringProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    try {
                        ProgressBar v = (ProgressBar) ringProgressDialog.findViewById(android.R.id.progress);
                        v.getIndeterminateDrawable().setColorFilter(ctx.getResources().getColor(R.color.mdtp_dark_gray),
                                android.graphics.PorterDuff.Mode.MULTIPLY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static boolean isNull(String field) {
        return field == null;
    }

    public static String getMeString(AppCompatActivity activity, int stringID) {
        return activity.getResources().getString(stringID);
    }

    public void showProgressDialog(final Context ctx, String msg) {
        if (ringProgressDialog == null) {
            ringProgressDialog = new MaterialDialog.Builder(ctx)
                    //  .title(ctx.getResources().getString(R.string.app_name))
                    .content(msg)
                    .progress(true, 0)
                    .theme(Theme.LIGHT)
                    .cancelable(false)
                    .show();
            ringProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    try {
                        ProgressBar v = (ProgressBar) ringProgressDialog.findViewById(android.R.id.progress);
                        v.getIndeterminateDrawable().setColorFilter(ctx.getResources().getColor(R.color.mdtp_dark_gray),
                                android.graphics.PorterDuff.Mode.MULTIPLY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void dismissDialog() {
        if (ringProgressDialog != null) {
            if (ringProgressDialog.isShowing()) {
                ringProgressDialog.dismiss();
                ringProgressDialog = null;
            }
        }
    }

    public void dismissNetworkErrorDialog() {
        if ( networkDialog != null) {
            if (networkDialog.isShowing()) {
                networkDialog.dismiss();
                networkDialog = null;
            }
        }
    }

    public void twoButtonAlertDialog(Context ctx, String title, String message, boolean okCancel, final MaterialDialog.ButtonCallback cb) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);

        builder.title(APP_NAME);
        builder.content(message);
        if (okCancel) {
            builder.negativeText(android.R.string.cancel);
            builder.positiveText(android.R.string.ok);
        } else {
            builder.negativeText("No");
            builder.positiveText("Yes");
        }

        builder.positiveColorRes(R.color.mdtp_dark_gray);
        builder.negativeColorRes(R.color.mdtp_dark_gray);
        builder.callback(cb);
        builder.cancelable(false);
        builder.show();

    }



    public void singleButtonAlertDialog(Context ctx, String title, String message) {

        if (networkDialog == null) {

            networkDialog = new MaterialDialog.Builder(ctx)
                    .theme(Theme.LIGHT)
                    .content(message)
                    .positiveText("Ok")
                    .positiveColorRes(R.color.dark_grey)
                    .negativeColorRes(R.color.dark_grey)
                    .title(APP_NAME)
                    .cancelable(false)
                    .build();
        }

        if (title != null || !title.isEmpty())
            networkDialog.setTitle(title);
        networkDialog.setContent(message);
       if(!networkDialog.isShowing())
         networkDialog.show();
//        if (title != null) {
//            //  builder.title(title);
//        }
//        builder.content(message);
//
//        builder.positiveText("Ok");
//
//        builder.positiveColorRes(R.color.dark_grey);
//        builder.negativeColorRes(R.color.dark_grey);
//        builder
//        builder.show();
    }

    public void singleButtonAlertDialog(Context ctx, String title, String message, final MaterialDialog.ButtonCallback cb) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);
        if (title == null||(title.trim().length()==0)) {
             builder.title(APP_NAME);
        }else{
            builder.title(title);
        }
        builder.content(message);
        builder.positiveText("Ok");
        if (message.contains("Are you sure"))
            builder.negativeText("Cancel");
        builder.positiveColorRes(R.color.dark_grey);
        builder.negativeColorRes(R.color.dark_grey);
        builder.callback(cb);
        builder.cancelable(false);
        builder.show();

    }




    public RESTHelper getBaseClassService(String url, boolean isIncludeAuthtokenHeader, Context ctx) {


        getDateInBase64();



        HashMap<String, String> headersMap = new HashMap<>();

        TimeZone tz = TimeZone.getDefault();


        headersMap.put("X-APP-VERSION", "1.0.0");
        headersMap.put("os-type", "android");
        headersMap.put("Content-Type", "application/json");
        headersMap.put("Accept","application/json");
        headersMap.put("X-APP-TIMEZONE",tz.getID());
        headersMap.put("authorization",getDateInBase64());


        if (isIncludeAuthtokenHeader) {
            SharedPreferenceHandle sharedPreferenceHandle = SharedPreferenceHandle.getSharedPreferenceHandle(ctx);
            headersMap.put("X-HEADER-AUTHTOKEN", sharedPreferenceHandle.getString(GlobalConstants.AUTH_TOKEN, EMPTY_STRING));
            headersMap.put("refreshToken", sharedPreferenceHandle.getString(GlobalConstants.REFRESH_TOKEN, EMPTY_STRING));
        }

        return new RetroHelper().getAdapter(ctx, url, headersMap).create(RESTHelper.class);
    }



    private String getDateInBase64() {
        String base64 = null;
        SimpleDateFormat df
                = new SimpleDateFormat( "yyyy-MM-dd'T'hh:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        String dateInStringFormat=df.format(new Date());

        try {

           byte[] data = dateInStringFormat.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);

            Log.i("Base 64 ", base64);

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }
        return base64.trim();
    }








    public void showUpdateAlert(final Context ctx)
    {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(ctx);
        builder.theme(Theme.LIGHT);

        builder.content("We are constantly updating the app for happy customers. Please install the latest version of the app from App Store.");

        builder.positiveText("PlayStore");

        builder.positiveColorRes(R.color.dark_grey);
        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onAny(MaterialDialog dialog) {
                super.onAny(dialog);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + ctx.getPackageName()));
                ctx.startActivity(intent);
            }
        });
        builder.cancelable(false);
        builder.show();



    }




    /**
     * convert input stream to string
     *
     * @param is - input stream
     * @return - string
     * @throws UnsupportedEncodingException
     */
    public String convertStreamToString(InputStream is)
            throws UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void serviceCallFailermsg(Throwable error, Context context) {

        String errorDesc;
        if (error instanceof IOException) {
            errorDesc = context.getString(R.string.networkmessage);
        } else if (error instanceof IllegalStateException) {
//            errorDesc = context.getString(R.string.server_error_message);
            errorDesc = context.getString(R.string.networkmessage);
        } else {
            errorDesc = error.getLocalizedMessage();
        }
        singleButtonAlertDialog(context, context.getResources().getString(R.string.app_name), errorDesc);
    }

    public boolean emailValidation(CharSequence emailId) {
        String emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

        Pattern pattern = Pattern.compile(emailPattern);

        if (pattern.matcher(emailId).matches()) {
            return true;
        }
        Log.d("mail", "false");
        return false;
    }


    /**
     * Creates a file for the image captured and returns it
     *
     * @param image the captured image
     */
    public File getFile(Bitmap image) {
        File file = null;

        final String DEFAULT_CURRENT_DIRECTORY = GlobalConstants.baseUrl;
        try {
            File root = new File(DEFAULT_CURRENT_DIRECTORY);
            root.mkdirs();
            String imagePath = "temp.png";
            file = new File(root, imagePath);
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }


    private static int getDensity(Context ctx)
    {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();


        switch (metrics.densityDpi)
        {
            case 560:
                return 200;
            case 480:
                return 300;
            case 320:
                return 700;
            case 240:
                return 700;
            case 160:
                return 700;
            default:
                return 700;
        }


    }



}
