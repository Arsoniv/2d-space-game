package org.arsoniv;

import java.awt.*;
import java.util.ArrayList;

public class MedKit extends Entity{

	Util util = new Util();

	public boolean used = false;

	public int screenWidth;
	public int screenHeight;

	public MedKit(int screenWidthI, int screenHeightI) {
		// init the inherited entity values
		screenWidth = screenWidthI;
		screenHeight = screenHeightI;
		x = (int) ((Math.random() * (screenWidth - 200) + 100));
		y = (int) ((Math.random() * (screenHeight - 200) + 100));
		xPrev = x;
		yPrev = y;
		width = 50;
		height = 50;
		color = Color.red;
	}

	public void checkIntersect(ArrayList<Entity> entities) {
		if (!used) {
			entities.forEach(entity -> {
				if (util.checkEntityIntersect(this, entity, false, true)) {
					entity.health += 40;
					x = (int) ((Math.random() * (screenWidth - 200) + 100));
					y = (int) ((Math.random() * (screenHeight - 200) + 100));
				}
			});
		}
	}
}
