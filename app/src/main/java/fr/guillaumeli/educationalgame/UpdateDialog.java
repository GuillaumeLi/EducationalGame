package fr.guillaumeli.educationalgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateDialog extends AppCompatActivity {

    private EditText wordEdtxt, rightLetterEdtxt, falseLetterEdtxt;
    private Button updateBtn, cancelBtn;
    private String id, word, rightLetter, falseLetter;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(UpdateDialog.this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view_init();
        button_config();
    }

    /**
     * Initialization of our objects with the corresponding ID in the activity
     */
    private void view_init() {
        setContentView(R.layout.dialog_update);
        wordEdtxt = findViewById(R.id.edtxt_word_update);
        rightLetterEdtxt = findViewById(R.id.edtxt_rightLetter_update);
        falseLetterEdtxt = findViewById(R.id.edtxt_falseLetter_update);
        updateBtn = findViewById(R.id.btn_update);
        cancelBtn = findViewById(R.id.btn_cancel);
        getAndSetIntentData();
    }

    private void button_config() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = wordEdtxt.getText().toString().trim();
                rightLetter = rightLetterEdtxt.getText().toString().trim();
                falseLetter = falseLetterEdtxt.getText().toString().trim();
                if(dataBaseHelper.updateData(id, word, rightLetter, falseLetter)) {
                    Toast.makeText(UpdateDialog.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDialog.this, "Failed to updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAndSetIntentData () {
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("Word") &&
                getIntent().hasExtra("Right letter") && getIntent().hasExtra("False letter")) {
            // Getting the data from the intent here
            id = getIntent().getStringExtra("ID");
            word = getIntent().getStringExtra("Word");
            rightLetter = getIntent().getStringExtra("Right letter");
            falseLetter = getIntent().getStringExtra("False letter");

            // Setting the intent data
            wordEdtxt.setText(word);
            rightLetterEdtxt.setText(rightLetter);
            falseLetterEdtxt.setText(falseLetter);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

}
