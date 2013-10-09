package com.mar21.au.graphics;

public class Utils {

	public static double[] rotate(double X, double Y, double hX, double hY, double A) {
		double distance = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
		//double angle = Math.sin((Math.asin(Y / distance) - A));
		double angle = Math.tan(Math.atan2(Y, X) - A);
		double outputY = distance * Math.sin(Math.atan(angle));
		double outputX = distance * Math.cos(Math.atan(angle));
		return new double[] { outputX, outputY };
	}

}
