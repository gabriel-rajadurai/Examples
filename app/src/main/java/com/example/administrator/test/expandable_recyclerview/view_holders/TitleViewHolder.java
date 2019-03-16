package com.example.administrator.test.expandable_recyclerview.view_holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by Administrator on 8/30/2017.
 */

public class TitleViewHolder extends GroupViewHolder
{
    private TextView Title;
    private ImageView imgExpandCollapse;
    private Context context;

    public TitleViewHolder(View itemView,Context context) {
        super(itemView);
        Title = itemView.findViewById(R.id.recycle_list_title);
        imgExpandCollapse = itemView.findViewById(R.id.img_expand_close);
        this.context = context;
    }

    public void setTitle(ExpandableGroup group)
    {
        Title.setText(group.getTitle());
    }

    @Override
    public void expand()
    {
        imgExpandCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_less_24dp));
    }

    @Override
    public void collapse()
    {
        imgExpandCollapse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_more_24dp));
    }
}
