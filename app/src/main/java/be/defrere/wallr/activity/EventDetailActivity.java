package be.defrere.wallr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Event;

public class EventDetailActivity extends AppCompatActivity {

    private AppDatabase db;
    private Event current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current = db.eventDao().findById(Integer.parseInt(getIntent().getExtras().get("eventid").toString()));

        System.out.println(Integer.parseInt(getIntent().getExtras().get("eventid").toString()));

        if (current == null) {
            finish();
        } else {
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(current.getName());
            this.setSupportActionBar(toolbar);
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
        // TODO Edit Event
    }

    public void onViewTextClick(View v) {
        // TODO Show latest texts
    }
}
