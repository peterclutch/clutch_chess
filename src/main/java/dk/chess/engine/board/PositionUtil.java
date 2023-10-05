package dk.chess.engine.board;

import dk.chess.engine.piece.Color;
import dk.chess.engine.piece.Piece;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PositionUtil {

    public static final char SET_BIT = '1';
    public static final char EMPTY_BIT = '0';

    private PositionUtil() {}

    public static int[] toCoordinates(long position) {
        String binary = Long.toBinaryString(position);
        return IntStream.range(0, binary.length()).filter(i -> binary.charAt(binary.length() - 1 - i) == SET_BIT).toArray();
    }

    public static long fromCoordinates(int[] coordinates) {
        Set<Integer> set = Arrays.stream(coordinates).boxed().collect(Collectors.toUnmodifiableSet());
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            b.append(set.contains(i) ? SET_BIT : EMPTY_BIT);
        }
        return new BigInteger(b.toString(), 2).longValue();
    }

    public static boolean isBitSet(long bitboard, int position) {
        return ((bitboard>>position)&1)==1;
    }

    public static long allPositions(Map<Piece, Long> pieces) {
        return allPositions(pieces.values());
    }

    public static long allPositions(Collection<Long> pieces) {
        return pieces.stream().reduce(0L, (e1, e2) -> e1 | e2);
    }

    public static long colorPositions(Map<Piece, Long> pieces, Color color) {
        return pieces.entrySet().stream()
                .filter(e -> e.getKey().getColor() == color)
                .map(Map.Entry::getValue)
                .reduce(0L, (e1, e2) -> e1 | e2);
    }

}
