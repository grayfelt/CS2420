import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class HexGame {
    private void playGame(File file) throws FileNotFoundException {
        System.out.println();
        Scanner sc = new Scanner(file);
        int[] top = new int[11];
        int[] bottom = new int[11];
        int[] left = new int[11];
        int[] right = new int[11];
        ArrayList occupied = new ArrayList();
        int movesBlue = 0;
        int movesRed = 0;
        //create a visual representation of the board
        String[] board = new String[122];
        for(int z = 0; z < 121; z++){
            board[z] = "0";
        }
        //initialize blue player state, and red player state
        DisjointSet blue = new DisjointSet();
        DisjointSet red = new DisjointSet();
        //always start with blue
        boolean blueTurn = true;
        //create edges in an array (for ease of access)
        for (int i = 0; i < 11; i++) {
            top[i] = i + 1;
            bottom[i] = i + 111;
            left[i] = i * 11 + 1;
            right[i] = i * 11 + 11;
        }
        //for every integer in the file
        while(sc.hasNextInt()) {
            int cell = sc.nextInt();
            //grab neighbors
            int[] neighborIdx = getNeighbors((cell));
            if(!occupied.contains(cell)){
                if (blueTurn) {
                    //insert desired cell into blue player state
                    blue.insert(cell);
                    //insert desired cell into visual representation
                    board[cell - 1] = "B";
                    //update occupied locations
                    occupied.add(cell);
                    //union if possible
                    blue.union2(cell, neighborIdx);
                    blueTurn = false;
                    movesBlue++;
                } else {
                    //insert desired cell into red player state
                    red.insert(cell);
                    //insert desired cell into visual representation
                    board[cell - 1] = "R";
                    //update occupied locations
                    occupied.add(cell);
                    //union if possible
                    red.union2(cell, neighborIdx);
                    blueTurn = true;
                    movesRed++;
                }
            }
            else{
                System.out.println("Space occupied, please try again.");
            }
            //check if red won, and end
            if (gameEnd(red, top, bottom)) {
                System.out.println("Red player has won the game in " + movesRed + " moves. Here is the final board.");
                break;
            }
            //check if blue won, and end
            else if (gameEnd(blue, left, right)) {
                System.out.println("Blue player has won the game in " + movesBlue + " moves. Here is the final board.");
                break;
            }
            //repeat
        }
        //print visual representation
        String ANSI_RED = "\u001B[31m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RESET = "\u001B[0m";
        int tmp = 0;
        int row = 0;
        for(int c = 0; c < board.length - 1; c++){
            if(tmp == 11){
                row++;
                System.out.print("\n");
                for(int x = 0; x < row; x++){
                    System.out.print(" ");
                }
                tmp = 0;
            }
            switch (board[c]) {
                case "0":
                    System.out.print(board[c] + " ");
                    break;
                case "R":
                    System.out.print(ANSI_RED + "R" + ANSI_RESET + " ");
                    break;
                case "B":
                    System.out.print(ANSI_BLUE + "B" + ANSI_RESET + " ");
                    break;
            }
            tmp++;
        }
    }
    private int[] getNeighbors(int cell) {
        //all the math determining the neighboring cells
        int behind = cell - 1;
        int next = cell + 1;
        int nextRowBehind = cell + 10;
        int nextRow = cell + 11;
        int lastRowBehind = cell - 10;
        int lastRow = cell - 11;
        //very first cell
        if (cell == 1) return new int[]{2,12};
            //last on first row
        else if (cell == 11) return new int[]{10,21,22};
            //first on last row
        else if (cell == 111) return new int[]{100,101,112};
            //last on last row
        else if (cell == 121) return new int[]{110, 120};
            //top side
        else if (cell < 12) return new int[]{behind, next, nextRowBehind, nextRow};
            // bottom side
        else if (cell > 110) return new int[]{lastRow, lastRowBehind, behind, next};
            //right side
        else if (cell % 11 == 0) return new int[]{lastRow, behind, nextRowBehind, nextRow};
            //left side
        else if ((cell - 1) % 11 == 0) return new int[]{lastRow, lastRowBehind, next, nextRow};
            //middle cells
        else return new int[]{lastRow, lastRowBehind, behind, next, nextRowBehind, nextRow};
    }
    private boolean gameEnd(DisjointSet player, int[] edge, int[] parallel) {
        //for every cell on the edge
        for (int a : edge) {
            //every non-zero cell
            if (player.find(a) != 0) {
                //for every cell on the parallel edge
                for (int b: parallel) {
                    //if they have the same parent, they are connected
                    if (player.find(a) == player.find(b)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File moves = new File("src\\moves.txt");
        File moves2 = new File("src\\moves2.txt");
        HexGame game1 = new HexGame();
        HexGame game2 = new HexGame();
        game1.playGame(moves);
        game2.playGame(moves2);

    }



}
