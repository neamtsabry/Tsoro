package edu.smith.cs.csc212.p10;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Token {
	Color color;
	
	public Token(Color color) {
		this.color = color;
	}

	
	public Shape draw(Graphics2D g, Rectangle2D area) {
		// Draw the token.
		
		g.setColor(color);
		
		Shape circ = new Ellipse2D.Double(area.getX(), area.getY(), area.getWidth(), area.getHeight());

		g.fill(circ);
		g.draw(circ);
		return circ;
	
	
	}
	
	

	
	
}




