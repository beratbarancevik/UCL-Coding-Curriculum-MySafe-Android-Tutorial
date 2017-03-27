package com.mysafe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mysafe.ImageContract.ImageEntry;

public class ImageDbHelper extends SQLiteOpenHelper {

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "mysafedb.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ImageDbHelper}.
     *
     * @param context of the app
     */
    public ImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the images table
        String SQL_CREATE_IMAGES_TABLE = "CREATE TABLE " + ImageEntry.TABLE_NAME + " ("
                                         + ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                         + ImageEntry.COLUMN_IMAGE_PATH + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_IMAGES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
