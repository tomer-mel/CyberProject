package com.example.tachat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean IsValid()
    {
        String username = findViewById(R.id.emailfill).toString();
        String password = findViewById(R.id.passwordfill).toString();
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
        TextView textView = findViewById(R.id.textView2);
        textView.setText(R.string.tryagain);
        return false;
    }
    public void homepage()
    {

    }
}