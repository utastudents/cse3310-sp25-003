package uta.cse3310.GameManager;

import java.util.Objects;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Checks if this position is within the bounds of a board
     * @param boardSize the size of the board (assumes square board)
     * @return true if position is valid
     */
    public boolean isValid(int boardSize) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }
    
    /**
     * Returns a new position that is between this position and another
     * (useful for finding captured pieces in checkers)
     * @param other the other position
     * @return the middle position
     */
    public Position middle(Position other) {
        return new Position(
            x + (other.getX() - x) / 2,
            y + (other.getY() - y) / 2
        );
    }
    
    /**
     * Calculate the distance between this position and another
     * @param other the other position
     * @return the distance (in terms of array indices)
     */
    public int distanceTo(Position other) {
        return Math.max(
            Math.abs(other.getX() - x),
            Math.abs(other.getY() - y)
        );
    }
    
    /**
     * Add to the position
     * @param dx amount to add to x
     * @param dy amount to add to y
     * @return a new position with the updated coordinates
     */
    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
