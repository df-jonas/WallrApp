package be.defrere.wallr.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import be.defrere.wallr.R;
import be.defrere.wallr.http.HttpRequest;
import be.defrere.wallr.http.HttpResponse;
import be.defrere.wallr.http.HttpTask;
import be.defrere.wallr.http.HttpVerb;
import be.defrere.wallr.models.User;

/**
 * Created by Jonas on 22/11/2017.
 */

public class HomeController {

    static private boolean onStartExecuted = false;
    private static final int REQUEST_CODE_SEND_SMS = 16548;
    private static SharedPreferences prefs = null;

    /**
     * Function provides all tasks to perform on start.
     */
    static public boolean onStart(Activity activity) {
        boolean ok = true;

        if (!onStartExecuted) {

            // Check if SMS permissions are granted
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_SEND_SMS);
            }

            // Init API url for future requests
            HttpRequest.setUrl("https://wallr.eu/api/");

            // Check if first-time conditions are met
            prefs = activity.getSharedPreferences(activity.getString(R.string.preferences), Context.MODE_PRIVATE);

            if (prefs.getBoolean("first_app_start", true) || prefs.getString("device_name", "").equals("") || prefs.getString("device_uuid", "").equals("") || prefs.getString("device_verifytoken", "").equals("") || prefs.getString("api_token", "").equals("")) {

                try {
                    prefs.edit().putBoolean("first_app_start", false).apply();

                    Toast.makeText(activity, "Een ogenblik geduld, we maken de app klaar voor eerste gebruik.", Toast.LENGTH_SHORT).show();

                    // Create HTTP prerequisites
                    HashMap<String, String> params = new HashMap<String, String>();
                    HashMap<String, String> headers = new HashMap<String, String>();
                    params.put("device_name", UUID.randomUUID().toString());
                    HttpRequest request = new HttpRequest("register", HttpVerb.POST, params, headers);

                    // Execute and callback
                    HttpResponse r = new HttpTask().execute(request).get();

                    if (r.getResponseCode() == 200) {

                        JSONObject json = new JSONObject(r.getResponseText());
                        if (json.has("device_name") && json.has("device_uuid") && json.has("device_verifytoken") && json.has("api_token")) {
                            prefs.edit()
                                    .putString("device_name", json.getString("device_name"))
                                    .putString("device_uuid", json.getString("device_uuid"))
                                    .putString("device_verifytoken", json.getString("device_verifytoken"))
                                    .putString("api_token", json.getString("api_token"))
                                    .apply();

                            if (User.findById(User.class, 1) != null) {

                            } else {
                                User u = new User(
                                        json.getInt("id"),
                                        json.getString("device_name"),
                                        json.getString("device_uuid"),
                                        json.getString("device_verifytoken"),
                                        json.getString("api_token")
                                );
                            }
                        }
                    } else {
                        ok = false;
                        System.err.println("An unexpected response code has been received: " + r.getResponseCode());
                    }
                } catch (JSONException e) {
                    ok = false;
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    ok = false;
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    ok = false;
                    e.printStackTrace();
                }
            } else {
                System.out.println("Settings applied correctly.");
                System.out.println(prefs.getAll().toString());
            }

            onStartExecuted = true;

        }
        return ok;
    }

    public static Object templateMethod() {
        // Create HTTP prerequisites
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> headers = new HashMap<String, String>();
        params.put("device_name", UUID.randomUUID().toString());
        HttpRequest request = new HttpRequest("me", HttpVerb.GET, params, headers);

        // Execute and callback
        try {
            HttpResponse r = new HttpTask().execute(request).get();
            if (r.getResponseCode() == 200) {

                JSONObject json = new JSONObject(r.getResponseText());

                // PLACE LOGIC HERE

            } else {
                System.err.println("An unexpected response code has been received: " + r.getResponseCode());
            }

            return null;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean addEvent(String name, String keyword) {

        // Create HTTP prerequisites
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> headers = new HashMap<String, String>();
        params.put("name", name);
        params.put("keyword", keyword);
        HttpRequest request = new HttpRequest("events", HttpVerb.POST, params, headers);

        // Execute and callback
        try {
            HttpResponse r = new HttpTask().execute(request).get();

            if (r.getResponseCode() == 200) {
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

}
