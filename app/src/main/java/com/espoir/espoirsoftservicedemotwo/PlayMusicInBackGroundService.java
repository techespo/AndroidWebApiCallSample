package com.espoir.espoirsoftservicedemotwo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayMusicInBackGroundService extends Service{

	private static final String LOGCAT = null;
	MediaPlayer objPlayer;

	public void onCreate(){
		super.onCreate();
		Log.d(LOGCAT, "Service Started!");
		objPlayer = MediaPlayer.create(this,R.raw.notification_sound);
	}

	public int onStartCommand(Intent intent, int flags, int startId){

		objPlayer.start();
		Log.d(LOGCAT, "Media Player started!");
		if(objPlayer.isLooping() != true){
			Log.d(LOGCAT, "Problem in Playing Audio");
		}
		//stopSelf();
		return 1;
	}

	public void onStop(){
		objPlayer.stop();
		objPlayer.release();
	}

	public void onPause(){
		objPlayer.stop();
		objPlayer.release();
	}

	public void onDestroy(){
		objPlayer.stop();
		objPlayer.release();
	}

	@Override
	public IBinder onBind(Intent objIndent) {
		return null;
	}
}
