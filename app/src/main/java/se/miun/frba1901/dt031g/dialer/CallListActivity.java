package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CallListActivity extends AppCompatActivity {
    LinearLayout numberContainer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        LinearLayout dialList = (LinearLayout)findViewById(R.id.dialList);
        createDialList(dialList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dial_list_appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.deleteNumbers:
                DialDatabaseManager.getInstance(this).deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createDialList(LinearLayout container){
        // Vi observar LiveData och skriver nedanstående kod som en callback
        DialDatabaseManager.getInstance(this).getAll().observe(this, dialnumbers -> {
            if(dialnumbers.size() == 0){
                container.removeAllViews();
                TextView displayNoNumbers = new TextView(this);
                displayNoNumbers.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                displayNoNumbers.setText(getString(R.string.no_numbers_found_message));
                displayNoNumbers.setGravity(Gravity.CENTER_HORIZONTAL);
                container.addView(displayNoNumbers);
            }
            else {
                numberContainer = container;
                for (DialNumber number : dialnumbers) {
                    DialNumberView numberInfoContainer = new DialNumberView(this,
                            number,
                            25,14);
                    container.addView(numberInfoContainer);
                }
            }
        });

    }
}