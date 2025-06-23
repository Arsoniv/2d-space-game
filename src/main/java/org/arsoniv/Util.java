package org.arsoniv;

public class Util {

	public boolean checkEntityIntersect(Entity e1, Entity e2, boolean shouldCountReach1, boolean shouldCountReach2) {
		int r1 = shouldCountReach1 ? e1.reach : 0;
		int r2 = shouldCountReach2 ? e2.reach : 0;

		int x1 = (int) (e1.x - r1);
		int y1 = (int) (e1.y - r1);
		int w1 = e1.width + r1 * 2;
		int h1 = e1.height + r1 * 2;

		int x2 = (int) (e2.x - r2);
		int y2 = (int) (e2.y - r2);
		int w2 = e2.width + r2 * 2;
		int h2 = e2.height + r2 * 2;

		return x1 < x2 + w2 &&
						x1 + w1 > x2 &&
						y1 < y2 + h2 &&
						y1 + h1 > y2;
	}
	public boolean checkIntersect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
	}
}
