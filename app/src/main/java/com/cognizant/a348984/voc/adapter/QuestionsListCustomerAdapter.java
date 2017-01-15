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

public class QuestionsListCustomerAdapter extends CursorAdapter {
    public QuestionsListCustomerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_question_customer, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView questionText = (TextView) view.findViewById(R.id.textQuestion);

        questionText.setText(cursor.getString(CategoryFragment.COL_QUESTIONS_VALUE));
    }
}
