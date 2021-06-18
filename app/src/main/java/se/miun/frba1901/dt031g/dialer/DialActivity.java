package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar(); 
        ab.setDisplayHomeAsUpEnabled(true);

        // Frågar om permissions till CALL_PHONE om det inte redan har blivit accepterat
        String[] permissionsWanted = {Manifest.permission.CALL_PHONE};
        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissionsWanted, 1);
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
}