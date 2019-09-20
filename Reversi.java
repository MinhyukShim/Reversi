import java.util.ArrayList; 
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Reversi 
{
    public static int boardSize = 8; 
    public static TileValue[][] board = new TileValue[boardSize][boardSize]; 
    public static ArrayList <CoOrd> legalMoves = new ArrayList<CoOrd>();;  
    public static CoOrd move;
    public static TileValue turnColour = TileValue.BLACK;

    public enum TileValue 
    {
        EMPTY, BLACK, WHITE;

        public TileValue oppositeColour()
        {
            if(this == TileValue.BLACK)
            {
                return TileValue.WHITE;
            }
            else if(this == TileValue.WHITE)
            {
                return TileValue.BLACK;
            }
            return TileValue.EMPTY;
            
        }
    }

    public static void main(String[] args)
    {
        initaliseBoard(); 
        printBoard(); 


        calculateLegalMoves();
        printLegalMoves();

    }

    public static void initaliseBoard()
    {
        for (int y = 0; y < boardSize; y++ )
        {
            for (int x = 0; x < boardSize; x++ )
            {
                board[y][x] = TileValue.EMPTY; 
            }
        }
        int midpoint = (boardSize / 2) - 1; 
        board[midpoint][midpoint] = TileValue.WHITE; 
        board[midpoint][midpoint + 1] = TileValue.BLACK; 
        board[midpoint + 1][midpoint] = TileValue.BLACK; 
        board[midpoint + 1][midpoint + 1] = TileValue.WHITE; 
    }

    public static void printBoard()
    {
        for (int y = 0; y < boardSize; y++ )
        {
            for (int x = 0; x < boardSize; x++ )
            {
                System.out.print(board[y][x] + " "); 
            }
            System.out.println(" "); 
        }
    }

    public static void printLegalMoves()
    {
        for (int i =0; i<legalMoves.size(); i++)
        {
            System.out.println(legalMoves.get(i).first + " " + legalMoves.get(i).last );
        }
    }

    public static void calculateLegalMoves()
    {
        //go through current board state. 
        //if current colour's turn is found then scan all directions
        //for each direction: if adjacent is empty then go next
        //if different colour then check next until same colour or empty is found
        // if same colour then add origin to legal move  otherwise dont
        //repeat for each direction  

        for (int y = 0; y < boardSize; y++ )
        {
            for (int x = 0; x < boardSize; x++ )
            {
                addLegalMove(traverseDirection(y,x,-1,0));
                addLegalMove(traverseDirection(y,x,-1,1));
                addLegalMove(traverseDirection(y,x,0,1));
                addLegalMove(traverseDirection(y,x,1,1));
                addLegalMove(traverseDirection(y,x,1,0));
                addLegalMove(traverseDirection(y,x,1,-1));
                addLegalMove(traverseDirection(y,x,0,-1));
                addLegalMove(traverseDirection(y,x,-1,-1));
            }
        }
    }

    public static void addLegalMove(CoOrd move)
    {
        if(move != null)
        {
            if(legalMoves != null)
            {
                if(!legalMoves.contains(move))
                {
                    legalMoves.add(move);
                }
            }
        }
    }

    public static CoOrd traverseDirection(int y, int x, int yDelta, int xDelta)
    {

        
        Boolean finishedSearching = false;


        if (board[y][x] == turnColour){
            finishedSearching = false;
        }
        else
        {
            finishedSearching = true;
        }
        //legal moves requirement met therefore continue going in that direction until empty slot
        Boolean firstCheck= false;
        while(!finishedSearching)
        {
            x+=xDelta;
            y+=yDelta;
            if(board[y][x] == TileValue.EMPTY && firstCheck== true)
            {
                return new CoOrd(y,x);
            }
            else if(board[y][x] == TileValue.EMPTY)
            {
                //this colour doesnt have an adjacent opposite colour  in this direction.
                return null;
            }
            if(board[y][x] == turnColour)
            {
                return null;
            }
            if(board[y][x]== turnColour.oppositeColour())
            {
                firstCheck = true;
            }
            if(x<0 || x >=boardSize || y <0 || y >= boardSize)
            {
                return null;
            }

        }
        return null;
    }





}
