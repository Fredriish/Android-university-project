package se.miun.frba1901.dt031g.dialer;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DialerTextView extends ConstraintLayout {
    TextView mainText = null;
    ImageView eraseImage = null;
    ImageView dialImage = null;
    Location userLocation = null;
    public DialerTextView(@NonNull Context context) {
        super(context);
        init(null);

    }

    public DialerTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        inflate(getContext(), R.layout.dialer_textview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Hämtar referenser till Views
        mainText = findViewById(R.id.enteredDigitView);
        eraseImage = findViewById(R.id.eraseIcon);
        dialImage = findViewById(R.id.callIcon);

        setupEventListeners();
    }

    /* Sätter alla clickListeners som ska finnas */
    private void setupEventListeners(){
        eraseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                eraseClicked();
            }
        });
        eraseImage.setOnLongClickListener(new OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                while(eraseClicked()); // Gör eraseClicked() tills allt är erased
                return true;
            }
        });
        dialImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialEvent();
            }
        });
    }
    /* Anropas då ring knappen klickas på, startar ett implicit intent för att ringa */
    private void dialEvent(){
        if (mainText.getText().length() > 0) {
            // Sparar nummret om det är satt som preference
            if(SettingsActivity.shouldStoreNumbers(getContext())){
                saveNumber(mainText.getText());
            }

            Intent intent;

            // Kollar om vi har permission till att ringa direkt från denna app, annars så skapas Intent med
            // ACTION.DIAL och öppnas genom dial appen
            if(getContext().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)
                intent = new Intent(Intent.ACTION_CALL);
            else
                intent = new Intent(Intent.ACTION_DIAL);

            Uri uri = Uri.parse("tel:" + Uri.encode(mainText.getText().toString()));
            intent.setData(uri);
            getContext().startActivity(intent);
        }
    }
    public void updateLocationData(Location location){
        userLocation = location;
    }
    /* Sparar nummret */
    private void saveNumber(CharSequence number){
        DialNumber dialData = new DialNumber();
        dialData.number = number.toString();
        if(userLocation != null){
            dialData.longitude = userLocation.getLongitude();
            dialData.latitude = userLocation.getLatitude();
        }
        dialData.date = Calendar.getInstance().getTime().toString();
        DialDatabaseManager.getInstance(getContext()).insertAll(dialData);
    }

    /**
     * @brief Används då en DialpadButton har blivit klickad på för att ändra det som är inskrivet
     * @param button Knappen som har blivit klickad på
     */
    public void buttonClicked(DialpadButton button){
        if(mainText != null){
            String str = mainText.getText().toString() + button.getTitle().toString();
            mainText.setText(str);
        }
    }

    /**
     * @brief Anropas då erase knappen har blivit klickan, tar bort senaste numret som har blivit skrivet
     * @return Returnerar true om något kunde tas bort från TextView, annars false
     */
    private boolean eraseClicked(){
        String tmpStr = mainText.getText().toString();
        if(tmpStr.length() > 0) {
            // Tar bort 1 char från stringen
            StringBuilder sb = new StringBuilder(tmpStr);
            sb.deleteCharAt(sb.length() - 1);
            mainText.setText(sb.toString());
            return true;
        }
        return false;
    }

}
