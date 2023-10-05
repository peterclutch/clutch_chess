package dk.chess.engine.board;

import dk.chess.engine.piece.Color;
import dk.chess.engine.piece.Piece;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardFactory {

    private BoardFactory() {}

    public static Board fromString(String board, Color colorTurn) {
        String boardClean = cleanBoardString(board);
        if (boardClean.length() != 64) {
            throw new IllegalArgumentException();
        }
        // empty pieces
        Map<Character, Long> pieces = EnumSet.allOf(Piece.class).stream().collect(Collectors.toMap(Piece::getSymbol, e -> 0L));
        // populate pieces
        IntStream.range(0, 64).forEach(i ->
            pieces.computeIfPresent(boardClean.charAt(i), (k, v) -> v | PositionUtil.fromCoordinates(new int[]{i}))
        );

        return new Board(
                pieces.entrySet().stream().collect(Collectors.toUnmodifiableMap(e -> Piece.getPiece(e.getKey()), Map.Entry::getValue)),
                colorTurn
        );
    }

    private static String cleanBoardString(String board) {
        return new StringBuilder(board.replace("\n", "")).reverse().toString();
    }
}
