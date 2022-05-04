import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
/*
 * Author: Ben Platt
 * Date: 04/24/22
 * This program creates a recursive triangle drawing. It squeezes more and more triangles 
 * inside a base triangle that covers half of the frame and is upside down relative to the others.
 */
public class RecursiveTriangles extends Canvas{
	// need a static variable for n for user input inside the paint method to work properly
	static int n;
	
	public static void main(String[] args) {
		// get user input and set the static n
		RecursiveTriangles r = new RecursiveTriangles();
		n = r.getN();
		
		// Set up the frame (window) and canvas
		JFrame frame = new JFrame("My Drawing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new RecursiveTriangles();
		canvas.setSize(400, 400);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	public void paint(Graphics g) {
		RecursiveTriangles r = new RecursiveTriangles();
		
		// This first bit is drawing the outer triangle that is where all the other triangles are squeezed into
		int[] xi = {0, 200, 400};
		int[] yi = {0, 400, 0};
		Color[] colors = {null, Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, 
				Color.MAGENTA, Color.PINK, Color.YELLOW};
		// sets color based on value of n and then draws it
		if(n <= 7) {
			g.setColor(colors[n]);
		}
		else {
			g.setColor(colors[7]);
		}
		g.fillPolygon(xi, yi, 3);
		// calls the recursive function for the rest of n values
		r.recursion(g, colors, xi, yi, n-1, 1);
		}
	
	public void recursion(Graphics g, Color[] colors, int[] x, int[] y, int n, int passes) {
		// The first call of the function draws one triangle in the center of the outer triangle
		if(passes == 1) {
			int[] xnew = {x[2]/4, x[2]/2, x[2]/2 + x[2]/4};
			int[] ynew = {y[1]/2, y[0], y[1]/2};
			if(n <= 7) {
				g.setColor(colors[n]);
			}
			else {
				g.setColor(colors[7]);
			}
			g.fillPolygon(xnew, ynew, 3);
			recursion(g, colors, xnew, ynew, n-1, passes+1);
		}
		else {
			if(n > 0) {
				/*
				 * three triangles that need to be drawn for each value of n > 2 This bit draws
				 * the first triangle (top left from the n + 1 triangle) The coordinates are
				 * just done by some simple vector math and averaging the coordinates of the n +
				 * 1 triangle
				 * 
				 * NOTE: the 0'th indices are the bottom left points of the triangle
				 * the 1st indices are the middle top points of the triangle
				 * the 2nd indices are the bottom right points of the triangle
				 */
				int[] x1 = {x[0] - (x[1] - x[0])/2, x[0], (x[0] + x[1])/2};
				int[] y1 = {(y[0] + y[1])/2, y[1], (y[0] + y[1])/2};
				if(n <= 7) {
					g.setColor(colors[n]);
				}
				else {
					g.setColor(colors[7]);
				}
				g.fillPolygon(x1, y1, 3);
				// now makes a recursive call to the next level of triangles
				recursion(g, colors, x1, y1, n-1, passes+1);
				
				// This bit does the triangle that is drawn below the n + ! triangle and calls for the next level
				// once again just some simple vector math
				int[] x2 = {(x[0] + x[1])/2, x[1], (x[1] + x[2])/2};
				int[] y2 = {y[0] + (y[0] - y[1])/2, y[0], y[0] + (y[0] - y[1])/2};
				if(n <= 7) {
					g.setColor(colors[n]);
				}
				else {
					g.setColor(colors[7]);
				}
				g.fillPolygon(x2, y2, 3);
				recursion(g, colors, x2, y2, n-1, passes+1);
				
				// This bit does the triangle that is drawn to the top right of the n + 1 triangle, also calls the next level
				int[] x3 = {(x[1] + x[2])/2, x[2], x[2] + (x[1] - x[0])/2};
				int[] y3 = {(y[1] + y[2])/2, y[1], (y[1] + y[2])/2};
				if(n <= 7) {
					g.setColor(colors[n]);
				}
				else {
					g.setColor(colors[7]);
				}
				g.fillPolygon(x3, y3, 3);
				recursion(g, colors, x3, y3, n-1, passes+1);
			}
		}
	}
	// This method handles the user input
	public int getN() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("What value of n would you like? (1+)");
		int n = input.nextInt();
		input.nextLine();
		
		return n;
	}
}

