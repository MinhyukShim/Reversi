package ai;

import java.util.ArrayList;
import ai.CoOrd;

public class Generous
{
    public int worstCapture = 0;
    public CoOrd worstMove;
    public int currentCapture = 0;
    public TileValue turnColour;
    public int boardSize = 8;
    public TileValue[][] board; 
    public ArrayList<CoOrd> worstMoves  = new ArrayList<CoOrd>();
    public ArrayList<Integer> captureNumber = new ArrayList<Integer>();

    public Generous(int boardSize)
    {
        this.boardSize = boardSize;
        worstCapture=boardSize*boardSize;
    }



    public CoOrd returnGenerousMove(ArrayList<CoOrd> legalMoves, TileValue[][] newBoard,TileValue currentTurn)
    {
        if(worstMoves.size()>0)
        {
            worstMoves.clear();
        }
        if(captureNumber.size()>0)
        {
            captureNumber.clear();
        }

        turnColour=currentTurn;
        updateBoard(newBoard);
        worstCapture=boardSize*boardSize;
        if(legalMoves.size()>0)
        {
            
            for (int i = 0; i< legalMoves.size(); i++)
            {
                CoOrd currentMove = legalMoves.get(i);
                capturePieces(currentMove);
                captureNumber.add(currentCapture);
                if (currentCapture < worstCapture)
                {
                    worstCapture=currentCapture;
                    
                   
                }
                currentCapture = 0;
                
            }
            for (int i =0; i<legalMoves.size(); i++)
            {
                if(captureNumber.get(i) == worstCapture)
                {
                    worstMoves.add(legalMoves.get(i));
                }
            }
            int numberOfMoves = worstMoves.size();
          
            //System.out.println(numberOfMoves);
            int x = (int)(Math.random()*(numberOfMoves));
            return worstMoves.get(x);
            


        }
        return null;
    }

    public void updateBoard(TileValue[][] newBoard)
    {
        board = newBoard;
    }

    public void capturePieces(CoOrd move)
    {
        int y = move.last;
        int x = move.first;
        traverseCapture(y,x,-1,0);
        traverseCapture(y,x,-1,1);
        traverseCapture(y,x,0,1);
        traverseCapture(y,x,1,1);
        traverseCapture(y,x,1,0);
        traverseCapture(y,x,1,-1);
        traverseCapture(y,x, 0,-1);
        traverseCapture(y,x,-1,-1);


    }
    
    public void  traverseCapture(int y,int x, int yDelta, int xDelta)
    {
        Boolean sameColourFound = false;
        while(!sameColourFound)
        {
            x+=xDelta;
            y+=yDelta;
            if(x<0 || x >=boardSize || y <0 || y >= boardSize)
            {
                return;
            }
            else if(board[y][x] == turnColour)
            {
                sameColourFound = true;
            }
            else if(board[y][x] == TileValue.EMPTY)
            {
                return;
            }
        }
        yDelta = yDelta*-1;
        xDelta = xDelta*-1;
        sameColourFound=false;
        while(!sameColourFound)
        {
            x+=xDelta;
            y+=yDelta;
            if(board[y][x] == turnColour.oppositeColour())
            {
                currentCapture++;
            }
            else
            {
                sameColourFound = true;
            }
        }

        
    }
}