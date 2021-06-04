package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean aboutOpened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean("ABOUT_OPENED", aboutOpened); // Sparar om vi har öppnat about eller inte
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        aboutOpened = savedInstanceState.getBoolean("ABOUT_OPENED");
    }
    public void startDialer(View view){
        Intent intent = new Intent(this, DialActivity.class);
        startActivity(intent);
    }
    public void startCallList(View view){
        Intent intent = new Intent(this, CallListActivity.class);
        startActivity(intent);
    }
    public void startSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void startMaps(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void startAbout(View view){
        if(aboutOpened)
            Toast.makeText(getApplicationContext(), "You have already seen the about section",Toast.LENGTH_LONG).show();
        else {
            AboutDialog aboutDialog = new AboutDialog();
            aboutDialog.show(getSupportFragmentManager(), "About dialog");
            aboutOpened = true; // Anger att vi har öppnat about
        }
    }
}