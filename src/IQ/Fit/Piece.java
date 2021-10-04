package IQ.Fit;

public class Piece {

    private final PieceColor pieceColor;      // The color of the piece ('Blue', 'red' ...) (this never changes)
    private final Direction orientation;  // The piece's orientation (this does not change after the board has been initialised)
    public int position;                  // The current position of the piece on the board.

    private static Piece[] list = new Piece[PieceColor.values().length];

    public Piece(PieceColor pieceColor,String placement){

        this.pieceColor = pieceColor;
        this.orientation = placementToOrientation(placement);
        this.position = placementToPosition(placement);
        /*list[PieceColor.ordinal()] = this;*/
    }

    /**
     * Given a four-character piece placement string, decode the piece's orientation.

     * @param placement A string representing the placement of a piece on the game board
     * @return A value of type `Direction` corresponding to the piece's orientation on board
     */
    public Direction placementToOrientation(String placement){
        char a = placement.charAt(0);
        if(a == 'N')
            return Direction.NORTH;
        if(a == 'S')
            return Direction.SOUTH;
        if(a == 'W')
            return Direction.WEST;
        else
            return Direction.EAST;
    }

    /**
     * Given a four-character piece placement string, decode the piece's position.
     * @param placement A string representing the placement of a piece on the game board
     * @return An two-digit int corresponding to the piece's position on the board.
     */
    public int placementToPosition(String placement){
        char x = placement.charAt(1);
        char y = placement.charAt(2);
        int i = Integer.parseInt(String.valueOf(x)) * 10 + Integer.parseInt(String.valueOf(y));;
        return i;
    }

    /**
     *
     * @return A string represent the color of the piece
     */
    public String getPieceColor(){
        if (pieceColor == PieceColor.blue){
            return "blue";
        }if (pieceColor == PieceColor.Blue){
            return "Blue";
        } if (pieceColor == PieceColor.red){
            return "red";
        } if (pieceColor == PieceColor.Red){
            return "Red";
        } if (pieceColor == PieceColor.green){
            return "green";
        } if (pieceColor == PieceColor.Green){
            return "Green";
        } if (pieceColor == PieceColor.indigo){
            return "indigo";
        } if (pieceColor == PieceColor.Indigo){
            return "Indigo";
        } if (pieceColor == PieceColor.limegreen){
            return "limegreen";
        } if (pieceColor == PieceColor.Limegreen){
            return "Limegreen";
        } if (pieceColor == PieceColor.navyblue){
            return "navyblue";
        } if (pieceColor == PieceColor.Navyblue){
            return "Navyblue";
        } if (pieceColor == PieceColor.orange){
            return "orange";
        } if (pieceColor == PieceColor.Orange){
            return "Orange";
        } if (pieceColor == PieceColor.pink){
            return "pink";
        } if (pieceColor == PieceColor.Pink){
            return "Pink";
        } if (pieceColor == PieceColor.skyblue){
            return "skyblue";
        } if (pieceColor == PieceColor.Skyblue){
            return "Skyblue";
        } if (pieceColor == PieceColor.yellow){
            return "yellow";
        } if (pieceColor == PieceColor.Yellow){
            return "Yellow";
        }
        return "";
    }

    public Direction getOrientation(){
        return orientation;
    }

    public int getPosition(){
        return position;
    }

    public boolean canFit(){
        return false;
    }
    @Override
    public String toString() {
        return null;
    }
}
