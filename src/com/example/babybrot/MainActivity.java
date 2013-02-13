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
	MandelbrotSurfaceView surfaceView;
	
	/** 
	 * Necessarily the {@literal @}{@link NonConfigurationInstance}
	 * requires the field to pertain to a {@literal @}{@link EActivity}
	 * annotated class. */
	@NonConfigurationInstance
	@Bean
	PaintWorker paintWorker;


	/**
	 * The SurfaceView object must know paintWorker is responsible to
	 * paint its canvas.
	 */
	@AfterViews
	void surfaceViewPaintWorkerInstall(){
		surfaceView.setPaintWorker(paintWorker);
	}
	
	@Click
	void surfaceViewClicked(){
		surfaceView.test();
	}

	

}
