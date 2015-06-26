package wings.bandela;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private PlaceManager placeManager;
    private PlaceEventListener placeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
            TESTING start for Gimbal code.
         */
        Gimbal.setApiKey(this.getApplication(), "f987ff31e7bb866513829f4b3ea7b554"); //"## PLACE YOUR API KEY HERE ##"

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

        CommunicationManager.getInstance().startReceivingCommunications();

        /*
            TESTING finished for Gimbal code.
         */


        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WhereAmI.class);
                startActivity(intent);
                //Once pressed on the button2 it triggers ViewHistoryActivity
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewMap.class);
                startActivity(intent);
                //Once pressed on the button2 it triggers ViewHistoryActivity
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetupGimbals.class);
                startActivity(intent);
                //Once pressed on the button2 it triggers ViewHistoryActivity
            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingAndHelp.class);
                startActivity(intent);
                //Once pressed on the button2 it triggers ViewHistoryActivity
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
