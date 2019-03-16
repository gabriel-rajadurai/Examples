package com.example.administrator.test.sqlite_test;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 8/22/2017.
 */

public final class SqlReaderContract
{
    private SqlReaderContract(){}

    public static class SqlEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "testable";
        public static final String COLUMN_NAME_USERNAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
