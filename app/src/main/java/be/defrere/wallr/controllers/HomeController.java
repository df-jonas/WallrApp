package be.defrere.wallr.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.UUID;
import be.defrere.wallr.R;
import be.defrere.wallr.http.HttpRequest;
import be.defrere.wallr.http.HttpResponse;
import be.defrere.wallr.http.HttpTask;
import be.defrere.wallr.http.HttpVerb;

/**
 * Created by Jonas on 22/11/2017.
 */

public class HomeController {

    static private boolean exec = false;
    private static final int REQUEST_CODE_SEND_SMS = 16548;
    private static SharedPreferences prefs = null;

    /**
     * Function provides all tasks to perform on start.
     */
    static public void onStart(Activity activity) {
        if (!exec) {

            // Check permissions
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_SEND_SMS);
            }

            // Init API
            HttpRequest.setUrl("https://wallr.eu/api/");

            // Check first-time conditions
            prefs = activity.getSharedPreferences(activity.getString(R.string.preferences), Context.MODE_PRIVATE);

            if (prefs.getBoolean("first_app_start", true) ||
                    prefs.getString("device_name", "").equals("") ||
                    prefs.getString("device_uuid", "").equals("") ||
                    prefs.getString("device_verifytoken", "").equals("") ||
                    prefs.getString("api_token", "").equals("")) {

                prefs.edit().putBoolean("first_app_start", false).apply();

                Toast.makeText(activity, "Een ogenblik geduld.", Toast.LENGTH_SHORT).show();

                HashMap<String, String> params = new HashMap<String, String>();
                HashMap<String, String> headers = new HashMap<String, String>();
                params.put("device_name", UUID.randomUUID().toString());
                new HomeController.RegisterHttpTask().execute(new HttpRequest("register", HttpVerb.POST, params, headers));
            }

            if (!prefs.getString("device_name", "").equals("") &&
                    !prefs.getString("device_uuid", "").equals("") &&
                    !prefs.getString("device_verifytoken", "").equals("") &&
                    !prefs.getString("api_token", "").equals("")) {
                System.out.println("Settings applied correctly.");
                System.out.println(prefs.getAll().toString());
            }

            exec = true;
        }
    }

    static class RegisterHttpTask extends HttpTask {
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
                System.err.println("An unexpected response code has been received: " + result.getResponseCode());
            }
            super.onPostExecute(result);
        }
    }
}
