package org.arsoniv;

import java.awt.*;

public class Entity {

	public float x = 0;
	public float y = 0;
	public float xPrev = 0;
	public float yPrev = 0;
	public float speed = 1;
	public Color color = Color.white;
	public int health = 100;
	public int width = 10;
	public int height = 10;
	public int reach = 0;
	public int damage = 2;

	public Point getInterpolatedPosition(float alpha) {
		// alpha represents how far between ticks the frame is.
		if (alpha > 1.0f) alpha = 1.0f;

		//use alpha to position the entity between ticks
		int xInterpolated = (int) (xPrev + (x - xPrev) * alpha);
		int yInterpolated = (int) (yPrev + (y - yPrev) * alpha);

		return new Point(xInterpolated, yInterpolated);
	}
}
