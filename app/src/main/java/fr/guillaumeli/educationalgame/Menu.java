package fr.guillaumeli.educationalgame;

import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Menu extends AppCompatActivity {

    private TextView gameTitle;
    private Button playBtn,settingsBtn;
    private ImageView beeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view_init();

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        beeMenu.getLayoutParams().height = height - (int)(height*0.8);
        beeMenu.getLayoutParams().width = width - (int)(width*0.2);

        button_config();
    }

    /**
     * Initialization of our objects with the corresponding ID in the activity
     */
    private void view_init() {
        setContentView(R.layout.activity_menu);
        playBtn = findViewById(R.id.playBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        beeMenu = findViewById(R.id.beeMenu);
    }

    /**
     * Configuration of our buttons
     * When we press the button we switch to the corresponding activity
     *
     * N.B.
     * Intent are used to connect separate components
     */
    private void button_config() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,GameScene.class));
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_settings);
            }
        });
    }
}