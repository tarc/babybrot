package com.example.babybrot;

import android.graphics.Canvas;
import android.graphics.Color;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

@EBean
public class BackGroundColoring {

	@RootContext
	MainActivity context;
	
	
	@Background
	void mbColor(PlaneSurfaceView surfaceView){
		if(surfaceView.isReady()){
			Canvas c = null;
			
			try{
				c = surfaceView.lock();
				c.drawColor(Color.RED);
			}finally{
				if(c!=null)
					surfaceView.unlock(c);
			}
		}
	}
	
}
