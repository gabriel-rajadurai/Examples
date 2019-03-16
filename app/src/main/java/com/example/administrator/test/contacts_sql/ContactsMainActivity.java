package com.example.administrator.test.contacts_sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.administrator.test.R;
import com.example.administrator.test.common.Utils;
import com.example.administrator.test.contacts_sql.fragments.ContactsFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class ContactsMainActivity extends AppCompatActivity
{
    ArrayList<ContactsModel> contactsModels;

    ContentResolver contentResolver;




    private static String TAG = "ContactsMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null)
            return;
        setContentView(R.layout.activity_contacts_main);


        populateContactsList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    void populateContactsList()
    {
        contentResolver = getContentResolver();
        contactsModels = new ArrayList<>();
        String sort_order = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,sort_order);



        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                //Get name
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String[] args = {id};
                String number = "Does not exist";
                String email = "No email";
                Bitmap photo = null;

                //Get phone number
                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0) {

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            args,
                            null);
                    if (phoneCursor.getCount() > 0) {
                        while (phoneCursor.moveToNext()) {
                            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phoneCursor.close();
                    }
                }

                //Get email
                Cursor ecursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        args,
                        null);
                if (ecursor.getCount() > 0) {
                    while (ecursor.moveToNext()) {
                        email = ecursor.getString(ecursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    ecursor.close();
                }

                //Get photo

                try {
                    InputStream istream = ContactsContract.Contacts.openContactPhotoInputStream(
                            contentResolver,
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));
                    if (istream != null) {
                        photo = BitmapFactory.decodeStream(istream);
                        istream.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                contactsModels.add(new ContactsModel(photo,name,number,email));

            }
        }
        openContactsFragment(contactsModels);
    }

    private void openContactsFragment(ArrayList<ContactsModel> contactsModels)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = ContactsFragment.newInstance(contactsModels);
        ft.add(R.id.contacts_container,fragment);
        ft.commit();
    }


}

