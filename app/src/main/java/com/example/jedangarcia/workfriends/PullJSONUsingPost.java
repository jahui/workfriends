package com.nullpointer.app;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jedan on 5/12/14.
 */
public class PullJSONUsingPost {

    String url;
    ArrayList<NameValuePair> postTuples;
    JSONArray jarray;


    public PullJSONUsingPost(String url, ArrayList<NameValuePair> postTuples){
        this.url = url;
        this.postTuples = postTuples;
        Poster postObject = new Poster();

        try {
            jarray = (JSONArray) postObject.execute(url, postTuples).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getJarray(){
        return jarray;
    }


}
