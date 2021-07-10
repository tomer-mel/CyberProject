package com.example.tachat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "username";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);
    }

    public boolean IsValid() // make sure the email ana password are valid
    {
        EditText editText = (EditText) findViewById(R.id.emailfill);
        String username = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.passwordfill);
        String password = editText2.getText().toString();
        if (username.contains("@")){
            if (password.length() > 5){
                return ((username.equals(getString(R.string.useremail1))) && (password.equals(getString(R.string.userpassword1)))) || ((username.equals(getString(R.string.useremail2))) && (password.equals(getString(R.string.userpassword2)))) || ((username.equals(getString(R.string.useremail3))) && (password.equals(getString(R.string.userpassword3)))) || ((username.equals(getString(R.string.useremail4))) && (password.equals(getString(R.string.userpassword4))));
           }
      }
        return false;
    }
    public void homepage(View view)
    {
        if (IsValid())
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