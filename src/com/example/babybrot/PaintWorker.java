package com.example.babybrot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

@EBean
public class PaintWorker {

	@RootContext
	MainActivity context;
	
	
	@Background
	void traverse(MandelbrotSurfaceView surfaceView){
		if(surfaceView.isActive()){
			Canvas c = null;
			
			try{
				synchronized (this) {
					surfaceView.setDonePainting(false);
					c = surfaceView.lock();
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
					Paint paint = new Paint();
					for(int y=0,yc=0;y<height;y+=pixelSize,yc++)
						for(int x=0,xc=0;x<width;x+=pixelSize,xc++){
							
							int color = scapeTime(xc-wc/2,yc-hc/2,wc,hc,paint);
							c.drawRect(new Rect(x,y,x+pixelSize,y+pixelSize),paint);

							//float color = scapeTime(xc-wc/2, yc-hc/2, wc, hc);
							textPaint.setColor(Color.WHITE);
							c.drawText(/*""+(xc-wc/2)+","+(yc-hc/2)+*/""+color,x,y+13,textPaint);
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


	private void checkers(Paint paint, int x, int y, int width, int height) {
		int count = (x+width/2)+(y+height/2)*width;
		paint.setColor((count%2==0)? Color.RED : Color.BLUE);
	}
	
	private int scapeTime(int xp, int yp, int width, int height, Paint paint){
		float x0=(2*((float)xp)/((float)width-1))*1.75f-0.75f;
		float y0=2*((float)yp)/((float)height-1);
		
		float x = 0;
		float y = 0;

		int iteration = 0;
		int max_iteration = 1000;

		while ( x*x + y*y < 2*2 & iteration < max_iteration ){
			float xtemp = x*x - y*y + x0;
			
			y = 2*x*y + y0;

			x = xtemp;

			iteration++;
		}

		//hslToRgb(0.5f,1f,0.5f,paint);

		//if (iteration>4) iteration =4;
		hslToRgb(((float)iteration-1)/23f,1f,0.5f,paint);

		return iteration;
	}
	

    private float hue2rgb(float p, float q, float t){
        if(t < 0) t += 1;
        if(t > 1) t -= 1;
        if(t < 1f/6f) return p + (q - p) * 6 * t;
        if(t < 1f/2f) return q;
        if(t < 2f/3f) return p + (q - p) * (2f/3f - t) * 6;
        return p;
    }

	private void hslToRgb(float h, float s, float l, Paint paint){
	    float r, g, b;
	    
	    if(s == 0){
	        r = g = b = l; // achromatic
	    }else{

	        float q = l < 0.5f ? l * (1f + s) : l + s - l * s;
	        float p = 2 * l - q;
	        r = hue2rgb(p, q, h + 1f/3f);
	        g = hue2rgb(p, q, h);
	        b = hue2rgb(p, q, h - 1f/3f);
	    }


	    
	    //paint.setARGB(255, (int) r*255, (int) g*255, (int) b*255);
	    //paint.setARGB(255, 0,0,0);
	    Log.d(PaintWorker.class.getName(),""+(int) r*255+","+(int) g*255+","+(int) b*255);
	    paint.setColor(Color.argb(255, (int) r*255, (int) g*255, (int) b*255));
	}
	
	
	
}
