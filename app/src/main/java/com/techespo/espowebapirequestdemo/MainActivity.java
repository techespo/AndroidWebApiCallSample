package com.techespo.espowebapirequestdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        apiCall();
    }

    public void apiCall(){
        new APIAsyncCall().execute();
    }

    public class APIAsyncCall extends AsyncTask<Void,Void,String>{
        ProgressDialog dialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading......");
            dialog.setCancelable(false);
            dialog.show();

        }
        @Override
        protected String doInBackground(Void... voids) {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;
            String inputLine;
            String result = null;
            try {

                //Create a URL object holding our url
                URL myUrl = new URL("http://ciboapp.com/admin/kdsApi/kdsApi/getOrderItems?restId=30692730&today=2018-01-04&limit=0&order_time=%20HTTP/1.1");
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s != null ){
                System.out.print(s);
            }
        }
    }
}
