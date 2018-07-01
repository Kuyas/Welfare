package com.example.android.welfare;

import android.support.design.widget.TextInputEditText;
import android.widget.Spinner;

public class AlterView {

    public void disableTextInput (TextInputEditText textInputEditText) {
        textInputEditText.setFocusable(false);
        textInputEditText.setEnabled(false);
        textInputEditText.setCursorVisible(false);
    }

    public void enableText (TextInputEditText textInputEditText) {
        textInputEditText.setFocusable(true);
        textInputEditText.setEnabled(true);
        textInputEditText.setCursorVisible(true);
    }

    public void disableSpinner (Spinner spinner) {

    }
}
