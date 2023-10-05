package dk.chess.engine.board;

import dk.chess.engine.exception.IllegalMoveException;
import dk.chess.engine.exception.WrongTurnException;
import dk.chess.engine.move.Coordinate;
import dk.chess.engine.move.Move;
import dk.chess.engine.move.MoveUtil;
import dk.chess.engine.piece.Color;
import dk.chess.engine.piece.Piece;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public record Board(
        Map<Piece, Long> pieces,
        long allPositions,
        long whitePositions,
        long blackPositions,
        Color colorTurn
) {

    public Board(Map<Piece, Long> pieces, Color colorTurn) {
        this(
                pieces,
                PositionUtil.allPositions(pieces),
                PositionUtil.colorPositions(pieces, Color.WHITE),
                PositionUtil.colorPositions(pieces, Color.BLACK),
                colorTurn
        );
    }

    public Board {
        pieces = Map.copyOf(pieces);
    }

    public Board movePiece(Move move) {
        Piece toMove = findPiece(move.from());
        if (toMove.getColor() != this.colorTurn) {
            throw new WrongTurnException();
        }
        if (!MoveUtil.legalMoves(this, toMove).contains(move)) {
            throw new IllegalMoveException();
        }
        Map<Piece, Long> updatedPieces = pieces.entrySet()
                .stream()
                .map(e -> movePiece(e, toMove, move))
                .map(e -> capturePiece(e, toMove, move))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Board(updatedPieces, colorTurn.takeTurn());
    }

    private Piece findPiece(Coordinate coordinate) {
        return pieces.entrySet()
                .stream()
                .filter(e -> (e.getValue()&coordinate.toBitboard()) > 0)
                .findFirst()
                .orElseThrow(IllegalMoveException::new)
                .getKey();
    }

    private Map.Entry<Piece, Long> movePiece(Map.Entry<Piece, Long> oldPosition, Piece toMove, Move move) {
        if (!oldPosition.getKey().equals(toMove)) {
            return oldPosition;
        }
        return Map.entry(
                oldPosition.getKey(),
                oldPosition.getValue()&~move.from().toBitboard()|move.to().toBitboard()
        );
    }

    private Map.Entry<Piece, Long> capturePiece(Map.Entry<Piece, Long> oldPosition, Piece toMove, Move move) {
        // cannot capture itself
        if (oldPosition.getKey().equals(toMove)) {
            return oldPosition;
        }
        // nothing to capture
        if ((oldPosition.getValue()&move.to().toBitboard()) <= 0) {
            return oldPosition;
        }
        return Map.entry(
                oldPosition.getKey(),
                oldPosition.getValue()^move.to().toBitboard());
    }

    public Board print() {
        Map<Integer, Piece> placedPieces = pieces.entrySet().stream()
                .flatMap(e -> {
                    int[] coordinates = PositionUtil.toCoordinates(e.getValue());
                    return Arrays.stream(coordinates).mapToObj(coor -> new PlacedPiece(e.getKey(), coor));
                }).collect(Collectors.toUnmodifiableMap(PlacedPiece::coordinate, PlacedPiece::piece));

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            b.append("|");
            b.append(placedPieces.containsKey(i) ? placedPieces.get(i).getSymbol() : " ");
            if ((i+1)% 8 == 0) {
                b.append("|");
                b.append("\n");
            }
        }
        System.out.println(b); // TODO replace
        return this;
    }

    private record PlacedPiece(Piece piece, Integer coordinate) {}

}
