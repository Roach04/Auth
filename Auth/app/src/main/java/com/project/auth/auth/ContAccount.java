package com.project.auth.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContAccount extends AppCompatActivity {

    Typeface typeface;
    TextView textView;
    EditText uname2,pass2,cpass2;
    String uname,passw,cpass;

    String names,idnos,mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textViewFinish);

        uname2 = (EditText) findViewById(R.id.editTextUser2);
        pass2 = (EditText) findViewById(R.id.editTextPass2);
        cpass2 = (EditText) findViewById(R.id.editTextConfirmPass);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        textView.setTypeface(typeface);
    }

    public void contFinish(View view) {

        uname = uname2.getText().toString();
        passw = pass2.getText().toString();
        cpass = cpass2.getText().toString();

        //validation.
        if (uname.isEmpty() && passw.isEmpty() && cpass.isEmpty()){

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage("Above Fields Are Empty.");

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            startActivity(new Intent(getApplicationContext(), ContAccount.class));

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (uname.isEmpty()){

            Toast.makeText(ContAccount.this, "Username field is Empty.", Toast.LENGTH_LONG).show();
        }
        else if (passw.isEmpty()){

            Toast.makeText(ContAccount.this, "Password field is Empty.", Toast.LENGTH_LONG).show();
        }
        else if (cpass.isEmpty()){

            Toast.makeText(ContAccount.this, "Confirm Password is Empty.", Toast.LENGTH_LONG).show();
        }
        else if (!passw.matches(cpass)){

            //Toast.makeText(ContAccount.this, "Your Passwords DON'T MATCH.", Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage("Your Passwords DON'T MATCH.");

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            startActivity(new Intent(getApplicationContext(), ContAccount.class));

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else{

            names = getIntent().getExtras().getString("names1");
            idnos = getIntent().getExtras().getString("idnos1");
            mails = getIntent().getExtras().getString("mails1");

            //do a hook for the register method.
            String method = "register";

            //instantiate the background task and pass in the context.
            BackgroundTask backgroundTask = new BackgroundTask(this);

            //pass in both the method and data which the user has provided to the
            // background task.
            backgroundTask.execute(method,names,idnos,mails,uname,passw);
        }
    }
}
