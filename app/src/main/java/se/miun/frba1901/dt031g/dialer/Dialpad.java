package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {
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
}
