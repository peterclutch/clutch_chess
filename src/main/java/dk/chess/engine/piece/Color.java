package dk.chess.engine.piece;

public enum Color {

    WHITE,
    BLACK;

    public Color takeTurn() {
        return this == BLACK ? WHITE : BLACK;
    }

}
