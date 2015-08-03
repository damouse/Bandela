package wings.bandela;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import java.util.List;


public class ViewMap extends Activity {

    private CommunicationListener communicationListener;
    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;

    static TextView beaconDetectorTextView;


    private static int rssi1, rssi2, rssi3, rssi4, rssi5, minRSSI;
    private static final int CUTOFF = 70;

    //private static RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //layout = (RelativeLayout)findViewById(R.id.mapView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);


        Gimbal.setApiKey(this.getApplication(), "41238e20-69b3-48f7-b5b5-9648d9ba4dfb"); //"## PLACE YOUR API KEY HERE ##"

        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Log.v("test", "enter onBeaconSighting");
                if(sighting.getBeacon().getName().equals("beacon 1")) {
                    rssi1 = Math.abs(sighting.getRSSI());
                }else if(sighting.getBeacon().getName().equals("beacon 2")) {
                    rssi2 = Math.abs(sighting.getRSSI());
                }else if(sighting.getBeacon().getName().equals("beacon 3")) {
                    rssi3 = Math.abs(sighting.getRSSI());
                }else if(sighting.getBeacon().getName().equals("beacon 4")) {
                    rssi4 = Math.abs(sighting.getRSSI());
                }else if(sighting.getBeacon().getName().equals("beacon 5")) {
                    rssi5 = Math.abs(sighting.getRSSI());
                }

                //if one beacon is not zero find minimum
                if((rssi1 | rssi2 | rssi3 | rssi4 | rssi5) != 0){
                    //rssi1 is closest
                    if(rssi1 != 0 && rssi1 < CUTOFF){

                        RelativeLayout mainLayout=(RelativeLayout)findViewById(R.id.mapView);
                        mainLayout.setBackgroundResource(R.drawable.highlight1);
                        beaconDetectorTextView.setText(String.format("Beacon 1 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi2 != 0 && rssi2 < CUTOFF){
                        RelativeLayout mainLayout=(RelativeLayout)findViewById(R.id.mapView);
                        mainLayout.setBackgroundResource(R.drawable.highlight2);
                        beaconDetectorTextView.setText(String.format("Beacon 2 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi3 != 0 && rssi3 < CUTOFF){
                        RelativeLayout mainLayout=(RelativeLayout)findViewById(R.id.mapView);
                        mainLayout.setBackgroundResource(R.drawable.highlight3);
                        beaconDetectorTextView.setText(String.format("Beacon 3 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi4 != 0 && rssi4 < CUTOFF){
                        RelativeLayout mainLayout=(RelativeLayout)findViewById(R.id.mapView);
                        mainLayout.setBackgroundResource(R.drawable.highlight4);
                        beaconDetectorTextView.setText(String.format("Beacon 4 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi5 != 0 && rssi5 < CUTOFF){
                        //layout.setBackgroundResource(R.drawable.blueprint);
                        beaconDetectorTextView.setText(String.format("Beacon 5 loudest, has RSSI %d", sighting.getRSSI()));
                    }
                }
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        beaconManager.startListening();

        CommunicationManager.getInstance().startReceivingCommunications();

        rssi1 = 0;
        rssi2 = 0;
        rssi3 = 0;
        rssi4 = 0;
        rssi5 = 0;

        beaconDetectorTextView = (TextView) findViewById(R.id.beaconDetectorTextView);
        beaconDetectorTextView.setText("Initializing...");
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
