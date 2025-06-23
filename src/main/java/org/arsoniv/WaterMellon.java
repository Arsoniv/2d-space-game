package org.arsoniv;

import java.awt.*;
import java.util.ArrayList;

public class WaterMellon extends Entity{

	Util util = new Util();

	public boolean used = false;

	public WaterMellon(int screenWidth, int screenHeight) {
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
				if (util.checkEntityIntersect(this, entity, false, false)) {
					entity.speed = entity.speed * 2;
					entity.damage = entity.damage * 2;
					used = true;
				}
			});
		}
	}
}
