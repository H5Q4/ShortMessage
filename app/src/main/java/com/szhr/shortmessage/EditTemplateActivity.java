package com.szhr.shortmessage;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.util.TemplatesProvider;

public class EditTemplateActivity extends BaseActivity {
    public static final String KEY_FOR_UPDATE = "for_update";

    private EditText contentEt;
    private boolean forUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);

        contentEt = findViewById(R.id.contentEt);

        forUpdate = getIntent().getBooleanExtra(KEY_FOR_UPDATE, false);

        if (forUpdate) {
            String tplBody = getIntent().getStringExtra(TemplateActivity.KEY_TEMPLATE_BODY);

            contentEt.setText(tplBody);
            contentEt.setSelection(tplBody.length());
        }
    }

    @Override
    protected void onClickBottomLeft() {
        Runnable bgTask = null;
        String tplBody = contentEt.getText().toString().trim();
        if (TextUtils.isEmpty(tplBody)) {
            toast(getString(R.string.empty_sms));
            return;
        }

        final ContentValues values = new ContentValues();
        values.put(TemplatesProvider.Template.TEXT, tplBody);

        if (forUpdate) {
            String tplId = getIntent().getStringExtra(TemplateActivity.KEY_TEMPLATE_ID);
            final Uri uri = ContentUris.withAppendedId(TemplatesProvider.Template.CONTENT_URI, Integer.parseInt(tplId));
            bgTask = new Runnable() {
                @Override
                public void run() {
                    getContentResolver().update(uri, values, null, null);
                }
            };
        } else {
            bgTask = new Runnable() {
                @Override
                public void run() {
                    getContentResolver().insert(TemplatesProvider.Template.CONTENT_URI, values);
                }
            };
        }

        getAsyncDialog().runAsync(bgTask, new Runnable() {
            @Override
            public void run() {
                toastThenFinish(getString(R.string.op_completed));
            }
        }, R.string.please_wait);
    }
}
