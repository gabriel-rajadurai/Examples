package com.example.administrator.test.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.RowElementData;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 7/28/2017.
 */

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.Ch_ViewHolder> {

    LayoutInflater inflater;
    List<RowElementData> data = Collections.emptyList();

    private static final String TAG = "RecyclerViewAdapter2";
    
    public RecyclerViewAdapter2(Context context, List<RowElementData> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;

    }
    @Override
    public Ch_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d("Viewholder", "created");
        View view = inflater.inflate(R.layout.r_layout,parent,false);
        return new Ch_ViewHolder(view,data);

    }

    @Override
    public void onBindViewHolder(final Ch_ViewHolder holder, final int position)
    {
        final RowElementData currentData = data.get(position);
        holder.rowElement.setText(currentData.name);
        Log.d("Color", "being set");
        //holder.img.setImageResource(currentData.colorCode);
        holder.img.setImageResource(currentData.colorCode);
        Log.d("Color", "set");
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (i == R.id.yes)
                {
                    currentData.selectedID = 0;

                    Log.d("Change in: ", currentData.name);
                    //holder.radioGroup.check(R.id.yes);
                }
                else if(i == R.id.no)
                {
                    currentData.selectedID = 1;

                    Log.d("Change in: ", currentData.name);
                    //holder.radioGroup.check(R.id.no);
                }
                else
                {
                    currentData.selectedID = 2;
                }
            }

        });

        if(currentData.selectedID == 0 )
        {
            holder.radioGroup.clearCheck();
            holder.radioGroup.check(R.id.yes);
            Log.d("C_Data",holder.rowElement.getText().toString());
        }
        else if((currentData.selectedID == 1))
        {

            holder.radioGroup.clearCheck();
            holder.radioGroup.check(R.id.no);
            Log.d("C_Data NO",holder.rowElement.getText().toString());
        }
        else
        {
            holder.radioGroup.clearCheck();
        }

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+data.size());
        return data.size();
    }

    class Ch_ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rowElement;
        RadioGroup radioGroup;
        ImageView img;


        public Ch_ViewHolder(View itemView, final List<RowElementData> data) {
            super(itemView);
            rowElement = itemView.findViewById(R.id.tv_element);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            img = itemView.findViewById(R.id.img_element);

        }

    }
}
