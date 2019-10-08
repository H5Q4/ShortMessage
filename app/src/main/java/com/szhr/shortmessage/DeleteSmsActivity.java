package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.ConfirmActivity;
import com.szhr.shortmessage.model.Sms;

import java.io.Serializable;

public class DeleteSmsActivity extends ConfirmActivity {

    public static final String KEY_FOR_ALL = "for_all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onConfirm() {
        boolean deleteAll = getIntent().getBooleanExtra(KEY_FOR_ALL, false);
        if (deleteAll) {
            // delete all
        } else {
            Sms sms = (Sms) getIntent().getSerializableExtra(ReadSmsActivity.KEY_SMS);
            // delete by id
        }
    }
}
