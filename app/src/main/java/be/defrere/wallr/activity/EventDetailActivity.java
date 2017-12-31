package be.defrere.wallr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Event;

public class EventDetailActivity extends AppCompatActivity {

    private AppDatabase db;
    private Event current;
    private int eventid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        if (eventid == 0) {
            eventid = Integer.parseInt(getIntent().getExtras().get("eventid").toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        current = db.eventDao().findById(eventid);

        if (current == null) {
            finish();
        } else {
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(current.getName());
            setSupportActionBar(toolbar);

            CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
            ctl.setTitle(current.getName());
        }
    }

    public void onShareClick(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://wallr.eu/event/" + current.getPublicEventId());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void onDeleteClick(View v) {
        db.eventDao().delete(current);
        finish();
    }

    public void onEditClick(View v) {
        Intent i = new Intent(this, EventAddActivity.class);
        i.putExtra("edit_eventid", current.getId());
        startActivity(i);
    }

    public void onViewTextClick(View v) {
        Intent i = new Intent(this, TextsActivity.class);
        startActivity(i);
    }
}
