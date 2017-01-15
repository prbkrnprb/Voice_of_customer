package com.cognizant.a348984.voc.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.cognizant.a348984.voc.CategoryFragment;
import com.cognizant.a348984.voc.R;

/**
 * Created by Prabakaran on 14-01-2017.
 */

public class CategoryListAdminAdapter extends CursorAdapter {

    public CategoryListAdminAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_category_admin, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView categoryText = (TextView) view.findViewById(R.id.textCategory);
        TextView createdByText = (TextView) view.findViewById(R.id.textCategory);
        TextView createdOnText = (TextView) view.findViewById(R.id.textCategory);

        categoryText.setText(cursor.getString(CategoryFragment.COL_CATEGORY_VALUE));
        createdByText.setText(cursor.getString(CategoryFragment.COL_CATEGORY_CREATED_BY));
        createdOnText.setText(cursor.getString(CategoryFragment.COL_CATEGORY_LAST_UPDATED));
    }
}
