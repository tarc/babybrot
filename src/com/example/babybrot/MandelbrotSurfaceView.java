package com.example.babybrot;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.ProgressBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EView;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EView
public class MandelbrotSurfaceView extends SurfaceView implements Callback {

	ProgressBar progressBar;
	
	private int pixelSize;
	public int getPixelSize() {
		return pixelSize;
	};

	private boolean active=false;
	public boolean isActive(){
		return active;
	}
	
	private boolean donePainting=true;
	public void setDonePainting(boolean donePainting){
		this.donePainting = donePainting;
	}
	
	private PaintWorker paintWorker;
	public void setPaintWorker(PaintWorker paintWorker) {
		this.paintWorker = paintWorker;
	};
	
	private SurfaceHolder mSurfaceHolder;
	
	public MandelbrotSurfaceView(Context context, AttributeSet 
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
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
		pixelSize = gcd(width, height);
		/*if (pixelSize%2==0)
			pixelSize/=4;*/
		pixelSize=1;
		zeroProgress();
		paintWorker.traverse(this);
	}
	
	@UiThread
	void zeroProgress(){
		progressBar.setProgress(0);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		active = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		active = false;

		while (!donePainting) {
			waitALittle();
        }
	}
	
	@UiThread(delay=100)
	void waitALittle(){
		
	}
	
	private int gcd(int a,int b){
		if(b==0) return a;
		
		return gcd(b,a%b);
	}

	public void test() {
		//paintWorker.test(this);
		
	}

}
