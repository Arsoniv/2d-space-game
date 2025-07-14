package org.arsoniv;

import java.awt.*;
import java.util.ArrayList;

public class Slurp extends Entity{

	Util util = new Util();

	public boolean used = false;

	public int screenWidth;
	public int screenHeight;

	public Slurp(int screenWidthI, int screenHeightI) {
		// init the inherited entity values
		screenWidth = screenWidthI;
		screenHeight = screenHeightI;
		x = (int) ((Math.random() * (screenWidth - 200) + 100));
		y = (int) ((Math.random() * (screenHeight - 200) + 100));
		xPrev = x;
		yPrev = y;
		width = 40;
		height = 50;
		color = Color.pink;
	}

	public void checkIntersect(Player player) {
		if (!used) {
			if (util.checkEntityIntersect(this, player, false, true)) {
				player.boostCharge = 500;
				x = (int) ((Math.random() * (screenWidth - 200) + 100));
				y = (int) ((Math.random() * (screenHeight - 200) + 100));
			}
		}
	}
}
