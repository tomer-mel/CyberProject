package com.example.tachat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean IsValid() //need to do a python server who will do that
    {
        EditText editText = (EditText) findViewById(R.id.emailfill);
        String username = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.passwordfill);
        String password = editText2.getText().toString();
        if (username.contains("@")){
            if (password.length() > 5){
                if (((username.equals(getString(R.string.useremail1))) || (username.equals(getString(R.string.useremail2)))) || (username.equals(getString(R.string.useremail3))) || (username.equals(getString(R.string.useremail4))))
                {
                    if (((password.equals(getString(R.string.userpassword1))) || (password.equals(getString(R.string.userpassword2)))) || (password.equals(getString(R.string.userpassword3))) || (password.equals(getString(R.string.userpassword4)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void homepage(View view)
    {
        if (IsValid()) //sends that to the server to check
        {
            Intent intent = new Intent(this, Logged_in.class);
            EditText editText = (EditText) findViewById(R.id.emailfill);
            String username = editText.getText().toString();
            username = username.substring(0, username.indexOf("@"));
            intent.putExtra(EXTRA_MESSAGE, username);
            startActivity(intent);
        }
        else {
            TextView textView = findViewById(R.id.textView2);
            textView.setText(R.string.tryagain);
        }
    }
}