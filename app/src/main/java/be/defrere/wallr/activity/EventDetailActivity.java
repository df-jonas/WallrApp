package be.defrere.wallr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Event;

public class EventDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Event current;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db =  AppDatabase.getAppDatabase(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        current = db.eventDao().findById(Integer.parseInt(getIntent().getExtras().get("eventid").toString()));

        System.out.println(Integer.parseInt(getIntent().getExtras().get("eventid").toString()));

        if (current == null) {
            finish();
        } else {
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(current.getName());
            this.setSupportActionBar(toolbar);

            TextView t = findViewById(R.id.event_detail_name);
        }
    }
}
