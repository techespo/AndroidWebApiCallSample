package com.techespo.espowebapirequestdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.OrderPojo;

public class MainActivity extends AppCompatActivity {

    ArrayList<OrderPojo> listItems;
    Context context;
    TextView txtTotalCount;

    ListView lvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        txtTotalCount = (TextView) findViewById(R.id.txt_total_count);
        lvOrders = (ListView) findViewById(R.id.lv_orders);

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
                try {
                    JSONObject objRoot = new JSONObject(s);
                    String totalCount = objRoot.getString("count");
                    txtTotalCount.setText("Total Count: "+totalCount);

                    listItems =  new ArrayList<OrderPojo>();
                    JSONArray objOrderArray = objRoot.getJSONArray("orders");
                    if(objOrderArray != null && objOrderArray.length()>0){
                        for (int i=0 ;i<objOrderArray.length();i++){
                            JSONObject indexObj = objOrderArray.getJSONObject(i);
                            OrderPojo pojo = new OrderPojo(
                                    indexObj.getString("order_id"),
                                    indexObj.getString("order_date"));
                            listItems.add(pojo);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if(listItems != null && listItems.size()>0){
                    OrdersListAdapter adapter =  new OrdersListAdapter();
                    lvOrders.setAdapter(adapter);
                }
                System.out.print(s);
                Toast.makeText(getApplicationContext(), s,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    class OrdersListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int i) {
            return listItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v =  view;
            if(v == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                v= inflater.inflate(R.layout.orderlist_row_layout,null);
            }
            OrderPojo pojo =  listItems.get(i);
            TextView date= (TextView) v.findViewById(R.id.txt_date);
            date.setText(pojo.getOrder_date());
            TextView order_id= (TextView) v.findViewById(R.id.txt_orderid);
            order_id.setText(pojo.getOrder_id());
            return v;
        }
    }
}
