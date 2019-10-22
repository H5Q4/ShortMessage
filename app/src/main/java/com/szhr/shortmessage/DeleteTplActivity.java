package com.szhr.shortmessage;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.szhr.shortmessage.base.AsyncDialog;
import com.szhr.shortmessage.base.ConfirmActivity;
import com.szhr.shortmessage.model.Sms;
import com.szhr.shortmessage.util.SmsOperations;
import com.szhr.shortmessage.util.TemplatesProvider;

public class DeleteTplActivity extends ConfirmActivity {

    public static final String KEY_FOR_ALL = "for_all";
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onConfirm() {
        boolean deleteAll = getIntent().getBooleanExtra(KEY_FOR_ALL, false);
        Runnable bgTask = null;
        if (deleteAll) {
            // delete all
            bgTask = new Runnable() {
                @Override
                public void run() {
                    result = getContentResolver().delete(TemplatesProvider.Template.CONTENT_URI,
                            null, null);
                }
            };
        } else {
            final String tplId = getIntent().getStringExtra(TemplateActivity.KEY_TEMPLATE_ID);

            bgTask = new Runnable() {
                @Override
                public void run() {
                    Uri uriToDelete = ContentUris
                            .withAppendedId(TemplatesProvider.Template.CONTENT_URI, Integer.parseInt(tplId));
                    result = getContentResolver().delete(uriToDelete, null, null);
                }
            };

        }

        getAsyncDialog().runAsync(bgTask, new Runnable() {
            @Override
            public void run() {
                if (result > 0) {
                    toastThenFinish(getString(R.string.op_completed));
                } else {
                    toastThenFinish(getString(R.string.op_failed));
                }
            }
        }, R.string.please_wait);
    }
}
