package wings.bandela;

import android.content.Context;
import android.util.Log;

/*
 * Created by Zheng Zheng on 7/1/2015.
 */
public class databaseTest {
    // The app's context.
    private Context context;
    private static final String TAG = "MyActivity";
    public databaseTest(){}
    public databaseTest(Context c){ this.context = c; }

    public void startTest() {
        Log.v(TAG,"&&&&&&&&&test started&&&&&&&&&&&");
        DatabaseHandler db = new DatabaseHandler(this.context);

        // Delete & recreate the database.
        db.recreateDatabase(this.context);


        Log.v(TAG, "Deleting all Bubbles in the Bubble table.");
        db.deleteAllGimbals();

        Log.v(TAG, "Make sure there are zero Bubbles in the Bubble table.");
        Log.v(TAG,"after delete, Gimbal table size is ");
        if (db.getGimbalTableSize() == 0)
        {
            Log.v(TAG, "zero");
        }
        else
        {
            Log.v(TAG, "not zero");
            return;
        }




    }

}
