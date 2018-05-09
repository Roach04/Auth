package com.project.auth.auth;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    TextView textView;
    String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = (TextView) findViewById(R.id.textViewAccount);

        display = getIntent().getExtras().getString("username");

        textView.setText(display);
    }

    /**
     * Menus
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu.
        getMenuInflater().inflate(R.menu.menu_account,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get the item.
        int pos = item.getItemId();

        switch (pos){

            case R.id.action_logout:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
