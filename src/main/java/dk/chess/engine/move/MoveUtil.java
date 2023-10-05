package dk.chess.engine.move;

import dk.chess.engine.board.PositionUtil;
import dk.chess.engine.board.Board;
import dk.chess.engine.piece.Piece;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoveUtil {

    private MoveUtil() {}

    private static final Map<Piece, Set<MoveBuilderAttr>> MOVE_BUILDER_ATTR_MAP = Map.ofEntries(
            Map.entry(Piece.WHITE_PAWN, WhitePawnConstant.ATTRIBUTES),
            Map.entry(Piece.BLACK_PAWN, BlackPawnConstant.ATTRIBUTES)
    );

    public static Set<Move> legalMoves(Board board, Piece piece) {
        if (piece.getColor() != board.colorTurn()) {
            return Set.of();
        }
        return MoveUtil.MOVE_BUILDER_ATTR_MAP.get(piece).stream()
                .map(attr -> legalMovesPerAttribute(board, piece, attr))
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Move> legalMovesPerAttribute(Board board, Piece piece, MoveUtil.MoveBuilderAttr attr) {
        long bitboard = attr.bitboardLegalMoves().apply(board.pieces().get(piece), board);
        return IntStream.range(Long.numberOfTrailingZeros(bitboard), 64-Long.numberOfLeadingZeros(bitboard))
                .filter(i -> PositionUtil.isBitSet(bitboard, i))
                .mapToObj(i -> attr.moveMapper().apply(i))
                .collect(Collectors.toUnmodifiableSet());
    }

    public record MoveBuilderAttr(BiFunction<Long, Board, Long> bitboardLegalMoves, Function<Integer, Move> moveMapper) { }

}
