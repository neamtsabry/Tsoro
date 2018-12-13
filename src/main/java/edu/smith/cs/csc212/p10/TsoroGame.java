package edu.smith.cs.csc212.p10;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import me.jjfoley.gfx.GFX;
import me.jjfoley.gfx.IntPoint;
import me.jjfoley.gfx.TextBox;

public class TsoroGame extends GFX {

	public TsoroGame() {
		this.setupGame();
	}

	public static int p1Tokens = 0;
	public static int p2Tokens = 0;

	public Map<TsoroCell, List<TsoroCell>> neighbors = new HashMap<>();

	TState state = TState.HomePage;
	boolean showInfo = false;
	List<TsoroCell> grid = new ArrayList<>();

	TextBox startMessage = new TextBox("TSORO");
	TextBox message = new TextBox("Blah? Blah.");
	TextBox tokMssg = new TextBox("Why don't they have eyes?!");
	TextBox tok2Mssg = new TextBox("But man was not made for defeat.");

	// Custom icon for JOptionPane.showInputDialog
	ImageIcon icon = new ImageIcon("Capture.png");

	// Prompt the user to enter their name
	String name1 = (String) JOptionPane.showInputDialog(null, "Enter Player1's name:", "Tsoro",
			JOptionPane.QUESTION_MESSAGE, icon, null, "");

	String name2 = (String) JOptionPane.showInputDialog(null, "Enter Player2's name:", "Tsoro",
			JOptionPane.QUESTION_MESSAGE, icon, null, "");

	// Capitalize the first alphabet of the user's name
	String nameCap1 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
	String nameCap2 = name2.substring(0, 1).toUpperCase() + name2.substring(1);

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

	public boolean tokensFinished() {
		if (p1Tokens == 3 && p2Tokens == 3) {
			return true;
		}
		return false;
	}

	public boolean playerWins(TMark player) {
//		p2Tokens-=1;
		List<TsoroCell> midRow = Arrays.asList(this.grid.get(1), this.grid.get(2), this.grid.get(3));
		if (allMarked(midRow, player)) {
			return true;
		}
		List<TsoroCell> botRow = Arrays.asList(this.grid.get(4), this.grid.get(5), this.grid.get(6));
		if (allMarked(botRow, player)) {
			return true;
		}

		List<TsoroCell> leftRightD = Arrays.asList(this.grid.get(0), this.grid.get(1), this.grid.get(4));
		if (allMarked(leftRightD, player)) {

			return true;
		}
		List<TsoroCell> rightLeftD = Arrays.asList(this.grid.get(0), this.grid.get(3), this.grid.get(6));
		if (allMarked(rightLeftD, player)) {
			return true;
		}
		List<TsoroCell> verticalD = Arrays.asList(this.grid.get(0), this.grid.get(2), this.grid.get(5));
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
		String name;

		public TsoroCell(String name, int x, int y, int w, int h) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.tri = new Line2D.Double(x, y, w, h);
			this.area = new Ellipse2D.Double(x, y, w, h);
			this.mouseHover = false;
			this.mouseHover2 = false;
			this.symbol = TMark.Empty;
			this.display = new TextBox("_");
			this.p2token = new Token(new Color(0, 0, 150));
			this.p1token = new Token(new Color(150, 0, 0));
		}

		public String toString() {
			return name;
		}

		public int hashCode() {
			return name.hashCode();
		}

