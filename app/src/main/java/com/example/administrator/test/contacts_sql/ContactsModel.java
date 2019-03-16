package com.example.administrator.test.contacts_sql;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 8/23/2017.
 */

public class ContactsModel implements Cloneable {
    public Bitmap profilePic;
    public String Name, Phone, Email;

    public ContactsModel(Bitmap profilePic, String name, String phone, String email) {
        this.profilePic = profilePic;
        Name = name;
        Phone = phone;
        Email = email;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
