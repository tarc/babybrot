package com.example.babybrot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

@EBean
public class PaintWorker {

	@RootContext
	MainActivity context;
	
	
	@Background
	void test(MandelbrotSurfaceView surfaceView){
		if(surfaceView.isActive()){
			Canvas c = null;
			
			try{
				synchronized (this) {
					surfaceView.setDonePainting(false);
					c = surfaceView.lock();
					Paint paint = new Paint();
					Paint textPaint = new Paint();
					textPaint.setAntiAlias(true);
					textPaint.setTextSize(15);
					textPaint.setTypeface(Typeface.create(Typeface.SERIF,
				          Typeface.ITALIC));
					textPaint.setTextAlign(Paint.Align.LEFT);
					int pixelSize = surfaceView.getPixelSize();
					int height = c.getHeight();
					int width = c.getWidth();
					
					int hc=height/pixelSize;
					int wc=width/pixelSize;

					int count=0;
					for(int y=0,yc=0;y<height;y+=pixelSize,yc++)
						for(int x=0,xc=0;x<width;x+=pixelSize,xc++){
							paint.setColor((count%2==0)? Color.RED : Color.BLUE);
							
							c.drawRect(new Rect(x,y,x+pixelSize,y+pixelSize),paint);

							textPaint.setColor((count%2==1)? Color.RED : Color.BLUE);
							c.drawText(""+(xc-wc/2)+","+(yc-hc/2),x,y+13,textPaint);
							count++;
						}
										
					surfaceView.setDonePainting(true);
				}
			}finally{
				if(c!=null)
					surfaceView.unlock(c);
			}
		}
	}
	
}
