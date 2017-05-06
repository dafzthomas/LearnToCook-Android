package com.apps.dafz.learntocook.helpers;

import android.provider.BaseColumns;

public final class Contract
{
    // DB path is given by "context.getDatabasePath()"
    public static final String DB_NAME = "MyRecipes.db";

    // it's a non-instantiatable class - so we hide the constructor
    private Contract()
    {
    }

    // Each table in the DB can be represented as an easy-to-share
    // resource of constants.
    // This contract class can be used by other parts of the Android
    // data-provision architecture to help define valid services.
    public static class Example implements BaseColumns
    {
        public static final String TABLE_NAME = "MyRecipes";
        public static final String COLNAME_ID = "_id";
        public static final String COLNAME_NAME = "name";
        public static final String COLNAME_TEXT = "text";
    }
}

