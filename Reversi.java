public class Reversi
{
    public static int boardSize = 8;
    public static TileValue[][] board = new TileValue[boardSize][boardSize];
    public static void main(String[] args)
    {
        initaliseBoard();
        printBoard();
    }

    public enum TileValue{
        EMPTY, BLACK, WHITE
    }

    public static void initaliseBoard(){
        for (int y=0; y< boardSize; y++){
            for(int x=0; x< boardSize; x++){
                board[y][x]= TileValue.EMPTY;
            }
        }
        int midpoint = (boardSize/2) -1;
        board[midpoint][midpoint] = TileValue.WHITE; 
        board[midpoint][midpoint+1] = TileValue.BLACK; 
        board[midpoint+1][midpoint] = TileValue.BLACK; 
        board[midpoint+1][midpoint+1] = TileValue.WHITE; 
    }
    public static void printBoard(){
        for (int y=0; y< boardSize; y++){
            for(int x=0; x< boardSize; x++){
                System.out.print(board[y][x] + " ");
            }
            System.out.println(" ");
        }
    }
}