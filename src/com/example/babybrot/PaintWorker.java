package com.example.babybrot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.UiThread;

@EBean
public class PaintWorker {

	@RootContext
	Babybrot context;
	
	final int MAX_ITERATION = 50; //For scape time algorithm

	@UiThread
	void incrementPBar(int delta){
		context.progressBar.incrementProgressBy(delta);
	}
	
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

					int total = height*width/100;
					int count=0;
					Paint paint = new Paint();
					for(int y=0,yc=0;y<height;y+=pixelSize,yc++)
						for(int x=0,xc=0;x<width;x+=pixelSize,xc++){
							
							int color = scapeTime(xc-wc/2,yc-hc/2,wc,hc,paint);
							c.drawRect(new Rect(x,y,x+pixelSize,y+pixelSize),paint);
							

							//float color = scapeTime(xc-wc/2, yc-hc/2, wc, hc);
							//textPaint.setColor(Color.WHITE);
							//c.drawText(/*""+(xc-wc/2)+","+(yc-hc/2)+*/""+color,x,y+13,textPaint);
							count++;
							if(count%total==0)
								incrementPBar(1);
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

		while ( x*x + y*y < 2*2 & iteration < MAX_ITERATION ){
			float xtemp = x*x - y*y + x0;
			
			y = 2*x*y + y0;

			x = xtemp;

			iteration++;
		}

		//hslToRgb(0.5f,1f,0.5f,paint);
		double wl;
		//if (iteration>4) iteration =4;
		if(iteration>=MAX_ITERATION)
			wl=781;
		else
			wl = (((double)iteration)/MAX_ITERATION)*(780-380)+380;
		//hslToRgb(((float)iteration-1)/23f,1f,0.5f,paint);
		int[] rgb = waveLengthToRGB(wl);
		paint.setColor(Color.argb(255, rgb[0], rgb[1], rgb[2]));

		return iteration;
	}
	

 
	
	static private double Gamma = 0.80;
	static private double IntensityMax = 255;
	
	/** Taken from Earl F. Glynn's web page:
	 * <a href="http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm">Spectra Lab Report</a>
	 * */
	public static int[] waveLengthToRGB(double Wavelength){
		double factor;
		double Red,Green,Blue;

		if((Wavelength >= 380) && (Wavelength<=439)){
			Red = -(Wavelength - 440) / (440 - 380);
			Green = 0.0;
			Blue = 1.0;
		}else if((Wavelength >= 440) && (Wavelength<=489)){
			Red = 0.0;
			Green = (Wavelength - 440) / (490 - 440);
			Blue = 1.0;
		}else if((Wavelength >= 490) && (Wavelength<=509)){
			Red = 0.0;
			Green = 1.0;
			Blue = -(Wavelength - 510) / (510 - 490);
		}else if((Wavelength >= 510) && (Wavelength<=579)){
			Red = (Wavelength - 510) / (580 - 510);
			Green = 1.0;
			Blue = 0.0;
		}else if((Wavelength >= 580) && (Wavelength<=644)){
			Red = 1.0;
			Green = -(Wavelength - 645) / (645 - 580);
			Blue = 0.0;
		}else if((Wavelength >= 645) && (Wavelength<=780)){
			Red = 1.0;
			Green = 0.0;
			Blue = 0.0;
		}else{
			Red = 0.0;
			Green = 0.0;
			Blue = 0.0;
		};

		// Let the intensity fall off near the vision limits
		
		if((Wavelength >= 380) && (Wavelength<=419)){
			factor = 0.3 + 0.7*(Wavelength - 380) / (420 - 380);
		}else if((Wavelength >= 420) && (Wavelength<=700)){
			factor = 1.0;
		}else if((Wavelength >= 701) && (Wavelength<=780)){
			factor = 0.3 + 0.7*(780 - Wavelength) / (780 - 700);
		}else{
			factor = 0.0;
		};
		
		int[] rgb = new int[3];
		rgb[0] = Red==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(Red * factor, Gamma));
		rgb[1] = Green==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(Green * factor, Gamma));
		rgb[2] = Blue==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(Blue * factor, Gamma));
		
		return rgb;
	}
}
