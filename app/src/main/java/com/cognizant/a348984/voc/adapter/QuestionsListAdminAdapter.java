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

public class QuestionsListAdminAdapter extends CursorAdapter {
    public QuestionsListAdminAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_question_admin, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView questionsText = (TextView) view.findViewById(R.id.textCategory);
        TextView createdByText = (TextView) view.findViewById(R.id.textCategory);
        TextView createdOnText = (TextView) view.findViewById(R.id.textCategory);

        questionsText.setText(cursor.getString(CategoryFragment.COL_QUESTIONS_VALUE));
        createdByText.setText(cursor.getString(CategoryFragment.COL_QUESTIONS_CREATED_BY));
        createdOnText.setText(cursor.getString(CategoryFragment.COL_QUESTIONS_LAST_UPDATED));
    }
}
