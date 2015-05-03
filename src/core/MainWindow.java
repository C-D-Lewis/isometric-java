package core;

import isometric.Isometric;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class MainWindow {
	
	private static final boolean DEBUG = true;
	
	private JFrame window;
	private JPanel canvas;
	
	public abstract void program(Graphics2D g2d);
	
	@SuppressWarnings("serial")
	public MainWindow() {
		// Set origin to be window center
		Isometric.setProjectionOffset(new Point(Build.WINDOW_SIZE.width / 2, Build.WINDOW_SIZE.height / 2));
		
		// Set up window
		window = new JFrame("Isometric Test");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(Build.WINDOW_SIZE);
		
		// Setup canvas
		canvas = new JPanel() {
			
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				
				long start = System.nanoTime();
				program(g2d);
				if(DEBUG) {
					Isometric.drawAxes(g2d);
					long finish = System.nanoTime();
					System.out.println("Frame render time: " + (finish - start) / 1000000 + " ms");
				}
			}
			
		};
		canvas.setPreferredSize(Build.WINDOW_SIZE);
		window.add(canvas);
		
		window.setVisible(true);
		window.pack();
		
		canvas.repaint();
	}
	
}
