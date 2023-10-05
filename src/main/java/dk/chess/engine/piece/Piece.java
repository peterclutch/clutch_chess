package dk.chess.engine.piece;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Piece {

    BLACK_PAWN(PieceType.PAWN, Color.BLACK),
    BLACK_KNIGHT(PieceType.KNIGHT, Color.BLACK),
    BLACK_BISHOP(PieceType.BISHOP, Color.BLACK),
    BLACK_ROOK(PieceType.ROOK, Color.BLACK),
    BLACK_QUEEN(PieceType.QUEEN, Color.BLACK),
    BLACK_KING(PieceType.KING, Color.BLACK),
    WHITE_PAWN(PieceType.PAWN, Color.WHITE),
    WHITE_KNIGHT(PieceType.KNIGHT, Color.WHITE),
    WHITE_BISHOP(PieceType.BISHOP, Color.WHITE),
    WHITE_ROOK(PieceType.ROOK, Color.WHITE),
    WHITE_QUEEN(PieceType.QUEEN, Color.WHITE),
    WHITE_KING(PieceType.KING, Color.WHITE);

    private final PieceType pieceType;
    private final Color color;

    private static final Map<Character, Piece> characterPieceMap = createCharacterPieceMap();

    public char getSymbol() {
        char symbol = pieceType.getSymbol();
        return color.equals(Color.WHITE) ? Character.toUpperCase(symbol) : symbol;
    }

    public static Piece getPiece(char symbol) {
        return characterPieceMap.get(symbol);
    }

    private static Map<Character, Piece> createCharacterPieceMap() {
        return Arrays.stream(Piece.values()).collect(Collectors.toMap(Piece::getSymbol, Function.identity()));
    }

}