		public boolean equals(Object other) {
			if (other instanceof TsoroCell) {
				TsoroCell place = (TsoroCell) other;
				return this.name.equals(place.name);
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
				g.setColor(new Color(222, 184, 135)); // brlywood
			}
			g.fill(this.area);

			switch (this.symbol) {
			case Empty:
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
		List<TsoroCell> neighborsA = new ArrayList<>();
		List<TsoroCell> neighborsB = new ArrayList<>();
		List<TsoroCell> neighborsC = new ArrayList<>();
		List<TsoroCell> neighborsD = new ArrayList<>();
		List<TsoroCell> neighborsE = new ArrayList<>();
		List<TsoroCell> neighborsF = new ArrayList<>();
		List<TsoroCell> neighborsG = new ArrayList<>();

		p1Tokens = 0;
		p2Tokens = 0;

		this.state = TState.Player1Turn;
		this.grid.clear();

		int size = this.getWidth() / 11;

		TsoroCell A = new TsoroCell("A", 5 * size, 1 * size, size - 2, size - 2);
		this.grid.add(A);
		neighborsB.add(A);
		neighborsC.add(A);
		neighborsD.add(A);

		TsoroCell B = new TsoroCell("B", 3 * size, 3 * size, size - 2, size - 2);
		this.grid.add(B);
		neighborsA.add(B);
		neighborsC.add(B);
		neighborsE.add(B);
		neighborsF.add(B);

		TsoroCell C = new TsoroCell("C", 5 * size, 3 * size, size - 2, size - 2);
		this.grid.add(C);
		neighborsA.add(C);
		neighborsF.add(C);
		neighborsB.add(C);
		neighborsD.add(C);

		TsoroCell D = new TsoroCell("D", 7 * size, 3 * size, size - 2, size - 2);
		this.grid.add(D);
		neighborsA.add(D);
		neighborsG.add(D);
		neighborsC.add(D);
		neighborsF.add(D);

		TsoroCell E = new TsoroCell("E", 1 * size, 5 * size, size - 2, size - 2);

		this.grid.add(E);
		neighborsB.add(E);
		neighborsF.add(E);

		TsoroCell F = new TsoroCell("F", 5 * size, 5 * size, size - 2, size - 2);
		this.grid.add(F);
		neighborsC.add(F);
		neighborsE.add(F);
		neighborsG.add(F);
		neighborsB.add(F);
		neighborsD.add(F);

		TsoroCell G = new TsoroCell("G", 9 * size, 5 * size, size - 2, size - 2);
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

	public void restart() {
		this.grid.clear();
		this.setupGame();
		this.state = TState.HomePage;
	}

	public void endGame() {
		this.stop();
	}

	@Override
	public void update(double time) {
		// look for click?
		if (this.state == TState.HomePage) {
			return;
		}

		IntPoint mouse = this.getMouseLocation();
		IntPoint click = this.processClick();

		if (this.tokensFinished()) {
			if (!this.hasAnyMoves()) {
				for (TsoroCell c : this.getAllCells()) {
					c.mouseHover = false;
					c.mouseHover2 = false;
				}
				this.state = TState.OutOfMoves;
			} else {
				adjSpace(click);
				click = null;
			}
		} else {
			if (this.state.isPlaying()) {
				for (TsoroCell cell : this.getAllCells()) {
					cell.mouseHover = cell.contains(mouse);
					if (cell.inPlay() && cell.contains(click)) {
						// More intelligence needed:
						if (this.state == TState.Player1Turn) {
							System.out.println("P1: " + cell.name);
							p1Tokens += 1;
							cell.symbol = TMark.Player1;
							this.state = TState.Player2Turn;
						} else if (this.state == TState.Player2Turn) {
							System.out.println("P2: " + cell.name);
							p2Tokens += 1;
							cell.symbol = TMark.Player2;
							this.state = TState.Player1Turn;
						} else if (this.state == TState.OutOfMoves) {
							this.state = TState.Player1Turn;
						}
					}
				}
			}
		}

		if (click != null && startButton.contains(click)) {
			System.out.println("restart");
			restart();
			return;
		}

		else if (click != null && quitButton.contains(click)) {
			System.out.println("quit");
			endGame();
			this.stop();
		}

		if (click != null && (this.state == TState.Player1Win || this.state == TState.Player2Win
				|| this.state == TState.OutOfMoves)) {
			this.setupGame();
		}

		if (this.playerWins(TMark.Player1)) {
			this.state = TState.Player1Win;
		} else if (this.playerWins(TMark.Player2)) {
			this.state = TState.Player2Win;
		}

		this.tokMssg.setString(nameCap1 + "'s red tokens:" + p1Tokens);
		this.tok2Mssg.setString(nameCap2 + "'s blue tokens:" + p2Tokens);

		// Set message based on state!
		switch (this.state) {
		case Player1Turn:
			this.message.setString(nameCap1 + "'s Turn");
			break;
		case Player2Turn:
			this.message.setString(nameCap2 + "'s Turn");
			break;
		case Player1Win:
			this.message.setString(nameCap1 + " Has Won!!");
			break;
		case Player2Win:
			this.message.setString(nameCap2 + " Has Won!!");
		case OutOfMoves:
			this.message.setString("Out of moves!");
			break;
		default:
			break;
		}
	}

	public static TsoroCell findEmpty(List<TsoroCell> neighbors) {
		for (TsoroCell n : neighbors) {
			if (n.symbol == TMark.Empty) {
				return n;
			}
		}
		return null;
	}

	public boolean hasAnyMoves() {
		// keep playing if we haven't used all of our tokens
		if (!this.tokensFinished()) {
			return true;
		}

		// moves available if you can swap:
		if (this.state == TState.Player1Turn || this.state == TState.Player2Turn) {
			TMark marker = this.state.getMarkForTurn();
			for (TsoroCell cell : this.getAllCells()) {
				if (cell.symbol == marker) {
					List<TsoroCell> adjacents = this.neighbors.get(cell);
					TsoroCell bestNeighbor = findEmpty(adjacents);
					if (bestNeighbor != null) {
						return true;
					}
				}
			}
		}

		// could not find any legal swaps.
		return false;
	}

	public void adjSpace(IntPoint click) {
		IntPoint mouse = this.getMouseLocation();

		for (TsoroCell cell : this.getAllCells()) {
			cell.mouseHover2 = false;
		}

		for (TsoroCell cell : this.getAllCells()) {
			cell.mouseHover = cell.contains(mouse);
			List<TsoroCell> adjacents = this.neighbors.get(cell);
			TsoroCell bestNeighbor = findEmpty(adjacents);

			if (cell.mouseHover) {
				for (TsoroCell n : adjacents) {
					n.mouseHover2 = true;
				}
			}

			if (cell.symbol != TMark.Empty && cell.contains(click)) {
				System.out.println("Cell: " + cell + " " + adjacents);
				if (bestNeighbor == null) {
					return;
				}

				System.out.println("swap(" + cell + ", " + bestNeighbor + ")");

				if (cell.equals(bestNeighbor)) {
					System.err.println("This should never happen!");
					return;
				}

				if (this.state == TState.Player1Turn) {
					if (cell.symbol == TMark.Player1) {
						cell.symbol = TMark.Empty;
						bestNeighbor.symbol = TMark.Player1;
						this.state = TState.Player2Turn;
					}
				} else if (this.state == TState.Player2Turn) {
					if (cell.symbol == TMark.Player2) {
						cell.symbol = TMark.Empty;
						bestNeighbor.symbol = TMark.Player2;
						this.state = TState.Player1Turn;
					}
				}
				return;
			}
		}
	}

	Rectangle2D startButton = new Rectangle2D.Double(350, 380, 140, 30);
	Rectangle2D quitButton = new Rectangle2D.Double(400, 450, 90, 15);

	@Override
	public void draw(Graphics2D g) {
		if (this.state == TState.HomePage) {
			drawHomePage(g);
			return;
		}

		g.setColor(Color.black);
		g.fillOval(50, 280, 80, 20);
		g.setColor(Color.black);
		g.fillOval(50, 250, 100, 20);
		g.setColor(new Color(255, 235, 205)); // blanched almond
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(6));

		// https://stackoverflow.com/questions/29447994/how-do-i-draw-a-triangle-in-java
		g.drawPolygon(new int[] { this.getWidth() - 70, 60, this.getWidth() / 2 }, new int[] { 250, 250, 65 }, 3);
		g.drawLine((this.getWidth() - 5) / 2, 60, (this.getWidth() - 5) / 2, 250);
		g.drawLine(150, 160, 350, 160);
		g.drawLine(150, 160, (this.getWidth() - 5) / 2, 250);
		g.drawLine(350, 160, (this.getWidth() - 5) / 2, 250);

		for (TsoroCell cell : this.getAllCells()) {
			cell.draw(g);
		}

		Rectangle2D centerText = new Rectangle2D.Double(10, this.getHeight() * 3 / 4, this.getWidth() - 20,
				this.getHeight() / 4 - 10);

		g.setPaint(new Color(0, 128, 128)); // nlue-greem
		g.fill(centerText);

		this.message.setFontSize(30.0);
		this.message.setColor(Color.black);
		this.message.centerInside(centerText);
		this.message.draw(g);

		Rectangle2D centerText2 = new Rectangle2D.Double(20, this.getHeight() * 3 / 4, (this.getWidth() - 20) / 3,
				(this.getHeight() / 4 - 10) / 4);

		this.tokMssg.setFontSize(20.0);
		this.tokMssg.setColor(Color.black);
		this.tokMssg.centerInside(centerText2);
		this.tokMssg.draw(g);

		Rectangle2D centerText3 = new Rectangle2D.Double(150, this.getHeight() * 3 / 4, this.getWidth(),
				(this.getHeight() / 4 - 10) / 4);

		this.tok2Mssg.setFontSize(20.0);
		this.tok2Mssg.setColor(Color.black);
		this.tok2Mssg.centerInside(centerText3);
		this.tok2Mssg.draw(g);

		Rectangle2D startButton = new Rectangle2D.Double(350, 380, 140, 30);

		g.setPaint(new Color(150, 0, 0));// Color.red);// new Color(0, 128, 128)); // nlue-greem
		g.fill(startButton);

		TextBox nameInput = new TextBox("Restart");

		nameInput.setFontSize(28.0);
		nameInput.setColor(Color.black);
		nameInput.centerInside(startButton);
		nameInput.draw(g);

		Rectangle2D quitButton = new Rectangle2D.Double(400, 450, 90, 15);

		g.setPaint(new Color(150, 0, 0));// Color.red);// new Color(0, 128, 128)); // nlue-greem
		g.fill(quitButton);

		TextBox quit = new TextBox("Quit");

		quit.setFontSize(18.0);
		quit.setColor(Color.black);
		quit.centerInside(quitButton);
		quit.draw(g);

		IntPoint click = this.processClick();

		if (click != null && startButton.contains(click)) {
			System.out.println("quit");
			this.state = TState.HomePage;
			TsoroGame app = new TsoroGame();
			app.start();
			return;
		}

		else if (click != null && quitButton.contains(click)) {
			this.stop();
		}
	}

	public void drawHomePage(Graphics2D g) {
		TextBox startMessage = new TextBox("TSORO");

		g.setColor(new Color(255, 235, 205)); // blanched almond
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(6));

		Rectangle2D centerText = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight() - 200);

