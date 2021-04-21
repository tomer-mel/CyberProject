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
    protected void onCreate(Bundle savedInstanceState) {    //finished
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.welcome);
        String welcome = "welcome " + message;
        textView.setText(welcome);
        //LoadMessages();
    }


    public byte[] EncryptText(String text, String key){     //finished
        int Rannum = new Random().nextInt(3) + 1;
        if (Rannum == 1)
            return(EncryptingTextWay1(text, key));
        if (Rannum == 2)
            return(EncryptingTextWay2(text, key));
        else
            return(EncryptingTextWay3(text, key));
    }
    public String MakeRandomKey(){
        byte[] rad = new byte[40];
        Random key = new Random();
        key.nextBytes(rad);
        return key.toString();
    }


    public Image HideTextInImage(byte[] encryptedmessage, String key){ // hides the text and key in the image in three ways, need to  figure out how to hide everything in the image.

    }


    public void SendImage(View view) { // sends the pic message to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        String key = MakeRandomKey();
        byte[] encryptedmessage = EncryptText(text, key);
        Image img = HideTextInImage(encryptedmessage, key);
    }



    public byte[] EncryptingTextWay1(String text, String key){
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





    //public void LoadMessages() // load previous messages from the server and display them on the screen
    //{}


    }

