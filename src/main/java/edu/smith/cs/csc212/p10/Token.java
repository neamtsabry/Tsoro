package edu.smith.cs.csc212.p10;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Token {
	Color color;
	
	public Token(Color color) {
		this.color = color;
	}
	
	
//	public void draw(Graphics2D g) {
//		
//		g.setColor(color);
//		Ellipse2D token = new Ellipse2D.Double(-.5, -.5, 1, 1);
//		g.fill(token);
//	}
	
	public static Shape draw(Graphics2D g, Color color) {
		// Draw the token.
		g.setColor(color);
		
		Shape circ = new Ellipse2D.Double(-.5, -.5, 1, 1);

		g.fill(circ);
		g.draw(circ);
		return circ;
	
	
	}
	
	

	
	
}




