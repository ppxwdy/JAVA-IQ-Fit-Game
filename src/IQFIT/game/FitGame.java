package comp1110.ass2;

import comp1110.ass2.gui.Board;
import gittest.A;

import java.util.*;

/**
 * This class provides the text interface for the IQ Fit Game
 * <p>
 * The game is based directly on Smart Games' IQ-Fit game
 * (https://www.smartgames.eu/uk/one-player-games/iq-fit)
 */
public class FitGame {

    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is a valid piece descriptor character (b, B, g, G, ... y, Y)
     * - the second character is in the range 0 .. 9 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in valid orientation N, S, E, W
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */

    static boolean isPiecePlacementWellFormed(String piecePlacement) {

        ArrayList<Character> descriptorChecker = new ArrayList<>(
                List.of('b', 'B', 'g', 'G', 'i', 'I', 'l', 'L', 'n', 'N',
                        'o', 'O', 'p', 'P', 'r', 'R', 's', 'S', 'y', 'Y'));

        ArrayList<Character> secondRangeChecker = new ArrayList<>(
                List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

        ArrayList<Character> thirdRangeChecker = new ArrayList<>(
                List.of('0', '1', '2', '3', '4'));

        ArrayList<Character> directorChecker = new ArrayList<>(
                List.of('N', 'S', 'E', 'W'));

        if (piecePlacement.length() != 4) {
            return false;
        }
        if (!descriptorChecker.contains(piecePlacement.charAt(0))) {
            return false;
        }
        if (!secondRangeChecker.contains(piecePlacement.charAt(1))) {
            return false;
        }
        if (!thirdRangeChecker.contains(piecePlacement.charAt(2))) {
            return false;
        }
        return directorChecker.contains(piecePlacement.charAt(3));
    }


    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     * - the pieces are ordered correctly within the string
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementWellFormed(String placement) {
        if (placement.length() % 4 != 0 || placement.length() / 4 > 10 || placement.length() / 4 < 1)
            return false;//N * 4 strings
        String[] four = new String[placement.length() / 4];
        for (int i = 0; i < placement.length() / 4; i++) {
            four[i] = placement.substring(4 * i, 4 * i + 4);
        }
        for (String s : four) {//each piece is well formed
            if (!isPiecePlacementWellFormed(s)) return false;
        }
        for (int i = 0; i < four.length - 1; i++) {//no shape appear more than once
            for (int j = i + 1; j < four.length; j++) {
                if (four[i].charAt(0) == four[j].charAt(0) || Math.abs(four[i].charAt(0) - four[j].charAt(0)) == 32)
                    return false;
            }
        }
        String o = getPiecesOnBoard(placement);
        return orderRightOfPieces(o);
    }

    /**
     * extract pieces name in a placement String (in lower cases)
     *
     * @param placement String
     * @return A String of pieces in this placement String
     */
    static String getPiecesOnBoard(String placement) {
        String[] four = new String[placement.length() / 4];
        for (int i = 0; i < placement.length() / 4; i++) {
            four[i] = placement.substring(4 * i, 4 * i + 4);
        }
        StringBuilder order;
        order = new StringBuilder();
        for (String s : four) {
            order.append(s, 0, 1);
        }
        return order.toString().toLowerCase();
    }

    /**
     * judge if a piece order is right
     *
     * @param piece_order a string of order of pieces in a placement string
     * @return right order or not
     */
    static boolean orderRightOfPieces(String piece_order) {
        String oD = "bgilnoprsy";
        int p = 0;
        for (int i = 0; i < piece_order.length(); i++) {//right order
            while (p < 10) {
                if (piece_order.charAt(i) == oD.charAt(p)) break;
                if (p == 9) return false;
                p++;
            }
        }
        char[] c = piece_order.toCharArray();
        for (int i = 0; i < c.length; i++) {
            for (int j = i + 1; j < c.length; j++) {
                if (c[i] == c[j]) return false;
            }
        }
        return true;
    }

    /**
     * Determine whether a placement string is valid.
     * <p>
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementValid(String placement) {
        char[] place = placement.toCharArray();
        int length = placement.length();
        /* well formed*/
        if (!isPlacementWellFormed(placement)) {
            return false;
        }
        /*placement is valid*/
        for (int n = 0; n < length; n += 4) {
            /* pieces must be entirely on the board*/
            if (place[n + 1] == '9') {
                return false;
            } else if (place[n + 2] == '4') {
                return false;
            }
            final boolean b = place[n + 3] == 'N' || place[n + 3] == 'S';
            final boolean b1 = place[n + 3] == 'W' || place[n + 3] == 'E';
            if (place[n] == 'g' || place[n] == 'G' || place[n] == 'i' || place[n] == 'I' || place[n] == 'l' || place[n] == 'L' || place[n] == 'n' || place[n] == 'N') {
                if (place[n + 1] > '7' && b) {
                    return false;
                } else if (place[n + 2] > '2' && b1) {
                    return false;
                }
            } else {
                if (place[n + 1] > '6' && b) {
                    return false;
                } else if (place[n + 2] > '1' && b1) {
                    return false;
                }
            }
        }
        /*detect overlapping*/
        int[][] boardState = pieceRecord(placement);
        return isPieceOverlap(boardState);
    }

