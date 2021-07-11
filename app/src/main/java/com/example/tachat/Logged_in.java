package com.example.tachat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.app.ActivityCompat;
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


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
        //LoadMessages();
        //while (true)
            //GetNewMessage();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SendImage(View view) throws IOException { // needs to send the pic to the server
        TextView textView = findViewById(R.id.textline);
        String text = textView.getText().toString();
        textView.setText("");
        TextView txt = findViewById(R.id.messages);
        txt.append(text+"\n");
        byte[] tx = text.getBytes();
        byte[] key = MakeRandomKey(text);
        byte[] encryptedmessage = EncryptText(tx, key);
        byte[] img = HideTextInImage(encryptedmessage, key);
        GotImage(img);
    }
    public void GotImage(byte[] img) throws IOException { // needs to send the pic to the server
        byte[] message = UncoverTextInImage(img);
        int half = message.length / 2;
        byte[] cypher = Arrays.copyOfRange(message, 0, half);
        byte[] k = Arrays.copyOfRange(message, half, message.length);
        String dec = DecryptText(cypher, k);
        TextView txt = findViewById(R.id.messages);
        //txt.append(dec+"\n");
    }

    public byte[] MakeRandomKey(String text){                              //finished
        byte[] textBytes = text.getBytes();
        byte[] key = new byte[textBytes.length+1];
        Random rad = new Random();
        rad.nextBytes(key);
        return key;
    }

    public byte[] EncryptText(byte[] text, byte[] key){                             //finished
        int Rannum = new Random().nextInt(3) + 1;
        if (Rannum == 1)
            return (EncryptingTextWay1(text, key));
        else
            return (EncryptingTextWay1(text, key));
    }
    public String DecryptText(byte[] text, byte[] key){                             //finished
        byte key2 = key[key.length-1];
        if (key2 == 0)
            return DecryptingTextWay1(text, key);
        else
            return DecryptingTextWay1(text, key);
    }

    public byte[] EncryptingTextWay1(byte[] text, byte[] key){   //finished
        byte[] encryptedmessage = new byte[text.length];
        for (int i = 0; i < text.length;i++){
            encryptedmessage[i] = (byte) (text[i] ^ key[i]);
        }
        key[key.length-1] = 0;
        return encryptedmessage;
    }
    public String DecryptingTextWay1(byte[] encryptedmessage, byte[] key){   //finished
        byte[] message = new byte[encryptedmessage.length];
        for (int i = 0; i < encryptedmessage.length;i++){
            message[i] = (byte) (encryptedmessage[i] ^ key[i]);
        }
        return new String(message);
    }

    public byte[] EncryptingTextWay2(byte[] text, byte[] key){   //finished
        byte[] encryptedmessage = new byte[text.length];
        for (int i = 0; i < text.length;i++){
            encryptedmessage[i] = (byte) (text[i] & key[i]);
        }
        key[key.length-1] = 1;
        return encryptedmessage;
    }
    public String DecryptingTextWay2(byte[] encryptedmessage, byte[] key){   //finished
        byte[] message = new byte[encryptedmessage.length];
        for (int i = 0; i < encryptedmessage.length;i++){
            message[i] = (byte) (encryptedmessage[i] & key[i]);
        }
        return new String(message);
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
    public String DecryptingTextWay3(String text, String key){  //finished
        byte[] bin = text.getBytes();
        byte[] keybytes = key.getBytes();
        byte[] encryptedmessage = new byte[bin.length];
        for (int i = 0; i < bin.length;i++){
            encryptedmessage[i] = (byte) (bin[i] | keybytes[i]);
        }
        return Arrays.toString(encryptedmessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public byte[] HideTextInImage(byte[] encryptedmessage, byte[] key) throws IOException {  //finished
        int Rannum = new Random().nextInt(2) + 1;
        if (Rannum == 1)
            return(HideTextInImageWay2(encryptedmessage,key));
        else
            return(HideTextInImageWay2(encryptedmessage,key));
    }
    public byte[] UncoverTextInImage(byte[] img) throws IOException {  //finished
        return(UncoveringTextInImageWay2(img));
    }

    public byte[] UncoveringTextInImageWay2(byte[] imageInByte) {
        int offset = 0;
        byte[] b = new byte[100];
        int endind = 100;
        for (int i = 0; i < b.length; ++i) {
            for (int bit = 7; bit >= 0; --bit, ++offset) {
                int get = imageInByte[offset];
                int d = (get & 0x01 ) << bit;
                b[i] = (byte)((b[i]) | (byte)d );
            }
            if (b[i] == 0) {
                endind = i ;
                break;
            }
        }

        return Arrays.copyOfRange(b, 0, endind);
    }
    public String UncoveringTextInImageWay1(byte[] imageInByte) throws IOException {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File picfile = wrapper.getDir("Images",MODE_PRIVATE);
        picfile = new File(picfile, "pic2"+".jpg");


        try {
            FileOutputStream fos=new FileOutputStream(picfile.getPath());
            Toast.makeText(this, picfile.getPath(), Toast.LENGTH_SHORT).show();
            fos.write(imageInByte);
            fos.close();
        }
        catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }
        Uri savedImageURI = Uri.parse(picfile.getAbsolutePath());
        ImageView img = findViewById(R.id.imageView);
        img.setImageURI(savedImageURI);
        ExifInterface exif = new ExifInterface(picfile);
        return exif.getAttribute("UserComment");
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public byte[] HideTextInImageWay1(byte[] encryptedmessage, byte[] key) throws IOException { //finished
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
        String s = new String(encryptedmessage);
        s += new String(key);
        exif.setAttribute("UserComment", s);
        exif.saveAttributes();
        String x = exif.getAttribute("UserComment");
        img =  findViewById(R.id.imageView);
        Bitmap bitmap2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baops = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baops);
        return baops.toByteArray();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public byte[] HideTextInImageWay2(byte[] encryptedmessage, byte[] key) { // fix it
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
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        byte[] imageInByte = byteBuffer.array();
        int offset = 0;
        byte[] addition = new byte[encryptedmessage.length + key.length];
        System.arraycopy(encryptedmessage, 0, addition, 0, encryptedmessage.length);
        System.arraycopy(key, 0, addition, encryptedmessage.length, key.length);
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
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(imageInByte);
        Bitmap bmp = bitmap.copy(bitmap.getConfig(),true);
        bmp.copyPixelsFromBuffer(byteBuffer2);
        img.setImageBitmap(Bitmap.createScaledBitmap(bmp, img.getWidth(), img.getHeight(), false));
        return imageInByte;
    }

    public void LoadMessages(){ //  make sure that can separate pics
        new Thread(() -> {
            String info = "";
            try{
                String ip= "192.168.43.75";
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
        }).start();
    }
    public void GetNewMessage(){ //  server: check if info is different and if so sends it
            new Thread(() -> {
                String info = "";
                try {
                    String ip = "192.168.43.75";
                    int port = 678;
                    Socket s = new Socket(ip, port);
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    dout.writeInt(4);
                    dout.writeUTF("GETU");
                    BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    info = din.readLine();
                    din.close();
                    dout.close();
                    s.close();
                    TextView txt = findViewById(R.id.messages);
                    txt.append(info);
                } catch (Exception e) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    GetNewMessage();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                }
            }).start();

    }
    public void SendToServer(byte[] img){ //  send the pic to the server
        new Thread(() -> {
            try {
                String ip = "192.168.43.75";
                int port = 678;
                Socket s = new Socket(ip, port);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                dout.writeInt(img.length);
//                dout.flush();
                dout.writeUTF("POST");
//                dout.flush();
                dout.write(img);
                dout.close();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }

}
