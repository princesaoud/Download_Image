package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bt_link;
    ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
        myImage = findViewById(R.id.mImage);

    }

    @Override
    public void onClick(View view) {
        TextView tv_url = findViewById(R.id.tv_url);

        if(tv_url != null){

            String url = tv_url.getText().toString().trim();

            try {
                DownloadImage task = new DownloadImage();
                Bitmap bitmap = task.execute(url).get();

                myImage.setImageBitmap(bitmap);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {


            try {
                URL url = new URL(urls[0]);
//                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//                huc.connect();
                URLConnection huc = url.openConnection();
                InputStream is = huc.getInputStream();

                return BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
