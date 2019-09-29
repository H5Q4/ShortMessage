package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.model.Sms;

import java.util.List;

public class MsgListActivity extends BaseListActivity {
    public static final String KEY_TYPE = "type";

    public static final int INBOX = 1;
    public static final int OUTBOX = 2;
    public static final int TEMPLATE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra(KEY_TYPE, 1);

        switch (type) {
            case INBOX:
                setTitle(getString(R.string.inbox));
                break;
            case OUTBOX:
                setTitle(getString(R.string.sent_box));
                List<Sms> sms = SmsOperations.getSms(this, SmsOperations.Folder.OUTBOX);

                break;
            case TEMPLATE:
                break;
        }

    }
}
