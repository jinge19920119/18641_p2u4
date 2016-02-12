package goldenbros.com.mylocation.location;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by jinge on 7/19/15.
 */
public class GetLocation extends Service implements LocationListener{
    private final Context context;
    boolean isGPSEnabled= false;
    boolean isNetworkEnabled= false;
    boolean canGetLocation = false;

    Location location;
    double longitude;
    double latitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    private static final long MIN_TIME_TW_UPDATE = 1000*60*1;


    protected LocationManager locationManager;

    /*
    constructor
     */
    public GetLocation(Context context){
        this.context= context;
        getLocation();
    }

    /*
    use locationManager to get the location
     */
    public Location getLocation(){
        locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isNetworkEnabled && !isGPSEnabled){

        } else {
            this.canGetLocation= true;

            if(isNetworkEnabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_TW_UPDATE,MIN_DISTANCE_CHANGE_FOR_UPDATE,this);

                if(locationManager!= null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }


            }

            if(isGPSEnabled){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_TW_UPDATE,MIN_DISTANCE_CHANGE_FOR_UPDATE,this);

                if(locationManager!=null){
                    location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location!=null){
                        latitude= location.getLatitude();
                        longitude= location.getLongitude();
                    }
                }
            }
        }
        return location;
    }


    /*
    can be used when stopping
     */
    public void stopTracking(){
        if(locationManager!=null){
            locationManager.removeUpdates(this);
        }
    }

    /*
    getters
     */
    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public boolean isCanGetLocation(){
        return  canGetLocation;
    }

    /*
    pop a alert dialog if GPS is not enabled
     */
    public void showSettingAlert(){
        AlertDialog.Builder alertDialogue= new AlertDialog.Builder(context);
        alertDialogue.setTitle("GPS is setting!");
        alertDialogue.setMessage("GPS is not enabling! ");
        alertDialogue.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogue.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
