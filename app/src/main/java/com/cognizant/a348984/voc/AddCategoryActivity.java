package com.cognizant.a348984.voc;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    EditText categoryText;
    Button addButton;
    AddCategoryDataTask addCategoryDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_category);
        categoryText = (EditText) findViewById(R.id.addCategory);
        addButton = (Button) findViewById(R.id.addCategoryButton);
        addCategoryDataTask = new AddCategoryDataTask();

        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addCategoryButton:
                addCategoryDataTask.execute(categoryText.getText().toString());
                finishActivity(0);
        }
    }
    public class AddCategoryDataTask extends AsyncTask<String, Integer, Integer> {

        final String LOG_TAG = "AddClass";

        private int getDataFromJSON(String jsonString) {
            try {
                final String RESULT = "result";

                JSONObject jsonObject = new JSONObject(jsonString);

                return jsonObject.getInt(RESULT);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception", e);
                return 0;
            }
        }

        @Override
        protected Integer doInBackground(String... params) {

            if (params.length == 0) {
                Log.w(LOG_TAG, "No params");
                return null;
            }
            Log.v(LOG_TAG, "params : " + params[0]);

            Uri uri = Uri.parse(Util.REQ_BASE_URL).buildUpon()
                    .appendQueryParameter(Util.API_KEY_PARM, Util.API_KEY_VALUE)
                    .appendQueryParameter(Util.API_REQ_PARM, Util.REQ_ADD_CATEGORY)
                    .appendQueryParameter(Util.API_REQ_VALUE_PARM, params[0])
                    .build();

            String jsonServerValue = Util.getDataFromUri(uri);

            Log.v(LOG_TAG, "Data from server : " + jsonServerValue);

            return getDataFromJSON(jsonServerValue);
        }
    }
}
