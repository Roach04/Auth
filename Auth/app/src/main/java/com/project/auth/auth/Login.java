package com.project.auth.auth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText one,two;
    TextView forgot,signUp;

    Typeface typeface;
    String mail,pass;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        one = (EditText) findViewById(R.id.editTextUser);
        two = (EditText) findViewById(R.id.editTextPass);

        button = (Button) findViewById(R.id.buttonLogin);

        forgot = (TextView) findViewById(R.id.textViewForgot);
        signUp = (TextView) findViewById(R.id.textViewSignUp);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        forgot.setTypeface(typeface);
        signUp.setTypeface(typeface);
    }

    public void signUp(View view) {

        //access the create account activity.
        startActivity(new Intent(this, CreateAccount.class));
    }

    public void forgotPass(View view) {

        startActivity(new Intent(this, ForgotPass.class));
    }

    public void login(View view) {

        mail = one.getText().toString();
        pass = two.getText().toString();

        String method = "login";

        //instantiate the background task.
        BackgroundTask backgroundTask = new BackgroundTask(this);

        //add all necessities to the background task.
        backgroundTask.execute(method,mail,pass);
    }
}
