package edu.smith.cs.csc212.p10;

public enum TState {

	Player1Turn, // 0
	Player2Turn, // 1
	HomePage,
	// Tie, // 2
	OutOfMoves, MoveTokAround, Player1Win, // 3
	Player2Win // 4
	;

	public boolean isPlaying() {
		return this == Player1Turn || this == Player2Turn || this==OutOfMoves ;
	}
	
	public TMark getMarkForTurn() {
		if (this == Player1Turn) {
			return TMark.Player1;
		} else if (this == Player2Turn) {
			return TMark.Player2;
		}
		throw new IllegalStateException("Must be player1 or 2 turn to get a mark!");
	}
}
