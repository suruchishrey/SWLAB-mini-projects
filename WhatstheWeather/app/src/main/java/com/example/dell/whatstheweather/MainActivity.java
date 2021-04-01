package com.example.dell.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView resultTextView;

    public void getweather(View view) {
        DownloadTask task = new DownloadTask();

        try {

            String result=task.execute("https://openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();

            InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
        } catch (Exception e) {
            resultTextView.setText("Sorry something went wrong!");
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText2);
        resultTextView = findViewById(R.id.textView3);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                String result = "";
                while (data != -1) {
                    char ch = (char) data;
                    result += ch;
                    data = inputStreamReader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                Log.i("ERROR:",e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("ERROR:",e.getMessage());
                e.printStackTrace();
            }

            return "failed";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            String weatherInfo = "";
            try {
                jsonObject = new JSONObject(s);
                weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content:", weatherInfo);
                JSONArray arr = null;

                arr = new JSONArray(weatherInfo);
                String message="";
                for (int i = 0; i < arr.length(); ++i) {

                    JSONObject object = arr.getJSONObject(i);
                    String main=object.getString("main");
                    String description=object.getString("description");
                    if(!main.equals("") && !description.equals(""))
                    {
                        message+=main+"\r\n"+description;
                    }
                }
                if(!message.equals(""))
                {
                    resultTextView.setText(message);
                }
                else{
                    resultTextView.setText("Sorry something went wrong!");
                }
            } catch (JSONException e) {
                resultTextView.setText("Sorry something went wrong! " + e.getMessage());

                e.printStackTrace();
            }
        }
    }
}
