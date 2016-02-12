package goldenbros.com.mylocation;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import goldenbros.com.mylocation.location.GetLocation;


public class MainActivity extends Activity {

    private Button b1;
    private EditText numEditText;
    private Button b2;
    double latitude=0;
    double longitude= 0;
    String content= new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation getLocation= new GetLocation(MainActivity.this);
                if(getLocation.isCanGetLocation()){
                    latitude= getLocation.getLatitude();
                    longitude= getLocation.getLongitude();
                    content= "latitude : "+ latitude+ " longitude : "+longitude;
                    Toast.makeText(MainActivity.this," lat: "+ latitude , Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, " long: "+ longitude, Toast.LENGTH_SHORT).show();
                } else {
                    getLocation.showSettingAlert();
                }
            }
        });
        numEditText= (EditText) findViewById(R.id.editText);
        b2= (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager manager= SmsManager.getDefault();
                String number= numEditText.getText().toString();
                manager.sendTextMessage(number,null, content, null, null);
                Toast.makeText(MainActivity.this, "Send message successfully", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
