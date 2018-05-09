package com.project.auth.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.project.auth.auth.Account;
import com.project.auth.auth.ContAccount;

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
 * Created by user on 2016-04-20.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {

    //context
    Context context;

    Activity activity;

    String state = "Something is Wrong.";

    BackgroundTask(Context context){

        this.context = context;

        //pass the activity to the context.
        activity = (Activity) context;
    }

    //alert dialog.
    //AlertDialog alertDialog;

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
        String regUrl = "http://paatech.co.ke/auth/registration.php";

        //get the url for login.
        String loginUrl = "http://paatech.co.ke/auth/login.php";

        //get the param at position 0 which in this is the registration.
        String method = params[0];

        if (method.equals("register")){

            //then get the params for registration.
            String names = params[1];
            String idnos = params[2];
            String mails = params[3];
            String uname = params[4];
            String passw = params[5];

            //instantiate url.
            try {
                URL url = new URL(regUrl);

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

                String data = URLEncoder.encode("names", "UTF-8")+"="+URLEncoder.encode(names,"UTF-8")+"&"+
                        URLEncoder.encode("idnos", "UTF-8")+"="+URLEncoder.encode(idnos,"UTF-8")+"&"+
                        URLEncoder.encode("mails", "UTF-8")+"="+URLEncoder.encode(mails,"UTF-8")+"&"+
                        URLEncoder.encode("uname", "UTF-8")+"="+URLEncoder.encode(uname,"UTF-8")+"&"+
                        URLEncoder.encode("passw", "UTF-8")+"="+URLEncoder.encode(passw,"UTF-8");

                Log.d("DATA", data);

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
        else if(method == "login"){

            //get the params or positions for the fields for logins.
            String mail = params[1];
            String pass = params[2];

            try {
                //instantiate the url and assign a variable to it.
                URL url = new URL(loginUrl);

                //http connection and assign a variable to it.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //set the request method.
                httpURLConnection.setRequestMethod("POST");

                //set the do output.
                httpURLConnection.setDoOutput(true);

                //set the do input. so as to read from the server.
                httpURLConnection.setDoInput(true);

                //output a stream.
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //buffered writer.
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                //get the data.
                String data = URLEncoder.encode("mail", "UTF-8")+"="+URLEncoder.encode(mail,"UTF-8")+"&"+
                        URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                //write to the buffered writer.
                bufferedWriter.write(data);

                //flush it.
                bufferedWriter.flush();

                //close it.
                bufferedWriter.close();

                //close the output stream.
                outputStream.close();

                //get the input stream.
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

                //return the response.
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*//check whether there is internet connectivity.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //network information.
        NetworkInfo info = cm.getActiveNetworkInfo();

        //check whether the phone is connected to the network.
        if (info == null && !info.isConnected()){

            Toast.makeText(context, "Network is Active.", Toast.LENGTH_LONG).show();
        }
        else{

            Toast.makeText(context, "Network is Inactive.", Toast.LENGTH_LONG).show();
        }*/

        Intent intent = new Intent(context,Login.class);

        context.startActivity(intent);

        return "Failed.";

    }

    public String statement(String state){

        progressDialog.dismiss();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        // Setting Dialog Title
        //alertDialog.setTitle("Registration Status.");

        // Setting Dialog Message
        alertDialog.setMessage(state);

        //ensure one cannot cancel the dialog.
        alertDialog.setCancelable(false);

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Btn
        alertDialog.setPositiveButton("Try Again.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //dismiss the dialog.
                        Intent myIntent = new Intent(context, Login.class);
                        context.startActivity(myIntent);

                    }
                });

        // Showing Alert Dialog
        alertDialog.show();

        return state;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(final String result) {

        //display the corresponding messages appropriately.
        if (result.equals("Account Creation Done.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Proceed.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, Login.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (result.equals("Account Not Created.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, CreateAccount.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (result.equals("This Email Address is Invalid.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, CreateAccount.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }

        else if(result.equals("This Email Address is Taken.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, CreateAccount.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (result.equals("No Data Entered.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Login Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, CreateAccount.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }

        /**
         *  Login Environment.
         */
        else if(result.equals("Field(s) Are Empty.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, Login.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (result.equals("Provide a Proper Email Address.")){

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Registration Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, Login.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else if (result.equals("Provide Correct Email Address and or Password.")) {

            //dismiss the progress dialog.
            progressDialog.dismiss();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);

            // Setting Dialog Title
            //alertDialog.setTitle("Login Status.");

            // Setting Dialog Message
            alertDialog.setMessage(result);

            //ensure one cannot cancel the dialog.
            alertDialog.setCancelable(false);

            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.delete);

            // Setting Positive "Yes" Btn
            alertDialog.setPositiveButton("Try Again.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //dismiss the dialog.
                            Intent myIntent = new Intent(context, Login.class);
                            context.startActivity(myIntent);

                        }
                    });

            // Showing Alert Dialog
            alertDialog.show();
        }
        else{

            try {
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonArray.getJSONObject(0);

                final int id = JO.getInt("id");
                final String username = JO.getString("username");

                String code = JO.getString("code");
                String message = JO.getString("message");

                //dismiss the progress dialog.
                progressDialog.dismiss();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);

                alertDialog.setMessage(message);

                //ensure one cannot cancel the dialog.
                alertDialog.setCancelable(false);

                // Setting Positive "Yes" Btn
                alertDialog.setPositiveButton("OK.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //dismiss the dialog.
                                Intent myIntent = new Intent(context, Account.class);

                                //pass in the result to the account activity.
                                myIntent.putExtra("id",id);
                                myIntent.putExtra("username",username);

                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                context.startActivity(myIntent);

                            }
                        });

                // Showing Alert Dialog
                alertDialog.show();

                EditText mail,pass;

                mail = (EditText) activity.findViewById(R.id.editTextUser);
                pass = (EditText) activity.findViewById(R.id.editTextPass);

                mail.setText("");
                pass.setText("");

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*progressDialog.setMessage(result);

            //dismiss the progress dialog.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 3000);

            Intent myIntent = new Intent(context, Account.class);
            context.startActivity(myIntent);*/
        }

    }
}
