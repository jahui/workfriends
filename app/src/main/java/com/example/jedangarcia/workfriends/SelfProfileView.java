package com.example.jedangarcia.workfriends;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by jedan.garcia on 11/13/15.
 */
public class SelfProfileView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);
    }

    public void onSayHiClick(View v){
        Intent intent = new Intent(this, MatchView.class);
        startActivity(intent);
    }

}
