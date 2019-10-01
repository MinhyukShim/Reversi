package ai;

import java.util.ArrayList;

public class ThreeDeepGreedy
{
    public int bestNetGain = 0;
    public int boardSize;
    public int currentNetGain = 0;
    public CoOrd bestMove;
    public ArrayList<Integer> netGainMoves;
    public TileValue[][] currentBoardState;
    public static TileValue turnColour;

    public ThreeDeepGreedy(int boardSize)
    {
        this.boardSize = boardSize;
        bestNetGain = -1 * boardSize* boardSize;

    }

    public void updateCurrentBoardState(TileValue[][] board, TileValue currentTurn)
    {   
        currentBoardState = board;
        turnColour= currentTurn;
    }

    public CoOrd goThroughLegalMoves(ArrayList<CoOrd> legalMoves)
    {
        netGainMoves.clear();
        for(int i =0; i<legalMoves.size(); i++)
        {
             currentNetGain = findNetGain(legalMoves.get(i));
             netGainMoves.add(currentNetGain);
             if(currentNetGain> bestNetGain)
             {
                bestMove = legalMoves.get(i);
                bestNetGain = currentNetGain;
             }
        }
        return bestMove;
    }

    public int findNetGain(CoOrd move)
    {
        
        return 0;
    }

    //place move capture pieces. see net gain.
    //get opposite colour and make best play see net loss
    //chnge to turn colour and calculate net gain;
    //add them all up
    //chhoose the move with the highest net gain.


    public void capturePieces(CoOrd click)
    {
        int y = click.last;
        int x = click.first;
        traverseCapture(y,x,-1,0);
        traverseCapture(y,x,-1,1);
        traverseCapture(y,x,0,1);
        traverseCapture(y,x,1,1);
        traverseCapture(y,x,1,0);
        traverseCapture(y,x,1,-1);
        traverseCapture(y,x, 0,-1);
        traverseCapture(y,x,-1,-1);
        turnColour = turnColour.oppositeColour();

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
            else if(currentBoardState[y][x] == turnColour)
            {
                sameColourFound = true;
            }
            else if(currentBoardState[y][x] == TileValue.EMPTY)
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
            if(currentBoardState[y][x] == turnColour.oppositeColour())
            {
                currentBoardState[y][x]= turnColour;
            }
            else
            {
                sameColourFound = true;
            }
        }

        
    }
}   