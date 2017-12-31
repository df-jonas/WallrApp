package be.defrere.wallr.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import be.defrere.wallr.R;

public class CredentialsActivity extends AppCompatActivity {

    SharedPreferences prefs;
    Toolbar toolbar;
    EditText txtDeviceName;
    EditText txtDeviceUuid;
    EditText txtApiToken;
    EditText txtVerifyToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtDeviceName = findViewById(R.id.txtDeviceName);
        txtDeviceUuid = findViewById(R.id.txtDeviceUuid);
        txtApiToken = findViewById(R.id.txtApiToken);
        txtVerifyToken = findViewById(R.id.txtDeviceVerifyToken);

        prefs = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
        String deviceName = prefs.getString("device_name", "");
        String deviceUuid = prefs.getString("device_uuid", "");
        String deviceVerifytoken = prefs.getString("device_verifytoken", "");
        String apiToken = prefs.getString("api_token", "");

        txtDeviceName.setText(deviceName);
        txtDeviceUuid.setText(deviceUuid);
        txtApiToken.setText(apiToken);
        txtVerifyToken.setText(deviceVerifytoken);
    }
}
