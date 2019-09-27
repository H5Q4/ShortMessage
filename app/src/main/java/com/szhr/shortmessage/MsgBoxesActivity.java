package com.szhr.shortmessage;

import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class MsgBoxesActivity extends BaseListActivity {

    private int inboxCount;
    private int sentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.read_sms));

        inboxCount = SmsOperations.getSmsCount(this, SmsOperations.Folder.INBOX);
        sentCount = SmsOperations.getSmsCount(this, SmsOperations.Folder.OUTBOX);

        addListItem(getString(R.string.inbox), inboxCount == 0 ? "" : String.valueOf(inboxCount));
        addListItem(getString(R.string.sent_box), sentCount == 0 ? "" : String.valueOf(sentCount));

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        if (position == 0) {
            if (inboxCount == 0) {
                toast(getString(R.string.empty_inbox));
            }
        } else {
            if (sentCount == 0) {
                toast(getString(R.string.empty_sent_box));
            }
        }
    }
}
