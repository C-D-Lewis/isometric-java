package core;

import isometric.Isometric;
import isometric.Vec3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Port of Isometric Pebble frame buffer library to Java Canvas
 * @author Chris Lewis
 */
public class Main {
	
	private static BufferedImage testImage;
	
	public static void main(String[] args) {
		try {
			testImage = ImageIO.read(new File("res/test.png"));
		} catch(Exception e) {
			System.err.println("To see test image, place in ./res/test.png");
		}
		
		new MainWindow() {
			
			@Override
			public void program(Graphics2D g2d) {
				// Black background
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, Build.WINDOW_SIZE.width, Build.WINDOW_SIZE.height);
				
				if(testImage != null) {
					Isometric.fillTexturedRect(g2d, new Vec3(0, 0, 0), testImage);
				}
				
				Isometric.drawRect(g2d, new Vec3(100, 100, 100), new Dimension(100, 100), Color.BLUE);
				Isometric.fillRect(g2d, new Vec3(50, 50, 50), new Dimension(50, 50), Color.RED);
				
				Isometric.fillBox(g2d, new Vec3(150, 150, 150), new Dimension(25, 25), 25, Color.YELLOW);
				Isometric.drawBox(g2d, new Vec3(150, 150, 150), new Dimension(25, 25), 25, Color.BLACK);
			}
			
		};
	}

}
