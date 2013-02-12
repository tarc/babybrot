package com.example.babybrot;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.googlecode.androidannotations.annotations.EView;

@EView
public class PlaneSurfaceView extends SurfaceView implements Callback {
	

	
	private boolean ready=false;
	public boolean isReady(){
		return ready;
	}
	
	SurfaceHolder mSurfaceHolder;
	
	public PlaneSurfaceView(Context context, AttributeSet 
			 attrs) {
		super(context, attrs);
		
		SurfaceHolder holder = getHolder();
		
		holder.addCallback(this);
		
		mSurfaceHolder = holder;
		
	}
	
	public Canvas lock(){
		return mSurfaceHolder.lockCanvas();
	}
	
	public void unlock(Canvas c){
		mSurfaceHolder.unlockCanvasAndPost(c);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		ready = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		ready = false;
	}

}
