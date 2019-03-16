package com.example.administrator.test.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.R;
import com.example.administrator.test.TaskData;
import com.example.administrator.test.adapters.HeteroAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeteroFragment extends Fragment
{
    List<R_Data> tasks;

    private RecyclerView hetero_View;

    public HeteroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_hetero, container, false);

        Log.d("HeteroFragment: ", "Created");

        tasks = new ArrayList<>();

        initRecyclerView(currentView);

        return currentView;
    }

    void initRecyclerView(View currentView)
    {
        hetero_View = currentView.findViewById(R.id.hetero_recycleView);
        hetero_View.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTasks();
        HeteroAdapter heteroAdapter = new HeteroAdapter(getActivity(),tasks);
        hetero_View.setAdapter(heteroAdapter);
//        heteroAdapter.notifyDataSetChanged();
    }

    void getTasks()
    {
        tasks = new ArrayList<>();
        tasks.add(new R_Data(new TaskData(),0,"Assigned Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "14 Holes","01:00AM"),1,"Assigned Task"));
        tasks.add(new R_Data(new TaskData(),0,"Completed Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "00/00(NA)","12:36AM"),2,"Completed Task"));
       /* tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "",false),0,"Assigned Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "",false),0,"Assigned Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "",true),0,"Completed Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "",true),0,"Completed Task"));
        tasks.add(new R_Data(new TaskData("Drilling", "CA3845", "",true),0,"Completed Task"));*/

    }

    public class R_Data
    {
        public TaskData taskData;
        public int type;
        public String label;

        R_Data(TaskData taskData, int type, String label)
        {
            this.taskData = taskData;
            this.type = type;
            this.label = label;
        }


    }

}
