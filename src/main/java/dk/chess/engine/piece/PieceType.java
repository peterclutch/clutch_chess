package dk.chess.engine.piece;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PieceType {

    PAWN('p'),
    KNIGHT('n'),
    BISHOP('b'),
    ROOK('r'),
    QUEEN('q'),
    KING('k');

    private final char symbol;

}
