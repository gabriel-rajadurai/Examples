package com.example.administrator.test.sqlite_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 8/22/2017.
 */

public class SQLDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Test.db";


    //Sql statements
    private static final String CREATE_TABLE =
            "CREATE TABLE " + SqlReaderContract.SqlEntry.TABLE_NAME + " (" +
                    SqlReaderContract.SqlEntry._ID + " INTEGER PRIMARY KEY," +
                    SqlReaderContract.SqlEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    SqlReaderContract.SqlEntry.COLUMN_NAME_AGE + " INTEGER)";

    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + SqlReaderContract.SqlEntry.TABLE_NAME;

    public SQLDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
