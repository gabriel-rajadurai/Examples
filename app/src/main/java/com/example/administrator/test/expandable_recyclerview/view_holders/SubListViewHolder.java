package com.example.administrator.test.expandable_recyclerview.view_holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.Utils;
import com.example.administrator.test.expandable_recyclerview.models.SubListData;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Administrator on 8/30/2017.
 */

public class SubListViewHolder extends ChildViewHolder implements View.OnClickListener
{
    public TextView subData;
    private SubListData data;
    private Utils utils;
    private Context context;

    public SubListViewHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        subData = itemView.findViewById(R.id.recycle_list_subdata);
        utils = new Utils();
        this.context = context;

    }

    public void setSubData(SubListData data)
    {
        subData.setText(data.getSubListData());
    }

    public void bindData(SubListData data)
    {
        this.data = data;
        setSubData(data);
    }

    @Override
    public void onClick(View view)
    {
        utils.singleButtonAlertDialog(context,"Exp Recycler view",
                "Child details : \n Name: " + data.subListData +
                "\n ID: " + data.id +
                "\n Parent: " + data.parentName);
    }
}
