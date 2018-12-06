package edu.smith.cs.csc212.p10;

public enum TState {

	Player1Turn, // 0
	Player2Turn, // 1
	// Tie, // 2
	OutOfMoves, MoveTokAround, Player1Win, // 3
	Player2Win // 4
	;

	public boolean isPlaying() {
		return this == Player1Turn || this == Player2Turn;
	}
}
