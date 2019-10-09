package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.AsyncDialog;
import com.szhr.shortmessage.base.ConfirmActivity;
import com.szhr.shortmessage.model.Sms;

import java.io.Serializable;

public class DeleteSmsActivity extends ConfirmActivity {

    public static final String KEY_FOR_ALL = "for_all";

    private AsyncDialog asyncDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asyncDialog = new AsyncDialog(this);
    }

    @Override
    protected void onConfirm() {
        boolean deleteAll = getIntent().getBooleanExtra(KEY_FOR_ALL, false);
        if (deleteAll) {
            // delete all
            asyncDialog.runAsync(new Runnable() {
                @Override
                public void run() {
                    SmsOperations.deleteAll(DeleteSmsActivity.this);
                }
            }, new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, R.string.please_wait);
        } else {
            asyncDialog.runAsync(new Runnable() {
                @Override
                public void run() {
                    Sms sms = (Sms) getIntent().getSerializableExtra(ReadSmsActivity.KEY_SMS);
                    // delete by id
                    SmsOperations.deleteSms(DeleteSmsActivity.this, sms.id);
                }
            }, new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, R.string.please_wait);

        }
    }

    @Override
    protected void onDestroy() {
        if (asyncDialog != null) {
            asyncDialog.clearPendingProgressDialog();
        }
        super.onDestroy();
    }
}
