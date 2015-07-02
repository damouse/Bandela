package wings.bandela;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng Zheng on 06/29/2015
 *  used to preserve position info and Gimbal beacons infos.
 *  and operates on the database:
 *  add Gimbals to GimbalTable or add Place to PlaceTable
 *  clear all entry in GimbalTable or PlaceTable
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final String TAG = "Bandela";
    //TAG used to output infos to logcat

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GimbalDatabase";
    //database name

    private static final String TABLE_GIMBALS = "gimbals";
    private static final String TABLE_PLACE = "places";
    //table name

    public static final String COLUMN_GIMBAL_ID = "gimbalId";
    public static final String COLUMN_GIMBAL_CONTENT = "gimbalContent";
    public static final String COLUMN_GIMBAL_TYPE = "gimbalType";
    //Gimbal table attribute name

    public static final String COLUMN_PLACE_ID = "placeId";
    public static final String COLUMN_PLACE_TITLE = "placeTitle";
    public static final String COLUMN_PLACE_BODY = "placeBody";
    public static final String COLUMN_PLACE_DATE_SEC = "placeDateSeconds";
    //Place table attribute name

    /**
     * Default constructor.
     * @param context
     */
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //*************************************************************************
    // Creating tables in this app's database.
    //*************************************************************************
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v(TAG, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Log.v(TAG, "233$$ onCreate called: creating database.");
        Log.v(TAG, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        // creating Gimbal table SQLite query.
        String CREATE_GIMBAL_TABLE = "CREATE TABLE " +
                TABLE_GIMBALS + "("
                + COLUMN_GIMBAL_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_GIMBAL_CONTENT + " TEXT,"
                + COLUMN_GIMBAL_TYPE + " INTEGER)";

        // creating place table SQLite query.
        String CREATE_PLACE_TABLE = "CREATE TABLE " +
                TABLE_PLACE + "("
                + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PLACE_TITLE + " TEXT,"
                + COLUMN_PLACE_BODY + " TEXT,"
                + COLUMN_PLACE_DATE_SEC + " INTEGER)";



        // All tables are created when database is initialized.
        db.execSQL(CREATE_GIMBAL_TABLE);
        db.execSQL(CREATE_PLACE_TABLE);
        Log.v(TAG, "****** START: Creating sample Gimbals.");

        Log.v(TAG, "****** END: Creating sample Gimbals.");
        //
    }


    //sending message to logcat

    //*************************************************************************
    // The onUpgrade() method is called when the handler is invoked with a
    // greater database version number from the one previously used. We will
    // simply remove the old database and create a new one.
    //*************************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Delete all tables.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIMBALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);

        // Create fresh tables.
        onCreate(db);
    }


    /**
     * Delete the entire database (all tables) and recreates it.
     *
     * @param context the app's context. See the following:
     * http://stackoverflow.com/questions/7917947/get-context-in-android
     */
    public void recreateDatabase(Context context)
    {
        Log.v(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
                + "!!!!!!!!!!");
        Log.v(TAG, "!! recreateDatabase called: deleting database.");
        Log.v(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
                + "!!!!!!!!!!");

        // Close the SQLiteOpenHelper. This kills any open connections to the
        // database.
        this.close();

        // Delete this database.
        context.deleteDatabase(DATABASE_NAME);

        // Force the database to recreate.
        SQLiteDatabase dummy = this.getWritableDatabase();
    }


    private void addGimbal(SQLiteDatabase db)
    {
        // Create key-value pairs for the columns in the Gimbal table.
        ContentValues values = new ContentValues();


        // Gimbal content field.
        values.put(COLUMN_GIMBAL_CONTENT, "test123");

        // Gimbal type field.
        values.put(COLUMN_GIMBAL_TYPE, 3);

        // Insert new row (Gimbal) into the Gimbal table. If it fails,
        // we will return false.
        if (db.insert(TABLE_GIMBALS, null, values) == -1)
        {
            Log.v(TAG, "****** Insert of sample Gimbal failed.");
        }
    }
    public boolean deleteAllGimbals(){
        // Get a reference of our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all records from Gimbal table.
        db.execSQL("DELETE FROM " + TABLE_GIMBALS);

        return true;
    }
    public int getGimbalTableSize()
    {
        // Get a reference to our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Query to select all rows of Gimbal table.
        String query = "Select * FROM " + TABLE_GIMBALS;

        Cursor cursor = db.rawQuery(query, null);
        int cnt=0;
        // Loop through all rows, adding Gimbals to our list as we go.
        if (cursor.moveToFirst()){
            do{
                cnt++;
            }while (cursor.moveToNext());
        }

        // Close out database and cursor.
        db.close();
        cursor.close();

        return cnt;
    }
}
