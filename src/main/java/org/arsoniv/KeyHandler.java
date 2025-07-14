package org.arsoniv;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {


	//boolean for each key
	public boolean w, a, s, d, esc, space = false;

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		//get keycode for the event
		final int key = keyEvent.getKeyCode();

		if (key == KeyEvent.VK_W) {
			w = true;
		}
		if (key == KeyEvent.VK_A) {
			a = true;
		}
		if (key == KeyEvent.VK_S) {
			s = true;
		}
		if (key == KeyEvent.VK_D) {
			d = true;
		}
		if (key == KeyEvent.VK_ESCAPE) {
			esc = true;
		}
		if (key == KeyEvent.VK_SPACE) {
			space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		//get keycode for the event
		final int key = keyEvent.getKeyCode();
		if (key == KeyEvent.VK_W) {
			w = false;
		}
		if (key == KeyEvent.VK_A) {
			a = false;
		}
		if (key == KeyEvent.VK_S) {
			s = false;
		}
		if (key == KeyEvent.VK_D) {
			d = false;
		}
	}
}
