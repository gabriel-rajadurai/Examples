package com.example.administrator.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.test.fragments.HeteroFragment;
import com.example.administrator.test.R;

import java.util.List;

/**
 * Created by Administrator on 8/1/2017.
 */

public class HeteroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    LayoutInflater inflater;
    List<HeteroFragment.R_Data> data;

    private static final String TAG = "HeteroAdapter";

    public HeteroAdapter(Context context, List<HeteroFragment.R_Data> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        switch(viewType)
        {
            case 0:
                View labelView = inflater.inflate(R.layout.label_layout,parent,false);
                viewHolder = new Label_ViewHolder(labelView);
                break;
            case 1:
                View assignView = inflater.inflate(R.layout.assigned_task,parent,false);
                viewHolder = new Assignedtask_ViewHolder(assignView);
                break;
            case 2:
                View compView = inflater.inflate(R.layout.completed_task,parent,false);
                viewHolder = new Comptask_ViewHolder(compView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (holder.getItemViewType())
        {
            case 0:
                Label_ViewHolder lholder = (Label_ViewHolder)holder;
                lholder.label.setText(data.get(position).label);
                break;
            case 1:
                Assignedtask_ViewHolder aholder =(Assignedtask_ViewHolder)holder;
                HeteroFragment.R_Data r_data = data.get(position);
                aholder.asgn_actName.setText(r_data.taskData.activityName);
                aholder.asgn_location.setText(r_data.taskData.location);
                aholder.asgn_expCount.setText(r_data.taskData.expCount);
                aholder.asgn_strtTime.setText(r_data.taskData.startTime);
                break;
            case 2:
                Comptask_ViewHolder cholder = (Comptask_ViewHolder)holder;
                HeteroFragment.R_Data rdata = data.get(position);
                cholder.comp_actName.setText(rdata.taskData.activityName);
                cholder.comp_location.setText(rdata.taskData.location);
                cholder.comp_expCount.setText(rdata.taskData.expCount);
                cholder.comp_strtTime.setText(rdata.taskData.startTime);
                break;
            default:
                break;

        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + data.size());
        return data.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return data.get(position).type;
    }


    public class Label_ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView label;
        public Label_ViewHolder(View itemView)
        {
            super(itemView);
            label = itemView.findViewById(R.id.task_label);
        }
    }

    public class Assignedtask_ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView asgn_actName;
        public TextView asgn_location;
        public TextView asgn_expCount;
        public TextView asgn_strtTime;

        public Assignedtask_ViewHolder(View itemView)
        {
            super(itemView);
            asgn_actName = itemView.findViewById(R.id.tv_asgn_actName);
            asgn_location = itemView.findViewById(R.id.tv_asgn_location);
            asgn_expCount = itemView.findViewById(R.id.tv_asgn_expCount);
            asgn_strtTime = itemView.findViewById(R.id.tv_asgn_startTime);
        }
    } public class Comptask_ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView comp_actName;
        public TextView comp_location;
        public TextView comp_expCount;
        public TextView comp_strtTime;

        public Comptask_ViewHolder(View itemView)
        {
            super(itemView);
            comp_actName = itemView.findViewById(R.id.tv_comp_actName);
            comp_location = itemView.findViewById(R.id.tv_comp_location);
            comp_expCount = itemView.findViewById(R.id.tv_comp_expCount);
            comp_strtTime = itemView.findViewById(R.id.tv_comp_startTime);
        }
    }
}
