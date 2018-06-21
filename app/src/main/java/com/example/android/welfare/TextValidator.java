package com.example.android.welfare;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator {
    private TextInputEditText input;
    private String inputString;
    private boolean error;

    public TextValidator(TextInputEditText textInput) {
        this.input = textInput;
        if (isEmpty(getText(this.input))) {
            error = true;
        } else {
            inputString = getText(this.input);
        }
    }
    private String getText(TextInputEditText input) {
        if (input==null) {
            return "";
        } else {
            return input.getText().toString().trim();
        }
    }

    private boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    public boolean isValid() {
        return error;
    }

    public boolean regexValidator(String regexPattern) {
        if (error) return true;
        else {
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(inputString);
            return matcher.matches();
        }
    }
}
