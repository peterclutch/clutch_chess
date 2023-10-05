package dk.chess.engine.board;

import dk.chess.engine.piece.Color;

public class PositionConstant {

    public static final long FIRST_RANK = PositionUtil.fromCoordinates(new int[]{0,1,2,3,4,5,6,7});
    public static final long FOURTH_RANK = PositionUtil.fromCoordinates(new int[]{24,25,26,27,28,29,30,31});
    public static final long FIFTH_RANK = PositionUtil.fromCoordinates(new int[]{32,33,34,35,36,37,38,39});
    public static final long EIGHT_RANK = PositionUtil.fromCoordinates(new int[]{56,57,58,59,60,61,62,63});
    public static final long A_FILE = PositionUtil.fromCoordinates(new int[]{7,15,23,31,39,47,55,63});
    public static final long H_FILE = PositionUtil.fromCoordinates(new int[]{0,8,16,24,32,40,48,56});

    public static final String START_POSITION_STRING = """
            rnbqkbnr
            pppppppp
            00000000
            00000000
            00000000
            00000000
            PPPPPPPP
            RNBQKBNR
            """;

    public static final Board START_POSITION = BoardFactory.fromString(START_POSITION_STRING, Color.WHITE);

    private PositionConstant() {}

}
