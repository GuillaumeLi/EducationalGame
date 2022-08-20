package fr.guillaumeli.educationalgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Settings extends AppCompatActivity {

    private ImageButton homeBtn;
    private Button addBtn;
    private EditText wordEdtxt, rightLetterEdtxt, falseLetterEdtxt;
    private RecyclerView wordDataRecyclerView;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(Settings.this);
    private RecyclerViewAdapter customerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view_init();
        button_config();
    }

    /**
     * Initialization of our objects with the corresponding ID in the activity
     */
    private void view_init() {
        setContentView(R.layout.activity_settings);
        homeBtn = findViewById(R.id.homeBtn);
        addBtn = findViewById(R.id.btn_add);
        wordEdtxt = findViewById(R.id.edtxt_word);
        rightLetterEdtxt = findViewById(R.id.edtxt_rightLetter);
        falseLetterEdtxt = findViewById(R.id.edtxt_falseLetter);
        wordDataRecyclerView = findViewById(R.id.recyclerView_data);
        showWordOnListView();
    }

    /**
     * Configuration of our buttons
     * When we press the button we switch to the corresponding activity
     *
     * N.B.
     * The finish() method close this activity and go back to the previous one
     */
    private void button_config() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allFilled = false;
                Data data = null;

                // Check if any edit text is empty
                // If yes print error message otherwise create the data
                if((wordEdtxt.getText().toString().matches(""))||(rightLetterEdtxt.getText().toString().matches(""))|| (falseLetterEdtxt.getText().toString().matches(""))) {
                    Toast.makeText(Settings.this, "Please fill every lines before adding the data", Toast.LENGTH_SHORT).show();
                } else {
                    data = new Data(1,wordEdtxt.getText().toString(), rightLetterEdtxt.getText().toString(), falseLetterEdtxt.getText().toString());
                    allFilled = true;
                }

                if(allFilled && dataBaseHelper.addData(data)) {
                    Toast.makeText(Settings.this, "Data successfully added", Toast.LENGTH_SHORT).show();
                    showWordOnListView();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    /**
     * Show the data in the ListView
     */
    private void showWordOnListView() {
        customerAdapter = new RecyclerViewAdapter(this, dataBaseHelper,Settings.this );
        wordDataRecyclerView.setAdapter(customerAdapter);
        wordDataRecyclerView.setLayoutManager(new LinearLayoutManager(Settings.this));
    }

}
