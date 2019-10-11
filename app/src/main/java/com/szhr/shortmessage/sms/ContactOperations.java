package com.szhr.shortmessage.sms;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.AUTHORITY;

public class ContactOperations {
    /**
     * 查询手机联系人
     */
    public static List<Contact> queryPhoneContacts(ContentResolver resolver, String searchString) {

        String selection = null;
        String[] selectionArgs = null;

        if (!TextUtils.isEmpty(searchString)) {
            selection = Contacts.DISPLAY_NAME + " LIKE ?";
            selectionArgs = new String[]{"%" + searchString + "%"};
        }

        String[] projection = {CommonDataKinds.Phone.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER, RawContacts._ID, RawContacts.ACCOUNT_TYPE};
        List<Contact> items = new ArrayList<>();
        Cursor cursor = resolver.query(CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, selectionArgs, CommonDataKinds.Phone.DISPLAY_NAME);
        if (cursor == null) {
            return items;
        }

        //  public static final String ACCOUNT_TYPE_SIM = "com.android.sim";
        //  public static final String ACCOUNT_TYPE_PHONE = "com.android.localphone";
        while (cursor.moveToNext()) {
            String displayName = cursor.getString(0);
            String phoneNumber = cursor.getString(1);
//            Log.d(TAG, cursor.getString(cursor.getColumnIndex(RawContacts.ACCOUNT_TYPE)));
            items.add(new Contact(displayName, phoneNumber));
        }

        cursor.close();
        return items;

    }

    public static int getPhoneContactCount(ContentResolver resolver) {
        String[] projection = {RawContacts._ID};
        Cursor cursor = resolver.query(CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        if (cursor == null) {
            return 0;
        }

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public static List<Contact> querySimContacts(ContentResolver resolver, String queryString) {

        String displayName;
        String phoneNumber;
        Contact contact = null;
        List<Contact> items = new ArrayList<>();

        Uri simUri = Uri.parse("content://icc/adn");
        String[] projection = {"name", "number"};

        Cursor cursorSim = resolver.query(simUri, projection, null, null, "name");

        if (cursorSim == null) return items;

        Log.i("SimContact", "total: " + cursorSim.getCount());

        while (cursorSim.moveToNext()) {
            displayName = cursorSim.getString(cursorSim.getColumnIndex("name"));
            phoneNumber = cursorSim.getString(cursorSim.getColumnIndex("number"));
            phoneNumber = phoneNumber.replaceAll("\\D", "").replaceAll("&", "");
            displayName = displayName.replace("|", "");

            if (queryString != null && !displayName.toLowerCase().contains(queryString.toLowerCase())) {
                continue;
            }
            contact = new Contact(displayName, phoneNumber);
            contact.setFromSim(true);
            items.add(contact);
        }
        cursorSim.close();

        return items;

    }
}
