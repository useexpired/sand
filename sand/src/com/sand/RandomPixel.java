package com.sand;

import java.util.Random;

import android.view.Display;


public class RandomPixel {

	char[][] arrRand;

	int nextX, nextY;
	int scrWidth, scrHeight, scrOrient;
	Random randR;

	//static final int X_MAX = 300;
	//static final int Y_MAX = 400;
	// ------------------------------------------------------	
	public RandomPixel(Display dd) {
		getScreenInfo(dd);
		initRandom();
	}
	// ------------------------------------------------------	
	public void getScreenInfo(Display display){

        /* Now we can retrieve all display-related infos */
        scrWidth = display.getWidth();
        scrHeight = display.getHeight();
        scrOrient = display.getOrientation();        
        
	}
	// ------------------------------------------------------
	public void initRandom() {

		arrRand = new char[scrWidth][scrHeight];
		randR = new Random();
		for (int ii = 0; ii < scrWidth; ii++)
			for (int jj = 0; jj < scrHeight; jj++)
				arrRand[ii][jj] = 'n'; // n= not shown,
										// y=already shown on screen

	}
	// ------------------------------------------------------
	public void nextRandom() {
		//
		// get random pixel located at nextX,nextY
		//
		int theX, theY;
		do {
			theX = randR.nextInt(scrWidth);
			theY = randR.nextInt(scrHeight);

			if (arrRand[theX][theY] == 'n') {
				nextX = theX;
				nextY = theY;
			}
		} while (arrRand[theX][theY] == 'y');
		arrRand[theX][theY] = 'y';
	}
}