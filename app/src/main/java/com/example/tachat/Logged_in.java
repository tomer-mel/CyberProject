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
    public void EncryptingText(View view){ // Encrypting the text and creates a key

    }
    public void HideTextInImage(View view){ // hides the text and key in the image(need to figure out which image to use and from where), also need to figure out

    }
    //public void SendImage(View view){ // sends the pic message to the server
    //    TextView textView = findViewById(R.id.welcome);
    //    String message = textView.getText().toString();
    //   message = EncodeText(message);
    //    Image = HideTextInImage(message);
    public byte[], byte[] EncryptingTextWay1(View view){ // Encrypting the text and creates a key
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        byte[] bin = text.getBytes();
        String message = "veryveryveryveryveryveryveryveryveryveryveryverylongmessage";
        byte[] key = message.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] ^ key[i]);
        }
        return encryptedmessage, key;
    }
    public void EncryptingTextWay2(View view){ // Encrypting the text and creates a key

    }
    public void EncryptingTextWay3(View view){ // Encrypting the text and creates a key

    }








    }

