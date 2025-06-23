package org.arsoniv;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Zombie extends Entity{

	public Util util = new Util();

	public Zombie(int xIn, int yIn) {
		color = Color.black;
		health = 55;

		x = xIn;
		y = yIn;

		reach = 15;
		damage = 2;
		speed = 3.5f;
		width = 35;
		height = 35;
	}

	public void moveTowardsPlayer(int playerX, int playerY, ArrayList<Entity> entities) {

		int oldX = (int) x;
		int oldY = (int) y;

		int dx = (int) (playerX - x);
		int dy = (int) (playerY - y);

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				x += speed;
			} else if (dx < 0) {
				x -= speed;
			}
		} else {
			if (dy > 0) {
				y += speed;
			} else if (dy < 0) {
				y -= speed;
			}
		}

		AtomicBoolean canMove = new AtomicBoolean(true);

		entities.forEach(entity -> {
			if (util.checkEntityIntersect(entity, this, false, false)) {
				canMove.set(false);
			}
		});

		if (!canMove.get()) {
			x = oldX;
			y = oldY;
		}else {
			xPrev = oldX;
			yPrev = oldY;
		}
	}
}
