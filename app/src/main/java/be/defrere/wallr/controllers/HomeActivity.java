package be.defrere.wallr.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import be.defrere.wallr.R;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize
        HomeController.onStart(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get a handle on activity resources
        toolbar = findViewById(R.id.toolbar);
        fabAdd = findViewById(R.id.fab);

        // Set toolbar
        this.setSupportActionBar(toolbar);
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
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }
}
