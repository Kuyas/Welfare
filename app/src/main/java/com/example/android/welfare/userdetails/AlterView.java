package com.example.android.welfare.userdetails;

import android.support.design.widget.TextInputEditText;
import android.widget.Button;
import android.widget.Spinner;

public class AlterView {

    public AlterView() {
    }

    public void disableTextInput(TextInputEditText textInputEditText) {
        textInputEditText.setFocusable(false);
        textInputEditText.setEnabled(false);
        textInputEditText.setCursorVisible(false);
    }

    public void enableTextInput(TextInputEditText textInputEditText) {
        textInputEditText.setFocusable(true);
        textInputEditText.setFocusableInTouchMode(true);
        textInputEditText.setEnabled(true);
        textInputEditText.setCursorVisible(true);
    }

    public void disableSpinner(Spinner spinner) {
        spinner.setEnabled(false);
        spinner.setClickable(false);
    }

    public void enableSpinner(Spinner spinner) {
        spinner.setEnabled(true);
        spinner.setClickable(true);
    }

    public void disableButton(Button button) {
        button.setEnabled(false);
        button.setClickable(false);
    }

    public void enableButton(Button button) {
        button.setEnabled(true);
        button.setClickable(true);
    }
}
