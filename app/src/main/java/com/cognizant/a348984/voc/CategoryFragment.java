package com.cognizant.a348984.voc;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cognizant.a348984.voc.adapter.CategoryListAdminAdapter;
import com.cognizant.a348984.voc.adapter.CategoryListCustomerAdapter;
import com.cognizant.a348984.voc.data.VOCContract.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryFragment extends Fragment implements LoaderCallbacks<Cursor> {

    public static final int COL_CATEGORY_VALUE = 2;
    public static final int COL_CATEGORY_CREATED_BY = 3;
    public static final int COL_CATEGORY_CREATED_ON = 4;
    public static final int COL_CATEGORY_LAST_UPDATED = 5;

    public static final int COL_QUESTIONS_VALUE = 2;
    public static final int COL_QUESTIONS_CREATED_BY = 4;
    public static final int COL_QUESTIONS_CREATED_ON = 5;
    public static final int COL_QUESTIONS_LAST_UPDATED = 6;

    private final String LOG_TAG = "CategoryFragmentClass";

    private CategoryListAdminAdapter categoryListAdminAdapter;
    private CategoryListCustomerAdapter categoryListCustomerAdapter;

    private Context mContext;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_category_admin,container,false);

        mContext = rootView.getContext();

        categoryListAdminAdapter = new CategoryListAdminAdapter(getActivity(),null,0);
        categoryListCustomerAdapter = new CategoryListCustomerAdapter(getActivity(),null,0);

        ListView categoryListView = (ListView) rootView.findViewById(R.id.categoryListView);
        categoryListView.setAdapter(categoryListAdminAdapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        uri = CategoryEntry.CONTENT_URI;
        return new CursorLoader(
                mContext,
                uri,
                new String[]{
                        CategoryEntry.COLUMN_VALUE,
                        CategoryEntry.COLUMN_CREATED_BY,
                        CategoryEntry.COLUMN_CREATED_ON},
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        categoryListAdminAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        categoryListAdminAdapter.swapCursor(null);
    }

    public class FetchCategoryDataTask extends AsyncTask<String, Void, CategoryData[]>{

        private void addCategoryDetails(CategoryData categoryData) {
                ContentValues values = new ContentValues();
                values.put(CategoryEntry.COLUMN_ID, categoryData.id);
                values.put(CategoryEntry.COLUMN_VALUE, categoryData.value);
                values.put(CategoryEntry.COLUMN_CREATED_BY, categoryData.createdBy);
                values.put(CategoryEntry.COLUMN_CREATED_ON, categoryData.createdOn);
                values.put(CategoryEntry.COLUMN_LAST_UPDATED, categoryData.lastUpdated);

                Uri uri = mContext.getContentResolver().insert(CategoryEntry.CONTENT_URI, values);
                Log.v(LOG_TAG, "Category details inserted : " + ContentUris.parseId(uri));
        }

        private CategoryData[] getDataFromJSON(String jsonString) {
            CategoryData resultData[];
            try {
                final String RESULT = "result";
                final String CATEGORY_ID = "category_id";
                final String VALUE = "value";
                final String CREATED_BY = "created_by";
                final String CREATED_ON = "created_on";
                final String LAST_UPDATED = "last_updated";

                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(RESULT);
                resultData = new CategoryData[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonCatObj = jsonArray.getJSONObject(i);
                    resultData[i] = new CategoryData();
                    resultData[i].id = jsonCatObj.getInt(CATEGORY_ID);
                    resultData[i].value = jsonCatObj.getString(VALUE);
                    resultData[i].createdBy = jsonCatObj.getString(CREATED_BY);
                    resultData[i].createdOn = jsonCatObj.getString(CREATED_ON);
                    resultData[i].lastUpdated = jsonCatObj.getString(LAST_UPDATED);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception", e);
                return null;
            }
            return resultData;
        }

        @Override
        protected CategoryData[] doInBackground(String... params) {

            if (params.length == 0) {
                Log.w(LOG_TAG, "No params");
                return null;
            }
            Log.v(LOG_TAG, "params : " + params[0]);

            Uri uri = Uri.parse(Util.REQ_BASE_URL).buildUpon()
                    .appendQueryParameter(Util.API_KEY_PARM, Util.API_KEY_VALUE)
                    .appendQueryParameter(Util.API_REQ_PARM, Util.REQ_CATEGORY)
                    .appendQueryParameter(Util.API_REQ_VALUE_PARM, params[0])
                    .build();

            String jsonServerValue = Util.getDataFromUri(uri);

            Log.v(LOG_TAG, "Data from server : " + jsonServerValue);

            return getDataFromJSON(jsonServerValue);
        }

        @Override
        protected void onPostExecute(CategoryData[] categoryDatas) {
            for(int i=0; i< categoryDatas.length; i++) {
                addCategoryDetails(categoryDatas[i]);
            }
        }
    }



}
