package com.example.administrator.test.expandable_recyclerview.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Administrator on 8/30/2017.
 */

public class TitleData extends ExpandableGroup<SubListData>
{
    public TitleData(String title, List<SubListData> items) {
        super(title, items);
    }
}
