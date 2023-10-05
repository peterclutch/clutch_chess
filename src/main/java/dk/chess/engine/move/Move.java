package dk.chess.engine.move;

public record Move(Coordinate from, Coordinate to) {

    public static Move of(int fromRank, int fromFile, int toRank, int toFile) {
        return new Move(new Coordinate(fromRank, fromFile), new Coordinate(toRank, toFile));
    }

}