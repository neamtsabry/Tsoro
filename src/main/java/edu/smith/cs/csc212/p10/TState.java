package edu.smith.cs.csc212.p10;

public enum TState {
	HomePage,//0
	Player1Turn, // 1
	Player2Turn, // 2
	// Tie, // 2
	OutOfMoves, 
	MoveTokAround, 
	Player1Win, // 3
	Player2Win // 4
	;

	public boolean isPlaying() {
		return this == Player1Turn || this == Player2Turn|| this == OutOfMoves || this == MoveTokAround;
	}
}
