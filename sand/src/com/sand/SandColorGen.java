package com.sand;
//--------------------------------------------------------------------------------
//
// 		SandColorGen Class
//

public class SandColorGen {
	private int[] sandColor = { 0xffeed5, 0xfff1d8, 0xd1b79e, 0xb49a81,
			0xcab097, 0xc3a990, 0xbba188, 0xfffae1, 0xf8dec5, 0xc2a88f,
			0xbba188, 0xb59b82, 0xd4bca0, 0xd9c1a5, 0xd5bda1, 0xe0c8ac,
			0xe1c8aa, 0xd0b799, 0xc2a789, 0xbea385, 0xcfb294, 0xccaf91 };

	// --------------------------------------------------------------------------------
	public SandColorGen() {
		initSand();
	}

	// --------------------------------------------------------------------------------
	private void initSand() {
		/*
		 * sandColor = new int[5]; sandColor[0] = Color.BLACK; sandColor[1] =
		 * Color.WHITE; sandColor[2] = Color.YELLOW; sandColor[3] = Color.GREEN;
		 * sandColor[4] = Color.BLUE;
		 */

		for (int ii = 0; ii < 22; ii++) {
			sandColor[ii] = sandColor[ii] | 0xff000000;
		}

	}

	// --------------------------------------------------------------------------------
	public int choseSand() {
		return (sandColor[(int) (Math.random() * 100 % 22)]);
	}
	

	
}