		g.setPaint(new Color(255, 235, 205));
		g.fill(centerText);
		g.setColor(new Color(0, 0, 150));
		g.setStroke(new BasicStroke(10));

		g.fillOval(50, 242, 100, 50);
		g.fillOval(50, 250, 100, 50);

		g.setColor(new Color(0, 0, 150));
		g.setStroke(new BasicStroke(10));

		g.fillOval(150, 220, 100, 50);
		g.fillOval(150, 230, 100, 50);

		g.setColor(new Color(150, 0, 0));
		g.setStroke(new BasicStroke(10));

		g.fillOval(100, 280, 100, 50);
		g.fillOval(100, 270, 100, 50);

		startMessage.setFontSize(100.0);
		startMessage.setFont(new Font("Algerian", Font.BOLD, 120));
		startMessage.setColor(Color.black);
		startMessage.centerInside(centerText);
		startMessage.draw(g);

		Rectangle2D centerText1 = new Rectangle2D.Double(10, this.getHeight() * 3 / 4, this.getWidth() - 20,
				this.getHeight() / 4 - 10);

		g.setPaint(new Color(0, 128, 128)); // nlue-greem
		g.fill(centerText1);

		TextBox welcome = new TextBox("Welcome Players!");

		welcome.setFontSize(30.0);
		welcome.setColor(Color.black);
		welcome.centerInside(centerText1);
		welcome.draw(g);

