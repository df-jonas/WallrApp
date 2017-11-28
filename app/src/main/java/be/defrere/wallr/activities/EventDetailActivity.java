package be.defrere.wallr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import be.defrere.wallr.R;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        TextView t = findViewById(R.id.event_detail_name);
        t.setText(Integer.toString((int) getIntent().getExtras().get("eventid")));
    }
}
