package edu.smith.cs.csc212.p10;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

public class TsoroGame extends GFX {

	public TsoroGame() {
		this.setupGame();
	}

	
	public static int p1Tokens = 0;
	public static int p2Tokens = 0;
	public static int p1score = 0;
	public static int p2score = 0;
	
	public List<TsoroCell> neighborsA = new ArrayList<>();
	public List<TsoroCell> neighborsB = new ArrayList<>();
	public List<TsoroCell> neighborsC = new ArrayList<>();
	public List<TsoroCell> neighborsD = new ArrayList<>();
	public List<TsoroCell> neighborsE = new ArrayList<>();
	public List<TsoroCell> neighborsF = new ArrayList<>();
	public List<TsoroCell> neighborsG = new ArrayList<>();

	public Map<TsoroCell, List<TsoroCell>> neighbors = new HashMap<>();

	TState state = TState.Player1Turn;
	List<TsoroCell> grid = new ArrayList<>();
	TextBox message = new TextBox("Hello World!");
	
	public List<TsoroCell> getAllCells() {
		return grid;
	}

	public boolean allMarked(List<TsoroCell> row, TMark marker) {
		for (TsoroCell cell : row) {
			if (cell.symbol == marker) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean tokensFinished() {
		// tokenCount-=1;
		System.out.println("p1 has" + p1Tokens);
		System.out.println("p2 has" + p2Tokens);

		if (p1Tokens + p2Tokens >= 6) {
			p1Tokens = 0;
			p2Tokens = 0;
			return true;
		}
		return false;
	}

	public boolean playerWins(TMark player) {
//		p2Tokens-=1;
		List<TsoroCell> midRow = Arrays.asList(this.grid.get(1), this.grid.get(2),
				this.grid.get(3));
		if (allMarked(midRow, player)) {
			return true;
		}
		List<TsoroCell> botRow = Arrays.asList(this.grid.get(4), this.grid.get(5),
				this.grid.get(6));
		if (allMarked(botRow, player)) {
			return true;
		}

		List<TsoroCell> leftRightD = Arrays.asList(this.grid.get(0), this.grid.get(1),
				this.grid.get(4));
		if (allMarked(leftRightD, player)) {
			return true;
		}
		List<TsoroCell> rightLeftD = Arrays.asList(this.grid.get(0), this.grid.get(3),
				this.grid.get(6));
		if (allMarked(rightLeftD, player)) {
			return true;
		}
		List<TsoroCell> verticalD = Arrays.asList(this.grid.get(0), this.grid.get(2),
				this.grid.get(5));
		if (allMarked(verticalD, player)) {
			return true;
		}

		return false;
	}

	public boolean boardIsFull() {
		return tokensFinished();
	}

	static class TsoroCell {
		Ellipse2D area;
		TMark symbol;
		boolean mouseHover;
		boolean mouseHover2;
		TextBox display;
		Token p1token;
		Token p2token;
		int x;
		int y;
		Line2D tri;

		public TsoroCell(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.tri = new Line2D.Double(x, y, w, h);
			this.area = new Ellipse2D.Double(x, y, w, h);
			this.mouseHover = false;
			this.mouseHover2 = false;
			this.symbol = TMark.Empty;
			this.display = new TextBox("_");
			this.p2token = new Token (Color.blue);
			this.p1token = new Token (Color.red);
		}
		

		public int hashCode() {
			return Integer.hashCode(x) + Integer.hashCode(y);
		}

		public boolean equals(Object other) {
			if (other instanceof TsoroCell) {
				TsoroCell place = (TsoroCell) other;
				return this.x == place.x && this.y == place.y;
			}
			return false;
		}

		public boolean inPlay() {
			return symbol == TMark.Empty;
		}

		public void draw(Graphics2D g) {
			if (mouseHover2) {
				g.setColor(Color.cyan);
			} else if (this.symbol == TMark.Empty && mouseHover) {
				g.setColor(Color.green);
			} else {
				g.setColor(new Color(222,184,135)); // brlywood
			}
			g.fill(this.area);
			
			switch (this.symbol) {
			case Empty:
				this.display.setString(" ");
				break;
			case Player1:
				this.p1token.draw(g, area);
				break;
			case Player2:
				this.p2token.draw(g, area);
				break;
			default:
				break;
			}
		}

		public boolean contains(IntPoint mouse) {
			if (mouse == null) {
				return false;
			}
			return this.area.contains(mouse);
		}
	}

	public void setupGame() {
		int size = this.getWidth() / 11;
		
		TsoroCell A = new TsoroCell(5 * size, 1 * size, size - 2, size - 2);
		this.grid.add(A);
		neighborsB.add(A);
		neighborsC.add(A);
		neighborsD.add(A);
		
		TsoroCell B = new TsoroCell(3 * size, 3 * size, size - 2, size - 2);
		this.grid.add(B);
		neighborsA.add(B);
		neighborsC.add(B);
		neighborsE.add(B);
		
		TsoroCell C = new TsoroCell(5 * size, 3 * size, size - 2, size - 2);
		this.grid.add(C);
		neighborsA.add(C);
		neighborsF.add(C);
		neighborsB.add(C);
		neighborsD.add(C);
		
		TsoroCell D = new TsoroCell(7 * size, 3 * size, size - 2, size - 2);
		this.grid.add(D);
		neighborsA.add(D);
		neighborsG.add(D);
		neighborsC.add(D);

		TsoroCell E = new TsoroCell(1 * size, 5 * size, size - 2, size - 2);
		this.grid.add(E);
		neighborsB.add(E);
		neighborsF.add(E);
		
		TsoroCell F = new TsoroCell(5 * size, 5 * size, size - 2, size - 2);
		this.grid.add(F);
		neighborsC.add(F);
		neighborsE.add(F);
		neighborsG.add(F);

		TsoroCell G = new TsoroCell(9 * size, 5 * size, size - 2, size - 2);
		this.grid.add(G);
		neighborsD.add(G);
		neighborsF.add(G);
		
		neighbors.put(A, neighborsA);
		neighbors.put(B, neighborsB);
		neighbors.put(C, neighborsC);
		neighbors.put(D, neighborsD);
		neighbors.put(E, neighborsE);
		neighbors.put(F, neighborsF);
		neighbors.put(G, neighborsG);
	}

	@Override
	public void update(double time) {
		IntPoint mouse = this.getMouseLocation();
		IntPoint click = this.processClick();

		hoverNeighbors();

		boolean stateChanged = false;
		if (this.state.isPlaying()) {
			for (TsoroCell cell : this.getAllCells()) {
				cell.mouseHover = cell.contains(mouse);
				if (cell.inPlay() && cell.contains(click)) {
					// More intelligence needed:
					if (this.state == TState.Player1Turn) {
						p1Tokens += 1;
						cell.symbol = TMark.Player1;
						this.state = TState.Player2Turn;
						stateChanged = true;
					} else if (this.state == TState.Player2Turn) {
						p2Tokens += 1;
						cell.symbol = TMark.Player2;
						this.state = TState.Player1Turn;
						stateChanged = true;
					} else {
						cell.symbol = TMark.Empty;
						this.state = TState.MoveTokAround;
						stateChanged = true;
					}
				}
			}
		} else {
			if (click != null && ( (this.state == TState.Player1Win) || (this.state == TState.Player2Win) ) ) {
				this.state = TState.Player1Turn;
				this.grid.clear();
				this.setupGame();
			}
		}

		if (stateChanged) {
			if (this.playerWins(TMark.Player1)) {
				p1Tokens = 0;
				p2Tokens = 0;
				p1score += 1;
				this.state = TState.Player1Win;
			} else if (this.playerWins(TMark.Player2)) {
				p1Tokens = 0;
				p2Tokens = 0;
				p2score += 1;
				this.state = TState.Player2Win;
			} else if (this.boardIsFull()) {
				this.state = TState.MoveTokAround;
			}
		}

		// Set message based on state!
		switch (this.state) {
		case Player1Turn:
			this.message.setString("Player 1 Turn");
			break;
		case Player2Turn:
			this.message.setString("Player 2 Turn");
			break;
		case Player1Win:
			this.message.setString("Player 1 Has Won");
			break;
		case Player2Win:
			this.message.setString("Player 2 Has Won");
			break;
		case MoveTokAround:
			this.message.setString("Match pieces by moving them!");
			adjSpace(click);
			break;
		case OutOfMoves:
			this.message.setString("Out of moves!");
			break;
		default:
			break;
		}
	}
	
	public static TsoroCell findEmpty(List<TsoroCell> neighbors) {
		for(TsoroCell n : neighbors) {
			if(n.symbol == TMark.Empty) {
				return n;
			}
		}
		return null;
	}

	public void hoverNeighbors() {
		IntPoint mouse = this.getMouseLocation();

		for (TsoroCell cell : this.getAllCells()) {
			cell.mouseHover2 = false;
		}
		
		for (TsoroCell cell : this.getAllCells()) {
			cell.mouseHover = cell.contains(mouse);
			if (cell.mouseHover) {
				List<TsoroCell> adjacents = this.neighbors.get(cell);
				for (TsoroCell n : adjacents) {
					n.mouseHover2 = true;
				}
			}
		}
	}

	public void adjSpace(IntPoint click) {
		for (TsoroCell cell : this.getAllCells()) {
			System.out.println("Cell: "+!cell.inPlay()+", "+cell.contains(click));
			if(!cell.inPlay() && cell.contains(click)) {
				List<TsoroCell> adjacents = this.neighbors.get(cell);
				//if (cell.inPlay() && cell.contains(click)) {
				System.out.println("Cell: "+cell);
				TMark usedToBe = cell.symbol;
				cell.symbol = TMark.Empty;
				TsoroCell bestNeighbor = findEmpty(adjacents);

				if (bestNeighbor == null) {
					continue;
				}
				
				if (usedToBe == TMark.Player2) {
						// move cell to the n spot 
						cell.symbol = TMark.Player2;
						this.state = TState.Player1Turn;
				} else if (usedToBe == TMark.Player1){
						cell.symbol = TMark.Player1;
						this.state = TState.Player2Turn;
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(255,235,205)); // blanched almond
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.black);
		g.setStroke(new BasicStroke(6));
		
		// https://stackoverflow.com/questions/29447994/how-do-i-draw-a-triangle-in-java
		g.drawPolygon(new int[] {this.getWidth()-70, 60, this.getWidth()/2}, new int[] {250, 250, 65}, 3);
		g.drawLine((this.getWidth()-5)/2, 60, (this.getWidth()-5)/2, 250);
		g.drawLine(150, 160, 350, 160);

		for (TsoroCell cell : this.getAllCells()) {
			cell.draw(g);
		}
		
		Rectangle2D centerText = new Rectangle2D.Double(10, this.getHeight() * 3 / 4, this.getWidth()-20,
				this.getHeight() / 4-10); 
		g.setPaint(new Color(0, 128, 128)); // nlue-greem
		g.fill(centerText);
		
		this.message.setFontSize(30.0);
		this.message.setColor(Color.black);
		this.message.centerInside(centerText);
		this.message.draw(g);
	}

	public static void main(String[] args) {
		TsoroGame app = new TsoroGame();
		app.start();
	}

}
