package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {
    DialpadButton[] dialpadButtons;
    DialerTextView dialerTextView = null;
    public Dialpad(@NonNull Context context) {
        super(context);
        init(null);
    }

    public Dialpad(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        inflate(getContext(), R.layout.dialpad, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dialerTextView = (DialerTextView)findViewById(R.id.dialerTextView); // Hämtar referens till DialerTextView
        setupDialpadButtons(); // Hämtar referenser till alla knapparna och fixar OnClickListeners osv
    }

    /**
     * @brief Sätter onClickListeners på DialpadButtons
     * @param buttons Knapparna som onClickListeners ska sättas på
     */
    private void setupOnClickListeners(DialpadButton[] buttons){

        for(DialpadButton aButton: buttons){
            if(aButton != null) {
                aButton.setOnClickListenerActions(new DialpadButton.OnClickListener() {
                    @Override
                    public void onClick(DialpadButton button) {
                        handleOnButtonClickData(button);
                    }
                });
            }
        }


    }
    /* Funktion som anropas då en DialpadButton har blivit klickad på*/
    private void handleOnButtonClickData(DialpadButton button){
        if(dialerTextView != null)
            dialerTextView.buttonClicked(button); // Skickar vidare datan till denna funktion
    }
    /* Funktionen hämtar referenser till DialpadButtons och anropar setupOnClickListeners för dessa knappar */
    private void setupDialpadButtons() {
        dialpadButtons = new DialpadButton[12];
        dialpadButtons[0] = (DialpadButton) findViewById(R.id.Button_one);
        dialpadButtons[1] = (DialpadButton) findViewById(R.id.Button_two);
        dialpadButtons[2] = (DialpadButton) findViewById(R.id.Button_three);
        dialpadButtons[3] = (DialpadButton) findViewById(R.id.Button_four);
        dialpadButtons[4] = (DialpadButton) findViewById(R.id.Button_five);
        dialpadButtons[5] = (DialpadButton) findViewById(R.id.Button_six);
        dialpadButtons[6] = (DialpadButton) findViewById(R.id.Button_seven);
        dialpadButtons[7] = (DialpadButton) findViewById(R.id.Button_eight);
        dialpadButtons[8] = (DialpadButton) findViewById(R.id.Button_nine);
        dialpadButtons[9] = (DialpadButton) findViewById(R.id.Button_pound);
        dialpadButtons[10] = (DialpadButton) findViewById(R.id.Button_star);
        dialpadButtons[11] = (DialpadButton) findViewById(R.id.Button_zero);
        setupOnClickListeners(dialpadButtons);
    }
}
