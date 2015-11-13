package com.example.jedangarcia.workfriends;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class Poster extends AsyncTask {
    HttpClient httpclient;
    HttpPost httppost;
    static JSONArray jarray = null;


    @Override
    protected JSONArray doInBackground(Object[] objects) {
        StringBuilder builder = new StringBuilder();

        String url = (String) objects[0];
        List<NameValuePair> forms = (List<NameValuePair>) objects[1];

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 15000);
        HttpConnectionParams.setSoTimeout(params, 20000);

        httpclient = new DefaultHttpClient(params);
        httppost = new HttpPost(url);

        Log.w("POSTER", forms.toString());
        Log.w("POSTER", httppost.getURI().toString());

        try {

            httppost.setEntity(new UrlEncodedFormEntity(forms));
            HttpResponse response =  httpclient.execute(httppost);

            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();


            if (statusCode == 200) { //OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            jarray = new JSONArray(builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jarray;

    }
}