package IQ.Fit;

public enum Direction {
    NORTH('↑'), EAST('→'), SOUTH('↓'), WEST('←');

    final private char symbol;

    /**
     * Constructor
     *
     * @param symbol This direction's symbol
     */
    Direction(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString(){
        return null;
    }
}
