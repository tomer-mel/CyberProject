package com.example.tachat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

public class Logged_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.welcome);
        String welcome = "welcome " + message;
        textView.setText(welcome);
    }


    public void EncryptText(String text){ // choosing which way to Encrypt the text in

    }


    public void HideTextInImage(byte[] encryptedmessage){ // hides the text and key in the image(need to figure out which image to use and from where), also need to figure out how to hide the text in the image.

    }


    //public void SendImage(View view, byte[] key){ // sends the pic message to the server
    //    TextView textView = findViewById(R.id.textline);
    //    String text = textView.getText().toString();
    //    message = EncryptText(String text);
    //    Image = HideTextInImage(byte[] encryptedmessage);


    // need to figure out how to return the key(it doesn't support multi return)
    public byte[] EncryptingTextWay1(String text){ // Encrypting the text and creates a key
        byte[] bin = text.getBytes();
        String keymessage = "veryveryveryveryveryveryveryveryveryveryveryverylongmessage";
        byte[] key = keymessage.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] ^ key[i]);
        }
        return encryptedmessage;
    }


    public void EncryptingTextWay2(View view){ // Encrypting the text and creates a key

    }


    public void EncryptingTextWay3(View view){ // Encrypting the text and creates a key

    }








    }

