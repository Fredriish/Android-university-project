package se.miun.frba1901.dt031g.dialer;
/*
* Filnamn: DialpadButton.java
* Klass som ska förställa en knapp i en "Dialpad", knappen
* har en stor titel och ett mindre meddelande under titeln.
 */
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DialpadButton extends LinearLayout {
    private TextView title;
    private TextView message;
    public interface OnClickListener{
        void onClick(DialpadButton button);
    }
    private DialpadButton.OnClickListener clickListener;

    /* Används för att sätta en implementation av interfacet OnClickListener som ska hända då knappen blir klickad på */
    public void setOnClickListenerActions(OnClickListener newListener){
        this.clickListener = newListener;
    }
    public CharSequence getTitle() {
        return title.getText();
    }

    public CharSequence getMessage() {
        return message.getText();
    }
    public DialpadButton(Context context) {
        super(context);
        this.clickListener = null;
        init(context, null);
    }
    public DialpadButton(Context context, AttributeSet attrs) {
        super(context);
        this.clickListener = null;
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        initLayoutAttributes();
        CharSequence titleText = "";
        CharSequence messageText = "";
        if(attrs != null) {

            // Behövde hämta ID manuellt (inte säker varför) vilket resulterar i komplex kod nedanför

            // Attributes som inte är custom men som ändå ska hämtas manuellt anges här
            int[] androidAttributes = new int[]{
                    android.R.attr.id
            };

            // Skapar en array som har plats för androidAttributes + custom attributes
            int[] attrsArray = new int[androidAttributes.length + R.styleable.DialpadButton.length];

            // Kopierar androidAttributes till början av attrsArray
            System.arraycopy(androidAttributes, 0, attrsArray, 0, androidAttributes.length);

            // Kopierar custom attributes till attrsArray, på platsen där androidAttributes slutar
            System.arraycopy(R.styleable.DialpadButton, 0, attrsArray, androidAttributes.length,
                    R.styleable.DialpadButton.length);

            // Hämtar attribut från våran "attrsArray"
            TypedArray attributeArray = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    attrsArray,
                    0, 0);
            int id = attributeArray.getResourceId(0,0); // Hämtar ID
            setId(id);

            // Hämtar custom attributes, vi måste addera androidAttributes.length för att skippa början
            // av arrayen där icke-custom attributes ligger
            titleText = attributeArray.getText(R.styleable.DialpadButton_title + androidAttributes.length);
            messageText = attributeArray.getText(R.styleable.DialpadButton_message + androidAttributes.length);
            attributeArray.recycle();
        }

        initComponents(context);

        if(titleText != null && titleText.length() > 0)
            setTitle(titleText.toString());
        if(messageText != null && messageText.length() > 0)
            setMessage(messageText.toString());
    }
    private void initComponents(Context context){
        title = new TextView(context);
        title.setGravity(Gravity.CENTER);
        title.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                1f
        ));
        title.setAutoSizeTextTypeUniformWithConfiguration(1,100
                ,1, TypedValue.COMPLEX_UNIT_SP);
        message = new TextView(context);
        message.setGravity(Gravity.CENTER);
        message.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                3f
                ));
        message.setAutoSizeTextTypeUniformWithConfiguration(1,100
                ,1, TypedValue.COMPLEX_UNIT_SP);
        addOnTouchListener(this, this, context);
        this.addView(title);
        this.addView(message);
    }
    private void initLayoutAttributes(){
        this.setOrientation(VERTICAL);
        this.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    }

    /** @brief Lägger till en animation som blir startad då när man håller in och blir reversad då man släpper,
     *  triggrar även ett onClick event från OnClickListener interface
     *  @param targetView: View objektet som fade animationen ska ske på
     *  @param triggerView: View objektet som triggrar animationen
     */
    private void addOnTouchListener(View targetView, View triggerView, Context context){
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(targetView, View.ALPHA, 0.3f);
        DialpadButton thisReference = this; // Kan inte använda this keyword nedanför då vi skapar overridar
        // ett interface så denna variabel används
        triggerView.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(clickListener != null)
                        clickListener.onClick(thisReference); // Skickar OnClickListener event
                    alphaAnimation.start();
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    alphaAnimation.reverse();
                    SoundPlayer.getInstance(context).playSound(thisReference);
                    return true;
                }
                return false;
            }
        });


    }

    /*
    @brief Sätter titeln av knappen
    @param text: Nya titeln
    @details Kommer bara att ta första bokstaven i parametern text
     */
    public void setTitle(String text){
        if(text.length() > 0)
           title.setText(text.substring(0,1)); // Tar bara första bokstaven
    }
    /*
    @brief Sätter meddelande i knappen
    @param text: Nya meddelandet
    @details Tar bara de första 4 bokstäverna i parametern text
     */
    public void setMessage(String text){
        if(text.length() > 4)
            message.setText(text.substring(0, 4));
        else
            message.setText(text);
    }

}
