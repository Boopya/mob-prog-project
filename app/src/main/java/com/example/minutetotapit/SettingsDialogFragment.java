package com.example.minutetotapit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class SettingsDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.settings)
                .setItems(R.array.modes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Night Mode
                                Toast.makeText(getContext(), "Night Mode", Toast.LENGTH_SHORT).show();
                                break;
                            case 1: // Normal Mode
                                Toast.makeText(getContext(), "Normal Mode", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
