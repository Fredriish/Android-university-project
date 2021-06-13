package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public static boolean shouldStoreNumbers(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getString(R.string.store_numbers_key), true);
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            setPreferenceClickListeners();
        }
        private void setPreferenceClickListeners(){
            Preference deleteStoredNumbersPreference = findPreference(getContext()
                    .getString(R.string.delete_stored_numbers_key));
            deleteStoredNumbersPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    deleteStoredNumbers();
                    return true;
                }
            });
        }
        /* Tömmer nummren som har sparats med hjälp av sharedpreference API */
        public void deleteStoredNumbers(){
            SharedPreferences dialNumberPreferences = getContext().getSharedPreferences(getContext()
                    .getString(R.string.saved_dialnumbers_filename), Context.MODE_PRIVATE);

            Set<String> newNumberSet = new HashSet<String>();
            SharedPreferences.Editor editor = dialNumberPreferences.edit();
            editor.putStringSet(getContext().getString(R.string.dialnumbers_key), newNumberSet);
            editor.apply();
        }
    }
}