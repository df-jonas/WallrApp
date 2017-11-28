package be.defrere.wallr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.defrere.wallr.R;

public class EventAddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText txtName;
    private EditText txtKeyword;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        toolbar = findViewById(R.id.toolbar);
        txtName = findViewById(R.id.txtName);
        txtKeyword = findViewById(R.id.txtKeyword);
        btnSend = findViewById(R.id.btnSend);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onMakeEvent(View view) {
        String name = txtName.getText().toString();
        String keyword = txtKeyword.getText().toString();

        // TODO make async event
    }
}
