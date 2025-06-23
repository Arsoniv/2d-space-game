package org.arsoniv;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel {

	public String name = "daddy";

	public int FPS = 60;
	public int TICK_RATE = 40;
	public float currentFPS = 0;
	public float currentTPS = 0;
	Thread repaintThread;
	Thread tickThread;
	KeyHandler keyH;
	MouseListener mouseH;
	public float lastTickTime = 0;
	public long tickInterval = 0;

	public int screenWidth = 1200;
	public int screenHeight = 600;

	public int ticksSurvived = 0;

	public int menu = 0;

	public Player player = new Player();
	public ArrayList<Zombie> zombies = new ArrayList<>();
	public Util util = new Util();
	public WaterMellon waterMellon = new WaterMellon(screenWidth ,screenHeight);
	public WaterMellon waterMellon2 = new WaterMellon(screenWidth ,screenHeight);
	public MedKit medKit = new MedKit(screenWidth, screenHeight);
	public Slurp slurp = new Slurp(screenWidth, screenHeight);

	public BufferedImage backGround;
	public BufferedImage playerIcon;
	public BufferedImage zombieImage;
	public BufferedImage waterMellonImage;
	public BufferedImage medkitImage;
	public BufferedImage slurpImage;

	public void reset() {
		zombies.clear();
		zombies.add(new Zombie(600, 200));
		zombies.add(new Zombie(200, 350));
		zombies.add(new Zombie(400, 200));
		zombies.add(new Zombie(1000, 350));

		player = new Player();

		waterMellon = new WaterMellon(screenWidth, screenHeight);
		waterMellon2 = new WaterMellon(screenWidth, screenHeight);
		medKit = new MedKit(screenWidth, screenHeight);
		slurp = new Slurp(screenWidth, screenHeight);

		menu = 0;
	}

	public GamePanel(KeyHandler keyHIn, MouseListener mouseHIn) {
		keyH = keyHIn;
		mouseH = mouseHIn;
		try {
			backGround = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images.png")));
			playerIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img.png")));
			zombieImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img_1.png")));
			waterMellonImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img_2.png")));
			medkitImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img_3.png")));
			slurpImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img_4.png")));
		} catch (IOException e) {
			System.out.println("image loading failed lol: "+e);
		}
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));

		zombies.add(new Zombie(600, 200));
		zombies.add(new Zombie(200, 350));
		zombies.add(new Zombie(400, 200));
		zombies.add(new Zombie(1000, 350));

		System.out.println(medKit.x+" "+medKit.y+" ");
		System.out.println(waterMellon.x+" "+waterMellon.y+" "+waterMellon.used);
		System.out.println(waterMellon2.x+" "+waterMellon2.y+" "+waterMellon2.util);
	}

	public void startRepaintThread() {
		repaintThread = new Thread(() -> {
			System.out.println("Repaint thread running");

			long lastTime = System.nanoTime();
			long drawInterval = 1_000_000_000 / FPS;
			long nextDraw = lastTime + drawInterval;

			while (repaintThread != null) {
				drawInterval = 1_000_000_000 / FPS;
				if (System.nanoTime() > nextDraw) {
					long now = System.nanoTime();
					currentFPS = (float) 1_000_000_000 / (now - lastTime);
					lastTime = now;
					nextDraw += drawInterval;
					repaint();
				}
			}
		});
		repaintThread.start();
	}
	public void startTickThread() {
		tickThread = new Thread(() -> {
			System.out.println("Tick thread running");

			long lastTime = System.nanoTime();
			tickInterval = 1_000_000_000L / TICK_RATE;
			long nextTick = lastTime + tickInterval;

			while (tickThread != null) {
				tickInterval = 1_000_000_000L / TICK_RATE;
				long now = System.nanoTime();
				if (now >= nextTick) {
					currentTPS = 1_000_000_000f / (now - lastTime);
					lastTime = now;
					lastTickTime = now;
					nextTick += tickInterval;
					update();
				}
			}
		});
		tickThread.start();
	}

	public void update() {

		if (menu == 0) {
			if (player.health <= 0) {
				menu = 2;
			}

			if (player.boostCharge < 500) {
				player.boostCharge += player.boostChargeSpeed;
			}

			zombies.forEach(zombie -> {
				ArrayList<Entity> entityArrayList = new ArrayList<>();
				for (Entity otherZombie : zombies) {
					if (otherZombie != zombie) {
						entityArrayList.add(otherZombie);
					}
				}
				entityArrayList.add(player);
				zombie.moveTowardsPlayer((int) player.x, (int) player.y, entityArrayList);
				if (util.checkEntityIntersect(zombie, player, true, false)) {
					player.health -= zombie.damage;
				}
			});

			ArrayList<Entity> zombieArrayList = new ArrayList<>(zombies);

			player.handleMovement(keyH, screenWidth, screenHeight, zombieArrayList);

			waterMellon.checkIntersect(zombieArrayList);
			waterMellon2.checkIntersect(zombieArrayList);

			slurp.checkIntersect(player);

			ArrayList<Entity> playerArrayList = new ArrayList<>();
			playerArrayList.add(player);

			medKit.checkIntersect(playerArrayList);

			ticksSurvived++;

			if (keyH.esc) {
				menu = 1;
				keyH.esc = false;
			}
		}else {
			if (keyH.esc) {
				menu = 0;
				keyH.esc = false;
			}
			if (menu == 1)  {
				if (mouseH.mouseDown) {
					if (mouseH.xNow > 40 && mouseH.xNow < 240 && mouseH.yNow > 90 && mouseH.yNow < 140) {
						int newFps = ((mouseH.xDrag - 40) / 5) * 5;

						if (newFps < 10) newFps = 10;

						FPS = newFps;
					}
					if (mouseH.xNow > 40 && mouseH.xNow < 240 && mouseH.yNow > 150 && mouseH.yNow < 200) {
						int newTickRate = Math.round(((float) (mouseH.xDrag - 40) / 5)/ 5.0f)*5;

						if (newTickRate < 5) newTickRate = 5;

						TICK_RATE = newTickRate;
					}
				}
			}
			if (menu == 2) {
				if (keyH.space) {
					keyH.space = false;
					reset();
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		final long now = System.nanoTime();

		float alpha = (now - lastTickTime) / tickInterval;

		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(backGround, 0, 0, screenWidth, screenHeight, null);

		if (!waterMellon.used) {
			g2d.drawImage(waterMellonImage, (int) waterMellon.x, (int) waterMellon.y, waterMellon.width, waterMellon.height, null);
		}
		if (!waterMellon2.used) {
			g2d.drawImage(waterMellonImage, (int) waterMellon2.x, (int) waterMellon2.y, waterMellon2.width, waterMellon2.height, null);
		}
		g2d.drawImage(medkitImage, (int) medKit.x, (int) medKit.y, medKit.width, medKit.height, null);

		g2d.drawImage(slurpImage, (int) slurp.x, (int) slurp.y, slurp.width, slurp.height, null);

		Point playerPoint = player.getInterpolatedPosition(alpha);

		g2d.drawImage(playerIcon, (int) playerPoint.getX(), (int) playerPoint.getY(), player.width, player.height, null);


		zombies.forEach(zombie -> {
			Point zombiePoint = zombie.getInterpolatedPosition(alpha);
			g2d.drawImage(zombieImage, (int) zombiePoint.getX(), (int) zombiePoint.getY(), zombie.width, zombie.height, null);
		});

		if (menu == 1) {
			g2d.setColor(Color.black);
			g2d.setFont(new Font("big", Font.BOLD, 40));
			g2d.drawString("Paused", 320, 50);

			g2d.setFont(new Font("small", Font.PLAIN, 12));

			//fps

			g2d.setColor(Color.black);
			g2d.fillRect(40, 90, 200, 50);
			g2d.setColor(Color.green);
			g2d.fillRect(40, 110, FPS, 30);

			g2d.setColor(Color.white);
			g2d.drawString("TPS: "+FPS, 45, 105);

			//tps

			g2d.setColor(Color.black);
			g2d.fillRect(40, 150, 200, 50);
			g2d.setColor(Color.red);
			g2d.fillRect(40, 170, TICK_RATE * 5, 30);

			g2d.setColor(Color.white);
			g2d.drawString("TPS: "+TICK_RATE, 45, 165);
		}

		if (menu == 2) {
			g2d.setColor(Color.red);
			g2d.setFont(new Font("big", Font.BOLD, 40));
			g2d.drawString("You Died!", 320, 50);
			g2d.setFont(new Font("small", Font.PLAIN, 12));
		}

		g2d.setColor(new Color(255, 0, 255));
		g2d.fillRect(0, screenHeight - 10, player.boostCharge/2, 10);

		g2d.setColor(Color.green);
		g2d.fillRect(270, screenHeight - 10, (int) (player.health*2.5), 10);

		g2d.setColor(Color.WHITE);
		g2d.drawString("FPS: "+(int)currentFPS, 10, 20);
		g2d.drawString("TPS: "+(int)currentTPS, 10, 40);
		g2d.drawString("NAME: "+name, 10, 60);
		g2d.drawString("Score: "+ticksSurvived/40, 10, 80);
	}
}
