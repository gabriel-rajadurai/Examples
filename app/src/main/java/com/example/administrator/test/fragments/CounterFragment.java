package com.example.administrator.test.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.GlobalConstants;
import com.example.administrator.test.common.SharedPreferenceHandle;
import com.example.administrator.test.common.Utils;
import com.rba.ui.dialog.MaterialDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class CounterFragment extends Fragment implements GlobalConstants
{
    int count = 0;
    Button add, remove, back, reset;

    TextView cnt;
    private SharedPreferenceHandle mSharedPreferencesHandle;
    private Context context;
    private Utils utils;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Initialize utils class
        utils = new Utils();

        //Get the fragment view and display it
        View Countview = inflater.inflate(R.layout.fragment_counter, container, false);

        //Initialize shared preferences
        mSharedPreferencesHandle = SharedPreferenceHandle.getSharedPreferenceHandle(context);

        //Get count value from sharedPrefs
        count = mSharedPreferencesHandle.getInt(COUNT,0);



        Log.d("Count value init",Integer.toString(count));

        //Initialize count text field
        cnt = Countview.findViewById(R.id.cnt);
        cnt.setText(Integer.toString(count));


        //Initialize the buttons
        add = Countview.findViewById(R.id.add);
        remove = Countview.findViewById(R.id.remove);
        back = Countview.findViewById(R.id.back);
        reset = Countview.findViewById(R.id.reset);

        //Initialize the button click listeners
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        if(count==0)
        {
            remove.setEnabled(false);
        }
        else if(count==10)
        {
            add.setEnabled(false);
        }

        //Return the fragment view
        return Countview;

    }



    //Add button
    void add()
    {
        if(count==10)
        {
            utils.singleButtonAlertDialog(context,getResources().getString(R.string.app_name),"Maximum limit reached!!");
            add.setEnabled(false);
        }
        else if(count<10)
        {
            count++;

            //Disable all other features until add completes its process
            add.setEnabled(false);
            remove.setEnabled(false);
            reset.setEnabled(false);
            back.setEnabled(false);

            //Wait until add process is over
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    //Enable all features after add process is complete
                    add.setEnabled(true);
                    remove.setEnabled(true);
                    reset.setEnabled(true);
                    back.setEnabled(true);
                }
            }, 5000);
        }
        cnt.setText(Integer.toString(count));
        //Save current count value into sharedPrefs
        mSharedPreferencesHandle.putInt(COUNT,count);
    }

    //Remove button
    void remove()
    {
        add.setEnabled(true);
        remove.setEnabled(false);
        if(count>0)
            count--;
        /*if(count==0)
        {
            utils.singelButtonAlertDialog(context,getResources().getString(R.string.app_name),"Minimum limit reached!!");
            remove.setEnabled(false);
        }
        else if(count>0)
        {
            count--;
        }*/

        cnt.setText(Integer.toString(count));
        //Save current count value into sharedPrefs
        mSharedPreferencesHandle.putInt(COUNT,count);
    }

    //Back button
    void back()
    {
        utils.twoButtonAlertDialog(context,getResources().getString(R.string.app_name),"Are you sure you want to go back?",true,new MaterialDialog.ButtonCallback()
        {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                dialog.dismiss();
                //Destroy Activity
                getActivity().finish();
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                dialog.dismiss();
            }
        });
    }

    void reset()
    {

        utils.twoButtonAlertDialog(context,getResources().getString(R.string.app_name),"Are you sure you want to reset the counter?",true,new MaterialDialog.ButtonCallback()
        {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                dialog.dismiss();
                count = 0;
                mSharedPreferencesHandle.putInt(COUNT,count);
                cnt.setText(Integer.toString(mSharedPreferencesHandle.getInt(COUNT,0)));
                add.setEnabled(true);
                remove.setEnabled(false);
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                dialog.dismiss();
            }
        });
    }
}
