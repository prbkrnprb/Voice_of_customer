package com.cognizant.a348984.voc.appathon.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 348984 on 1/12/2017.
 */

public class VOCContract {
    public static final String CONTENT_AUTHORITY = "com.cognizant.a348984.voc.appathon";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOGIN = "login";
    public static final String PATH_LOGGED_IN = "logged_in";
    public static final String PATH_CATEGORY = "category";
    public static final String PATH_CATEGORY_ID = "category_id";
    public static final String PATH_QUESTIONS = "questions";
    public static final String PATH_QUESTIONS_ID = "questions_id";
    public static final String PATH_PROGRESS = "progress";


    public static final class LoginEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOGIN).build();
        public static final Uri CONTENT_LOGGED_IN_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOGIN).appendPath(PATH_LOGGED_IN).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_LOGIN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_LOGGED_IN;


        public static final String TABLE_NAME = "login_details";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TYPE  = "type";
        public static final String COLUMN_LAST_LOGIN = "last_login";
        public static final String COLUMN_IS_LOGGED_IN = "is_logged_in";

        public static Uri buildLoginDetailsUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI, _id);
        }

        public static String getEmailIdFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }

    public static final class CategoryEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();
        public static final Uri CONTENT_CATEGORY_ID_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).appendPath(PATH_CATEGORY_ID).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_CATEGORY;

        public static final String TABLE_NAME = "category";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_CREATED_BY = "created_by";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_LAST_UPDATED = "last_updated";

        public static Uri buildCategoryUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }
    }

    public static final class QuestionsEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUESTIONS).build();
        public static final Uri CONTENT_QUESTIONS_ID_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUESTIONS).appendPath(PATH_QUESTIONS_ID).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_QUESTIONS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_QUESTIONS;

        public static final String TABLE_NAME = "questions";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_CREATED_BY = "created_by";
        public static final String COLUMN_CREATED_ON = "created_on";
        public static final String COLUMN_LAST_UPDATED = "last_updated";

        public static Uri buildQuestionUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }
    }

    public static final class ProgressEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROGRESS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_PROGRESS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                        + CONTENT_AUTHORITY + "/"
                        + PATH_PROGRESS;

        public static final String TABLE_NAME = "progress";

        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_DETAIL_ID = "detail_id";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
        public static final String COLUMN_QUESTION_NAME = "question_name";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_IS_SAVED = "is_saved";

        public static Uri buildProgressUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }
    }
}
