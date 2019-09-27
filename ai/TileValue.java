package ai;

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