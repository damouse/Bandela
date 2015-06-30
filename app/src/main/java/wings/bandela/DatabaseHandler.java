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
 * Created by Mike Feilbach on 3/21/2015.
 * Handles the app's database. Offers methods to add/query/delete records
 * from the database's different tables. Acts as an abstraction of the SQLite
 * database.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    // For tagging messages in Log Cat.
    private static final String TAG = "MyActivity";

    //*************************************************************************
    // Database attributes.
    //*************************************************************************
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "liftLogDatabase";

    //*************************************************************************
    // Bubble table attributes.
    //*************************************************************************

    // Name of Bubble table within the database.
    private static final String TABLE_GIMBALS = "bubbles";

    // Columns in the Bubble table.
    public static final String COLUMN_GIMBAL_ID = "bubbleId";
    public static final String COLUMN_GIMBAL_CONTENT = "bubbleContent";
    public static final String COLUMN_GIMBAL_TYPE = "bubbleType";

    //*************************************************************************
    // WorkoutLog table attributes.
    //*************************************************************************

    // Name of the WorkoutLog table within the database.
    private static final String TABLE_PLACE = "workoutLogs";

    // Columns in the WorkoutLog table.
    public static final String COLUMN_PLACE_ID = "logId";
    public static final String COLUMN_PLACE_TITLE = "logTitle";
    public static final String COLUMN_PLACE_BODY = "logBody";
    public static final String COLUMN_PLACE_DATE_SEC = "logDateSeconds";

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

        // The Bubble table.
        String CREATE_GIMBAL_TABLE = "CREATE TABLE " +
                TABLE_GIMBALS + "("
                + COLUMN_GIMBAL_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_GIMBAL_CONTENT + " TEXT,"
                + COLUMN_GIMBAL_TYPE + " INTEGER)";

        // The Log table.
        String CREATE_PLACE_TABLE = "CREATE TABLE " +
                TABLE_PLACE + "("
                + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PLACE_TITLE + " TEXT,"
                + COLUMN_PLACE_BODY + " TEXT,"
                + COLUMN_PLACE_DATE_SEC + " INTEGER)";

        // All tables are created when database is initialized.
        db.execSQL(CREATE_GIMBAL_TABLE);
        db.execSQL(CREATE_PLACE_TABLE);

        Log.v(TAG, "****** START: Creating sample Bubbles.");


        Log.v(TAG, "****** END: Creating sample Bubbles.");
    }


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



}
