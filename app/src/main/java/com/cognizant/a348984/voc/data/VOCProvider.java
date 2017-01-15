package com.cognizant.a348984.voc.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.cognizant.a348984.voc.data.VOCContract.CategoryEntry;
import com.cognizant.a348984.voc.data.VOCContract.LoginEntry;
import com.cognizant.a348984.voc.data.VOCContract.ProgressEntry;
import com.cognizant.a348984.voc.data.VOCContract.QuestionsEntry;

/**
 * Created by 348984 on 1/12/2017.
 */

public class VOCProvider extends ContentProvider {
    private VOCDbHelper dbHelper;

    public static final int LOGIN_ALL = 100;
    public static final int LOGIN_WITH_EMAIL = 101;
    public static final int CATEGORY_ALL = 200;
    public static final int CATEGORY_WITH_ID = 201;
    public static final int QUESTIONS_ALL = 300;
    public static final int QUESTIONS_WITH_ID = 301;
    public static final int PROGRESS_ALL = 400;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_LOGIN, LOGIN_ALL);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_LOGGED_IN + "/#", LOGIN_WITH_EMAIL);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_CATEGORY, CATEGORY_ALL);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_CATEGORY_ID + "/#", CATEGORY_WITH_ID);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_QUESTIONS, QUESTIONS_ALL);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_QUESTIONS_ID + "/#", QUESTIONS_WITH_ID);
        matcher.addURI(VOCContract.CONTENT_AUTHORITY,
                VOCContract.PATH_PROGRESS, PROGRESS_ALL);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new VOCDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnCursor;

        switch (matcher.match(uri)){
            case LOGIN_ALL:
                returnCursor = dbHelper.getReadableDatabase().query(
                        LoginEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case LOGIN_WITH_EMAIL:
                returnCursor = dbHelper.getReadableDatabase().query(
                        LoginEntry.TABLE_NAME,
                        projection,
                        LoginEntry.COLUMN_EMAIL + "=" + ContentUris.parseId(uri),
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORY_ALL:
                returnCursor = dbHelper.getReadableDatabase().query(
                        CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORY_WITH_ID:
                returnCursor = dbHelper.getReadableDatabase().query(
                        CategoryEntry.TABLE_NAME,
                        projection,
                        CategoryEntry.COLUMN_ID + "=" + ContentUris.parseId(uri),
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case QUESTIONS_ALL:
                returnCursor = dbHelper.getReadableDatabase().query(
                        QuestionsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case QUESTIONS_WITH_ID:
                returnCursor = dbHelper.getReadableDatabase().query(
                        QuestionsEntry.TABLE_NAME,
                        projection,
                        QuestionsEntry.COLUMN_ID + "=" + ContentUris.parseId(uri),
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PROGRESS_ALL:
                returnCursor = dbHelper.getReadableDatabase().query(
                        ProgressEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case LOGIN_ALL:
                return LoginEntry.CONTENT_TYPE;
            case LOGIN_WITH_EMAIL:
                return LoginEntry.CONTENT_ITEM_TYPE;
            case CATEGORY_ALL:
                return CategoryEntry.CONTENT_TYPE;
            case CATEGORY_WITH_ID:
                return CategoryEntry.CONTENT_TYPE;
            case QUESTIONS_ALL:
                return QuestionsEntry.CONTENT_TYPE;
            case QUESTIONS_WITH_ID:
                return QuestionsEntry.CONTENT_TYPE;
            case PROGRESS_ALL:
                return ProgressEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (matcher.match(uri)){
            case LOGIN_ALL: {
                long _id = db.insert(LoginEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = LoginEntry.buildLoginDetailsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert into Login table " + uri);
                }
                break;
            }
            case CATEGORY_ALL: {
                long _id = db.insert(CategoryEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = CategoryEntry.buildCategoryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert into Category table " + uri);
                }
                break;
            }
            case QUESTIONS_ALL: {
                long _id = db.insert(QuestionsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = QuestionsEntry.buildQuestionUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert into Questions table " + uri);
                }
                break;
            }
            case PROGRESS_ALL: {
                long _id = db.insert(ProgressEntry.TABLE_NAME, null, values);
                if (_id != 0) {
                    returnUri = ProgressEntry.buildProgressUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert into Progress table " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int returnRows;

        switch (matcher.match(uri)){
            case LOGIN_ALL: {
                returnRows = db.delete(LoginEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case CATEGORY_ALL: {
                returnRows = db.delete(CategoryEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case QUESTIONS_ALL: {
                returnRows = db.delete(QuestionsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case PROGRESS_ALL: {
                returnRows = db.delete(ProgressEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if(selection == null || returnRows > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return returnRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int returnRows;

        switch (matcher.match(uri)){
            case LOGIN_WITH_EMAIL: {
                String emailId = LoginEntry.getEmailIdFromUri(uri);
                returnRows = db.update(LoginEntry.TABLE_NAME,values,LoginEntry.COLUMN_EMAIL + "= ?",
                        new String[]{emailId});
                break;
            }
            case CATEGORY_ALL: {
                returnRows = db.update(CategoryEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            case QUESTIONS_ALL: {
                returnRows = db.update(QuestionsEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            case PROGRESS_ALL: {
                returnRows = db.update(ProgressEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if(returnRows > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return returnRows;
    }
}
