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

public class CreateAccount extends AppCompatActivity {

    Typeface typeface;
    TextView textView;
    EditText name,idno,mail;
    String names,idnos,mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textViewAcc);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        textView.setTypeface(typeface);

        name = (EditText) findViewById(R.id.editTextFullNames);
        idno = (EditText) findViewById(R.id.editTextId);
        mail = (EditText) findViewById(R.id.editTextEmail);
    }

    public void attach(View view) {

        //convert all inputs to string.
        names = name.getText().toString();
        idnos  = idno.getText().toString();
        mails  = mail.getText().toString();

        //check if the fields are empty.
        //if they aren't empty, attach and send 'em thru'
        if (names.isEmpty() && idnos.isEmpty() && mails.isEmpty()){

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
                            startActivity(new Intent(getApplicationContext(), CreateAccount.class));

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (names.isEmpty()){

            Toast.makeText(CreateAccount.this, "Full names field is Empty.", Toast.LENGTH_LONG).show();
        }
        else if (idnos.isEmpty()){

            Toast.makeText(CreateAccount.this, "Id Number field is Empty.", Toast.LENGTH_LONG).show();
        }
        else  if(mails.isEmpty()){

            Toast.makeText(CreateAccount.this, "Email Address field is Empty.", Toast.LENGTH_LONG).show();
        }
        else{

            //attach the info and send thru' via intents.
            Intent intent = new Intent(this, ContAccount.class);

            //attach key value pairs to the intent.
            intent.putExtra("names1",names);
            intent.putExtra("idnos1", idnos);
            intent.putExtra("mails1", mails);

            //Pass the intent plus its attached data to the appropriate activity
            // mentioned above.
            startActivity(intent);


        }
    }
}
