package com.example.android.jogodavelha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class GameBeginDialog extends DialogFragment {

    private TextInputLayout player1Layout;
    private TextInputLayout player2Layout;

    private TextInputEditText player1EditText;
    private TextInputEditText player2EditText;

    private String player1;
    private String player2;

    private View rootView;
    private MainActivity activity;

    public static GameBeginDialog newInstance(MainActivity activity) {
        GameBeginDialog dialog = new GameBeginDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.game_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.done, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                GameBeginDialog.this.onDialogShow(alertDialog);
            }
        });
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.activity_game_begin_dialog, null, false);

        player1Layout = rootView.findViewById(R.id.layout_player1);
        player2Layout = rootView.findViewById(R.id.layout_player2);

        player1EditText = rootView.findViewById(R.id.et_player1);
        player2EditText = rootView.findViewById(R.id.et_player2);
        addTextWatchers();
    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameBeginDialog.this.onDoneClicked();
            }
        });
    }

    private void onDoneClicked() {
        if (isAValidName(player1Layout, player1) & isAValidName(player2Layout, player2)) {
            activity.onPlayersSet(player1, player2);
            dismiss();
        }
    }

    private boolean isAValidName(TextInputLayout layout, String name) {
        if (TextUtils.isEmpty(name)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.game_dialog_empty_name));
            return false;
        }

        if (player1 != null && player2 != null && player1.equalsIgnoreCase(player2)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.game_dialog_same_names));
            return false;
        }

        layout.setErrorEnabled(false);
        layout.setError("");
        return true;
    }

    private void addTextWatchers() {
        player1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                player1 = s.toString();
            }
        });
        player2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                player2 = s.toString();
            }
        });
    }
}
