package com.espoir.espoirsoftservicedemotwo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.espoir.espoirsoftservicedemotwo.BoundService.MyBinder;


public class MainActivity extends ActionBarActivity {
	 BoundService mBoundService;
	 boolean mServiceBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);
        
        
        ((Button) findViewById(R.id.play_music)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(MainActivity.this, PlayMusicInBackGroundService.class);
			        startService(intent);
			}
		});
        ((Button) findViewById(R.id.stop_music)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(MainActivity.this, PlayMusicInBackGroundService.class);
			        stopService(intent);
			}
		});
        
        ((Button) findViewById(R.id.play_music)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(MainActivity.this, PlayMusicInBackGroundService.class);
			        startService(intent);
			}
		});
        
        printTimestampButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        if (mServiceBound) {
        timestampText.setText(mBoundService.getTimestamp());
        }
        }
        });
        
        stopServiceButon.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        if (mServiceBound) {
        unbindService(mServiceConnection);
        mServiceBound = false;
        }
        Intent intent = new Intent(MainActivity.this,
        BoundService.class);
        stopService(intent);
        }
        });
        
        }
        
        @Override
        protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
        
        @Override
        protected void onStop() {
        super.onStop();
        if (mServiceBound) {
        unbindService(mServiceConnection);
        mServiceBound = false;
        }
        }
        
        private ServiceConnection mServiceConnection = new ServiceConnection() {
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
        mServiceBound = false;
        }
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        MyBinder myBinder = (MyBinder) service;
        mBoundService = myBinder.getService();
        mServiceBound = true;
        }
        };
       }