import java.util.ArrayList;

public class Random
{
    public Random()
    {

    }
    public CoOrd chooseRandom(ArrayList<CoOrd> legalMoves)
    {
        int numberOfMoves = legalMoves.size();
        if (numberOfMoves>0)
        {
            int x = (int)(Math.random()*(numberOfMoves));
            return legalMoves.get(x);
        }
        return null;
    }
}