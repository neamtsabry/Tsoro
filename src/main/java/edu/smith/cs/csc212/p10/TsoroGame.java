package edu.smith.cs.csc212.p10;



import java.awt.Color;

import java.awt.Graphics2D;

import java.awt.Shape;

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

//		tokenCount-=1;

//		System.out.println(tokenCount);

		return true;

	}



	public boolean player1Wins() {

//		p1Tokens-=1;

		List<TsoroCell> midRow = Arrays.asList(this.grid.get(1), this.grid.get(2),

				this.grid.get(3));

		if (allMarked(midRow, TMark.Player1)) {

			return true;

		}

		List<TsoroCell> botRow = Arrays.asList(this.grid.get(4), this.grid.get(5),

				this.grid.get(6));

		if (allMarked(botRow, TMark.Player1)) {

			return true;

		}



		List<TsoroCell> leftRightD = Arrays.asList(this.grid.get(0), this.grid.get(1),

				this.grid.get(4));

		if (allMarked(leftRightD, TMark.Player1)) {

			return true;

		}

		List<TsoroCell> rightLeftD = Arrays.asList(this.grid.get(0), this.grid.get(3),

				this.grid.get(6));

		if (allMarked(rightLeftD, TMark.Player1)) {

			return true;

		}

		List<TsoroCell> verticalD = Arrays.asList(this.grid.get(0), this.grid.get(2),

				this.grid.get(5));

		if (allMarked(verticalD, TMark.Player1)) {

			return true;

		}



		return false;

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



	public boolean player2Wins() {

//		p2Tokens-=1;

		List<TsoroCell> midRow = Arrays.asList(this.grid.get(1), this.grid.get(2),

				this.grid.get(3));

		if (allMarked(midRow, TMark.Player2)) {

			return true;

		}

		List<TsoroCell> botRow = Arrays.asList(this.grid.get(4), this.grid.get(5),

				this.grid.get(6));

		if (allMarked(botRow, TMark.Player2)) {

			return true;

		}



		List<TsoroCell> leftRightD = Arrays.asList(this.grid.get(0), this.grid.get(1),

				this.grid.get(4));

		if (allMarked(leftRightD, TMark.Player2)) {

			return true;

		}

		List<TsoroCell> rightLeftD = Arrays.asList(this.grid.get(0), this.grid.get(3),

				this.grid.get(6));

		if (allMarked(rightLeftD, TMark.Player2)) {

			return true;

		}

		List<TsoroCell> verticalD = Arrays.asList(this.grid.get(0), this.grid.get(2),

				this.grid.get(5));

		if (allMarked(verticalD, TMark.Player2)) {

			return true;

		}



		return false;

	}



	public boolean boardIsFull() {

		return tokensFinished();

//		for (TicTacToeCell cell : this.getAllCells()) {

//			if (cell.inPlay()) {

//				return false;

//			}

//		}

//		return true;

	}



	static class TsoroCell {

		Rectangle2D area;

		TMark symbol;

		boolean mouseHover;

		boolean mouseHover2;

		TextBox display;

		Shape p1Circle;



		int x;

		int y;



		public TsoroCell(int x, int y, int w, int h) {

			this.x = x;

			this.y = y;

			this.area = new Rectangle2D.Double(x, y, w, h);

			this.mouseHover = false;

			this.mouseHover2 = false;



			this.symbol = TMark.Empty;

			this.display = new TextBox("X");

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



			switch (this.symbol) {

			case Empty:

				this.display.setString("_");

				break;

			case Player1:

				this.display.setString("X");

				break;

			case Player2:

				this.display.setString("O");

				break;



			default:

				break;

			}



			if (mouseHover2) {

				g.setColor(Color.cyan);

			} else if (this.symbol == TMark.Empty && mouseHover) {

				g.setColor(Color.green);

			} else {

				g.setColor(Color.yellow);

			}

			g.fill(this.area);



			this.display.centerInside(this.area);

			this.display.setFontSize(70.0);

			this.display.setColor(Color.black);

			this.display.draw(g);



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

		neighbors.put(C, neighborsA);

		neighbors.put(D, neighborsA);

		neighbors.put(E, neighborsA);

		neighbors.put(F, neighborsA);

		neighbors.put(G, neighborsA);

	}



	@Override

	public void update(double time) {

		IntPoint mouse = this.getMouseLocation();

		IntPoint click = this.processClick();



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



//			if (click != null) {

//				this.state = TState.Player1Turn;

//				this.grid.clear();

//				this.setupGame();

//			}

		}



		if (stateChanged) {

			if (this.player1Wins()) {

				this.state = TState.Player1Win;

			} else if (this.player2Wins()) {

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

			adjSpace();

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



	public void adjSpace() {

		IntPoint mouse = this.getMouseLocation();

		IntPoint click = this.processClick();

		

		for (TsoroCell cell : this.getAllCells()) {

			cell.mouseHover2 = false;

		}



		for (TsoroCell cell : this.getAllCells()) {

			cell.mouseHover = cell.contains(mouse);

			List<TsoroCell> neighbors = this.neighbors.get(cell);

			if (cell.mouseHover) {

				for (TsoroCell n : neighbors) {

					n.mouseHover2 = true;

				}

			}



			if (!cell.inPlay() && cell.contains(click)) {

				System.out.println("Cell: "+cell);

				TMark usedToBe = cell.symbol;

				cell.symbol = TMark.Empty;

				TsoroCell bestNeighbor = findEmpty(neighbors);

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

		g.setColor(Color.white);

		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// g.fillOval(0, 0, this.getWidth(), this.getHeight());



		for (TsoroCell cell : this.getAllCells()) {

			cell.draw(g);

		}



		g.setColor(Color.black);



		Rectangle2D centerText = new Rectangle2D.Double(0, this.getHeight() * 3 / 4, this.getWidth(),

				this.getHeight() / 4);

		this.message.setFontSize(35.0);

		this.message.setColor(Color.black);

		this.message.centerInside(centerText);

		this.message.draw(g);

	}



	public static void main(String[] args) {

		TsoroGame app = new TsoroGame();

		app.start();

	}



}