package dk.chess.engine.move;

import dk.chess.engine.board.PositionConstant;
import dk.chess.engine.board.Board;

import java.util.Set;
import java.util.function.Function;

public class WhitePawnConstant {

    private static final Function<Integer, Move> ADVANCE_MOVE = i -> Move.of(i/8+1, i%8, i/8, i%8);
    private static final Function<Integer, Move> DOUBLE_ADVANCE_MOVE = i -> Move.of(i/8+2, i%8, i/8, i%8);
    private static final Function<Integer, Move> TAKE_LEFT_MOVE = i -> Move.of(i/8+1, i%8+1, i/8, i%8);
    private static final Function<Integer, Move> TAKE_RIGHT_MOVE = i -> Move.of(i/8+1, i%8-1, i/8, i%8);

    public static final Set<MoveUtil.MoveBuilderAttr> ATTRIBUTES = Set.of(
            new MoveUtil.MoveBuilderAttr(WhitePawnConstant::advanceBitboard, WhitePawnConstant.ADVANCE_MOVE),
            new MoveUtil.MoveBuilderAttr(WhitePawnConstant::advanceDoubleBitboard, WhitePawnConstant.DOUBLE_ADVANCE_MOVE),
            new MoveUtil.MoveBuilderAttr(WhitePawnConstant::takeLeftBitboard, WhitePawnConstant.TAKE_LEFT_MOVE),
            new MoveUtil.MoveBuilderAttr(WhitePawnConstant::takeRightBitboard, WhitePawnConstant.TAKE_RIGHT_MOVE)
    );

    private static long advanceBitboard(long pawnBitboard, Board board) {
        long allPiecesBitboard = board.allPositions();
        return pawnBitboard>>8&~allPiecesBitboard&~PositionConstant.EIGHT_RANK;
    }

    private static long advanceDoubleBitboard(long pawnBitboard, Board board) {
        long allPiecesBitboard = board.allPositions();
        return pawnBitboard>>16&~allPiecesBitboard&(~allPiecesBitboard>>8)&PositionConstant.FOURTH_RANK;
    }

    private static long takeLeftBitboard(long pawnBitboard, Board board) {
        long opponentPieces = board.blackPositions();
        return pawnBitboard>>9&opponentPieces&~PositionConstant.EIGHT_RANK&~PositionConstant.A_FILE;
    }

    private static long takeRightBitboard(long pawnBitboard, Board board) {
        long opponentPieces = board.blackPositions();
        return pawnBitboard>>7&opponentPieces&~PositionConstant.EIGHT_RANK&~PositionConstant.H_FILE;
    }



}
