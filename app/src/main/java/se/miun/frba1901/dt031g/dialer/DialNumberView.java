package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialNumberView extends LinearLayout {
    private DialNumber dialNumber;
    private float mainTextSize;
    private float subTextSize;
    public DialNumberView(Context context, DialNumber number, float tSizeMain, float tSizeSub){
        super(context);
        dialNumber = number;
        mainTextSize = tSizeMain;
        subTextSize = tSizeSub;
        init();
    }

    public void init(){
        this.setOrientation(VERTICAL);

        TextView numberText = new TextView(getContext());
        numberText.setTextSize(mainTextSize);
        numberText.setText(dialNumber.number);
        numberText.setTextColor(Color.BLACK);
        this.addView(numberText);

        TextView dateText = new TextView(getContext());
        dateText.setTextSize(subTextSize);
        dateText.setText(dialNumber.date);
        this.addView(dateText);

        TextView locationText = new TextView(getContext());
        locationText.setTextSize(subTextSize);
        String tmpLatitude;
        if(dialNumber.latitude == null)
            tmpLatitude = "??";
        else
            tmpLatitude = dialNumber.latitude.toString();

        String tmpLongitude;
        if(dialNumber.longitude == null)
            tmpLongitude = "??";
        else
            tmpLongitude = dialNumber.longitude.toString();

        locationText.setText("[" + tmpLatitude + ", " + tmpLongitude + "]");
        this.addView(locationText);
    }

}
