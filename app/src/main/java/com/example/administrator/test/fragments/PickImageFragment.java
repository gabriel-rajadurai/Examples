package com.example.administrator.test.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.test.FragActivity;
import com.example.administrator.test.MainActivity;
import com.example.administrator.test.R;
import com.rapidbizapps.android.pickAPic.OnImageSelect;
import com.rapidbizapps.android.pickAPic.PikAPic;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickImageFragment extends Fragment implements OnImageSelect{


    private ImageView img_picked;
    private Button bt_pickImg;
    private PikAPic pikAPic;
    final String TAG = "PickImageFragment";

    public PickImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_pick_image, container, false);

        //Initailize
        img_picked = currentView.findViewById(R.id.img_picked);
        bt_pickImg = currentView.findViewById(R.id.bt_pickImg);
        pikAPic = new PikAPic.Builder(getContext()).setDialogTitle("Choose your pic").setCallback(PickImageFragment.this).build();

        //Initialize listeners
        bt_pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                pikAPic.pickImage();
            }
        });

        return currentView;
    }

    @Override
    public void onImageSelectionSuccess(String filePath, Bitmap imgBitmap)
    {
        img_picked.setImageBitmap(imgBitmap);
        Log.d(TAG, "onImageSelectionSuccess: " + filePath);
    }

    @Override
    public void onSelectionError(String err) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}
