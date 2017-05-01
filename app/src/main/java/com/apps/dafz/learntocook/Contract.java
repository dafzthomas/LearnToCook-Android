package com.apps.dafz.learntocook;

import android.provider.BaseColumns;

public final class Contract
{
    // DB path is given by "context.getDatabasePath()"
    public static final String DB_NAME = "my_recipes.db";

    // it's a non-instantiatable class - so we hide the constructor
    private Contract()
    {
    }

    // Each table in the DB can be represented as an easy-to-share resource of constants
    // This is in line with the general trend to abstract literal text out of the system
    // so why don't we use String.xml?
    // This contract class can be used by other parts of the Android data-provision architecture
    // to help define valid services.
    public static class Example implements BaseColumns
    {
        public static final String TABLE_NAME = "demo";
        public static final String COLNAME_ID = "_id";
        public static final String COLNAME_NAME = "name";
        public static final String COLNAME_HEIGHT = "height";
    }
}

