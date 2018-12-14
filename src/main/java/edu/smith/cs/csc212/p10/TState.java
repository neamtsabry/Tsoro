package edu.smith.cs.csc212.p10;

public enum TState {
	
	HomePage, 
	Player1Turn, 
	Player2Turn, 
	OutOfMoves, 
	Player1Win,
	Player2Win 
	;
	
	/**
	 * Check if it's player 1's or 2's turn 
	 * @return -- true if it's either player's turn
	 */
	public boolean isPlaying() {
		return this == Player1Turn || this == Player2Turn;
	}
	
	/**
	 * @return -- player1 or player2 mark (token) on cell 
	 */
	public TMark getMarkForTurn() {
		if (this == Player1Turn) {
			return TMark.Player1;
		} else if (this == Player2Turn) {
			return TMark.Player2;
		}
		throw new IllegalStateException("Must be player1 or 2 turn to get a mark!");
	}
}
