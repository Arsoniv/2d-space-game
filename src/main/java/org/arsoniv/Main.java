package org.arsoniv;

import javax.swing.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		KeyHandler keyH = new KeyHandler();
		MouseListener mouseH = new MouseListener();

		GamePanel gp = new GamePanel(keyH, mouseH);

		System.out.println("Input your name: \n");

		Scanner scanner = new Scanner(System.in);

		gp.name = scanner.nextLine();

		JFrame window = new JFrame();
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		window.addKeyListener(keyH);
		window.addMouseListener(mouseH);
		window.addMouseMotionListener(mouseH);

		window.add(gp);

		gp.menu = 1;

		gp.startTickThread();
		gp.startRepaintThread();

		gp.update();
		gp.repaint();

		window.pack();
	}
}