package dk.chess;

import dk.chess.engine.board.PositionConstant;
import dk.chess.engine.move.Move;

public class Main {

    public static void main(String[] args) {
        PositionConstant.START_POSITION
                .print()
                .movePiece(Move.of(6, 4, 4, 4))
                .print()
                .movePiece(Move.of(1, 3, 3, 3))
                .print()
                .movePiece(Move.of(6, 2, 4, 2))
                .print()
                .movePiece(Move.of(1, 2, 2, 2))
                .print()
                .movePiece(Move.of(6, 1, 5, 1))
                .print()
                .movePiece(Move.of(3, 3, 4, 2))
                .print()
                .movePiece(Move.of(5, 1, 4, 2))
                .print();
    }

}
