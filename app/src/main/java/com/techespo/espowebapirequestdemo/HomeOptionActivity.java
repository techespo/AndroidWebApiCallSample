package com.techespo.espowebapirequestdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_option);

        ((Button)findViewById(R.id.btn_get_request)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(HomeOptionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ((Button)findViewById(R.id.btn_post_request)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(HomeOptionActivity.this,POSTApiCallActivity.class);
                startActivity(intent);
            }
        });
    }
}
