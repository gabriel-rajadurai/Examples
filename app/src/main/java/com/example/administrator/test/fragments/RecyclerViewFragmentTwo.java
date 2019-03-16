package com.example.administrator.test.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.test.R;
import com.example.administrator.test.RowElementData;
import com.example.administrator.test.adapters.RecyclerViewAdapter2;
import com.example.administrator.test.common.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragmentTwo extends Fragment
{
    RecyclerView ch_view;
    private List<RowElementData> data;
    private Utils utils;
    private RecyclerViewAdapter2 rAdapter;
    private Button submit;



    String path;

    private static final String TAG = "RecyclerViewFragmentTwo";

    public RecyclerViewFragmentTwo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_recycler_view_fragment_two, container, false);
        Log.d("Fragment2", "created");

        //Initialize all variables
        submit = currentView.findViewById(R.id.b_submit);
        data = new ArrayList<>();
        utils = new Utils();
        path = getContext().getApplicationInfo().dataDir+"/test.data";



        initRecyclerView(currentView);

        //Initialize button listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbmit();
            }
        });

        //Check to see if external storage is available or if it is read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            submit.setEnabled(false);
        }

        return currentView;
    }


    private void initRecyclerView(View currentView) {

        ch_view = currentView.findViewById(R.id.ch_view);
        ch_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        ch_view.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        rAdapter = new RecyclerViewAdapter2(getActivity(), data);
        ch_view.setAdapter(rAdapter);

        Log.d(TAG, "initRecyclerView: "+data.size());

        getData();
        rAdapter.notifyDataSetChanged();
        Log.d(TAG, "initRecyclerView: "+data.size());
    }

    public void getData()
    {

        String[] names = {"A","B","C","D","E","F","G","H","I","J","K","L","M","A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1","L1","M1"};

        for(int i=0;i<names.length;i++) {
            RowElementData current = new RowElementData();
            current.name = names[i];
            current.selectedID = 2;
            if (i % 3 == 1) {
                current.colorCode = R.drawable.red_box;
            } else if (i % 3 == 2) {
                current.colorCode = R.drawable.green_box;
            } else {
                current.colorCode = R.drawable.blue_box;
            }
            data.add(current);

//        try
//        {
//            File test = new File(path);
//            FileInputStream fis = new FileInputStream(test);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            Log.d("File Read: ","File exists");
//            data = (ArrayList<RowElementData>)ois.readObject();
//            //Log.d("Data", data.get(2).name);
//            ois.close();
//        }
//        catch (FileNotFoundException  | ClassNotFoundException e )
//        {
//            String[] names = {"A","B","C","D","E","F","G","H","I","J","K","L","M","A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1","L1","M1"};
//
//            for(int i=0;i<names.length;i++)
//            {
//                RowElementData current = new RowElementData();
//                current.name = names[i];
//                current.selectedID = 2;
//                if(i%3==1)
//                {
//                    current.colorCode = R.drawable.red_box;
//                }
//                else if(i%3==2)
//                {
//                    current.colorCode = R.drawable.green_box;
//                }
//                else
//                {
//                    current.colorCode = R.drawable.blue_box;
//                }
//                data.add(current);
//            }
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//
//        return data;
        }
    }

    public void sbmit()
    {
        String text = "You have selected, \n";
        for (RowElementData cData:data)
        {
            if(cData.selectedID==0)
            {
                text+=cData.name+"\n";
            }
        }
        utils.singleButtonAlertDialog(getContext(),"Test", text);

        //Write to file

        File test = new File(path);
        try
        {
            FileOutputStream fos = new FileOutputStream(test);
            Log.d("File Write: ","Writing to file");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}
