package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private boolean aboutOpened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDeviceSoundFiles();
        DialDatabaseManager.getInstance(this); // Initierar singleton klass för databas
    }

    /** Kopierar voice filerna till enheten */
    private void setupDeviceSoundFiles(){
        if(!Util.defaultVoiceExist(getApplicationContext()))
            Util.copyDefaultVoiceToInternalStorage(getApplicationContext());
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
    public void startDownload(View view){
        Intent intent = new Intent(this, DownloadActivity.class);
        intent.putExtra("DOWNLOAD_SITE_URL", getString(R.string.voices_download_url));
        intent.putExtra("VOICE_STORAGE_PATH", new File(getFilesDir(), Util.VOICE_DIR).toString());

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