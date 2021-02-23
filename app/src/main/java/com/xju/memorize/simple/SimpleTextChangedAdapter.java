package com.xju.memorize.simple;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class SimpleTextChangedAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Empty
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Empty
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Empty
    }
}
