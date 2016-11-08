package esi.kaly.com.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import esi.kaly.com.stormy.R;

/**
 * Created by Ivan on 10/7/2016.
 */
public class AlertDialogFragment extends DialogFragment{

    private static final String TITLE = "TITLE";
    private static final String MESSAGE = "MESSAGE";

    private String mTitle;
    private String mMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(TITLE);
        mMessage = getArguments().getString(MESSAGE);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(R.string.error_ok_button, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }


    public static AlertDialogFragment newInstance(String title, String message) {

        Bundle args = new Bundle();
        AlertDialogFragment fragment = new AlertDialogFragment();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertDialogFragment newInstance(String title, String message, String positiveText) {

        Bundle args = new Bundle();
        AlertDialogFragment fragment = new AlertDialogFragment();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }
}
