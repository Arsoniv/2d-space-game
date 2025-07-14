package org.arsoniv;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player extends Entity{

	public int boostChargeSpeed = 5;
	public int boostCharge = 300;

	Util util = new Util();

	public Player() {
		// init the inherited entity values
		this.speed = 5;
		this.color = Color.blue;

		this.width = 80;
		this.height = 80;

		this.x = 20;
		this.y = 20;
	}

	public void handleMovement(KeyHandler keyH, int screenWidth, int screenHeight, ArrayList<Entity> entities) {
		xPrev = x;
		yPrev = y;

		float dx = 0;
		float dy = 0;

		if (keyH.w) dy -= 1;
		if (keyH.s) dy += 1;
		if (keyH.a) dx -= 1;
		if (keyH.d) dx += 1;

		float length = (float) Math.sqrt(dx * dx + dy * dy);
		if (length != 0) {
			dx /= length;
			dy /= length;
		}

		int oldX = (int) x;
		int oldY = (int) y;

		if (keyH.space && boostCharge > 499) {
			x += (int) (dx * speed * 65);
			y += (int) (dy * speed * 65);

			boostCharge = 0;
		} else {
			x += (int) (dx * speed);
			y += (int) (dy * speed);
		}

		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x > screenWidth - width) x = screenWidth - width;
		if (y > screenHeight - height) y = screenHeight - height;

		AtomicBoolean canMove = new AtomicBoolean(true);

		entities.forEach(entity -> {
			if (util.checkEntityIntersect(entity, this, false, false)) canMove.set(false);
		});

		if (!canMove.get()) {
			x = oldX;
			y = oldY;
		}

		keyH.space = false;
	}
}
