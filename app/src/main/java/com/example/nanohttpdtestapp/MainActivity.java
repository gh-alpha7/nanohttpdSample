package com.example.nanohttpdtestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;


public class MainActivity extends AppCompatActivity {
    private WebServer server;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = new WebServer(8080);
        try {
            server.start();
        } catch(IOException ioe) {
            Log.w("Httpd", "The server could not start.");
        }
    }

    class WebServer extends NanoHTTPD{
        public WebServer(int port){
            super(port);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String uri = session.getUri();
            FileInputStream fis = null;
            String fileName = Environment.getExternalStorageDirectory()
                    + "/nanofile.txt";
            File file = new File(fileName);
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return newFixedLengthResponse(Response.Status.OK, "text/plain", fis, file.length());
        }
    }
}