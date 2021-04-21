package com.example.tachat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Random;

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


    public byte[] EncryptText(String text, String key){ // choosing which way to Encrypt the text in
        int Rannum = new Random().nextInt(3) + 1;
        if (Rannum == 1)
            return(EncryptingTextWay1(text, key));
        if (Rannum == 2)
            return(EncryptingTextWay2(text, key));
        if (Rannum == 3)
            return(EncryptingTextWay3(text, key));
        else
            return EncryptingTextWay1(text, key);
    }
    public String MakeRandomKey(){
        byte[] rad = new byte[40];
        Random key = new Random();
        key.nextBytes(rad);
        return key.toString();
    }


    public Image HideTextInImage(byte[] encryptedmessage, String keymessage){ // hides the text and key in the ire out how to hide the text in the image.

    }


    //public void SendImage(View view, byte[] key){ // sends the pic message to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        String key = MakeRandomKey();
        byte[] encryptedmessage = EncryptText(text, key);
        Image img = HideTextInImage(encryptedmessage, key);




    public byte[] EncryptingTextWay1(String text, String key){ // Encrypting the text
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] ^ keybytes[i]);
        }
        return encryptedmessage;
    }


    public byte[] EncryptingTextWay2(String text, String key){ // Encrypting the text

    }


    public byte[] EncryptingTextWay3(String text, String key){ // Encrypting the text

    }








    }

