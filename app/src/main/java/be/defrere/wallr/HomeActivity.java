package be.defrere.wallr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

import be.defrere.wallr.http.HttpRequest;
import be.defrere.wallr.http.HttpResponse;
import be.defrere.wallr.http.HttpTask;
import be.defrere.wallr.http.HttpVerb;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences prefs;
    FloatingActionButton fabAdd;
    Toolbar toolbar;

    private static final int REQUEST_CODE_SEND_SMS = 16548;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_SEND_SMS);
        }

        // Init API
        HttpRequest.setUrl("https://wallr.eu/api/");

        // Check first-time conditions
        prefs = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        if (prefs.getBoolean("first_app_start", true) ||
                prefs.getString("device_name", "").equals("") ||
                prefs.getString("device_uuid", "").equals("") ||
                prefs.getString("device_verifytoken", "").equals("") ||
                prefs.getString("api_token", "").equals("")) {

            prefs.edit().putBoolean("first_app_start", false).apply();

            Toast.makeText(this, "Een ogenblik geduld.", Toast.LENGTH_SHORT).show();

            HashMap<String, String> params = new HashMap<String, String>();
            HashMap<String, String> headers = new HashMap<String, String>();
            params.put("device_name", UUID.randomUUID().toString());
            new HomeActivity.RegisterHttpTask().execute(new HttpRequest("register", HttpVerb.POST, params, headers));
        }

        if (!prefs.getString("device_name", "").equals("") &&
                !prefs.getString("device_uuid", "").equals("") &&
                !prefs.getString("device_verifytoken", "").equals("") &&
                !prefs.getString("api_token", "").equals("")) {
            System.out.println("Settings applied correctly.");
            System.out.println(prefs.getAll().toString());
        }

        // Init App
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void onAddClick(View v) {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    class RegisterHttpTask extends HttpTask {
        @Override
        protected void onPostExecute(HttpResponse result) {
            if (result.getResponseCode() == 200) {
                try {
                    JSONObject json = new JSONObject(result.getResponseText());
                    if (json.has("device_name") && json.has("device_uuid") && json.has("device_verifytoken") && json.has("api_token")) {
                        prefs.edit()
                                .putString("device_name", json.getString("device_name"))
                                .putString("device_uuid", json.getString("device_uuid"))
                                .putString("device_verifytoken", json.getString("device_verifytoken"))
                                .putString("api_token", json.getString("api_token"))
                                .apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
            super.onPostExecute(result);
        }
    }
}
