package wings.bandela;

import android.widget.Toast;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Place;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import java.util.Vector;

/**
 * Created by Zachary on 6/25/2015.
 */
public class gimbalHelper {
    Vector<String> specificOnVisitStartMessages = new Vector<String>(5);
    Vector<String> specificOnVisitEndMessages = new Vector<String>(5);
    Vector<String> specificBeaconMessages = new Vector<String>(5);
    Vector<String> specificPlaceNames = new Vector<String>(5);
    Vector<Place> specificPlaces = new Vector<Place>(5);
    Vector<String> specificBeaconNames = new Vector<String>(5);
    Vector<Beacon> specificBeacons = new Vector<Beacon>(5);
    Vector<Integer> beaconStrength = new Vector<Integer>(5);

    PlaceEventListener placeEventListener;
    //PlaceManager does not have a constructor like how BeaconManager does.
    //PlaceManager placeManager = new PlaceManager();
    BeaconEventListener beaconEventListener;
    BeaconManager beaconManager = new BeaconManager();

    public gimbalHelper()
    {

    }

    /*
    //This method adds a place to be monitored. This should be used in conjunction with the Gimbal Manager on the web. When you add a place, you
    //will have to include what message should be presented to the app user when they arrive at this place, what message should be presented when
    //the app user leaves the place, and also what that place name is.
     */
    public void addPlace(String onVisitStartMessage, String onVisitEndMessage, String specificPlaceName) //String beaconSightingMessage    Beacon specificBeacon
    {
        //Here we add every message, the beacon, and the place to their respective vectors.
        specificOnVisitStartMessages.add(onVisitStartMessage);
        specificOnVisitEndMessages.add(onVisitEndMessage);
        //specificBeaconMessages.add(beaconSightingMessage);
        specificPlaceNames.add(specificPlaceName);
        //specificBeaconNames.add(specificBeacon);

        //Here we call our updating method, which will finalize the addition of the specific place.
        setupPlaces();
    }

    //This method allows us to remove a place or beacon if we decide we no longer want it.
    public void removePlace(String placeName)
    {
        //Here we scour the place vector for a place representing the parameter name.
        for(int i = 0; i < specificPlaceNames.size(); i++)
        {
            if(specificPlaceNames.get(i).equals(placeName)) //|| specificBeaconNames.get(i).getName().equals(placeOrBeaconName)
            {
                //We remove everything that has to deal with that place or beacon.
                specificPlaceNames.remove(i);
                specificPlaces.remove(i);
                //specificBeaconMessages.remove(i);
                specificOnVisitEndMessages.remove(i);
                specificOnVisitStartMessages.remove(i);
                //specificBeaconNames.remove(i);
            }
        }
        //Here we call our updating method, which will finalize the removal of the specific place.
        setupPlaces();
    }





    /*
    //Below are beacon specific methods.
     */






    public void addBeacon(String specificBeaconName, String beaconSightingMessage)
    {
        //Here we add every message, the beacon, and the place to their respective vectors.
        specificBeaconNames.add(specificBeaconName);
        specificBeaconMessages.add(beaconSightingMessage);
        //Here we call our updating method, which will finalize the addition of the beacon.
        setupBeacons();
    }

    public void removeBeacon(String beaconName)
    {
        //Here we scour the beacon vector for a beacon representing the parameter name.
        for(int i = 0; i < specificBeaconNames.size(); i++)
        {
            if(specificBeaconNames.get(i).equals(beaconName))
            {
                //We remove everything that has to deal with that place or beacon.
                specificBeaconMessages.remove(i);
                specificBeaconNames.remove(i);
                specificBeacons.remove(i);
            }
        }
        //Here we call our updating method, which will finalize the removal of the beacon.
        setupBeacons();
    }

    public int getBeaconStrength(String beaconName)
    {
        int signalStrength = 1;
        //Here we scour the beacon vector for a beacon representing the parameter name.
        for(int i = 0; i < specificBeaconNames.size(); i++)
        {
            if(specificBeaconNames.get(i).equals(beaconName))
            {
                //We return the signal strength, which can be found in the beaconStrength vector. This is because
                //we can not get the RSSI value directly from a beacon object, only from a BeaconSighting object,
                //and the beaconStrength vector should hold the last known RSSI strength.
                signalStrength = beaconStrength.get(i);
            }
        }
        return signalStrength;
    }

    public String  getBeaconBatteryLevel(String beaconName)
    {
        String batteryLevel = "";
        //Here we scour the beacon vector for a beacon representing the parameter name.
        for(int i = 0; i < specificBeaconNames.size(); i++)
        {
            if(specificBeaconNames.get(i).equals(beaconName))
            {
                //This code may or may not work. If it works, it will return the value of the battery level in a string format. The available
                //options should only be HIGH, MEDIUM_HIGH, MEDIUM_LOW, and LOW.
                batteryLevel = String.valueOf(specificBeacons.get(i).getBatteryLevel());
            }
        }
        return batteryLevel;
    }

