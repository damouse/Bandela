package wings.bandela;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import java.util.List;


public class ViewMap extends Activity {

    TextView beaconDetectorTextView;
    BeaconEventListener beaconEventListener;
    PlaceEventListener placeEventListener;
    BeaconManager beaconManager;
    PlaceManager placeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        Gimbal.setApiKey(this.getApplication(), "41238e20-69b3-48f7-b5b5-9648d9ba4dfb"); //"## PLACE YOUR API KEY HERE ##"


        beaconDetectorTextView = (TextView) findViewById(R.id.beaconDetectorTextView);
        beaconDetectorTextView.setText("The Beacon info should be here.");

        //gimbalHelper theGimbalHelper = new gimbalHelper(beaconDetectorTextView);
        //theGimbalHelper.addBeacon("beacon 5", "Hello you have seen me! Beacon 4!");

        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                //super.onBeaconSighting(beaconSighting);
                beaconDetectorTextView.setText(beaconSighting.getRSSI());
                Toast aTestingMessage = Toast.makeText(getBaseContext(), "We detected a beacon! " + beaconSighting.getRSSI(), Toast.LENGTH_LONG);
                aTestingMessage.show();
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();

        placeEventListener = new PlaceEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting, List<Visit> visits) {
                //super.onBeaconSighting(beaconSighting, visits);
                beaconDetectorTextView.setText(beaconSighting.getRSSI());
                Toast aTestingMessage = Toast.makeText(getBaseContext(), "We detected a beacon! " + beaconSighting.getRSSI(), Toast.LENGTH_LONG);
                aTestingMessage.show();


            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);
        PlaceManager.getInstance().startMonitoring();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
