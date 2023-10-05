package dk.chess.engine.board;

import dk.chess.engine.piece.Color;
import dk.chess.engine.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BoardFactoryTest {

    @Test
    @DisplayName("correct starting position")
    public void startPositionCorrect() {
        Board board = PositionConstant.START_POSITION;

        Assertions.assertAll(
                "assert all pieces position",
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{0,7}), board.pieces().get(Piece.WHITE_ROOK)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{1,6}), board.pieces().get(Piece.WHITE_KNIGHT)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{2,5}), board.pieces().get(Piece.WHITE_BISHOP)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{3}), board.pieces().get(Piece.WHITE_KING)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{4}), board.pieces().get(Piece.WHITE_QUEEN)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{8,9,10,11,12,13,14,15}), board.pieces().get(Piece.WHITE_PAWN)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{55,54,53,52,51,50,49,48}), board.pieces().get(Piece.BLACK_PAWN)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{63,56}), board.pieces().get(Piece.BLACK_ROOK)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{62,57}), board.pieces().get(Piece.BLACK_KNIGHT)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{61,58}), board.pieces().get(Piece.BLACK_BISHOP)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{59}), board.pieces().get(Piece.BLACK_KING)),
                () -> Assertions.assertEquals(PositionUtil.fromCoordinates(new int[]{60}), board.pieces().get(Piece.BLACK_QUEEN)),
                () -> Assertions.assertEquals(Color.WHITE, board.colorTurn())
        );
    }

    @ParameterizedTest()
    @MethodSource("illegalPositions")
    @DisplayName("illegal positions")
    public void illegalPosition(String position) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> BoardFactory.fromString(position, Color.WHITE));
    }

    @ParameterizedTest()
    @MethodSource("legalPositions")
    @DisplayName("legal positions")
    public void legalPostion(String position) {
        Assertions.assertNotNull(BoardFactory.fromString(position, Color.WHITE));
    }

    private static Stream<Arguments> illegalPositions() {
        return Stream.of(
                Arguments.of(PositionConstant.START_POSITION_STRING + "Q"),
                Arguments.of("   " + PositionConstant.START_POSITION_STRING + "   "),
                Arguments.of(PositionConstant.START_POSITION_STRING.substring(1)),
                Arguments.of("QPRKNqprkn"),
                Arguments.of("")
        );
    }

    private static Stream<Arguments> legalPositions() {
        return Stream.of(
                Arguments.of("""
            00000000
            00000000
            rnbqkbnr
            pppppppp
            00000000
            00000000
            PPPPPPPP
            RNBQKBNR
            """),
                Arguments.of("rnbqkbnrpppppppp00000000000000000000000000000000PPPPPPPPRNBQKBNR"),
                Arguments.of("rNbQkBnRpPpPpPpP0Q0q0Q0q0QQ00qq00000000000000000PPPPPPPPRNBQKBNR"),
                Arguments.of(new StringBuilder(PositionConstant.START_POSITION_STRING).reverse().toString()),
                Arguments.of("""
            00
            0
            000
            0000000000
            rn
            bqkbnr
            pppppppp
            00
            0
            0
            0000
            00000000PPPPPPPPRNBQKBNR
            """)
        );
    }

}
