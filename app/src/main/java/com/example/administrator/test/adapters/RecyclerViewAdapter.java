package com.example.administrator.test.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.RowElementData;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 7/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RvViewHolder>
{
    LayoutInflater inflater;
    //String[] data = {"one","two","three","four","five","six"};
    List<RowElementData> data = Collections.emptyList();

    public RecyclerViewAdapter(Context context,List<RowElementData> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.row_layout,parent,false);
        RvViewHolder vHolder = new RvViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position)
    {
        RowElementData currentData = data.get(position);
        holder.rowElement.setText(currentData.name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RvViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowElement;
        public RvViewHolder(View itemView) {
            super(itemView);
            rowElement = itemView.findViewById(R.id.rowElement);
        }
    }
}
