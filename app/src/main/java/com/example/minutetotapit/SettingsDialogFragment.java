package com.example.minutetotapit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class SettingsDialogFragment extends DialogFragment {

    String username;
    Context context;

    public SettingsDialogFragment(String username, Context context) {
        this.username = username;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.settings)
                .setItems(R.array.modes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogFragment deleteAccountDialogFragment = new DeleteAccountDialogFragment(username, context);
                        deleteAccountDialogFragment.show(getFragmentManager(), "delete_account");
                    }
                });

        // create the AlertDialog object and return it
        return builder.create();
    }
}
