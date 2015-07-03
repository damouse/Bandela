package wings.bandela;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Place;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;
import com.gimbal.android.BeaconSighting;

import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zachary on 6/25/2015.
 */
public class gimbalHelper {
    Vector<String> specificOnVisitStartMessages = new Vector<String>(5);
    Vector<String> specificOnVisitEndMessages = new Vector<String>(5);
    Vector<String> specificBeaconMessages = new Vector<String>(5);
    Vector<Place> specificPlaces = new Vector<Place>(5);
    Vector<Beacon> specificBeacons = new Vector<Beacon>(5);

    PlaceEventListener placeEventListener;
    PlaceManager placeManager;
    BeaconEventListener beaconEventListener;
    BeaconManager beaconManager;

    public gimbalHelper()
    {

    }

    public void addPlace(String onVisitStartMessage, String onVisitEndMessage, Place specificPlace) //String beaconSightingMessage    Beacon specificBeacon
    {
        //Here we add every message, the beacon, and the place to their respective vectors.
        specificOnVisitStartMessages.add(onVisitStartMessage);
        specificOnVisitEndMessages.add(onVisitEndMessage);
        //specificBeaconMessages.add(beaconSightingMessage);
        specificPlaces.add(specificPlace);
        //specificBeacons.add(specificBeacon);

        //Here we create the methods used to detect beacons and places, and then display the correct message.
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                //Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
                for(int i = 0; i < specificOnVisitStartMessages.size(); i++)
                {
                    if(visit.getPlace() == specificPlaces.get(i))
                    {
                        Toast aTestingMessage = Toast.makeText(getBaseContext(), specificOnVisitStartMessages.get(i), Toast.LENGTH_LONG);
                        aTestingMessage.show();
                    }
                }
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                //Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
                for(int i = 0; i < specificOnVisitEndMessages.size(); i++)
                {
                    if(visit.getPlace() == specificPlaces.get(i))
                    {
                        Toast aTestingMessage = Toast.makeText(getBaseContext(), specificOnVisitEndMessages.get(i), Toast.LENGTH_LONG);
                        aTestingMessage.show();
                    }
                }
            }
            /*
            public void onBeaconSighting(BeaconSighting sighting, List<Visit> visits) {
                // This will be invoked when a beacon assigned to a place within a current visit is sighted.
                for(int i = 0; i < specificBeacons.size(); i++)
                {
                    if(sighting.getBeacon().getName().equals(specificBeacons.get(i).getName()))
                    {
                        Toast aTestingMessage = Toast.makeText(getBaseContext(), specificBeaconMessages.get(i), Toast.LENGTH_LONG);
                        aTestingMessage.show();
                    }
                }
            }
            */
        };
        placeManager.addListener(placeEventListener);
        placeManager.startMonitoring();
        //PlaceManager.getInstance().addListener(placeEventListener);
    }
    //This method allows us to remove a place or beacon if we decide we no longer want it.
    public void removePlace(String placeName)
    {
        //Here we scour the place vector for a place representing the parameter name.
        for(int i = 0; i < specificPlaces.size(); i++)
        {
            if(specificPlaces.get(i).getName().equals(placeName)) //|| specificBeacons.get(i).getName().equals(placeOrBeaconName)
            {
                //We remove everything that has to deal with that place or beacon.
                specificPlaces.remove(i);
                //specificBeaconMessages.remove(i);
                specificOnVisitEndMessages.remove(i);
                specificOnVisitStartMessages.remove(i);
                //specificBeacons.remove(i);
            }
        }
    }

    public void addBeacon(Beacon specificBeacon, String beaconSightingMessage)
    {
        //Here we add every message, the beacon, and the place to their respective vectors.
        specificBeacons.add(specificBeacon);
        specificBeaconMessages.add(beaconSightingMessage);

        //Here we create our method to do something when the app detects a beacon for every beacon we have currently added.
        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                for(int i = 0; i < specificBeacons.size(); i++)
                {
                    if(beaconSighting.getBeacon().getName().equals(specificBeacons.get(i).getName()))
                    {
                        Toast aTestingMessage = Toast.makeText(getBaseContext(), specificBeaconMessages.get(i), Toast.LENGTH_LONG);
                        aTestingMessage.show();
                    }
                }

            }
        };
        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();
    }

    public void removeBeacon(String beaconName)
    {
        //Here we scour the beacon vector for a beacon representing the parameter name.
        for(int i = 0; i < specificPlaces.size(); i++)
        {
            if(specificBeacons.get(i).getName().equals(beaconName)) //|| specificBeacons.get(i).getName().equals(placeOrBeaconName)
            {
                //We remove everything that has to deal with that place or beacon.
                specificBeaconMessages.remove(i);
                specificBeacons.remove(i);
            }
        }
    }








    /*
    public void setGeneralMessageForVisits(final String onVisitStartMessage, final String onVisitEndMessage, final String beaconSightingMessage)
    {
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                //Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
                Toast aTestingMessage = Toast.makeText(getBaseContext(), onVisitStartMessage, Toast.LENGTH_LONG);
                aTestingMessage.show();
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                //Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
                Toast aTestingMessage = Toast.makeText(getBaseContext(), "Good Bye from " + visit.getPlace() + ". \nDeparture time: " + visit.getDepartureTimeInMillis(), Toast.LENGTH_LONG);
                aTestingMessage.show();
                aTestingMessage = Toast.makeText(getBaseContext(), onVisitEndMessage, Toast.LENGTH_LONG);
                aTestingMessage.show();
            }

            public void onBeaconSighting(BeaconSighting sighting, List<Visit> visits) {
                // This will be invoked when a beacon assigned to a place within a current visit is sighted.
                Toast aTestingMessage = Toast.makeText(getBaseContext(), "You have visited the " + sighting.getBeacon().getName() + " beacon.", Toast.LENGTH_LONG);
                aTestingMessage.show();
                aTestingMessage = Toast.makeText(getBaseContext(), beaconSightingMessage, Toast.LENGTH_LONG);
                aTestingMessage.show();
            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);

    }
    */
    /*
    placeEventListener = new PlaceEventListener() {
        @Override
        public void onVisitStart(Visit visit) {
            // This will be invoked when a place is entered. Example below shows a simple log upon enter
            //Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
            Toast aTestingMessage = Toast.makeText(getBaseContext(), "Welcome to " + visit.getPlace() + ". \nArrival time: " + visit.getArrivalTimeInMillis(), Toast.LENGTH_LONG);
            aTestingMessage.show();
        }

        @Override
        public void onVisitEnd(Visit visit) {
            // This will be invoked when a place is exited. Example below shows a simple log upon exit
            //Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
            Toast aTestingMessage = Toast.makeText(getBaseContext(), "Good Bye from " + visit.getPlace() + ". \nDeparture time: " + visit.getDepartureTimeInMillis(), Toast.LENGTH_LONG);
            aTestingMessage.show();
            aTestingMessage = Toast.makeText(getBaseContext(), "You were there for " + visit.getDwellTimeInMillis(), Toast.LENGTH_LONG);
            aTestingMessage.show();
        }

        public void onBeaconSighting(BeaconSighting sighting, List<Visit> visits) {
            // This will be invoked when a beacon assigned to a place within a current visit is sighted.
            Toast aTestingMessage = Toast.makeText(getBaseContext(), "You have visited the " + sighting.getBeacon().getName() + " beacon.", Toast.LENGTH_LONG);
            aTestingMessage.show();
            aTestingMessage = Toast.makeText(getBaseContext(), "Signal strength is " + sighting.getRSSI(), Toast.LENGTH_LONG);
            aTestingMessage.show();
        }
    };
    PlaceManager.getInstance().addListener(placeEventListener);
    */
}
