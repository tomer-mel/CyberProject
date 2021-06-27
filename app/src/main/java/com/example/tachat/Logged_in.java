package com.example.tachat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.exifinterface.media.ExifInterface;
import android.media.Image;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
// make it possible to add users
// HideTextInImageWay1
// HideTextInImageWay2
// server that gets every pic with message and holds a string of them and sends them to the app whenever it opens
// the server will have a folder with pictures for every user and will check that the user have all the pictures in the chat and will send the pictures between the users
// make a database for pictures with different topics and chose random topic from which to send pictures

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

    public void SendImage(View view) throws IOException, URISyntaxException { // sends the pic message to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        String key = MakeRandomKey();
        String encryptedmessage = EncryptText(text, key);
        ImageView img = HideTextInImage(encryptedmessage, key);
    }

    public String MakeRandomKey(){                              //finished
        byte[] key = new byte[200];
        Random rad = new Random();
        rad.nextBytes(key);
        return new String(key, StandardCharsets.UTF_8);
    }

    public String EncryptText(String text, String key){                             //finished
        int Rannum = new Random().nextInt(3) + 1;
        if (Rannum == 1)
            return Arrays.toString((EncryptingTextWay1(text, key))) +"way1";
        if (Rannum == 2)
            return(Arrays.toString(EncryptingTextWay2(text, key)))+"way2";
        else
            return(Arrays.toString(EncryptingTextWay3(text, key)))+"way3";
    }

    public byte[] EncryptingTextWay1(String text, String key){   //finished
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] ^ keybytes[i]);
        }
        return encryptedmessage;
    }

    public byte[] EncryptingTextWay2(String text, String key){  //finished
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] & keybytes[i]);
        }
        return encryptedmessage;
    }
    public byte[] EncryptingTextWay3(String text, String key){  //finished
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] | keybytes[i]);
        }
        return encryptedmessage;
    }

    public ImageView HideTextInImage(String encryptedmessage, String key) throws IOException, URISyntaxException { // hides the text and key in the image in one of two ways, need to figure out how to hide everything in the image.
        int Rannum = new Random().nextInt(2) + 1;
        if (Rannum == 1)
            return(HideTextInImageWay1(encryptedmessage, key));
        else
            return(HideTextInImageWay2(encryptedmessage, key));
    }

    public ImageView HideTextInImageWay1(String encryptedmessage, String key){ // hides the text in the Image
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.yoda);
        return img;
    }

    public ImageView HideTextInImageWay2(String encryptedmessage, String key) throws URISyntaxException, IOException { // hides the text in the Image in a different way (metadata)
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.yoda);
        return img;
    }

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

