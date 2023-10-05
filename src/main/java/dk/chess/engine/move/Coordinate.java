package dk.chess.engine.move;

public record Coordinate(int rank, int file) {

    public long toBitboard() {
        return 1L<<(8*(rank))<<file;
    }

}
