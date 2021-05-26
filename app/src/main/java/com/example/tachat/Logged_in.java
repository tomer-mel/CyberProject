package com.example.tachat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

// EncryptingTextWay2, HideTextInImageWay1, HideTextInImageWay2
// server that gets every pic with message and holds a string of them and sends them to the app whenever it opens
// make a database for pictures with different topics and chose random topic from which to send pictures
// make it possible to add users

public class Logged_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.welcome);
        String welcome = "Welcome " + message;
        textView.setText(welcome);
        TextView last_messages_line = findViewById(R.id.last_line);
        last_messages_line.requestFocus();
        TextView end2 = findViewById(R.id.end);
        end2.requestFocus();
       // LoadMessages();
    }



    public void SendImage(View view) { // sends the pic message to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        String key = MakeRandomKey();
        //    byte[] encryptedmessage = EncryptText(text, key);
        //Image img = HideTextInImage(encryptedmessage, key);
    }



    public String MakeRandomKey(){
        byte[] rad = new byte[40];
        Random key = new Random();
        key.nextBytes(rad);
        return key.toString();
    }


    //public byte[] EncryptText(String text, String key){
    //    int Rannum = new Random().nextInt(2) + 1;
    //    if (Rannum == 1)
     //       return(EncryptingTextWay1(text, key));
    //    else
    //        return(EncryptingTextWay2(text, key));
   // }


    public byte[] EncryptingTextWay1(String text, String key){   //finished
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] ^ keybytes[i]);
        }
        return encryptedmessage;
    }


    //public byte[] EncryptingTextWay2(String text, String key){ // Encrypting the text
    //}

    //public Image HideTextInImage(byte[] encryptedmessage, String key){ // hides the text and key in the image in one of two ways, need to figure out how to hide everything in the image.
    //    int Rannum = new Random().nextInt(2) + 1;
    //    if (Rannum == 1)
    //       return(HideTextInImageWay1(encryptedmessage, key));
    //    else
    //        return(HideTextInImageWay2(encryptedmessage, key));
    //}

    //public byte[] HideTextInImageWay1(byte[] encryptedmessage, String key){ // hides the text in the Image
    //}

    //public byte[] HideTextInImageWay2(byte[] encryptedmessage, String key){ // hides the text in the Image in a different way
    //}



    public void LoadMessages(){
        new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String info = "";
                try{
                    String ip= "192.168.14.5";
                    int port = 6789;
                    Socket s = new Socket(ip, port);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeUTF("GET");
                    BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    info = din.readLine();
                    din.close();
                    dout.close();
                    s.close();
                    TextView txt = findViewById(R.id.messages);
                    txt.append(info);
                }catch(Exception e){System.out.println(e);}
            }
        }).start();
    }



}

