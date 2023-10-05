package dk.chess.engine.move;

import dk.chess.engine.board.Board;
import dk.chess.engine.board.BoardFactory;
import dk.chess.engine.board.PositionConstant;
import dk.chess.engine.piece.Color;
import dk.chess.engine.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

public class PawnMoveUtilTest {

    private final static String NO_PAWNS = """
            rnbqkbnr
            00000000
            00000000
            00000000
            00000000
            00000000
            00000000
            RNBQKBNR
            """;

    private final static String ALL_PAWNS_ADVACNE_ONE = """
            rnbqkbnr
            00000000
            pppppppp
            00000000
            00000000
            PPPPPPPP
            00000000
            RNBQKBNR
            """;

    private final static String PAWN_CAPTURES = """
            rnbqkbnr
            00000000
            00000000
            000pp000
            000PP000
            00000000
            00000000
            RNBQKBNR
            """;

    private final static String ZERO_SEVEN_RANK_NON_CAPTURE = """
            rnbqkbnr
            00000000
            00000000
            p000000p
            P000000P
            00000000
            00000000
            RNBQKBNR
            """;

    private final static String PAWNS_BEHIND_PIECES = """
            pppppppp
            rnbqkbnr
            00000000
            00000000
            00000000
            00000000
            RNBQKBNR
            PPPPPPPP
            """;

    @Test
    public void noLegalMovesOnWrongTurn() {
        Set<Move> moves = MoveUtil.legalMoves(PositionConstant.START_POSITION, Piece.BLACK_PAWN);
        Assertions.assertTrue(moves.isEmpty());
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("legalPawnMoves")
    @DisplayName("legal pawn moves")
    public void legalPawnMove(Board board, Set<Move> expected, String description) {
        Assertions.assertEquals(expected, MoveUtil.legalMoves(board, board.colorTurn().equals(Color.WHITE) ? Piece.WHITE_PAWN : Piece.BLACK_PAWN));
    }

    private static Stream<Arguments> legalPawnMoves() {
        return Stream.of(
                Arguments.of(
                        PositionConstant.START_POSITION,
                        Set.of(
                                Move.of(6,0, 5, 0),
                                Move.of(6,0, 4, 0),
                                Move.of(6,1, 5, 1),
                                Move.of(6,1, 4, 1),
                                Move.of(6,2, 5, 2),
                                Move.of(6,2, 4, 2),
                                Move.of(6,3, 5, 3),
                                Move.of(6,3, 4, 3),
                                Move.of(6,4, 5, 4),
                                Move.of(6,4, 4, 4),
                                Move.of(6,5, 5, 5),
                                Move.of(6,5, 4, 5),
                                Move.of(6,6, 5, 6),
                                Move.of(6,6, 4, 6),
                                Move.of(6,7, 5, 7),
                                Move.of(6,7, 4, 7)),
                        "White: all legal moves from starting position"
                ),
                Arguments.of(BoardFactory.fromString(NO_PAWNS, Color.WHITE), Set.of(), "White: no pawns"),
                Arguments.of(BoardFactory.fromString(ALL_PAWNS_ADVACNE_ONE, Color.WHITE), Set.of(
                                Move.of(5,0, 4, 0),
                                Move.of(5,1, 4, 1),
                                Move.of(5,2, 4, 2),
                                Move.of(5,3, 4, 3),
                                Move.of(5,4, 4, 4),
                                Move.of(5,5, 4, 5),
                                Move.of(5,6, 4, 6),
                                Move.of(5,7, 4, 7)),
                        "White: all pawns can advance once"
                ),
                Arguments.of(BoardFactory.fromString(PAWN_CAPTURES, Color.WHITE), Set.of(
                                Move.of(4,3, 3, 4),
                                Move.of(4,4, 3, 3)),
                        "White: pawn captures and no advance through opponent"
                ),
                Arguments.of(
                        BoardFactory.fromString(ZERO_SEVEN_RANK_NON_CAPTURE, Color.WHITE),
                        Set.of(),
                        "White: can't capture left on 0 rank, right on 7 rank"
                ),
                Arguments.of(
                        BoardFactory.fromString(PAWNS_BEHIND_PIECES, Color.WHITE),
                        Set.of(),
                        "White: can't capture own pieces or move through own pieces"
                ),
                Arguments.of(
                        BoardFactory.fromString(PositionConstant.START_POSITION_STRING, Color.BLACK),
                        Set.of(
                                Move.of(1,0, 2, 0),
                                Move.of(1,0, 3, 0),
                                Move.of(1,1, 2, 1),
                                Move.of(1,1, 3, 1),
                                Move.of(1,2, 2, 2),
                                Move.of(1,2, 3, 2),
                                Move.of(1,3, 2, 3),
                                Move.of(1,3, 3, 3),
                                Move.of(1,4, 2, 4),
                                Move.of(1,4, 3, 4),
                                Move.of(1,5, 2, 5),
                                Move.of(1,5, 3, 5),
                                Move.of(1,6, 2, 6),
                                Move.of(1,6, 3, 6),
                                Move.of(1,7, 2, 7),
                                Move.of(1,7, 3, 7)),
                        "Black: all legal moves from starting position"
                ),
                Arguments.of(BoardFactory.fromString(NO_PAWNS, Color.BLACK), Set.of(), "Black: no pawns"),
                Arguments.of(BoardFactory.fromString(ALL_PAWNS_ADVACNE_ONE, Color.BLACK), Set.of(
                                Move.of(2,0, 3, 0),
                                Move.of(2,1, 3, 1),
                                Move.of(2,2, 3, 2),
                                Move.of(2,3, 3, 3),
                                Move.of(2,4, 3, 4),
                                Move.of(2,5, 3, 5),
                                Move.of(2,6, 3, 6),
                                Move.of(2,7, 3, 7)),
                        "Black: all pawns can advance once"
                ),
                Arguments.of(BoardFactory.fromString(PAWN_CAPTURES, Color.BLACK), Set.of(
                                Move.of(3,4, 4, 3),
                                Move.of(3,3, 4, 4)),
                        "Black: pawn captures and no advance through opponent"
                ),
                Arguments.of(
                        BoardFactory.fromString(ZERO_SEVEN_RANK_NON_CAPTURE, Color.BLACK),
                        Set.of(),
                        "Black: can't capture left on 0 rank, right on 7 rank"
                ),
                Arguments.of(
                        BoardFactory.fromString(PAWNS_BEHIND_PIECES, Color.BLACK),
                        Set.of(),
                        "Black: can't capture own pieces or move through own pieces"
                )
        );
    }

}
