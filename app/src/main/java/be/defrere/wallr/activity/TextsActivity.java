package be.defrere.wallr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import be.defrere.wallr.R;

public class TextsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texts);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
