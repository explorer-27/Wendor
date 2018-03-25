package com.example.ashu.wendor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import static com.example.ashu.wendor.WendorContentProvider.DBHelper.itemId;

/**
 * Created by ashu on 19/3/18.
 */

public class WendorContentProvider extends ContentProvider {

    static final String AUTHORITY = "com.example.ashu.wendor";
    static final String URL = "content://" + AUTHORITY + "/items/#";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final int ITEMS = 1;
    static final int ITEM_WITH_ID = 2;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "items", ITEMS);
        uriMatcher.addURI(AUTHORITY, "items/#", ITEM_WITH_ID);
    }
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelper dbHelper = new DBHelper(context);

        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBHelper.ITEMS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ITEMS:
                qb.setProjectionMap(null);
                break;

            case ITEM_WITH_ID:
                qb.appendWhere(itemId + " = " + uri.getPathSegments().get(1));
                break;

            default:
                throw new UnsupportedOperationException("unkown uri" + uri);
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, null);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        //Add a new Item ..

        long rowID = db.insert(DBHelper.ITEMS_TABLE_NAME, null, values);
        Uri _uri = null;
        /**
         * If record is added successfully
         */
        try {
            if (rowID > 0) {
                _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                //Toast.makeText(getContext(),""+_uri,Toast.LENGTH_LONG).show();
            }

        } catch (SQLiteConstraintException e) {
            Toast.makeText(getContext(), "Existing Record", Toast.LENGTH_LONG).show();
            _uri = null;
        }
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated = 0;


        switch (uriMatcher.match(uri)) {
            case ITEM_WITH_ID:
                String id = uri.getPathSegments().get(1);
                updated = db.update(DBHelper.ITEMS_TABLE_NAME, values, "" + itemId + " = ?", new String[]{id});

                break;

            default:
                throw new UnsupportedOperationException("unkown uri" + uri);
        }
        if (updated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    public static class DBHelper extends SQLiteOpenHelper {
        static final String DATABASE_NAME = "items.db";
        static final String ITEMS_TABLE_NAME = "items";
        static final int DATABASE_VERSION = 1;


        public static String itemId = "itemId";
        public static String name = "name";
        public static String price = "price";
        public static String totUnits = "totUnits";
        public static String leftUnits = "leftUnits";
        public static String imageUrl = "imageUrl";
        public static String imagePath = "imagePath";
        static final String CREATE_DB_TABLE =
                " CREATE TABLE " + DBHelper.ITEMS_TABLE_NAME +
                        " ( " + itemId + " INTEGER PRIMARY KEY , "
                        + name + " TEXT NOT NULL , "
                        + price + " INTEGER NOT NULL ,"
                        + totUnits + " INTEGER NOT NULL ,"
                        + leftUnits + " INTEGER NOT NULL ,"
                        + imageUrl + " Text NOT NULL ,"
                        + imagePath + " Text NOT NULL ); ";




        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME);
            onCreate(db);
        }
    }
}
