package com.example.administrator.test.expandable_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.R;
import com.example.administrator.test.common.Utils;
import com.example.administrator.test.expandable_recyclerview.models.SubListData;
import com.example.administrator.test.expandable_recyclerview.view_holders.SubListViewHolder;
import com.example.administrator.test.expandable_recyclerview.view_holders.TitleViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;


import java.util.List;

/**
 * Created by Administrator on 8/30/2017.
 */

public class ExpandableRecyleAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder,SubListViewHolder>
{
    private Context context;
    private static final String TAG = "ExpandableRecyleAdapter";

    public ExpandableRecyleAdapter(Context context,List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
    }

    @Override
    public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.title_group_layout,parent,false);
        return new TitleViewHolder(titleView,context);
    }

    @Override
    public SubListViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View childView = inflater.inflate(R.layout.data_child_layout,parent,false);
        return new SubListViewHolder(childView, context);
    }

    @Override
    public void onBindChildViewHolder(SubListViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex)
    {
        SubListData data = (SubListData) group.getItems().get(childIndex);
        holder.bindData(data);

    }

    @Override
    public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group)
    {
        holder.setTitle(group);
    }




}
