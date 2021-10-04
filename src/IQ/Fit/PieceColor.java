package IQ.Fit;

public enum PieceColor {
    blue("b"),Blue("B"),
    red("r"),Red("R"),
    green("g"),Green("G"),
    indigo("i"),Indigo("I"),
    limegreen("l"),Limegreen("L"),
    navyblue("n"),Navyblue("B"),
    orange("o"),Orange("O"),pink("p"),
    Pink("P"),skyblue("S"),Skyblue("S"),
    yellow("y"),Yellow("Y");

    final private String color;

    /**
     * Constructor
     * @param color is a character which represent the color
     */
    PieceColor(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return null;
    }
}
