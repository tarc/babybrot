package com.example.babybrot;

import android.app.Activity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NonConfigurationInstance;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById
	PlaneSurfaceView surfaceView;
	
	@NonConfigurationInstance
	@Bean
	BackGroundColoring bgColoring;


	@AfterViews
	void startSV(){
	}
	
	@Click
	void surfaceViewClicked(){
		bgColoring.mbColor(surfaceView);
	}

	

}
