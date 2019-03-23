package com.example.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView latitude;
    private TextView longitude;
    public double ll,lo ,l=1;
private Button button;
    private final String TAG="MainActivity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude=(TextView)findViewById(R.id.latval);
        longitude=(TextView)findViewById(R.id.lonval);



          callPermissions();
button=(Button) findViewById(R.id.map);

button.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        v.getContext().startActivity(intent);
    }
});

        //longitude.setText( );



    }
public void requestLocationUpdates(){

        if((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)== PermissionChecker.PERMISSION_GRANTED)  &&(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PermissionChecker.PERMISSION_GRANTED)) {


            fusedLocationProviderClient = new FusedLocationProviderClient(this);
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setFastestInterval(2000);
            locationRequest.setInterval(4000);

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    //int ll,lo;
                   /* latitude.setText((int) locationResult.getLastLocation().getLatitude());
                    longitude.setText((int) locationResult.getLastLocation().getLongitude());*/
                   ll= locationResult.getLastLocation().getLatitude();
                   lo= locationResult.getLastLocation().getLongitude();

                    Log.e(TAG,"lat nd log "+locationResult.getLastLocation().getLatitude()+" "+locationResult.getLastLocation().getLongitude());
                    latitude.setText(String.valueOf(ll));
                    longitude.setText(String.valueOf(lo));

                }
            }, getMainLooper());

        }
        else{
            callPermissions();
        }
}
    public void callPermissions(){
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationUpdates();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });

    }
}
