package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;

public class DialActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private Dialpad dialpad;
    private final int LOCATION_UPDATE_INTERVAL_MS = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar(); 
        ab.setDisplayHomeAsUpEnabled(true);

        dialpad = (Dialpad)findViewById(R.id.dialpad);
        // Frågar om permissions till CALL_PHONE och ACCESS_FINE_LOCATION om det inte redan har blivit accepterat
        String[] permissionsWanted = {Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION};

        requestPermissions(permissionsWanted, 1);
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationCallback locationCallback = new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult){
                    if(locationResult == null){
                        return;
                    }
                    for(Location location : locationResult.getLocations()){
                        dialpad.updateLocationData(location);
                    }
                }
            };
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
                    Looper.getMainLooper());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.dialer_overflow, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()){
            case R.id.overflowSettingsAlternative:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.overflowDownloadVoices:
                intent = new Intent(this, DownloadActivity.class);
                intent.putExtra("DOWNLOAD_SITE_URL", getString(R.string.voices_download_url));
                intent.putExtra("VOICE_STORAGE_PATH", new File(getFilesDir(), Util.VOICE_DIR).toString());
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationClient = null;
    }
}