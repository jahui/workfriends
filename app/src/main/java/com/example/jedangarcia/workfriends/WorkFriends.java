package com.example.jedangarcia.workfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WorkFriends extends Activity {

    static final String root = new String("http://www.geekfancy.com/workfriends/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_work_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Listeners
    //Still building this - jedan

        /*
    public void onClickLogin(View v) throws ExecutionException, InterruptedException{


        //getting the input username
        EditText uname = (EditText) findViewById(R.id.editText2);
        String input_uname = uname.getText().toString();

        //getting the input password
        EditText password = (EditText) findViewById(R.id.editText);
        String input_password = password.getText().toString();

        //Posting to validate_login.php with the entered information
        Poster usernameValidation = new Poster();
        String validateUserUrl = root + "validate_login.php";
        ArrayList<NameValuePair> userAndPasswordFields = new ArrayList<NameValuePair>(2);
        userAndPasswordFields.add(new BasicNameValuePair("uname", input_uname));
        userAndPasswordFields.add(new BasicNameValuePair("password", input_password));
        JSONArray validationJArray = (JSONArray) usernameValidation.execute(validateUserUrl, userAndPasswordFields).get();


    }
    */

    public void onClickLogin(View v){
        Intent intent = new Intent(this, MatchView.class);
        startActivity(intent);
    }



}

