package com.project.auth.auth;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewPass extends AppCompatActivity {

    TextView textView;
    EditText edtPass,edtConf;

    String pass,conf,method,mail;

    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textViewNewPass);

        edtPass = (EditText) findViewById(R.id.editTextNewPass);
        edtConf = (EditText) findViewById(R.id.editTextNewConfirmPass);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        textView.setTypeface(typeface);

        //get the mail
        mail = getIntent().getStringExtra("mail");
    }

    public void change(View view) {

        pass = edtPass.getText().toString();
        conf = edtConf.getText().toString();

        method = "new_password";

        NewPassTask newPassTask = new NewPassTask(this);

        newPassTask.execute(method,pass,conf,mail);
    }
}
