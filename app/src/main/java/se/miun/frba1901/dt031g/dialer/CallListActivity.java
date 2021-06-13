package se.miun.frba1901.dt031g.dialer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashSet;
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
                // Tar bort nummer från sharedpreference
                SharedPreferences dialNumberPreferences = this.getSharedPreferences(this
                        .getString(R.string.saved_dialnumbers_filename), Context.MODE_PRIVATE);
                Set<String> newNumberSet = new HashSet<String>();
                SharedPreferences.Editor editor = dialNumberPreferences.edit();
                editor.putStringSet(this.getString(R.string.dialnumbers_key), newNumberSet);
                if(editor.commit()){
                    if(numberContainer != null)
                        numberContainer.removeAllViews(); // Tar bort alla views eftersom vi har just tömt datan
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createDialList(LinearLayout container){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.saved_dialnumbers_filename),
                Context.MODE_PRIVATE);
        Set<String> numberSet = sharedPreferences.getStringSet(
                getString(R.string.dialnumbers_key),
                null);
        if(numberSet != null) {
            numberContainer = container; // Isåfall sparar vi detta som container till våra nummer

            // Loopar igenom numberSet och skapar en textview med nummret för varje entry
            for (String number : numberSet) {
                TextView numberText = new TextView(this);
                numberText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                numberText.setText(number);
                container.addView(numberText);
            }
        }
        else{
            TextView displayNoNumbers = new TextView(this);
            displayNoNumbers.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            displayNoNumbers.setText(getString(R.string.no_numbers_found_message));
            container.addView(displayNoNumbers);
        }
    }
}