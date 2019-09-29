package ai;

import java.util.ArrayList;
import ai.CoOrd;

public class CornerSeeker
{
    public int shortestDistance = 0;
    public CoOrd bestMove;
    public int currentDistance = 0;
    public TileValue turnColour;
    public int boardSize = 8;
    public ArrayList<CoOrd> bestMoves  = new ArrayList<CoOrd>();
    public ArrayList<Integer> distanceNumber = new ArrayList<Integer>();
    public CoOrd[] corners = new CoOrd[4];


    public CornerSeeker(int boardSize)
    {
        this.boardSize = boardSize;
        corners[0] = new CoOrd(0,0);
        corners[1] = new CoOrd(boardSize,0);
        corners[2] = new CoOrd(0,boardSize);
        corners[3] = new CoOrd(boardSize,boardSize);
        shortestDistance = boardSize*boardSize;

    }



    public CoOrd returnClosestCornerMove(ArrayList<CoOrd> legalMoves)
    {
        if(bestMoves.size()>0)
        {
            bestMoves.clear();
        }
        if(distanceNumber.size()>0)
        {
            distanceNumber.clear();
        }
        shortestDistance = boardSize*boardSize;
        for(int i =0; i <legalMoves.size(); i++)
        {
            int distance = returnShortestDistance(legalMoves.get(i));
            distanceNumber.add(distance);
            System.out.println("distaince: " + distance);
            if(distance<shortestDistance)
            {
                shortestDistance = distance;
                System.out.println(shortestDistance);
            }
        }
        for (int i=0; i <legalMoves.size();i++)
        {
            if(distanceNumber.get(i) == shortestDistance)
            {
                bestMoves.add(legalMoves.get(i));
                System.out.println("added");
            }
        }

    
        if(bestMoves.size()>0)
        {
          
            int numberOfMoves = bestMoves.size();
            System.out.println(numberOfMoves);
            int x = (int)(Math.random()*(numberOfMoves));
            return bestMoves.get(x);
            


        }
        return null;
    }


    public int returnShortestDistance(CoOrd move)
    {
        int shortest = boardSize*boardSize;
        for(int i =0; i<4; i++)
        {
            int distance = (corners[i].first- move.first) * (corners[i].first - move.first) + (corners[i].last- move.last) * (corners[i].last - move.last);
            if (distance< shortest)
            {
                shortest= distance;
            }
        }
        return shortest;
    
    }

}