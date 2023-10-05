package dk.chess.engine.move;

import dk.chess.engine.board.PositionConstant;
import dk.chess.engine.board.Board;

import java.util.Set;
import java.util.function.Function;

public class BlackPawnConstant {

    private static final Function<Integer, Move> ADVANCE_MOVE = i -> Move.of(i/8-1, i%8, i/8, i%8);
    private static final Function<Integer, Move> DOUBLE_ADVANCE_MOVE = i -> Move.of(i/8-2, i%8, i/8, i%8);
    private static final Function<Integer, Move> TAKE_LEFT_MOVE = i -> Move.of(i/8-1, i%8+1, i/8, i%8);
    private static final Function<Integer, Move> TAKE_RIGHT_MOVE = i -> Move.of(i/8-1, i%8-1, i/8, i%8);

    public static final Set<MoveUtil.MoveBuilderAttr> ATTRIBUTES = Set.of(
            new MoveUtil.MoveBuilderAttr(BlackPawnConstant::advanceBitboard, BlackPawnConstant.ADVANCE_MOVE),
            new MoveUtil.MoveBuilderAttr(BlackPawnConstant::advanceDoubleBitboard, BlackPawnConstant.DOUBLE_ADVANCE_MOVE),
            new MoveUtil.MoveBuilderAttr(BlackPawnConstant::takeLeftBitboard, BlackPawnConstant.TAKE_LEFT_MOVE),
            new MoveUtil.MoveBuilderAttr(BlackPawnConstant::takeRightBitboard, BlackPawnConstant.TAKE_RIGHT_MOVE)
    );

    private static long advanceBitboard(long pawnBitboard, Board board) {
        long allPiecesBitboard = board.allPositions();
        return pawnBitboard<<8&~allPiecesBitboard&~PositionConstant.FIRST_RANK;
    }

    private static long advanceDoubleBitboard(long pawnBitboard, Board board) {
        long allPiecesBitboard = board.allPositions();
        return pawnBitboard<<16&~allPiecesBitboard&(~allPiecesBitboard<<8)&PositionConstant.FIFTH_RANK;
    }

    private static long takeLeftBitboard(long pawnBitboard, Board board) {
        long opponentPieces = board.whitePositions();
        return pawnBitboard<<7&opponentPieces&~PositionConstant.FIRST_RANK&~PositionConstant.A_FILE;
    }

    private static long takeRightBitboard(long pawnBitboard, Board board) {
        long opponentPieces = board.whitePositions();
        return pawnBitboard<<9&opponentPieces&~PositionConstant.FIRST_RANK&~PositionConstant.H_FILE;
    }

}
