package com.szhr.shortmessage;

import android.content.Intent;
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

        inboxCount = SmsOperations.getInboxCount(this);
        sentCount = SmsOperations.getOutboxCount(this);

        addListItem(getString(R.string.inbox), inboxCount == 0 ? "" : String.valueOf(inboxCount));
        addListItem(getString(R.string.sent_box), sentCount == 0 ? "" : String.valueOf(sentCount));

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent(this, MsgListActivity.class);

        if (position == 0) {
            if (inboxCount == 0) {
                toast(getString(R.string.empty_inbox));
                return;
            }

            intent.putExtra(MsgListActivity.KEY_TYPE, MsgListActivity.INBOX);
        } else {
            if (sentCount == 0) {
                toast(getString(R.string.empty_sent_box));
                return;
            }
            intent.putExtra(MsgListActivity.KEY_TYPE, MsgListActivity.OUTBOX);
        }

        startActivity(intent);
    }
}
