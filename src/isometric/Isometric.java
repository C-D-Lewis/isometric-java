package isometric;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Isometric {
	
	private static Point projectionOffset = new Point(0, 0);
	private static boolean enabled = true;
	
	public static boolean isEnabled() {
		return enabled;
	}
	
	public static void setEnabled(boolean b) {
		enabled = b;
	}
	
	public static void setProjectionOffset(Point p) {
		projectionOffset = new Point(p);
	}
	
	public static Point project(int x, int y, int z) {
		Point result = new Point();
		if(enabled) {
			result.x = projectionOffset.x + (x - y);
			result.y = projectionOffset.y + ((x / 2) + (y / 2)) - z;
		} else {
			result.x = x;
			result.y = y;
		}
		return result;
	}

	public static void drawRect(Graphics2D g2d, Vec3 origin, Dimension size, Color color) {
		g2d.setColor(color);
		
		// Top
		Point start = project(origin.x, origin.y, origin.z);
		Point finish = project(origin.x + size.width, origin.y, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
		
		// Right
		start = project(origin.x + size.width, origin.y, origin.z);
		finish = project(origin.x + size.width, origin.y + size.height, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
		
		// Bottom
		start = project(origin.x, origin.y + size.height, origin.z);
		finish = project(origin.x + size.width, origin.y + size.height, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);

		// Left
		start = project(origin.x, origin.y, origin.z);
		finish = project(origin.x, origin.y + size.height, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
	}
	
	public static void fillRect(Graphics2D g2d, Vec3 origin, Dimension size, Color color) {
		g2d.setColor(color);
		
		for(int i = 0; i < 2; i++) {
			for(int y = origin.y; y < origin.y + size.height; y++) {
				Point p1 = project(origin.x, y, origin.z);
				Point p2 = project(origin.x + size.width, y, origin.z);
				g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			origin.z--; // Draw twice to fill
		}
	}
	
	public static void fillBox(Graphics2D g2d, Vec3 origin, Dimension size, int zHeight, Color color) {
		g2d.setColor(color);
		
		// Draw only front for speed
		int z = 0;
		for(z = origin.z; z < origin.z + zHeight; z++) {
			// Right
		    Point start = project(origin.x + size.width, origin.y, z);
		    Point finish = project(origin.x + size.width, origin.y + size.height, z);
		    g2d.drawLine(start.x, start.y, finish.x, finish.y);

		    // Bottom
		    start = project(origin.x, origin.y + size.height, z);
		    finish = project(origin.x + size.width, origin.y + size.height, z);
		    g2d.drawLine(start.x, start.y, finish.x, finish.y);
		}
		
		fillRect(g2d, new Vec3(origin.x, origin.y, (zHeight > 0) ? z - 1 : z), size, color);
	}
	
	public static void drawBox(Graphics2D g2d, Vec3 origin, Dimension size, int zHeight, Color color) {
		g2d.setColor(color);
		
		// Bottom
		Point start = project(origin.x + size.width, origin.y, origin.z);
		Point finish = project(origin.x + size.width, origin.y + size.height, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
		start = project(origin.x, origin.y + size.height, origin.z);
		finish = project(origin.x + size.width, origin.y + size.height, origin.z);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);

		// Top
		drawRect(g2d, new Vec3(origin.x, origin.y, origin.z + zHeight), size, color);

		// Sides
		start = project(origin.x, origin.y + size.height, origin.z); 
		finish = project(origin.x, origin.y + size.height, origin.z + zHeight);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);

		start = project(origin.x + size.width, origin.y + size.height, origin.z); 
		finish = project(origin.x + size.width, origin.y + size.height, origin.z + zHeight);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);

		start = project(origin.x + size.width, origin.y, origin.z); 
		finish = project(origin.x + size.width, origin.y, origin.z + zHeight);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
	}
	
	public static void fillTexturedRect(Graphics2D g2d, Vec3 origin, BufferedImage image) {
		// Get pixel data
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int z = origin.z; z < origin.z + 2; z++) {
			for(int y = origin.y; y < origin.y + height; y++) {
				for(int x = origin.x; x < origin.x + width; x++) {
					Point p = project(x, y, z);
					
					g2d.setColor(new Color(image.getRGB(x, y)));
					g2d.drawRect(p.x, p.y, 1, 1);
				}
			}
		}
	}

	public static void drawAxes(Graphics2D g2d) {
		// X
		Point start = project(0, 0, 0);
		Point finish = project(50, 0, 0);
		g2d.setColor(Color.RED);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
		
		// Y
		start = project(0, 0, 0);
		finish = project(0, 50, 0);
		g2d.setColor(Color.BLUE);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
		
		// Z
		start = project(0, 0, 0);
		finish = project(0, 0, 50);
		g2d.setColor(Color.GREEN);
		g2d.drawLine(start.x, start.y, finish.x, finish.y);
	}

}
