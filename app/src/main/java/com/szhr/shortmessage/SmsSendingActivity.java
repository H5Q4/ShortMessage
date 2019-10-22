package com.szhr.shortmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.widget.ImageView;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.util.SmsOperations;

import static com.szhr.shortmessage.util.SmsOperations.ACTION_SMS_DELIVERY;
import static com.szhr.shortmessage.util.SmsOperations.ACTION_SMS_SEND;

public class SmsSendingActivity extends BaseActivity {
    public static final String KEY_NUMBER = "number";
    public static final String KEY_BODY = "body";
    public static final String KEY_PERSIST = "persist";

    public static final int WHAT_SEND_SUCCESS = 1;
    public static final int WHAT_SEND_FAIL = 2;
    public static final int WHAT_DELIVERY_SUCCESS = 3;
    public static final int WHAT_DELIVERY_FAIL = 4;

    private ImageView animIv;
    private AnimationDrawable animationDrawable;
    private SMSReceiver sendReceiver;
    private SMSReceiver deliveryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_sending);

        animIv = (ImageView)findViewById(R.id.animIv);
        animIv.setImageResource(R.drawable.sms_sending);

        animationDrawable = (AnimationDrawable) animIv.getDrawable();

        String number = getIntent().getStringExtra(KEY_NUMBER);
        String body = getIntent().getStringExtra(KEY_BODY);
        boolean persist = getIntent().getBooleanExtra(KEY_PERSIST, false);

        // 注册send
        sendReceiver = new SMSReceiver();
        IntentFilter sendFilter = new IntentFilter(ACTION_SMS_SEND);
        registerReceiver(sendReceiver, sendFilter);
        // 注册delivery
        deliveryReceiver = new SMSReceiver();
        IntentFilter deliveryFilter = new IntentFilter(ACTION_SMS_DELIVERY);
        registerReceiver(deliveryReceiver, deliveryFilter);

        animationDrawable.start();
        SmsOperations.sendMessage(this, number, body, persist);
    }

    @Override
    protected void handleSelfMessage(Message message) {
        String text = "";
        switch (message.what) {
            case WHAT_SEND_SUCCESS:
                text = getString(R.string.send_success);
                break;
            case WHAT_SEND_FAIL:
                text = getString(R.string.send_fail);
                break;
            case WHAT_DELIVERY_SUCCESS:
                text = getString(R.string.receive_success);
                break;
            case WHAT_DELIVERY_FAIL:
                text = getString(R.string.receive_fail);
                break;
        }
        toastThenFinish(text);
    }

    public static void start(Context context, String number, String body, boolean persist) {
        Intent intent = new Intent(context, SmsSendingActivity.class);
        intent.putExtra(KEY_NUMBER, number);
        intent.putExtra(KEY_BODY, body);
        intent.putExtra(KEY_PERSIST, persist);
        context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        if (sendReceiver != null) {
            unregisterReceiver(sendReceiver);
        }
        if (deliveryReceiver != null) {
            unregisterReceiver(deliveryReceiver);
        }
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        super.onPause();
    }

    public class SMSReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();
            int resultCode = getResultCode();

            if (animationDrawable != null && animationDrawable.isRunning()) {
                animationDrawable.stop();
            }

            Message message = Message.obtain();

            if (actionName.equals(ACTION_SMS_SEND)) {
                if (resultCode == RESULT_OK) {
                   message.what = WHAT_SEND_SUCCESS;
               } else {
                    message.what = WHAT_SEND_FAIL;
               }
            } else if (actionName.equals(ACTION_SMS_DELIVERY)) {
                if (resultCode == RESULT_OK) {
                    message.what = WHAT_DELIVERY_SUCCESS;
                } else {
                    message.what = WHAT_DELIVERY_FAIL;
                }
            }

            handler.sendMessage(message);
        }
    }
}