    public int getBeaconTemperature(String beaconName)
    {
        int temperature = 1;
        //Here we scour the beacon vector for a beacon representing the parameter name.
        for(int i = 0; i < specificBeaconNames.size(); i++)
        {
            if(specificBeaconNames.get(i).equals(beaconName))
            {
                //We return the signal strength, which can be found in the beaconStrength vector.
                temperature = specificBeacons.get(i).getTemperature();
            }
        }
        return temperature;
    }





    /*
    //Below are methods that are used inside other methods above.
     */





    //This is a method that is supposed to be used inside the addPlace and removePlace methods. It creates a placeEventListener and goes through the
    //place vector, visit start message vector, and the visit end message vector to create where specific greetings and farewells happen. It also immediately
    //starts monitoring for any users that enter and exit the place perimeter. There are 3 variables that will detect the arrival time, departure time, and how
    //long someone spent in a spot. I am assuming that we will be logging these times, so keeping the variables hold their value for more than a second
    //I do not believe will be necessary, and is therefore ok that they be constantly overwritten when an app user enters and leaves another place.
    public void setupPlaces()
    {
        //Here we create the methods used to detect beacons and places, and then display the correct message.
        placeEventListener = new PlaceEventListener() {
            long visitStartTime = 0;
            long visitEndTime = 0;
            long visitDwellTime = 0;
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                //Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
                for(int i = 0; i < specificOnVisitStartMessages.size(); i++)
                {
                    if(visit.getPlace().getName().equals(specificPlaceNames.get(i)))
                    {
                        //Toast aTestingMessage = Toast.makeText(getBaseContext(), specificOnVisitStartMessages.get(i), Toast.LENGTH_LONG);
                        //aTestingMessage.show();
                        //We can now store the place's info in our place vector if we have not already run into it.
                        //If we have already run into the place, then don't worry about storing the place
                        //into the place vector, as it's already there!
                        if(specificPlaces.get(i) == null)
                        {
                            specificPlaces.set(i, visit.getPlace());
                        }
                        else
                        {
                            //Do nothing, the place is already in the place vector!
                        }
                        visitStartTime = visit.getArrivalTimeInMillis();
                    }
                }
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                //Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
                for(int i = 0; i < specificOnVisitEndMessages.size(); i++)
                {
                    //We do not compare the name of the place like we did in the onVisitStart method, because
                    //we should have the place in the place vector now. This is because we can't leave
                    //a place that we never started to visit!
                    if(visit.getPlace() == specificPlaces.get(i))
                    {
                        //Toast aTestingMessage = Toast.makeText(getBaseContext(), specificOnVisitEndMessages.get(i), Toast.LENGTH_LONG);
                        //aTestingMessage.show();
                        visitEndTime = visit.getDepartureTimeInMillis();
                        visitDwellTime = visit.getDwellTimeInMillis();
                    }
                }
            }
            /*
            public void onBeaconSighting(BeaconSighting sighting, List<Visit> visits) {
                // This will be invoked when a beacon assigned to a place within a current visit is sighted.
                for(int i = 0; i < specificBeaconNames.size(); i++)
                {
                    if(sighting.getBeacon().getName().equals(specificBeaconNames.get(i).getName()))
                    {
                        Toast aTestingMessage = Toast.makeText(getBaseContext(), specificBeaconMessages.get(i), Toast.LENGTH_LONG);
                        aTestingMessage.show();
                    }
                }
            }
            */
        };
        //placeManager.addListener(placeEventListener);
        //placeManager.startMonitoring();
        PlaceManager.getInstance().addListener(placeEventListener);
        PlaceManager.getInstance().startMonitoring();
    }

    //This is a method that is supposed to be used inside the addBeacon and removeBeacon methods. It creates a beaconEventListener and goes through the
    //beacon vector, and the beacon message vector to create what happens when someone gets within the distance of a beacon. It also immediately
    //starts monitoring for any users that get within range of the beacons.
    public void setupBeacons()
    {
        //Here we create our method to do something when the app detects a beacon for every beacon we have currently added.
        beaconEventListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                for(int i = 0; i < specificBeaconNames.size(); i++)
                {
                    if(beaconSighting.getBeacon().getName().equals(specificBeaconNames.get(i)))
                    {
                        //Toast aTestingMessage = Toast.makeText(getBaseContext(), specificBeaconMessages.get(i), Toast.LENGTH_LONG);
                        //aTestingMessage.show();

                        //This code records the strength of the beacon which it is interacting with in a separate vector.
                        beaconStrength.set(i, beaconSighting.getRSSI());
                        //We also record the beacon's info if it's not already stored in the beacon vector.
                        if(specificBeacons.get(i) == null)
                        {
                            specificBeacons.set(i, beaconSighting.getBeacon());
                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        //Do nothing, since the beacon does not match the beacon's name we have in the beacon name vector.D
                    }
                }

            }
        };
        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();

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
