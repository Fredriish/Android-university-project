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

public class DialpadButton extends LinearLayout {
    public TextView title;
    public TextView message;

    public DialpadButton(Context context, AttributeSet attrs) {
        super(context);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        initLayoutAttributes();
        TypedArray attributeArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DialpadButton,
                0,0);
        CharSequence titleText = attributeArray.getText(R.styleable.DialpadButton_title);
        CharSequence messageText = attributeArray.getText(R.styleable.DialpadButton_message);
        attributeArray.recycle();

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
        addFadeOnHoldAnimation(this, this);
        this.addView(title);
        this.addView(message);
    }
    private void initLayoutAttributes(){
        this.setOrientation(VERTICAL);
        this.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    }
    /* @brief Lägger till en animation som blir startad då när man håller in och blir reversad då man släpper
       @param targetView: View objektet som fade animationen ska ske på
       @param triggerView: View objektet som triggrar animationen
     */
    private void addFadeOnHoldAnimation(View targetView, View triggerView){
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(targetView, View.ALPHA, 0.3f);
        triggerView.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    alphaAnimation.start();
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    alphaAnimation.reverse();
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
