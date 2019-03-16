package com.example.administrator.test;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import java.io.Serializable;

/**
 * Created by Administrator on 7/27/2017.
 */


public class RowElementData implements Serializable
{
    public String name;
    public int selectedID;
    @DrawableRes public  int colorCode;
}
