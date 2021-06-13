package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Set;

public class CallListActivity extends AppCompatActivity {

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
    private void createDialList(LinearLayout container){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        Set<String> numberSet = sharedPreferences.getStringSet(getString(R.string.dialnumbers_key), null);
        int a = 10;
    }
}