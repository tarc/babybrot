package com.example.babybrot;

import android.app.Activity;
import android.widget.ProgressBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NonConfigurationInstance;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.babybrot)
public class Babybrot extends Activity {

	@ViewById
	MandelbrotSurfaceView surfaceView;
	
	@ViewById
	ProgressBar progressBar;
	
	void incrementProgressBy(int delta){
		progressBar.incrementProgressBy(delta);
	}
	
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
		surfaceView.progressBar = progressBar;
	}
	
	@Click
	void surfaceViewClicked(){
		surfaceView.test();
	}

	

}
