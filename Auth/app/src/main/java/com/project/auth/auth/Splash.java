package com.project.auth.auth;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    Typeface typeface;
    TextView textView;
    Animation animation;

    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        textView = (TextView) findViewById(R.id.textView);

        textView.setTypeface(typeface);

        animation = AnimationUtils.loadAnimation(this,R.anim.splash);

        textView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                display();
            }

            @Override
            public void onAnimationEnd(Animation animation) {


                start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
            progressDialog.dismiss();

            finish();

            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        else{

            //Toast.makeText(context, "Network is Inactive.", Toast.LENGTH_LONG).show();

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage("No Internet Connectivity.");

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //kill the activity.
                            finish();
                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
    }

}
