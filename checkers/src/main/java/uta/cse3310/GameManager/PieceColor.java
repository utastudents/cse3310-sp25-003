package uta.cse3310.GameManager;

public class PieceColor {

    public static final PieceColor RED = new PieceColor();
    public static final PieceColor BLACK = new PieceColor();

    private PieceColor() {
    }

    public PieceColor getColor() {
        return this;
    }

    public int getSize() {
        return 0;
    }

}