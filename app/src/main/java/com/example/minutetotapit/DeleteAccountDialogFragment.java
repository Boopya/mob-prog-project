package com.example.minutetotapit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class DeleteAccountDialogFragment extends DialogFragment {

    DatabaseHelper db;
    String username;

    public DeleteAccountDialogFragment(String username, Context context) {
        this.username = username;
        db = new DatabaseHelper(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle saveedInstanceState) {
        // use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_account)
                .setMessage("Are you sure you want to delete your account permanently?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Account successfully deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        db.deleteAccount(username);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        // create the AlertDialog object and return it
        return builder.create();
    }
}
