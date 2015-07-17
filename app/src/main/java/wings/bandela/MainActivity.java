package wings.bandela;

import com.gimbal.android.Gimbal;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.Communication;
import com.gimbal.android.Push;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Place;
import com.gimbal.android.Visit;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.BeaconManager;
import com.gimbal.proximity.ProximityListener;
import com.gimbal.proximity.ProximityOptions;

/* potentially unwanted imports. These suggested imports were found under a section
//stating that these imports should be included if using CommunicationListener and
//CommunicationManager instances.

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import android.util.Log;
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private PlaceManager placeManager;
    private PlaceEventListener placeEventListener;
    private CommunicationListener communicationListener;
    private BeaconEventListener beaconSightingListener;
    private static final String TAG = "MyActivity";

    private ArrayAdapter<String> listAdapter;
    private ListView listView;

    private BeaconEventListener beaconEventListener;
    private BeaconManager beaconManager = new BeaconManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
            TESTING start for Gimbal code.
         */
        Gimbal.setApiKey(this.getApplication(), "41238e20-69b3-48f7-b5b5-9648d9ba4dfb"); //"## PLACE YOUR API KEY HERE ##"

        //Proximity testing code
        serviceStarted();

        /*
        Start of first gimbal testing code.
         */
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                Toast aTestingMessage = Toast.makeText(getBaseContext(), String.format("Start Visit for %s", visit.getPlace().getName()), Toast.LENGTH_LONG);
                aTestingMessage.show();
            }

            @Override
            public void onVisitEnd(Visit visit) {
                Toast aTestingMessage = Toast.makeText(getBaseContext(), String.format("Start Visit for %s", visit.getPlace().getName()), Toast.LENGTH_LONG);
                aTestingMessage.show();
            }
        };

        placeManager = PlaceManager.getInstance();
        placeManager.addListener(placeEventListener);
        placeManager.startMonitoring();

        //This code checks to see if monitoring is actually on
        if(PlaceManager.getInstance().isMonitoring() == true)
        {
            Toast aTestingMessage = Toast.makeText(getBaseContext(), "Test message to indicate PlaceManager is Monitoring :)", Toast.LENGTH_LONG);
            aTestingMessage.show();
        }
        else
        {
            Toast aTestingMessage = Toast.makeText(getBaseContext(), "Test message to indicate PlaceManager IS NOT Monitoring :(", Toast.LENGTH_LONG);
            aTestingMessage.show();
        }

        CommunicationManager.getInstance().startReceivingCommunications();

        beaconSightingListener = new BeaconEventListener(){
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Toast aTestingMessage = Toast.makeText(getBaseContext(), "Testing message indicating onBeaconSighting method is working", Toast.LENGTH_LONG);
                aTestingMessage.show();
                //Log.i("INFO", sighting.toString());
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        beaconManager.startListening();

        //create buttons in main activity

        /* Commenting out buttons /*
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WhereAmI.class);
                startActivity(intent);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewMap.class);
                startActivity(intent);
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetupGimbals.class);
                startActivity(intent);
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingAndHelp.class);
                startActivity(intent);
            }
        });

        //end commenting out
        */

        beaconEventListener = new BeaconEventListener(){

            public void onBeaconSighting(BeaconSighting sighting) {

                listAdapter.add(String.format("sighted beacon %s", sighting.getRSSI()));
                listAdapter.notifyDataSetChanged();
            }
        };

        beaconManager.addListener(beaconEventListener);
    }//end onCreate method

    void serviceStarted() {
        Toast aTestingMessage = Toast.makeText(getBaseContext(), "Testing message stating that the proximity service has started.", Toast.LENGTH_LONG);
        aTestingMessage.show();
    }

    // this will be called if the service has failed to start
    void serviceFailed(int errorCode, String message) {
        Toast aTestingMessage = Toast.makeText(getBaseContext(), "Testing message stating that the proximity service has failed.", Toast.LENGTH_LONG);
        aTestingMessage.show();
    }

}
