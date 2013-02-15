package com.example.babybrot.utils;

/** http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm 
 *  by Earl F. Glynn*/
public class WlColor {
	int Red,Green,Blue;
	
	public int getRed() {
		return Red;
	}
	
	public int getGreen() {
		return Green;
	}
	
	public int getBlue() {
		return Blue;
	}
	
	private double Gamma = 0.80f;
	private double IntensityMax = 255;
	
	private long Adjust(double Color, double Factor){
		if(Color == 0.0)
			return 0;     // Don't want 0^x = 1 for x <> 0
		else
			return Math.round(IntensityMax * Math.pow(Color * Factor, Gamma));
	};
	
	public WlColor(double Wavelength){

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
		
		this.Red = (int) Adjust(Red, factor);
		this.Green = (int) Adjust(Green, factor);
		this.Blue = (int) Adjust(Blue, factor);
	}
}