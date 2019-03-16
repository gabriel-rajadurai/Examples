package com.example.administrator.test.expandable_recyclerview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.FragActivity;
import com.example.administrator.test.R;
import com.example.administrator.test.expandable_recyclerview.models.SubListData;
import com.example.administrator.test.expandable_recyclerview.models.TitleData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableRecyclerFragment extends Fragment
{

    private RecyclerView expandableRecycleView;
    private View rootView;
    private List<TitleData> title = new ArrayList<>();
    private ExpandableRecyleAdapter adapter;
    FragActivity activity;


    public ExpandableRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_expandable_recycler, container, false);
        populateData();
        setupRecyclerView();
        return rootView;
    }

    private void populateData()
    {
        List<SubListData> subData = new ArrayList<>();

        //First group
        subData.add(new SubListData("First", "0541", "Parent 1"));
        subData.add(new SubListData("Second", "0542", "Parent 1"));
        subData.add(new SubListData("Third", "0543", "Parent 1"));
        subData.add(new SubListData("Fourth", "0544", "Parent 1"));
        subData.add(new SubListData("Fifth", "0545", "Parent 1"));
        title.add(new TitleData("Parent 1", subData));

        //Second group
        subData = new ArrayList<>();
        subData.add(new SubListData("Child A", "054A", "Parent A"));
        subData.add(new SubListData("Child B", "054B", "Parent A"));
        subData.add(new SubListData("Child C", "054C", "Parent A"));
        subData.add(new SubListData("Child D", "054D", "Parent A"));
        title.add(new TitleData("Parent A", subData));

        //Third group
        subData = new ArrayList<>();
        subData.add(new SubListData("Part 1", "054PA", "Group A"));
        subData.add(new SubListData("Part 2", "054PB", "Group A"));
        subData.add(new SubListData("Part 3", "054PC", "Group A"));
        subData.add(new SubListData("Part 4", "054PD", "Group A"));
        subData.add(new SubListData("Part 5", "054PE", "Group A"));
        subData.add(new SubListData("Part 6", "054PF", "Group A"));
        title.add(new TitleData("Group A ", subData));


        //Fourth group
        subData = new ArrayList<>();
        subData.add(new SubListData("BAB", "054PBA", "Parent B"));
        subData.add(new SubListData("BBC", "054PBB", "Parent B"));
        subData.add(new SubListData("BCD", "054PBC", "Parent B"));
        subData.add(new SubListData("BCE", "054PBC", "Parent B"));
        subData.add(new SubListData("BCF", "054PBC", "Parent B"));
        title.add(new TitleData("Parent B ", subData));

        //Fifth group
        subData = new ArrayList<>();
        subData.add(new SubListData("XA", "054TA", "Title A"));
        subData.add(new SubListData("XB", "054TB", "Title A"));
        subData.add(new SubListData("XD", "054TC", "Title A"));
        subData.add(new SubListData("XE", "054TC", "Title A"));
        subData.add(new SubListData("XF", "054TC", "Title A"));
        title.add(new TitleData("Title A ", subData));

        //Sixth group
        subData = new ArrayList<>();
        subData.add(new SubListData("XGA", "054GBA", "Group B"));
        subData.add(new SubListData("XGB", "054GBB", "Group B"));
        subData.add(new SubListData("XGC", "054GBC", "Group B"));
        subData.add(new SubListData("XGD", "054GBC", "Group B"));
        subData.add(new SubListData("XGE", "054GBC", "Group B"));
        subData.add(new SubListData("XGF", "054GBC", "Group B"));
        subData.add(new SubListData("XGG", "054GBC", "Group B"));
        subData.add(new SubListData("XGH", "054GBC", "Group B"));
        title.add(new TitleData("Group B ", subData));

        //Seventh group
        subData = new ArrayList<>();
        subData.add(new SubListData("XP2A", "054P2A", "Parent 2"));
        subData.add(new SubListData("XP2B", "054P2B", "Parent 2"));
        subData.add(new SubListData("XP2C", "054P2C", "Parent 2"));
        title.add(new TitleData("Parent 2 ", subData));

        //Eighth group
        subData = new ArrayList<>();
        subData.add(new SubListData("XTBA", "054TBA", "Title B"));
        subData.add(new SubListData("XTBB", "054TBB", "Title B"));
        subData.add(new SubListData("XTBC", "054TBC", "Title B"));
        title.add(new TitleData("Title B ", subData));





    }

    private void setupRecyclerView()
    {
        expandableRecycleView = rootView.findViewById(R.id.expandable_recycleview);
        expandableRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        expandableRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        adapter = new ExpandableRecyleAdapter(getContext(),title);
        expandableRecycleView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
}
