package be.defrere.wallr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import be.defrere.wallr.R;
import be.defrere.wallr.controllers.HomeController;
import be.defrere.wallr.models.Event;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    Toolbar toolbar;
    ListView lv;
    ArrayList<Event> events;
    AppCompatActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize
        boolean onStartSuccess = HomeController.onStart(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get a handle on activity resources
        toolbar = findViewById(R.id.toolbar);
        fabAdd = findViewById(R.id.fab);
        lv = findViewById(R.id.event_listview);

        events = new ArrayList<Event>();
        events.add(new Event(1, 1, "Event X", "ttttt"));
        events.add(new Event(2, 1, "Event Y", "ttttt"));

        Event[] ev = events.toArray(new Event[events.size()]);
        lv.setAdapter(new ArrayAdapter<Event>(this, R.layout.listview_event, R.id.event_listview_label, ev));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(events.get(position));
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("eventid", events.get(position).getIdentifier());
                startActivity(intent);
            }
        });

        // Set toolbar
        this.setSupportActionBar(toolbar);

        // Show onStartSuccess value
        if (onStartSuccess) {
            // TODO Snackbar
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings: {

                // TODO add settings view

                break;
            }
            case R.id.menu_credentials: {

                // TODO add credentials view

                break;
            }
            case R.id.menu_about: {

                // TODO add about view

                break;
            }
        }
        return false;
    }

    public void onAddClick(View v) {
        Intent intent = new Intent(this, EventAddActivity.class);
        startActivity(intent);
    }
}
