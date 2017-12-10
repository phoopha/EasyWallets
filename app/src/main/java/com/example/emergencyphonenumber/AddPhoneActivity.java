package com.example.emergencyphonenumber;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.emergencyphonenumber.db.PhoneDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class AddPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddPhoneActivity.class.getName();

    private EditText mPhoneTitleEditText, mPhoneNumberEditText;
    private ImageView mPhoneImageView;
    private Button mSaveButton;

    private File mSelectedPictureFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        mPhoneTitleEditText = findViewById(R.id.wallet_title_edit_text);
        mPhoneNumberEditText = findViewById(R.id.wallet_number_edit_text);
        mPhoneImageView = findViewById(R.id.phone_image_view);
        mSaveButton = findViewById(R.id.save_button);

        mPhoneImageView.setImageResource(R.drawable.ic_income);

        mSaveButton.setOnClickListener(this);

        getSupportActionBar().setTitle(
                String.format(
                        Locale.getDefault(),
                        "บันทึกรายรับ"
                )
        );
    }

    @Override
    public void onClick(View view) {
        saveDataToDb();
        setResult(RESULT_OK);
        finish();
    }


    private void saveDataToDb() {
        String phoneTitle = mPhoneTitleEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(PhoneDbHelper.COL_TITLE, phoneTitle);
        cv.put(PhoneDbHelper.COL_NUMBER, phoneNumber);
        cv.put(PhoneDbHelper.COL_PICTURE, "ic_income.png");

        PhoneDbHelper dbHelper = new PhoneDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(PhoneDbHelper.TABLE_NAME, null, cv);
        if (result == -1) {
            //
        }
    }

    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dst);
        byte[] buffer = new byte[1024];

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }
}
