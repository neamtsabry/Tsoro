package edu.smith.cs.csc212.p10;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Token {
	double center;
	double diameter;
	Color color;
	
	public Token(Color color) {
		this.center=-.2;
		this.diameter=.5;
		this.color = color;
	}

	
	public Shape draw(Graphics2D g) {
		// Draw the token.
		
		g.setColor(color);
		
		Shape circ = new Ellipse2D.Double(center, center, diameter, diameter);

		g.fill(circ);
		g.draw(circ);
		return circ;
	
	
	}
	
	

	
	
}




