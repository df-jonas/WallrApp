package be.defrere.wallr.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import be.defrere.wallr.R;
import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.util.http.HttpInterface;
import be.defrere.wallr.util.http.HttpRequest;
import be.defrere.wallr.util.http.HttpResponse;
import be.defrere.wallr.util.http.HttpTask;
import be.defrere.wallr.util.http.HttpVerb;
import be.defrere.wallr.entity.Event;

public class HomeActivity extends AppCompatActivity implements HttpInterface {

    private FloatingActionButton fabAdd;
    private Toolbar toolbar;
    private ListView lv;
    private List<Event> events;
    private Activity activity = this;
    private boolean onAppStartExecuted = false;
    private final int REQUEST_CODE_SEND_SMS = 16548;
    private SharedPreferences prefs = null;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(this);

        // Initialize
        if (!onAppStartExecuted) {
            onAppStart();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get a handle on activity resources
        toolbar = findViewById(R.id.toolbar);
        fabAdd = findViewById(R.id.fab);
        lv = findViewById(R.id.event_listview);

        // Set toolbar
        this.setSupportActionBar(toolbar);

        onAppStartExecuted = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        events = db.eventDao().all();

        Event[] ev = events.toArray(new Event[events.size()]);
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.listview_event, R.id.event_listview_label, ev));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(events.get(position));
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("eventid", events.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_settings: {
                intent = new Intent(this, SettingsActivity.class);
                break;
            }
            case R.id.menu_credentials: {
                intent = new Intent(this, CredentialsActivity.class);
                break;
            }
            case R.id.menu_about: {
                intent = new Intent(this, AboutActivity.class);
                break;
            }
            case R.id.menu_add_event: {
                intent = new Intent(this, EventAddActivity.class);
                break;
            }
        }
        if (intent != null)
            startActivity(intent);
        return false;
    }

    public void onAddClick(View v) {
        Intent intent = new Intent(this, EventAddActivity.class);
        startActivity(intent);
    }

    public void onAppStart() {
        // Check if SMS permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_SEND_SMS);
        }

        // Init API url for future requests
        HttpRequest.setUrl("https://wallr.eu/api/");

        // Check if first-time conditions are met
        prefs = activity.getSharedPreferences(activity.getString(R.string.preferences), Context.MODE_PRIVATE);

        if (prefs.getBoolean("first_app_start", true) || prefs.getString("device_name", "").equals("") || prefs.getString("device_uuid", "").equals("") || prefs.getString("device_verifytoken", "").equals("") || prefs.getString("api_token", "").equals("")) {

            prefs.edit().putBoolean("first_app_start", false).apply();

            Toast.makeText(activity, "Een ogenblik geduld, we maken de app klaar voor eerste gebruik.", Toast.LENGTH_SHORT).show();

            // Create HTTP prerequisites
            HashMap<String, String> params = new HashMap<>();
            HashMap<String, String> headers = new HashMap<>();
            params.put("device_name", UUID.randomUUID().toString());
            HttpRequest request = new HttpRequest("register", HttpVerb.POST, params, headers);

            // Execute and httpCallback
            new HttpTask(this).execute(request);

        } else {
            System.out.println("Settings applied correctly.");
            System.out.println(prefs.getAll().toString());
        }
    }

    @Override
    public void httpCallback(HttpResponse r) {

        boolean ok = true;

        try {
            if (r.getResponseCode() == 200) {

                JSONObject json = new JSONObject(r.getResponseText());

                if (json.has("device_name") && json.has("device_uuid") && json.has("device_verifytoken") && json.has("api_token")) {
                    prefs.edit()
                            .putString("device_name", json.getString("device_name"))
                            .putString("device_uuid", json.getString("device_uuid"))
                            .putString("device_verifytoken", json.getString("device_verifytoken"))
                            .putString("api_token", json.getString("api_token"))
                            .apply();
                }
            } else {
                ok = false;
                System.err.println("An unexpected response code has been received: " + r.getResponseCode());
            }
        } catch (JSONException e) {
            ok = false;
            e.printStackTrace();
        }

        if (ok) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.event_listview), R.string.snackbar_home_firstuse_done, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
