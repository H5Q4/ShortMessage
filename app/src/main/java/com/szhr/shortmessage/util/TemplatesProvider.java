/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.szhr.shortmessage.util;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class TemplatesProvider extends ContentProvider {

    private static final String TYPE = "vnd.android.cursor.dir/vnd.com.android.mms.template";

    private static final String DB_NAME = "message_templates.db";
    private static final int DATABASE_VERSION = 10;
    private static final String TABLE_NAME = "message_template";

    private static final int DB_VERSION = 1;
    private static final int TEMPLATES = 1;
    private static final int TEMPLATE_ID = 2;

    private static UriMatcher sMatcher;
    public static boolean INIT_PRE_TEMPLATE = false;

    public static final String DATA_KEY_DEL_PARAMS = "params";
    public static final String DEL_PARAMS_SPLIT = ",";

    SQLiteDatabase mDb;
    private static final String[] PRE_TEMPLATE_MESSAG_EN =  new String[]{
        "I\'ll call you back later.",
        "I am in conference. I\'ll call you back later.",
        "Please call me later.",
        "Will you please call me back?"
    };

    private static final String[] PRE_TEMPLATE_MESSAG_CN =  new String[]{
        "我过会儿打给您。",
        "我在开会，稍后打给您。",
        "过会儿再打给我。"
        ,"请回拨我，好吗？"
    };

    private static final int ENGLISH = 0;
    private static final int CHINESE = 1;
    private int mLocale = ENGLISH;

    public static class Template implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.mms/templates");
        public static final String TEXT = "text";
        public static final String LOCALE = "locale";
    }

    static {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI("com.android.mms", "templates", TEMPLATES);
        sMatcher.addURI("com.android.mms", "templates/#", TEMPLATE_ID);
    }

    private static final String TEMPLATE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (" + Template._ID + " integer primary key autoincrement, " + Template.TEXT
            + " text not null, " + "locale integer);";

    @Override
    public boolean onCreate() {
        DbHelper dbHelper = new DbHelper(getContext(), DB_NAME, null, DB_VERSION);
        mDb = dbHelper.getWritableDatabase();
        if ("zh_CN".equals(getContext().getResources().getConfiguration().locale.toString())) {
            mLocale = CHINESE;
        } else {
            mLocale = ENGLISH;
        }

        if (INIT_PRE_TEMPLATE) {
            loadPreMessageTemp();
            INIT_PRE_TEMPLATE = false;
        }

        IntentFilter userFilter = new IntentFilter();
        userFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
        getContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)) {
                    String locale = getContext().getResources()
                            .getConfiguration().locale.toString();
                    if ("zh_CN".equals(locale)) {
                        mLocale = CHINESE;
                    } else {
                        mLocale = ENGLISH;
                    }
                }
            }
        }, userFilter);
        return mDb != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME);

        if (sMatcher.match(uri) == TEMPLATE_ID) {
            queryBuilder.appendWhere(Template._ID + "=" + uri.getPathSegments().get(1));
        }
        queryBuilder.appendWhere(Template.LOCALE + "=" + mLocale);

        Cursor cursor = queryBuilder.query(mDb, projection, selection, selectionArgs, null, null,
                sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return TYPE;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (sMatcher.match(uri) != TEMPLATES) {
            throw new IllegalArgumentException("Unknown URL " + uri);
        }

        if (!values.containsKey(Template.TEXT)) {
            throw new IllegalArgumentException("Text is missing");
        }

        ContentValues newValues = new ContentValues(values.size() + 1);
        newValues.putAll(values);
        newValues.put(Template.LOCALE, mLocale);
        long rowID = mDb.insert(TABLE_NAME, null, newValues);
        if (rowID > 0) {
            getContext().getContentResolver().notifyChange(Template.CONTENT_URI, null);
            return ContentUris.withAppendedId(Template.CONTENT_URI, rowID);
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;

        if (sMatcher.match(uri) == TEMPLATES) {
            count = mDb.delete(TABLE_NAME, selection, selectionArgs);
            if (INIT_PRE_TEMPLATE) {
                loadPreMessageTemp();
                INIT_PRE_TEMPLATE = false;
            }
        } else {
            String params = uri.getQueryParameter(DATA_KEY_DEL_PARAMS);
            if (!TextUtils.isEmpty(params)) {
                count = mDb.delete(TABLE_NAME, Template._ID + " in (" + params + ")"
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                        selectionArgs);
            } else {
                String segment = uri.getPathSegments().get(1);
                count = mDb.delete(TABLE_NAME, Template._ID + "=" + segment
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                        selectionArgs);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;

        if (sMatcher.match(uri) == TEMPLATES) {
            count = mDb.update(TABLE_NAME, values, selection, selectionArgs);
        } else {
            String segment = uri.getPathSegments().get(1);
            count = mDb.update(TABLE_NAME, values,
                    Template._ID + "=" + segment
                            + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                    selectionArgs);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public void loadPreMessageTemp() {
        ContentValues values = new ContentValues();
        ContentValues newValues = new ContentValues(values.size() + 1);

        for (int i = 0; i < PRE_TEMPLATE_MESSAG_EN.length; i++) {
            values.put(Template.TEXT, PRE_TEMPLATE_MESSAG_EN[i]);
            newValues.putAll(values);
            newValues.put(Template.LOCALE, ENGLISH);
            long rowID = mDb.insert(TABLE_NAME, null, newValues);
        }

        for (int i = 0; i < PRE_TEMPLATE_MESSAG_CN.length; i++) {
            values.put(Template.TEXT, PRE_TEMPLATE_MESSAG_CN[i]);
            newValues.putAll(values);
            newValues.put(Template.LOCALE, CHINESE);
            long rowID = mDb.insert(TABLE_NAME, null, newValues);
        }
    }

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TEMPLATE_TABLE_CREATE);
            INIT_PRE_TEMPLATE = true;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
