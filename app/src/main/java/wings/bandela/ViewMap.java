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
import com.gimbal.android.CommunicationManager;
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


    int rssi1, rssi2, rssi3, rssi4, rssi5;
    int minRSSI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);


        Gimbal.setApiKey(this.getApplication(), "41238e20-69b3-48f7-b5b5-9648d9ba4dfb"); //"## PLACE YOUR API KEY HERE ##"


        beaconDetectorTextView = (TextView) findViewById(R.id.beaconDetectorTextView);
        beaconDetectorTextView.setText("The Beacon info should be here.");

        rssi1 = 0;
        rssi2 = 0;
        rssi3 = 0;
        rssi4 = 0;
        rssi5 = 0;
        minRSSI = 1000;

        beaconManager = new BeaconManager();

        beaconEventListener = new BeaconEventListener(){
            //return rssi for beacons sighted
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {

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

                //if none are equal to zero find minimum
                if((rssi1 & rssi2 & rssi3 & rssi4 & rssi5) != 0){
                    minRSSI = Math.min( Math.min(rssi1, rssi2), rssi3);
                    //rssi1 is closest
                    if(rssi1 == minRSSI){
                        //listAdapter.add(String.format("Beacon 1 loudest, has RSSI %d", sighting.getRSSI()));
                        //listAdapter.notifyDataSetChanged();
                        beaconDetectorTextView.setText(String.format("Beacon 1 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi2 == minRSSI){
                        //listAdapter.add(String.format("Beacon 2 loudest, has RSSI %d", sighting.getRSSI()));
                        //listAdapter.notifyDataSetChanged();
                        beaconDetectorTextView.setText(String.format("Beacon 2 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi3 == minRSSI){
                        //listAdapter.add(String.format("Beacon 3 loudest, has RSSI %d", sighting.getRSSI()));
                        //listAdapter.notifyDataSetChanged();
                        beaconDetectorTextView.setText(String.format("Beacon 3 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi4 == minRSSI){
                        //listAdapter.add(String.format("Beacon 3 loudest, has RSSI %d", sighting.getRSSI()));
                        //listAdapter.notifyDataSetChanged();
                        beaconDetectorTextView.setText(String.format("Beacon 4 loudest, has RSSI %d", sighting.getRSSI()));
                    } else if(rssi5 == minRSSI){
                        //listAdapter.add(String.format("Beacon 3 loudest, has RSSI %d", sighting.getRSSI()));
                        //listAdapter.notifyDataSetChanged();
                        beaconDetectorTextView.setText(String.format("Beacon 5 loudest, has RSSI %d", sighting.getRSSI()));
                    }
                } else {

                    beaconDetectorTextView.setText("No beacon is the loudest");


                }
            }
        };


        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();

        CommunicationManager.getInstance().startReceivingCommunications();
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
