package com.example.android.welfare.userdetails;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator {
    public static final String ifscregex = "^[A-Za-z]{4}\\d{7}$";
    public static final String accountnumberregex = "^\\d{9,18}$";
    public static final String mobilenumberregex = "\\d{10}";
    public static final String passwordregex = "[a-zA-Z0-9]{8,16}";
    public static final String dateregex = "[0-9]{2}-[0-9]{2}-[0-9]{4}";


    private TextInputEditText input;
    private TextView inputtextview;
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

    public TextValidator(TextView textView) {
        this.inputtextview = textView;
        if (isEmpty(getText(this.inputtextview))) {
            error = true;
        } else {
            inputString = getText(this.inputtextview);
        }
    }

    private String getText(TextInputEditText input) {
        if (input == null) {
            return "";
        } else {
            return input.getText().toString().trim();
        }
    }

    private String getText(TextView inputtextview) {
        if (inputtextview == null) {
            return "";
        } else {
            return inputtextview.getText().toString().trim();
        }
    }

    public String returnText() {
        return inputString;
    }

    private boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    public boolean isValid() {
        return !error;
    }

    public boolean regexValidator(String regexPattern) {
        if (error) return false;
        else {
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(inputString);
            return matcher.matches();
        }
    }
}
