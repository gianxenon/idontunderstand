package com.example.lmtapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class exitDialogFragment  extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Log Out");
    builder.setMessage("Are you sure do you want to Log Out?");
    builder.setCancelable(false);

    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            System.exit(1);
        }
    });
    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(getContext(),"you clicked cancel",Toast.LENGTH_LONG).show();

        }
    });
    AlertDialog alertDialog = builder.create();
    return  alertDialog;
    }
}
