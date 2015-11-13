package com.example.jedangarcia.workfriends;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jedangarcia.workfriends.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class MatchView extends Activity {

    private String matchURL = "http://geekfancy.com/workfriends2/get_match.php?uid=1&week=2015.46";
    private final String DEBUG_TAG = "MatchView";
    private String match1ID;
    private String match2ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String uidstring = intent.getStringExtra("uid");
        String weekstring = intent.getStringExtra("week");

        String stringUrl = matchURL;// + "uid=" + uidstring + "&week=" + weekstring;

        class DownloadWebpageTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {

                // params comes from the execute() call: params[0] is the url.
                try {
                    return downloadUrl(urls[0]);
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid.";
                }
            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                // parse the JSON
                Log.d(DEBUG_TAG, result);
                try {
                    JSONArray matchesJson = new JSONArray(result);

                    JSONObject matchJson = matchesJson.getJSONObject(0);
                    match1ID = matchJson.getString("match_1");

                    //matchJson = matchesJson.getJSONObject(1);
                    match2ID = matchJson.getString("match_2");

                    class GetUserWebPage extends AsyncTask<String, Void, String> {

                        //private final String DEBUG_TAG = "GetUserWebPage";
                        @Override
                        protected String doInBackground(String... urls) {

                            // params comes from the execute() call: params[0] is the url.
                            try {
                                return downloadUrl(urls[0]);
                            } catch (IOException e) {
                                return "Unable to retrieve web page. URL may be invalid.";
                            }
                        }
                        // onPostExecute displays the results of the AsyncTask.
                        @Override
                        protected void onPostExecute(String result) {
                            // parse the JSON
                            //Log.d(DEBUG_TAG + "nested", result);
                            try {
                                JSONArray matchesJson = new JSONArray(result);

                                JSONObject matchJson = matchesJson.getJSONObject(0);
                                String first_name = matchJson.getString("first_name");

                                //matchJson = matchesJson.getJSONObject(1);
                                String last_name = matchJson.getString("last_name");

                                TextView view = (TextView) findViewById (R.id.textView);
                                view.setText(first_name + " " + last_name);
                                view.invalidate();



                            } catch (Exception e){
                                Log.e(DEBUG_TAG, e.getMessage());
                            }

                        }

                    }

                    class GetUserWebPage2 extends AsyncTask<String, Void, String> {

                        //private final String DEBUG_TAG = "GetUserWebPage";
                        @Override
                        protected String doInBackground(String... urls) {

                            // params comes from the execute() call: params[0] is the url.
                            try {
                                return downloadUrl(urls[0]);
                            } catch (IOException e) {
                                return "Unable to retrieve web page. URL may be invalid.";
                            }
                        }
                        // onPostExecute displays the results of the AsyncTask.
                        @Override
                        protected void onPostExecute(String result) {
                            // parse the JSON
                            //Log.d(DEBUG_TAG + "nested", result);
                            try {
                                JSONArray matchesJson = new JSONArray(result);

                                JSONObject matchJson = matchesJson.getJSONObject(0);
                                String first_name = matchJson.getString("first_name");

                                //matchJson = matchesJson.getJSONObject(1);
                                String last_name = matchJson.getString("last_name");

                                TextView view2 = (TextView) findViewById (R.id.textView2);
                                view2.setText(first_name + " " + last_name);
                                view2.invalidate();



                            } catch (Exception e){
                                Log.e(DEBUG_TAG, e.getMessage());
                            }

                        }

                    }


                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String getUserURL = "http://geekfancy.com/workfriends2/get_specific_user.php?uid=";
                        new GetUserWebPage().execute(getUserURL+match1ID);
                        new GetUserWebPage2().execute(getUserURL+match2ID);
                    } else {
                        Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_SHORT);
                    }


                } catch (Exception e){
                    Log.e(DEBUG_TAG, e.getMessage());
                }

            }
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // run the first query to get the matched user ids
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            Toast.makeText(getApplicationContext(), "No network connection available.", Toast.LENGTH_SHORT);
        }

        setContentView(R.layout.activity_match_view);
    }
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
