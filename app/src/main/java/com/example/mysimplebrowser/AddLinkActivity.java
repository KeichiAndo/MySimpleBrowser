package com.example.mysimplebrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysimplebrowser.database.AppDatabase;
import com.example.mysimplebrowser.database.LinkEntry;

public class AddLinkActivity extends AppCompatActivity {

    EditText mEditTextName;
    EditText mEditTextUrl;
    Button mSaveButton;

    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mEditTextName = findViewById(R.id.et_add_link_name);
        mEditTextUrl = findViewById(R.id.et_add_link_url);
        mSaveButton = findViewById(R.id.btn_save);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextName.getText().toString().equals("") || mEditTextUrl.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),
                            "Please fill in the name and url of your desired link",
                            Toast.LENGTH_SHORT).show();
                    mEditTextName.requestFocus();
                } else if (!URLUtil.isValidUrl(mEditTextUrl.getText().toString())) {
                    Toast.makeText(getBaseContext(),
                            "Invalid URL",
                            Toast.LENGTH_SHORT).show();
                    mEditTextUrl.requestFocus();
                } else {
                    String linkName = mEditTextName.getText().toString();
                    String linkUrl = mEditTextUrl.getText().toString();

                    final LinkEntry linkEntry = new LinkEntry(linkName, linkUrl);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.linkDao().insertTask(linkEntry);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
