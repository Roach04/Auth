package com.project.auth.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user on 2017-07-01.
 */
public class ForgotMailTask extends AsyncTask<String,Void,String> {

    //context
    Context context;

    Activity activity;

    String mail;

    ForgotMailTask(Context context){

        this.context = context;

        //pass the activity to the context.
        activity = (Activity) context;
    }
    //progress dialog.
    ProgressDialog progressDialog;

    //b4 execution of task.
    @Override
    protected void onPreExecute() {

        //initialize the progress dialog.
        progressDialog = new ProgressDialog(context);

        //set the title.
        //progressDialog.setTitle("Auth Status.");

        progressDialog.setMessage("Connecting To The Server...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        //get the url for registration.
        String forgotUrl = "http://paatech.co.ke/auth/forgot.php";

        //get the param at position 0 which in this is the registration.
        String method = params[0];

        if (method.equals("forgot_pass")){

            //then get the params for registration.
            mail = params[1];

            //instantiate url.
            try {
                URL url = new URL(forgotUrl);

                //open a connection.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //set the connection method.
                httpURLConnection.setRequestMethod("POST");

                //set the do output.
                httpURLConnection.setDoOutput(true);

                //get the output stream.
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //instantiate a buffered writer.
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("mail", "UTF-8")+"="+URLEncoder.encode(mail,"UTF-8");

                Log.d("MAIL", data);

                //write the data.
                bufferedWriter.write(data);

                //flush it.
                bufferedWriter.flush();

                //close the buffered writer.
                bufferedWriter.close();

                //close the output stream.
                outputStream.close();

                //to get a response from the server.
                InputStream inputStream = httpURLConnection.getInputStream();

                //read into the input stream, instantiate the buffered reader.
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String response = "";

                String line = "";

                //check to ensure that the buffered reader is not null.
                //then get the info from the same.
                while ((line = bufferedReader.readLine()) != null){

                    response += line;
                }

                //close the reader.
                bufferedReader.close();

                //close the stream.
                inputStream.close();

                //disconnect the http connection.
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Failure.";
    }

    @Override
    protected void onPostExecute(String result) {

        //instantiate the json object.
        try {
            JSONObject jsonObject = new JSONObject(result);

            //get the json array, pass in the json object get the json array and
            // pass in text.
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            JSONObject JO = jsonArray.getJSONObject(0);

            String code = JO.getString("code");
            String message = JO.getString("message");

            /**
             * Validation.
             */
            if (code.equals("forgot_empty")) {

                //dismiss the progress dialog.
                progressDialog.dismiss();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                //ensure one cannot cancel the dialog.
                alertDialog.setCancelable(false);

                // Setting Positive "Yes" Btn
                alertDialog.setPositiveButton("Try Again.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //dismiss the dialog.
                                Intent myIntent = new Intent(context, ForgotPass.class);
                                context.startActivity(myIntent);

                            }
                        });

                // Showing Alert Dialog
                alertDialog.show();
            }
            else if (code.equals("invalid_mail")) {

                //dismiss the progress dialog.
                progressDialog.dismiss();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                //ensure one cannot cancel the dialog.
                alertDialog.setCancelable(false);

                // Setting Positive "Yes" Btn
                alertDialog.setPositiveButton("Try Again.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //dismiss the dialog.
                                Intent myIntent = new Intent(context, ForgotPass.class);
                                context.startActivity(myIntent);

                            }
                        });

                // Showing Alert Dialog
                alertDialog.show();
            }
            else if (code.equals("absent_mail")) {

                //dismiss the progress dialog.
                progressDialog.dismiss();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                //ensure one cannot cancel the dialog.
                alertDialog.setCancelable(false);

                // Setting Positive "Yes" Btn
                alertDialog.setPositiveButton("Try Again.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //dismiss the dialog.
                                Intent myIntent = new Intent(context, ForgotPass.class);
                                context.startActivity(myIntent);

                            }
                        });

                // Showing Alert Dialog
                alertDialog.show();
            }

            /**
             * Enable the user to change the password.
             */
            else{

                //dismiss the progress dialog.
                progressDialog.dismiss();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                //ensure one cannot cancel the dialog.
                alertDialog.setCancelable(false);

                // Setting Positive "Yes" Btn
                alertDialog.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //dismiss the dialog.
                                Intent myIntent = new Intent(context, NewPass.class);

                                //pass in the mail to the new pass activity.
                                myIntent.putExtra("mail",mail);

                                context.startActivity(myIntent);

                            }
                        });

                // Showing Alert Dialog
                alertDialog.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
