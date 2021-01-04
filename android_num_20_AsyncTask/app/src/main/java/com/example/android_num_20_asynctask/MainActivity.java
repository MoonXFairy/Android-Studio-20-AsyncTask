package com.example.android_num_20_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener
{
    ImageView imageView;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imgV1);
        button = findViewById(R.id.loading);
        button.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v)
    {
        new downloadimg().execute("https://memes.tw/user-template/ba60efc72c59e8d25e9550665f592918.png");
    }
    private  class  downloadimg extends AsyncTask<String, Integer, Bitmap>
    {
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            //Load picture from Url
            try
            {
                URL url = new URL(strings[0]);
                progressBar.incrementProgressBy(50);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                progressBar.incrementProgressBy(100);
                return temp;

            }

            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if (bitmap != null)
            {
                imageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Download Successful",
                        Toast.LENGTH_SHORT).show();//Download is complete
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Download fail",
                        Toast.LENGTH_SHORT).show();//Download failed pop-up download failed
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            if (values != null)
            {
                progressBar.setProgress(values[0]);
            }
        }
    }
}