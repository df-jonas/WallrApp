package be.defrere.wallr.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.util.http.HttpInterface;
import be.defrere.wallr.R;
import be.defrere.wallr.util.http.HttpRequest;
import be.defrere.wallr.util.http.HttpResponse;
import be.defrere.wallr.util.http.HttpTask;
import be.defrere.wallr.util.http.HttpVerb;
import be.defrere.wallr.entity.Event;

public class EventAddActivity extends AppCompatActivity implements HttpInterface {

    private Toolbar toolbar;
    private EditText txtName;
    private EditText txtKeyword;
    private EditText txtPhone;
    private Button btnSend;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        toolbar = findViewById(R.id.toolbar);
        txtName = findViewById(R.id.txtName);
        txtKeyword = findViewById(R.id.txtKeyword);
        txtPhone = findViewById(R.id.txtPhone);
        btnSend = findViewById(R.id.btnSend);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onMakeEvent(View view) {
        String name = txtName.getText().toString();
        String keyword = txtKeyword.getText().toString();
        String phone = txtPhone.getText().toString();

        // Create HTTP prerequisites
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> headers = new HashMap<String, String>();
        params.put("name", name);
        params.put("keyword", keyword);
        params.put("phone", phone);
        headers.put("Authorization", "Bearer " + getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE).getString("api_token", ""));

        HttpRequest request = new HttpRequest("events", HttpVerb.POST, params, headers);

        // Execute and httpCallback
        new HttpTask(this).execute(request);
    }

    @Override
    public void httpCallback(HttpResponse r) {
        try {
            JSONObject json = new JSONObject(r.getResponseText());

            Event e = new Event();
            e.setId(json.getInt("id"));
            e.setName(json.getString("name"));
            e.setPublicEventId(json.getString("public_event_id"));
            e.setKeyword(json.getString("keyword"));
            e.setPhone(json.getString("phone"));
            db.eventDao().insert(e);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        finish();
    }
}
