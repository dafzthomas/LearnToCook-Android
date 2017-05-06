package com.apps.dafz.learntocook.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context)
    {
        // just passes specific info to the parent class constructor
        // Note that getDatabasePath() gets a File reference to
        // the default folder for this app's databases (usually
        // like "/data/.../this.app.name/databases"
        super(context,
                context.getDatabasePath(Contract.DB_NAME).getAbsolutePath(),
                null, DATABASE_VERSION);
    }
    // if the table doesn't exist, builds one - only gets invoked if
// there is no DB, so the SQL "IF NOT EXISTS" clause is overkill.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // only creates the DB if it does not already exist
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + Contract.Example.TABLE_NAME + " (" +
                        Contract.Example.COLNAME_ID +
                        " integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        Contract.Example.COLNAME_NAME + " varchar(32) DEFAULT NULL, " +
                        Contract.Example.COLNAME_TEXT + " varchar(32) DEFAULT NULL)"
        );
        // puts a single test record into the new DB
        // so we never have an empty DB
        ContentValues cv = new ContentValues();
        cv.put(Contract.Example.COLNAME_NAME, "Cheesecakse");
        cv.put(Contract.Example.COLNAME_TEXT, "Just make the damned thing!");
        db.insert(Contract.Example.TABLE_NAME, null, cv);
    }

    // if the version number of our DB (defined above) ever changes, this
// method is called by the helper setup and our old DB will be removed
// and replaced by whatever onCreate() builds for us.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
// dumps the table and rebuilds it - losing all previous records (yikes!)
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Example.TABLE_NAME);
        onCreate(db);
    }

    // Convenience method, not a standard part of a DBHelper - it seemed
// like a good idea to put the detailed database access code in one place.
// Just grabs the whole list - it would be easy to get it sorted by
// height or by name by adding some selection clauses. See the
// documentation for SQLiteDatabase.query().
    public Cursor getAll(SQLiteDatabase db)
    {
        // describes the record structure
        String[] columns = new String[]{"_id",
                Contract.Example.COLNAME_NAME,
                Contract.Example.COLNAME_TEXT};
        // gives the query the table name and the list of columns
        return db.query(Contract.Example.TABLE_NAME, columns,
                null, null, null, null, null, null);
    }
}

