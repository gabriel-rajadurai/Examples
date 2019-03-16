package com.example.administrator.test.expandable_recyclerview.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 8/30/2017.
 */

public class SubListData implements Parcelable
{
    public String subListData;
    public String id;
    public String parentName;



    public SubListData(String subListData, String id, String parentName) {
        this.subListData = subListData;
        this.id = id;
        this.parentName = parentName;
    }

    protected SubListData(Parcel in) {
        subListData = in.readString();
    }

    public static final Creator<SubListData> CREATOR = new Creator<SubListData>() {
        @Override
        public SubListData createFromParcel(Parcel in) {
            return new SubListData(in);
        }

        @Override
        public SubListData[] newArray(int size) {
            return new SubListData[size];
        }
    };

    public String getSubListData() {
        return subListData;
    }
    public String getId() {
        return id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setSubListData(String subListData) {
        this.subListData = subListData;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subListData);
    }
}
