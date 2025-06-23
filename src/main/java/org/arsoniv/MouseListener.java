package org.arsoniv;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

	public boolean newPress = false;
	public int xLast = 0;
	public int yLast = 0;
	public int xNow = 0;
	public int yNow = 0;
	public int xDrag = 0;
	public int yDrag = 0;

	public boolean mouseDown = false;

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		newPress = true;
		mouseDown = true;
		xLast = mouseEvent.getX();
		yLast = mouseEvent.getY();
		xDrag = mouseEvent.getX();
		yDrag = mouseEvent.getY();
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		xDrag = mouseEvent.getX();
		yDrag = mouseEvent.getY();
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		xNow = mouseEvent.getX();
		yNow = mouseEvent.getY();
	}
}
