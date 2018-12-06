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
	public List<List<TicTacToeCell>> neighbors0 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors1 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors2 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors3 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors4 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors5 = new ArrayList<>();

	public List<List<TicTacToeCell>> neighbors6 = new ArrayList<>();
//public List<List<List<TicTacToeCell>>> neighbors=new ArrayList<>();
	public Map<List<TicTacToeCell>, List<List<TicTacToeCell>>> neighbors = new HashMap<List<TicTacToeCell>, List<List<TicTacToeCell>>>();

	TState state = TState.Player1Turn;
	List<List<TicTacToeCell>> grid = new ArrayList<>();
	TextBox message = new TextBox("Hello World!");

	public List<TicTacToeCell> getAllCells() {
		List<TicTacToeCell> flatList = new ArrayList<>();
		for (List<TicTacToeCell> row : grid) {
			flatList.addAll(row);
		}
		return flatList;

	}

	public boolean allMarked(List<TicTacToeCell> row, TMark marker) {
		for (TicTacToeCell cell : row) {
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
		List<TicTacToeCell> midRow = Arrays.asList(this.grid.get(1).get(1), this.grid.get(2).get(2),
				this.grid.get(3).get(3));
		if (allMarked(midRow, TMark.Player1)) {
			return true;
		}
		List<TicTacToeCell> botRow = Arrays.asList(this.grid.get(4).get(4), this.grid.get(5).get(5),
				this.grid.get(6).get(6));
		if (allMarked(botRow, TMark.Player1)) {
			return true;
		}

		List<TicTacToeCell> leftRightD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(1).get(1),
				this.grid.get(4).get(4));
		if (allMarked(leftRightD, TMark.Player1)) {
			return true;
		}
		List<TicTacToeCell> rightLeftD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(3).get(3),
				this.grid.get(6).get(6));
		if (allMarked(rightLeftD, TMark.Player1)) {
			return true;
		}
		List<TicTacToeCell> verticalD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(2).get(2),
				this.grid.get(5).get(5));
		if (allMarked(verticalD, TMark.Player1)) {
			return true;
		}

		return false;
	}

	public static boolean tokensFinished() {
		// tokenCount-=1;
		System.out.println("p1 has" + p1Tokens);
		System.out.println("p2 has" + p2Tokens);

		if (p1Tokens + p2Tokens == 0) {
			return true;
		}
		return false;
	}

	public boolean player2Wins() {
//		p2Tokens-=1;
		List<TicTacToeCell> midRow = Arrays.asList(this.grid.get(1).get(1), this.grid.get(2).get(2),
				this.grid.get(3).get(3));
		if (allMarked(midRow, TMark.Player2)) {
			return true;
		}
		List<TicTacToeCell> botRow = Arrays.asList(this.grid.get(4).get(4), this.grid.get(5).get(5),
				this.grid.get(6).get(6));
		if (allMarked(botRow, TMark.Player2)) {
			return true;
		}

		List<TicTacToeCell> leftRightD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(1).get(1),
				this.grid.get(4).get(4));
		if (allMarked(leftRightD, TMark.Player2)) {
			return true;
		}
		List<TicTacToeCell> rightLeftD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(3).get(3),
				this.grid.get(6).get(6));
		if (allMarked(rightLeftD, TMark.Player2)) {
			return true;
		}
		List<TicTacToeCell> verticalD = Arrays.asList(this.grid.get(0).get(0), this.grid.get(2).get(2),
				this.grid.get(5).get(5));
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

	static class TicTacToeCell {
		Rectangle2D area;
		TMark symbol;
		boolean mouseHover;
		TextBox display;
		Shape p1Circle;

		public TicTacToeCell(int x, int y, int w, int h) {
			this.area = new Rectangle2D.Double(x, y, w, h);
			this.mouseHover = false;
			this.symbol = TMark.Empty;
			this.display = new TextBox("X");
//			this.p1Circle= new Ellipse();
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

			if (this.symbol == TMark.Empty && mouseHover) {
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
//		for (int x = 0; x < 8; x++) {
		List<TicTacToeCell> row = new ArrayList<>();
		List<TicTacToeCell> temp = new ArrayList<>();
		// Grid 0
		row.add(new TicTacToeCell(5 * size, 1 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(5 * size, 1 * size, size - 2, size - 2));

		this.grid.add(row);
		neighbors1.add(temp);
		neighbors2.add(temp);
		neighbors3.add(temp);
		temp.clear();
		// grid 1
		row.add(new TicTacToeCell(3 * size, 3 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(3 * size, 3 * size, size - 2, size - 2));
		this.grid.add(row);
		neighbors0.add(temp);
		neighbors2.add(temp);
		neighbors4.add(temp);
		temp.clear();
		// grid 2
		row.add(new TicTacToeCell(5 * size, 3 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(5 * size, 3 * size, size - 2, size - 2));
		this.grid.add(row);
		neighbors0.add(temp);
		neighbors2.add(temp);
		neighbors6.add(temp);
		temp.clear();
		// grid 3
		row.add(new TicTacToeCell(7 * size, 3 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(7 * size, 3 * size, size - 2, size - 2));
		this.grid.add(temp);
		neighbors1.add(temp);
		neighbors5.add(temp);
		temp.clear();
		// grid 4
		row.add(new TicTacToeCell(1 * size, 5 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(1 * size, 5 * size, size - 2, size - 2));
		this.grid.add(row);
		neighbors1.add(temp);
		neighbors5.add(temp);
		temp.clear();
		// grid 5
		row.add(new TicTacToeCell(5 * size, 5 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(5 * size, 5 * size, size - 2, size - 2));
		this.grid.add(row);
		neighbors2.add(temp);
		neighbors6.add(temp);
		neighbors4.add(temp);
		temp.clear();

		// grid 6
		row.add(new TicTacToeCell(9 * size, 5 * size, size - 2, size - 2));
		temp.add(new TicTacToeCell(9 * size, 5 * size, size - 2, size - 2));
		this.grid.add(row);
		neighbors3.add(temp);
		neighbors5.add(temp);
		temp.clear();

		neighbors.put(this.grid.get(1), neighbors0);
	}

	@Override
	public void update(double time) {
		IntPoint mouse = this.getMouseLocation();
		IntPoint click = this.processClick();

		boolean stateChanged = false;
		if (this.state.isPlaying()) {
			for (TicTacToeCell cell : this.getAllCells()) {
				cell.mouseHover = cell.contains(mouse);

				if (cell.inPlay() && cell.contains(click)) {
					// More intelligence needed:
					if (this.state == TState.Player1Turn) {
						p1Tokens -= 1;
						cell.symbol = TMark.Player1;
						this.state = TState.Player2Turn;
						stateChanged = true;
					} else {
						p2Tokens -= 1;
						cell.symbol = TMark.Player2;
						this.state = TState.Player1Turn;
						stateChanged = true;
					}
				}
			}
		} else {
			if (click != null) {
				this.state = TState.Player1Turn;
				this.grid.clear();
				this.setupGame();
			}
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
			break;
		default:
			break;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (TicTacToeCell cell : this.getAllCells()) {
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
