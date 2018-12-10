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

	
	public Shape draw(Graphics2D g, Ellipse2D area) {
		g.setColor(color);
		
		Shape circ = new Ellipse2D.Double(area.getX()+10, area.getY()+10, area.getWidth()-20, area.getHeight()-20);

		g.fill(circ);
		g.draw(circ);
		return circ;
	}
	
}




