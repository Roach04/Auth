package com.project.auth.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPass extends AppCompatActivity {

    EditText text;
    TextView view;

    Typeface typeface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = (TextView) findViewById(R.id.textViewForgotText);
        text = (EditText) findViewById(R.id.editTextForgotPassEdit);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        //set the typeface to the view
        view.setTypeface(typeface);

        start();
    }

    public void display(){

        //initialize the progress dialog.
        progressDialog = new ProgressDialog(this);

        //set the title.
        //progressDialog.setTitle("Auth Status.");

        progressDialog.setMessage("Checking Connectivity.");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
    }

    public void start(){

        //check whether there is internet connectivity.

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //network information.
        NetworkInfo info = cm.getActiveNetworkInfo();

        //check whether the phone is connected to the network.
        if (info != null && info.isConnected()){

            //Toast.makeText(context, "Network is Active.", Toast.LENGTH_LONG).show();

            //dismiss the progress dialog.
            /*progressDialog.dismiss();

            finish();

            startActivity(new Intent(getApplicationContext(), Login.class));*/
        }
        else{

            //dismiss the progress dialog.
            //progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Message
            alertDialog.setMessage("No Internet Connectivity.");

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //kill the activity.
                            /*Intent myIntent = new Intent(getApplicationContext(), ForgotPass.class);

                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            getApplicationContext().startActivity(myIntent);*/

                            finish();
                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
    }

    public void recoverPass(View view) {

        String mail = text.getText().toString();

        //get the method.
        String method = "forgot_pass";

        //instantiate the forgot mail task.
        ForgotMailTask forgotMailTask = new ForgotMailTask(this);

        //execute the task and pass in the params.
        forgotMailTask.execute(method,mail);
    }
}
