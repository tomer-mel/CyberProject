package com.example.tachat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.exifinterface.media.ExifInterface;
import android.media.Image;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

// decryptText
// unhideTextInImage

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
        LoadMessages();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SendImage(View view) throws IOException, URISyntaxException { // needs to send the pic to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        String key = MakeRandomKey(text);
        String encryptedmessage = EncryptText(text, key);
        ImageView img = HideTextInImage(encryptedmessage, key);
        //  sends pic to server


    }

    public String MakeRandomKey(String text){                              //finished
        byte[] textBytes = text.getBytes();
        byte[] key = new byte[textBytes.length];
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImageView HideTextInImage(String encryptedmessage, String key) throws IOException, URISyntaxException {  //finished
        int Rannum = new Random().nextInt(2) + 1;
        if (Rannum == 1)
            return(HideTextInImageWay1(encryptedmessage, key));
        else
            return(HideTextInImageWay1(encryptedmessage, key));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImageView HideTextInImageWay1(String encryptedmessage, String key) throws IOException { // hides the text in the Image metadata
        Drawable drawable;
        ImageView img = findViewById(R.id.imageView);
        int rannum = new Random().nextInt(15) + 1;
        if (rannum == 1)
            drawable = getDrawable(R.drawable.a);
        else if (rannum == 2)
            drawable = getDrawable(R.drawable.b);
        else if (rannum == 3)
            drawable = getDrawable(R.drawable.c);
        else if (rannum == 4)
            drawable = getDrawable(R.drawable.d);
        else if (rannum == 5)
            drawable = getDrawable(R.drawable.e);
        else if (rannum == 6)
            drawable = getDrawable(R.drawable.f);
        else if (rannum == 7)
            drawable = getDrawable(R.drawable.g);
        else if (rannum == 8)
            drawable = getDrawable(R.drawable.h);
        else if (rannum == 9)
            drawable = getDrawable(R.drawable.i);
        else if (rannum == 10)
            drawable = getDrawable(R.drawable.j);
        else if (rannum == 11)
            drawable = getDrawable(R.drawable.k);
        else if (rannum == 12)
            drawable = getDrawable(R.drawable.l);
        else if (rannum == 13)
            drawable = getDrawable(R.drawable.m);
        else if (rannum == 14)
            drawable = getDrawable(R.drawable.n);
        else
            drawable = getDrawable(R.drawable.o);
        img.setImageDrawable(drawable);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File picfile = wrapper.getDir("Images",MODE_PRIVATE);
        picfile = new File(picfile, "pic"+".jpg");
        try{
            OutputStream stream = null;
            stream = new FileOutputStream(picfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        Uri savedImageURI = Uri.parse(picfile.getAbsolutePath());
        img.setImageURI(savedImageURI);
        ExifInterface exif = new ExifInterface(picfile);
        exif.setAttribute("UserComment", encryptedmessage+key);
        exif.saveAttributes();
        String x = exif.getAttribute("UserComment");
        return img;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImageView HideTextInImageWay2(String encryptedmessage, String key) { // hides the text in the Image pixels
        ImageView img =  findViewById(R.id.imageView);
        img.setImageDrawable(getDrawable(R.drawable.o));
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baops = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baops);
        byte[] imageInByte = baops.toByteArray();
        int offset = 0;
        encryptedmessage +=key;
        byte[]addition = encryptedmessage.getBytes();
        if(addition.length + offset > imageInByte.length)
        {
            throw new IllegalArgumentException("File not long enough!");
        }
        for(int i=0; i<addition.length; ++i)
        {
            int add = addition[i];
            for(int bit=7; bit>=0; --bit, ++offset)
            {
                int b = (add >>> bit) & 1;
                imageInByte[offset] = (byte)((imageInByte[offset] & 0xFE) | b );
            }
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, offset, imageInByte.length);
        img.setImageBitmap(Bitmap.createScaledBitmap(bmp, img.getWidth(), img.getHeight(), false));
        return img;
    }

    public void LoadMessages(){
        new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String info = "";
                try{
                    String ip= "192.168.14.82";
                    int port = 678;
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

