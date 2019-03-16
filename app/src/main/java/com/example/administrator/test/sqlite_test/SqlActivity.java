package com.example.administrator.test.sqlite_test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.common.Utils;

public class SqlActivity extends AppCompatActivity
{
    SQLDbHelper sqlDbHelper;
    SQLiteDatabase db;
    Utils utils;

    EditText et_insertName, et_insertAge, et_updateName, et_updateAge, et_getName;
    TextView tv_getAge;

    Button bt_insert, bt_update, bt_get, bt_drop;

    private String TAG = "SqlActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        //initialize
        sqlDbHelper = new SQLDbHelper(this);
        utils = new Utils();

        et_insertName = (EditText)findViewById(R.id.et_sqlName);
        et_insertAge = (EditText)findViewById(R.id.et_sqlAge);
        et_updateName = (EditText)findViewById(R.id.et_sqlupdateName);
        et_updateAge = (EditText)findViewById(R.id.et_sqlupdateAge);
        tv_getAge = (TextView)findViewById(R.id.tv_sqlgetAge);
        et_getName = (EditText)findViewById(R.id.et_sqlgetName);

        bt_insert = (Button)findViewById(R.id.bt_insertSql);
        bt_update = (Button)findViewById(R.id.bt_updateSql);
        bt_get = (Button)findViewById(R.id.bt_getSql);
        bt_drop = (Button)findViewById(R.id.bt_dropSql);

        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_intoDB();
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDB();
            }
        });
        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfromDB();
            }
        });
        bt_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropDB();
            }
        });
    }

    private void updateDB()
    {
        if(validateData("Update"))
        {
            db = sqlDbHelper.getWritableDatabase();
            if(db==null)
            {
                utils.singleButtonAlertDialog(this,"SQL test","No database present currently!!");
                return;
            }
            ContentValues values = new ContentValues();
            values.put(SqlReaderContract.SqlEntry.COLUMN_NAME_AGE, et_updateAge.getText().toString());
            String selection = SqlReaderContract.SqlEntry.COLUMN_NAME_USERNAME + "= ?";
            String[] selectionArgs = {et_updateName.getText().toString()};
            db.update(SqlReaderContract.SqlEntry.TABLE_NAME,values,selection,selectionArgs);
            db.close();
        }
        else
        {
            utils.singleButtonAlertDialog(this,"SQL test","You can not leave fields empty!!");
        }
    }

    private void getfromDB()
    {
        if(validateData("Get"))
        {
            db = sqlDbHelper.getReadableDatabase();
            if(db==null)
            {
                utils.singleButtonAlertDialog(this,"SQL test","No database present currently!!");
                return;
            }
            String[] projection = {
                    SqlReaderContract.SqlEntry._ID,
                    SqlReaderContract.SqlEntry.COLUMN_NAME_USERNAME,
                    SqlReaderContract.SqlEntry.COLUMN_NAME_AGE};
            String selection = SqlReaderContract.SqlEntry.COLUMN_NAME_USERNAME + "= ?";
            String[] selectionArgs = new String[]{et_getName.getText().toString()};
            //String sortOrder = SqlReaderContract.SqlEntry.COLUMN_NAME_AGE + " DESC";
            Cursor cursor = db.query(
                    SqlReaderContract.SqlEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);


            int age = 0;
            while (cursor.moveToNext())
            {
                age = cursor.getInt(cursor.getColumnIndex("age"));

            }
            tv_getAge.setText("Age: "+ age);
            cursor.close();

            db.close();
        }
        else
        {
            utils.singleButtonAlertDialog(this,"SQL test","You can not leave fields empty!!");
        }
    }

    private void dropDB()
    {
        db = sqlDbHelper.getWritableDatabase();
        db.delete(SqlReaderContract.SqlEntry.TABLE_NAME,null,null);
    }


    void insert_intoDB()
    {
        if(validateData("Insert"))
        {
            db = sqlDbHelper.getWritableDatabase();

            if(db==null)
            {
                utils.singleButtonAlertDialog(this,"SQL test","No database present currently!!");
                return;
            }

            //Values
            ContentValues values = new ContentValues();
            values.put(SqlReaderContract.SqlEntry.COLUMN_NAME_USERNAME, et_insertName.getText().toString());
            values.put(SqlReaderContract.SqlEntry.COLUMN_NAME_AGE, Integer.parseInt(et_insertAge.getText().toString()));
            db.insert(SqlReaderContract.SqlEntry.TABLE_NAME,null,values);
            db.close();
        }
        else
        {
            utils.singleButtonAlertDialog(this,"SQL test","You can not leave fields empty!!");
        }
    }

    boolean validateData(String opName)
    {
        Log.d(TAG, "validateData: " + et_insertName.getText());
        switch (opName)
        {
            case "Insert":
                if(et_insertAge.getText().toString().equals("")||et_insertName.getText().toString().equals(""))
                {
                    return false;
                }
                break;
            case "Update":
                if(et_updateAge.getText().toString().equals("")||et_updateName.getText().toString().equals(""))
                {
                    return false;
                }
                break;
            case "Get":
                if(et_getName.getText().toString().equals(""))
                    return false;
                break;
            default:
                break;
        }
        return true;
    }
}
