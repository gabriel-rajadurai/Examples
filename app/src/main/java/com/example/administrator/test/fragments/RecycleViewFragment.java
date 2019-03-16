package com.example.administrator.test.fragments;


import android.os.Bundle;
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
import com.example.administrator.test.adapters.RecyclerViewAdapter;
import com.example.administrator.test.common.ClickListener;
import com.example.administrator.test.common.RecyclerTouchListener;
import com.example.administrator.test.common.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleViewFragment extends Fragment
{
    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;

    private List<RowElementData> data;

    private Utils utils;






    public RecycleViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        //Initialize variables
        data = new ArrayList<>();
        recyclerView = currentView.findViewById(R.id.recycler_view);
        utils = new Utils();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnItemTouchListener(setRecyclerTouchListener());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        return currentView;
    }

    public List<RowElementData> getData()
    {
        data = new ArrayList<>();
        String[] names = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q"};
        for(int i=0;i<names.length;i++)
        {
            RowElementData current = new RowElementData();
            current.name = names[i];
            data.add(current);
        }
        return data;
    }

    private RecyclerTouchListener setRecyclerTouchListener()
    {
        return new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                utils.singleButtonAlertDialog(getContext(),"Test", "You clicked on "+data.get(position).name);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
    }

}
