package com.example.mysimplebrowser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_LINK_ID = "extraLinkId";
    private static final int DEFAULT_TASK_ID = -1;

    private int mLinkId = DEFAULT_TASK_ID;

    private boolean mEditing = false;

    EditText mEditTextName;
    EditText mEditTextUrl;
    Button mEditButton;
    Button mUrlButton;

    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mEditTextName = findViewById(R.id.et_link_name);
        mEditTextUrl = findViewById(R.id.et_link_url);
        mEditButton = findViewById(R.id.btn_edit);
        mUrlButton = findViewById(R.id.btn_open_url);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_LINK_ID)) {
            if (mLinkId == DEFAULT_TASK_ID) {
                mLinkId = intent.getIntExtra(EXTRA_LINK_ID, DEFAULT_TASK_ID);

                DetailViewModelFactory factory = new DetailViewModelFactory(mDb, mLinkId);

                final DetailViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

                viewModel.getLink().observe(this, new Observer<LinkEntry>() {
                    @Override
                    public void onChanged(@Nullable LinkEntry taskEntry) {
                        viewModel.getLink().removeObserver(this);
                        mEditTextName.setText(taskEntry.getLinkName());
                        mEditTextUrl.setText(taskEntry.getLinkUrl());
                    }
                });
            }
        }

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditing) {
                    mEditTextName.setEnabled(true);
                    mEditTextUrl.setEnabled(true);
                    mUrlButton.setEnabled(false);

                    mEditTextName.requestFocus();
                    mEditButton.setText(R.string.save);

                    mEditing = true;
                } else {
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
                        mEditTextName.setEnabled(false);
                        mEditTextUrl.setEnabled(false);
                        mUrlButton.setEnabled(true);

                        mEditButton.setText(R.string.edit);

                        String linkName = mEditTextName.getText().toString();
                        String linkUrl = mEditTextUrl.getText().toString();

                        final LinkEntry linkEntry = new LinkEntry(linkName, linkUrl);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                linkEntry.setId(mLinkId);
                                mDb.linkDao().updateTask(linkEntry);
                            }
                        });

                        mEditing = false;
                    }
                }
            }
        });

        mUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, mEditTextUrl.getText().toString());
                startActivity(intent);
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
