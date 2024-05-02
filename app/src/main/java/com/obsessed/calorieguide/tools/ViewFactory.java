package com.obsessed.calorieguide.tools;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class ViewFactory {
    private Context context;

    public ViewFactory(Context context) {
        this.context = context;
    }

    public LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    public Spinner createSpinner() {
        Spinner spinner = new Spinner(context);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        return spinner;
    }

    public EditText createEditText() {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                3.0f
        ));
        return editText;
    }
}