    /**
     * From placement string to board-state in the form of int[][]
     *
     * @param placement String
     * @return a 2D int-array represent the board state
     */
    public static int[][] pieceRecord(String placement) {
        //initialize board state with 5 * 10 of int[][] 0
        if (placement == null) throw new AssertionError("Placement String is Null!");
        char[] place = placement.toCharArray();
        int[][] piecePlace = new int[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                piecePlace[i][j] = 0;
            }
        }
        //n: index for position of every piece placement in placement string
        //place[n]: char at position n in placement string
        for (int n = 0; n < placement.length(); n += 4) {

            int i = Integer.parseInt(String.valueOf(place[n + 2]));//row position
            int j = Integer.parseInt(String.valueOf(place[n + 1]));//column position

            int[][] addition;
            int s;
            int t;

            int[][] temp = array_by_symbol(placement.charAt(n));//north addition array
            addition = rotatePiece(temp, place[n + 3]);//get addition array in right direction
            s = addition.length;//row number
            t = addition[0].length;//column number

            for (int k = i; k < i + s; k++) {//add one piece to board
                for (int l = j; l < j + t; l++) {
                    piecePlace[k][l] += addition[k - i][l - j];
                }
            }

        }
        return piecePlace;
    }
    /**
     * check if the board-state is valid(by checking the absence of 2)
     * @param piecePlace a 2D int array represent the piece on placement on the board
     * @return false if pieces overlap with others.
     */
    public static boolean isPieceOverlap(int[][] piecePlace) {
        int c = piecePlace[0].length;
        for (int[] ints : piecePlace) {
            for (int j = 0; j < c; j++) {
                if (ints[j] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * get rotated addition array from north-oriented addition array
     * use **** to add the new piece to board
     *
     * @param northAdd        an array for example {{1, 1, 1}, {1, 0, 0}} for l, North
     * @param targetDirection the direction in which your target array is
     * @return target array
     */
    static int[][] rotatePiece(int[][] northAdd, char targetDirection) {
        int m = northAdd.length;
        int n = northAdd[0].length;

        switch (targetDirection) {
            case 'E':
                int[][] newAdd = new int[n][m];
                for (int i = 0; i < n; i++) {
                    newAdd[i][0] = northAdd[1][i];
                    newAdd[i][1] = northAdd[0][i];
                }
                return newAdd;
            case 'S':
                int[][] newAdd1 = new int[m][n];
                for (int i = 0; i < n; i++) {
                    newAdd1[0][i] = northAdd[1][n - 1 - i];
                    newAdd1[1][i] = northAdd[0][n - 1 - i];
                }
                return newAdd1;
            case 'W':
                int[][] newAdd2 = new int[n][m];
                for (int i = 0; i < n; i++) {
                    newAdd2[i][0] = northAdd[0][n - i - 1];
                    newAdd2[i][1] = northAdd[1][n - i - 1];
                }
                return newAdd2;
            default:
                return northAdd;
        }
    }

    /**
     * get array (in north) by piece name
     * @param c name of the piece
     * @return array of the piece (north)
     */
    static int[][] array_by_symbol(char c) {
        switch (c) {
            case 'b':
            case 'r':
                return new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}};
            case 'B':
                return new int[][]{{1, 1, 1, 1}, {0, 1, 0, 1}};
            case 'g':
            case 'n':
                return new int[][]{{1, 1, 1}, {0, 1, 0}};
            case 'G':
                return new int[][]{{1, 1, 1}, {1, 1, 0}};
            case 'i':
                return new int[][]{{1, 1, 1}, {0, 0, 1}};
            case 'I':
                return new int[][]{{1, 1, 1}, {0, 1, 1}};
            case 'l':
                return new int[][]{{1, 1, 1}, {1, 0, 0}};
            case 'L':
            case 'N':
                return new int[][]{{1, 1, 1}, {1, 0, 1}};
            case 'o':
            case 's':
                return new int[][]{{1, 1, 1, 1}, {0, 1, 0, 0}};
            case 'O':
                return new int[][]{{1, 1, 1, 1}, {1, 0, 1, 0}};
            case 'p':
                return new int[][]{{1, 1, 1, 1}, {0, 0, 1, 0}};
            case 'P':
                return new int[][]{{1, 1, 1, 1}, {1, 1, 0, 0}};
            case 'R':
                return new int[][]{{1, 1, 1, 1}, {1, 0, 0, 1}};
            case 'S':
                return new int[][]{{1, 1, 1, 1}, {0, 1, 1, 0}};
            case 'y':
                return new int[][]{{1, 1, 1, 1}, {0, 0, 0, 1}};
            case 'Y':
                return new int[][]{{1, 1, 1, 1}, {0, 0, 1, 1}};
            default:
                return new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}};
        }
    }

    /**
     * Given a string describing a placement of pieces, and a location
     * that must be covered by the next move, return a set of all
     * possible next viable piece placements which cover the location.
     * <p>
     * For a piece placement to be viable it must:
     * - be a well formed piece placement
     * - be a piece that is not already placed
     * - not overlap a piece that is already placed
     * - cover the location
     *
     * @param placement A starting placement string
     * @param col       The location's column.
     * @param row       The location's row.
     * @return A set of all viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, int col, int row) {
        if (col > 9 || row > 4) return null;
        char[] pieces_taken = getPiecesOnBoard(placement).toCharArray();
        char[] pieces_all = "bgilnoprsy".toCharArray();
        char[] pieces_available = new char[pieces_all.length - pieces_taken.length];
        int k = 0;
        if (pieces_taken.length == 0) pieces_available = pieces_all;
        for (char c : pieces_all) {//get available pieces(in lower cases)
            for (int j = 0; j < pieces_taken.length; j++) {
                if (c == pieces_taken[j]) break;
                if (j == pieces_taken.length - 1) {
                    pieces_available[k] = c;
                    k++;
                }
            }
        }
        char[] orientation = {'N', 'E', 'S', 'W'};
        Set<String> viable = new HashSet<>();
        for (int i = Math.max(0, row - 4); i <= Math.min(row, 3); i++) {//try all holes
            for (int j = Math.max(0, col - 4); j <= Math.min(col, 8); j++) {
                for (char value : pieces_available) {//lower case piece
                    for (char o : orientation) {
                        String test = addPieceToPlacement(placement, newPiecePlacement(value, j, i, o));
                        if (test == null) continue;
                        if (!isPlacementValid(test)) continue;
                        if (row - i >= rotatePiece(array_by_symbol(value), o).length || col - j >= rotatePiece(array_by_symbol(value), o)[0].length) continue;
                        if (rotatePiece(array_by_symbol(value), o)[row - i][col - j] != 1) continue;
                        viable.add(newPiecePlacement(value, j, i, o));
                    }
                    value = (char)(value - 32);
                    for (char o : orientation) {//capital
                        String test = addPieceToPlacement(placement, newPiecePlacement(value, j, i, o));
                        if (test == null) continue;
                        if (!isPlacementValid(test)) continue;
                        if (row - i >= rotatePiece(array_by_symbol(value), o).length || col - j >= rotatePiece(array_by_symbol(value), o)[0].length) continue;
                        if (rotatePiece(array_by_symbol(value), o)[row - i][col - j] != 1) continue;
                        viable.add(newPiecePlacement(value, j, i, o));
                    }
                }
            }
        }
        if (viable.isEmpty()) return null;
        return viable;
    }
    /**
     *  new piece placement builder (by student)
     * @param c piece char
     * @param col column int
     * @param row row int
     * @param orientation char
     * @return new piece placement
     */
    static String newPiecePlacement(char c, int col, int row, char orientation) {
        return c + String.valueOf(col) + row + orientation;
    }
    /**
     * add piece string to string in a right order (added by student)(do not add any piece twice, it is not considered in this method)
     * @param placement placement
     * @param piecePlacement piece placement
     * @return new placement
     */
    public static String addPieceToPlacement(String placement, String piecePlacement) {
        for (int i = 0; i < placement.length() / 4; i++) {
            String test = placement.substring(0, 4 * i) + piecePlacement + placement.substring(4 * i);
            if (orderRightOfPieces(getPiecesOnBoard(test))) return test;
        }
        String test =  placement + piecePlacement;
        if (orderRightOfPieces(getPiecesOnBoard(test))) return test;
        return placement;
    }


    /**
     * Return the solution to a particular challenge.
     * @param challenge A challenge string.
     * @return A placement string describing the encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        solution = null;//zero the variable after each time it is run
        findSolution(challenge);
        if (solution == null) return "No!";
        return solution;
    }

    static String solution = null; //solution variable definition

    /**
     * Recursive call to find the solution
     * @param input placement string
     */
    static void findSolution(String input) {//FIND SOLUTION
        int[] oneCoordinate = getOneCoordinate(input);
        if (oneCoordinate == null) {//Condition: board is filled, assign the solution, mission complete
            solution = input;
        }
        else if (solution == null) {
            Set<String> viablePiece = getViablePiecePlacements(input, oneCoordinate[1], oneCoordinate[0]);

            if (viablePiece == null) {//Condition: dead end met, return to abort
                return;
            }

            Iterator<String> it = viablePiece.iterator();
            for (int i = 0; i < viablePiece.size(); i++) {//Branching
                String piece = it.next();
                String newBoardPlacement = addPieceToPlacement(input, piece);
                if (!allHolesUntrapped(newBoardPlacement)) {
                    continue;
                }
                findSolution(newBoardPlacement);
            }
        }
    }

    /**
     * Get the coordinates of the position who has only 1 direction is available (not occupied or on the rim)
     * @param placement placement string as the board state
     * @return the first wanted position's coordinates or the top-left one's
     */
    static int[] getOneCoordinate(String placement) {
        int[][] board = pieceRecord(placement);

        for (int i = 0; i < 5; i++) {//Three sides occupied
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 0) {
                    int r = 0;
                    if (j + 1 < 10) if (board[i][j + 1] == 1) r++;
                    if (j - 1 >= 0) if (board[i][j - 1] == 1) r++;
                    if (i + 1 < 5) if (board[i + 1][j] == 1) r++;
                    if (i - 1 >= 0) if (board[i - 1][j] == 1) r++;
                    if (i == 0 || i == 4) r++;
                    if (j == 0 || j == 9) r++;

                    if (r == 3) {
                        int[] coordinate = new int[2];
                        coordinate[0] = i;
                        coordinate[1] = j;
                        return coordinate;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {//No such hole, select the top-leftest hole
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 0) {
                    return new int[] {i, j};
                }
            }
        }
        return null;// all holes occupied
    }

    /**
     * Check if there exists a single unoccupied-hole
     * @param placement String
     * @return true if no such exist
     */
    public static boolean allHolesUntrapped(String placement) {
        int[][] board = pieceRecord(placement);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 0) {
                    int r = 0;
                    if (j + 1 < 10) if (board[i][j + 1] == 1) r++;
                    if (j - 1 >= 0) if (board[i][j - 1] == 1) r++;
                    if (i + 1 < 5) if (board[i + 1][j] == 1) r++;
                    if (i - 1 >= 0) if (board[i - 1][j] == 1) r++;
                    if (i == 0 || i == 4) r++;
                    if (j == 0 || j == 9) r++;

                    if (r == 4) {
                        return false;//there is a trapped position
                    }
                }
            }
        }
        return true;// all holes occupied
    }



    /**
     * give a random piece to start
     * @return String
     */
    public static String randomStart() {
        int col;
        int row;
        char[] orientation = {'N', 'E', 'S', 'W'};
        String piece;

        do {
            int oNumber = (int)(Math.random() * 4);
            if (oNumber == 0 || oNumber == 2) {
                col = (int)(Math.random() * 6);
                row = (int)(Math.random() * 3);
            } else {
                col = (int)(Math.random() * 8);
                row = (int)(Math.random() * 1);
            }
            piece = newPiecePlacement('b', col, row, orientation[oNumber]);
        } while (piece.equals("b02S") || piece.equals("b70E") || piece.equals("b00S") || piece.equals("b10W") || piece.equals("b01N"));

        return piece;
    }

    /**
     * For test if the solution is unique
     * @param test string
     * @return boolean
     */
    public static boolean solutionUnique(String test) {
        all = new HashSet<>();
        n = 0;
        p = 0;
        findAllSolution(test);
        return all.size() == 1;
    }

    static Set<String> all;
    static int n;
    public static int p;

    /**
     * Recursive call to find all solutions
     * @param input placement string
     */
    static void findAllSolution(String input) {//FIND SOLUTION
        if (all.size() == 0) n++;
        if (all.size() == 1) p++;
        int[] oneCoordinate = getOneCoordinate(input);
        if (oneCoordinate == null) {//Condition: board is filled, assign the solution, mission complete
            all.add(input);
        } else if ((all.size() == 0 && n < 5000) || (all.size() == 1 && p < 5000)) {
            Set<String> viablePiece = getViablePiecePlacements(input, oneCoordinate[1], oneCoordinate[0]);

            if (viablePiece == null) {//Condition: dead end met, return to abort
                return;
            }

            Iterator<String> it = viablePiece.iterator();
            for (int i = 0; i < viablePiece.size(); i++) {//Branching
                String piece = it.next();
                String newBoardPlacement = addPieceToPlacement(input, piece);
                if (!allHolesUntrapped(newBoardPlacement)) {
                    continue;
                }
                findAllSolution(newBoardPlacement);
            }
        }
    }



    /**
     * Temporary method to print the board state in a matrix manner
     * @param placement current placement string
     */
    static void boardStateVisual(String placement) {
        int[][] board = pieceRecord(placement);
        boardStateVisual(board);
    }
    /**
     * Temporary method to print the board state in a matrix manner
     * @param board current placement 2-d array
     */
    static void boardStateVisual(int[][] board) {
        for (int[] ints : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    public static void main(String[] args) {
        Set<String> all = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String b = Board.createChallenge(4);
            all.add(b);
            System.out.println(b);
            System.out.println(all);
        }
        System.out.println(all);
//            long startTime = System.currentTimeMillis();    //获取开始时间
//            long endTime = System.currentTimeMillis();    //获取结束时间
//
//            System.out.println("Time consumed：" + (endTime - startTime) + "ms");    //输出程序运行时间
//

        }

    }

