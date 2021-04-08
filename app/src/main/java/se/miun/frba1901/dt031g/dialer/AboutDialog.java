package se.miun.frba1901.dt031g.dialer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AboutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About")
                .setMessage("This app is supposed to mimic the keypad on a phone. The app will " +
                        "consist of activities to: \n\n"
                + "• Enter numbers to dial\n"
                + "• See previously dialed numbers\n"
                + "• Change the keypad settings\n"
                + "• Show on a Map where previous calls are dialed from")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                });

        return builder.create();
    }
}
