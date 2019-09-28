package com.szhr.shortmessage;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;

import com.szhr.shortmessage.model.Sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsOperations {

    public static class Folder {
        public static final String INBOX = "inbox";
        public static final String OUTBOX = "sent";
    }

    public static void sendMessage(Context context, String phone, String message, boolean persist) {
        SmsManager sms = SmsManager.getDefault();
        // if message length is too long messages are divided
        List<String> messages = sms.divideMessage(message);
        for (String msg : messages) {
            PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent("SMS_SENT"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent("SMS_DELIVERED"), 0);

            if (persist) {
                sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);
            } else {
                // TODO Uncomment this
//            sms.sendTextMessageWithoutPersisting(phone, null, msg, sentIntent, deliveredIntent);
            }
        }
    }

    public static int getSmsCount(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            c.close();
        }

        return totalSMS;
    }

    public static int getInboxCount(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            c.close();
        }

        return totalSMS;
    }

    public static int getOutboxCount(Context context) {
        return getSmsCount(context) - getInboxCount(context);
    }

    public static List<Sms> getSms(Context context, String folder) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Telephony.Sms.CONTENT_URI;
        if (Folder.INBOX.equals(folder)) {
            uri = Telephony.Sms.Inbox.CONTENT_URI;
        }
        Cursor c = cr.query(uri, null, null, null, null);
        int totalSMS = 0;
        List<Sms> result = new ArrayList<>();
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.PERSON));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    int status = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.STATUS));
                    int type =c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.TYPE));
                    Date dateFormat= new Date(Long.valueOf(smsDate));

                    if (Folder.INBOX.equals(folder) &&
                            c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                                    == Telephony.Sms.MESSAGE_TYPE_INBOX) {
                       continue;
                    }

                    Sms sms = new Sms();
                    sms.receiver = number;
                    sms.content = body;
                    sms.date = dateFormat;
                    sms.status = status;
                    sms.type = type;
                    // TODO determine sms.fromSim
                    result.add(sms);

                    c.moveToNext();
                }
            }

            c.close();

        }

        return result;

    }
}
