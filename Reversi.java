
import java.util.ArrayList; 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.Group; 
import javafx.scene.paint.Color; 
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import ai.*;
public class Reversi extends Application
{
    public static int boardSize = 8; 
    public static TileValue[][] board = new TileValue[boardSize][boardSize]; 
    public static ArrayList <CoOrd> legalMoves = new ArrayList<CoOrd>(); 
    public static TileValue turnColour = TileValue.BLACK;
    public static Boolean blackStuck = false;
    public static Boolean whiteStuck = false;
    
    public static Group root = new Group();
    public static Group legalMovesGroup = new Group();
    
    public static Text whosTurn = new Text();
    public static Text pieceText = new Text();

    public static int whitePieces = 2;
    public static int blackPieces = 2;

    public static Players playerBlack = Players.Greedy;
    public static Players playerWhite = Players.Generous;

    public static Random randomPlayer = new Random();
    public static Greedy greedyPlayer = new Greedy(boardSize);
    public static Generous generousPlayer = new Generous(boardSize);

    public static int boardSizePixels = 960;
    public static int tileSize = boardSizePixels/boardSize;
    public static int counterLimit = boardSize*boardSize;

    public static void main(String[] args)
    {
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Reversi");

        Rectangle rectangle = new Rectangle(0, 0, boardSizePixels, boardSizePixels);
        rectangle.setFill(Color.GREEN); 
        root.getChildren().add(rectangle);
       
        root = drawBoard(root);

        pieceText.setFont(new Font(40));
        pieceText.setText("White pieces: " + whitePieces + "\nBlack pieces: " + blackPieces);
        pieceText.setX(1000);
        pieceText.setY(100);
        root.getChildren().add(pieceText);


        whosTurn.setFont(new Font(40));
        whosTurn.setText("Black's Turn");
        whosTurn.setX(1000);
        whosTurn.setY(300);
        root.getChildren().add(whosTurn);

        initaliseBoard(); 

        Scene scene = new Scene(root, 1920,1080);

        primaryStage.setScene(scene);
        primaryStage.show();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(turnColour==TileValue.WHITE)
                {
                    handleTurn(playerWhite,event);
                }
                else
                {
                    handleTurn(playerBlack,event);
                }
                updateText();


            }
        });




        
    }

    public static void updateText()
    {
        pieceText.setText("White pieces: " + whitePieces + "\nBlack pieces: " + blackPieces);
        if(turnColour == TileValue.WHITE)
        {
            whosTurn.setText("White's Turn");
        }
        else{
            whosTurn.setText("Black's Turn");
        }
    }

    public static void handleTurn(Players player, MouseEvent event)
    {
        switch(player)
        {
            case Human:
                humanController(event);
                break;
            case Random:
                randomController();
                break;
            case Greedy:
                greedyController();
                break;
            case Generous:
                generousController();
                break;
        }
    }



    public static void generousController()
    {
        System.out.println(legalMoves.size());
        CoOrd move = generousPlayer.returnGenerousMove(legalMoves,board,turnColour);
        updateMove(move); 
    }
    public static void greedyController()
    {
        CoOrd move = greedyPlayer.returnGreedyMove(legalMoves,board,turnColour);
        updateMove(move); 
    }


    public static void humanController(MouseEvent event)
    {
        CoOrd click = mousePositionToCoOrds(event.getSceneX(),event.getSceneY());
        updateMove(click);
    }

    public static void randomController()
    {
        CoOrd move = randomPlayer.chooseRandom(legalMoves);
        updateMove(move);
                
    }

    public static void updateMove(CoOrd move)
    {
        if (updateBoard(move))
        {
            root = printBoard(root);
            calcAndDrawLegalMoves();
            
        }
        checkTurnState();
    }

    public static void calcAndDrawLegalMoves()
    {
        calculateLegalMoves();
        legalMovesGroup = displayLegalMoves(legalMovesGroup);
        root.getChildren().add( legalMovesGroup );
    }


    public static void checkTurnState()
    {
        checkLegalMovesForColour();
        


        if (blackStuck== true && whiteStuck == true || blackPieces+whitePieces >= counterLimit)
        {
            if (whitePieces>blackPieces)
            {
                System.out.println("White wins");
            }
            else if (blackPieces>whitePieces)
            {
                System.out.println("Black wins");   
            }
            else
            {
                System.out.println("Draw!");
            }
        }

    }


    public static void checkLegalMovesForColour()
    {
        if (turnColour== TileValue.BLACK)
        {
            if (legalMoves.isEmpty())
            {
                blackStuck= true;
                turnColour = turnColour.oppositeColour();
                calcAndDrawLegalMoves();
            }
            else
            {
                blackStuck = false;
            }
        }
        else if(turnColour == TileValue.WHITE)
        {
            if (legalMoves.isEmpty())
            {
                whiteStuck= true;
                turnColour = turnColour.oppositeColour();
                calcAndDrawLegalMoves();
            }
            else
            {
                whiteStuck = false;
            } 
        }
    }


    public static Group drawBoard(Group group)
    {
        for (int i =1; i<=boardSize; i++)
        {
            int lineWidth = 10;
            Line line = new Line(tileSize*i, 0, tileSize*i, boardSizePixels-(lineWidth/2));
            Line line2 = new Line(0,tileSize*i, boardSizePixels-(lineWidth/2), tileSize*i);
            line.setStrokeWidth(lineWidth);
            line2.setStrokeWidth(lineWidth);
            line.setStroke(Color.rgb(50, 50, 50));
            line2.setStroke(Color.rgb(50, 50, 50));
            group.getChildren().add(line);
            group.getChildren().add(line2);
        }
        return group;
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

        //place the pieces in the middle.
        int midpoint = (boardSize / 2) - 1; 
        board[midpoint][midpoint] = TileValue.WHITE; 
        board[midpoint][midpoint + 1] = TileValue.BLACK; 
        board[midpoint + 1][midpoint] = TileValue.BLACK; 
        board[midpoint + 1][midpoint + 1] = TileValue.WHITE; 
        root = printBoard(root); 
        calcAndDrawLegalMoves();
    }

    public static Group printBoard(Group group)
    {
        for (int y = 0; y < boardSize; y++ )
        {
            for (int x = 0; x < boardSize; x++ )
            {
                if(board[y][x] == TileValue.WHITE)
                {
                    Circle circle = new Circle((x+0.5)*tileSize, (y+0.5)*tileSize, tileSize*0.43); 
                    circle.setFill(Color.WHITE);
                    group.getChildren().add(circle);
                }
                else if(board[y][x] == TileValue.BLACK)                    
                {
                    Circle circle = new Circle((x+0.5)*tileSize, (y+0.5)*tileSize, tileSize*0.43); 
                    circle.setFill(Color.BLACK);
                    group.getChildren().add(circle);
                
                }
                //System.out.print(board[y][x] + " ");
            }
            //System.out.println(" ");
        }
        return group;
    }

    public static Group displayLegalMoves(Group group)
    {
        root.getChildren().removeAll( group );
        group.getChildren().clear();
        if(legalMoves.size() != 0)
        {
            for (int i =0; i<legalMoves.size(); i++)
            {
                Circle circle = new Circle((legalMoves.get(i).first+0.5)*tileSize, (legalMoves.get(i).last+0.5)*tileSize, tileSize*0.43);
                if(turnColour==TileValue.WHITE)
                {
                    circle.setFill(Color.rgb(255,255,255,0.4));
                }
                else
                {
                    circle.setFill(Color.rgb(0,0,0,0.4));
                }
                
                group.getChildren().add(circle);
            }
        }
        else
        {
            System.out.println("No legal moves! " + turnColour );
        }
        return group;
    }

    public static void calculateLegalMoves()
    {
        //go through current board state. 
        //if current colour's turn is found then scan all directions
        //for each direction: if adjacent is empty then go next
        //if different colour then check next until same colour or empty is found
        // if same colour then add origin to legal move  otherwise dont
        //repeat for each direction  
        legalMoves.clear();
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

        //might need to change the legalMoves data structure to a set to as the contains function will take much longer when scaled upwards
        if(move != null && !legalMoves.contains(move))
        {
            legalMoves.add(move);
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
        Boolean oppositeAdjacentColour= false;
        while(!finishedSearching)
        {
            //move in direction
            x+=xDelta;
            y+=yDelta;

            //check if out of bounds
            if(x<0 || x >=boardSize || y <0 || y >= boardSize)
            {
                return null;
            }
            
            //a legal move requires the opposite colour's piece inbetween the potential spot and an already placed piece
            if(board[y][x] == TileValue.EMPTY && oppositeAdjacentColour== true)
            {
                return new CoOrd(x,y);
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
                oppositeAdjacentColour = true;
            }


        }
        return null;
    }


    public static CoOrd mousePositionToCoOrds(double x, double y)
    {
        int tileX = (int)x/tileSize;
        int tileY = (int)y/tileSize;

        return new CoOrd(tileX, tileY);
    }

    public static Boolean updateBoard(CoOrd click)
    {
        if(legalMoves.contains(click))
        {
            board[click.last][click.first] = turnColour;
            incrementPieceCounter();
            capturePieces(click);
            return true;
        }
        return false;

    }

    public static void incrementPieceCounter()
    {
        if (turnColour == TileValue.WHITE)
        {
            whitePieces++;
        }
        else if (turnColour == TileValue.BLACK)
        {
            blackPieces++;
        }
    }

    public static void decrementPieceCounter()
    {
        if(turnColour==TileValue.WHITE)
        {
            blackPieces--;
        }
        else
        {
            whitePieces--;
        }
    }

    public static void capturePieces(CoOrd click)
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
    
    public static void  traverseCapture(int y,int x, int yDelta, int xDelta)
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
                board[y][x]= turnColour;
                incrementPieceCounter();
                decrementPieceCounter();
            }
            else
            {
                sameColourFound = true;
            }
        }

        
    }
}

