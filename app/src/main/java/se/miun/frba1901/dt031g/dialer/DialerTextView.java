package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DialerTextView extends ConstraintLayout {
    TextView mainText = null;
    ImageView eraseImage = null;
    ImageView dialImage = null;
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
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + Uri.encode(mainText.getText().toString()));

            intent.setData(uri);
            getContext().startActivity(intent);
        }
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
