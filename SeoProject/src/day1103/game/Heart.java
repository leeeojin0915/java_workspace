package day1103.game;

import java.awt.Graphics2D;
import java.awt.Image;

public class Heart extends GameObject{

	public Heart(Image img, int x, int y, int width, int height, int velX, int velY) {
		super(img, x, y, width, height, velX, velY);
	}

	public void tick() {
		
	}

	public void render(Graphics2D g2) {
		g2.drawImage(img, x, y, null);
	}
	

}