		Rectangle2D startButton = new Rectangle2D.Double(350, 380, 140, 30);

		g.setPaint(new Color(150, 0, 0));// Color.red);// new Color(0, 128, 128)); // nlue-greem
		g.fill(startButton);

		TextBox nameInput = new TextBox("Start");

		nameInput.setFontSize(28.0);
		nameInput.setColor(Color.black);
		nameInput.centerInside(startButton);
		nameInput.draw(g);

		Rectangle2D quitButton = new Rectangle2D.Double(400, 450, 90, 15);

		g.setPaint(new Color(150, 0, 0));// Color.red);// new Color(0, 128, 128)); // nlue-greem
		g.fill(quitButton);

		TextBox quit = new TextBox("Quit");

		quit.setFontSize(18.0);
		quit.setColor(Color.black);
		quit.centerInside(quitButton);
		quit.draw(g);

		Rectangle2D infoButton = new Rectangle2D.Double(380, 420, 110, 20);

		g.setPaint(new Color(150, 0, 0));// Color.red);// new Color(0, 128, 128)); // nlue-greem
		g.fill(infoButton);

		TextBox info = new TextBox("Info");

		info.setFontSize(24.0);
		info.setColor(Color.black);
		info.centerInside(infoButton);
		info.draw(g);

		if (this.showInfo) {
			Rectangle2D box = new Rectangle2D.Double(0, 375, 500, 115);
			g.setPaint(new Color(255, 235, 205)); // nlue-greem
			g.fill(box);
			
			Rectangle2D bound = new Rectangle2D.Double(0, 345, 450, 115);
			g.setPaint(new Color(255, 235, 205)); // nlue-greem
			g.fill(bound);

			Rectangle2D bound2 = new Rectangle2D.Double(0, 360, 450, 115);
			g.setPaint(new Color(255, 235, 205)); // nlue-greem
			g.fill(bound2);

			Rectangle2D bound3 = new Rectangle2D.Double(0, 375, 450, 115);
			g.setPaint(new Color(255, 235, 205)); // nlue-greem
			g.fill(bound3);

			Rectangle2D bound4 = new Rectangle2D.Double(0, 390, 450, 115);
			g.setPaint(new Color(255, 235, 205)); // nlue-greem
			g.fill(bound4);

			Rectangle2D outer = new Rectangle2D.Double(10, this.getHeight() * 3 / 4, this.getWidth() - 20,
					this.getHeight() / 4 - 10);
			g.setPaint(new Color(0, 128, 128)); // nlue-greem
			g.fill(outer);

			TextBox instructions = new TextBox("Take turns to place tokens on available spaces on the board ");
			instructions.setFontSize(15.0);
			instructions.setColor(Color.black);
			instructions.centerInside(bound);
			instructions.draw(g);

			TextBox instructions2 = new TextBox(" Once a player makes a match of three, the game ends.");
			instructions2.setFontSize(15.0);
			instructions2.setColor(Color.black);
			instructions2.centerInside(bound2);
			instructions2.draw(g);

			TextBox instructions3 = new TextBox("When tokens finished, move them around into available spaces. \n");
			instructions3.setFontSize(15.0);
			instructions3.setColor(Color.black);
			instructions3.centerInside(bound3);
			instructions3.draw(g);

			TextBox instructions4 = new TextBox("Game ends when match is made or player is out of moves!");
			instructions4.setFontSize(15.0);
			instructions4.setColor(Color.black);
			instructions4.centerInside(bound4);
			instructions4.draw(g);

			Rectangle2D backButton = new Rectangle2D.Double(435, 380, 55, 30);
			g.setPaint(Color.red);// new Color(0, 128, 128)); // nlue-greem
			g.fill(backButton);

			TextBox back = new TextBox("Back");
			back.setFontSize(22.0);
			back.setColor(Color.black);
			back.centerInside(backButton);
			back.draw(g);

			IntPoint click2 = this.processClick();
			if (click2 != null && startButton.contains(click2)) {
				this.showInfo = false;
			}
			return;

		}

		IntPoint click = this.processClick();

		if (click != null && startButton.contains(click)) {
			this.state = TState.Player1Turn;
			return;
		} else if (click != null && quitButton.contains(click)) {
			endGame();
		} else if (click != null && infoButton.contains(click)) {
			this.showInfo = true;
		}
	}

	public static void main(String[] args) {
		TsoroGame app = new TsoroGame();
		app.start();

	}

}