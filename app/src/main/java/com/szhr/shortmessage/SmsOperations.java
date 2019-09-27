package com.szhr.shortmessage;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;

import java.util.List;

public class SmsOperations {

    public static class Folder {
        public static final String INBOX = "inbox";
        public static final String OUTBOX = "sent";
    }

    public static void sendMessage(Context context, String phone, String message) {
        SmsManager sms = SmsManager.getDefault();
        // if message length is too long messages are divided
        List<String> messages = sms.divideMessage(message);
        for (String msg : messages) {
            PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent("SMS_SENT"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent("SMS_DELIVERED"), 0);
            sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);
        }
    }

    public static int getSmsCount(Context context, String folder) {
        // gets total count of messages in inbox
        if (folder == null) {
            folder = "";
        }
        String uri = "content://sms/" + folder;
        Uri mSmsQueryUri = Uri.parse(folder);
        String[] columns = new String[] {Telephony.Sms._ID};
        Cursor c = context.getContentResolver().query(mSmsQueryUri, columns, null, null, null);
        if (c == null) {
            return 0;
        }
        c.close();

        return c.getCount();
    }
}
