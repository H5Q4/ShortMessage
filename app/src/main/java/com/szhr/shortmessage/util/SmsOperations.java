package com.szhr.shortmessage.util;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
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
                    int id = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms._ID));
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String sender = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    int status = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.STATUS));
                    int type = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.TYPE));
                    Date dateFormat = new Date(Long.valueOf(smsDate));

                    if (Folder.INBOX.equals(folder) &&
                            c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                                    == Telephony.Sms.MESSAGE_TYPE_INBOX) {
                        continue;
                    }

                    Sms sms = new Sms();
                    sms.id = id;
                    sms.sender = sender;
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

    public static void saveDraft(Context context, Sms sms) {
        //Store the message in the draft folder so that it shows in Messaging apps.
        ContentValues values = new ContentValues();
        // Message address.
        values.put("address", sms.sender);
        // Message body.
        values.put("body", sms.content);
        // Date of the draft message.
        values.put("date", String.valueOf(System.currentTimeMillis()));
        values.put("type", "3");
        // Put the actual thread id here. 0 if there is no thread yet.
        values.put("thread_id", "0");
        context.getContentResolver().insert(Uri.parse("content://sms/draft"), values);
    }

    public static void deleteSms(Context context, int id) {
        Uri uri = Telephony.Sms.CONTENT_URI;
        Cursor c = context.getContentResolver().query(uri,
                new String[]{Telephony.Sms._ID}, null,
                null, null); // only query _ID and not everything
        if (c == null) return;

        while (c.moveToNext()) {
            // Delete the SMS
            int pid = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms._ID)); // Get _id;
            if (id == pid) {
                uri = Telephony.Sms.CONTENT_URI.buildUpon().appendPath(String.valueOf(pid)).build();
                context.getContentResolver().delete(uri,
                        null, null);
            }

        }
        c.close();
    }

    public static void deleteAll(Context context) {
        context.getContentResolver().delete(Telephony.Sms.CONTENT_URI,
                null, null);
    }
}
