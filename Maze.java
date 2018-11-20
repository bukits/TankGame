package JHW;

import java.awt.*;

import java.awt.geom.*;
import java.util.LinkedList;

public class Maze {

    public final int w = 700; // width
    public final int h = 700; // height

    /*Origin Coordinates*/
    public int originX; 
    public int originY;

    /*Wall Width*/
    public int wallWidth = 10;

    public int randomNumber; // Random Number

    public LinkedList<Rectangle2D.Double> walls = new LinkedList<Rectangle2D.Double>();
    public LinkedList<Integer> wallType = new LinkedList<Integer>();

    private final int mazeWIDTH = 7; // Maze Width 700
    private final int mazeHEIGHT = 7;  // Maze Height 700

    private final int MAXSIZE = (getMazeWIDTH() > mazeHEIGHT) ? getMazeWIDTH() : mazeHEIGHT; // array size 700*700 pixel //DIMENSION

    private final int MAZEBINARYSIZE = 3*MAXSIZE+2;

    public int [][] mazeBinary = new int [MAZEBINARYSIZE][MAZEBINARYSIZE]; 

    private boolean[][] northMaze;     
    private boolean[][] eastMaze;      
    private boolean[][] southMaze;
    private boolean[][] westMaze;
    private boolean[][] visited;
    
    public Maze(int x, int y) throws ArrayIndexOutOfBoundsException {
        originX = x;
        originY = y;
 
        mazeInit(); 
        generateMaze();

        for (int i = 1; i<=mazeHEIGHT; i++) {
            for (int j = 1; j<=getMazeWIDTH(); j++)
            {

                //LEFT BOUND
                if (southMaze[i][j]) {
                    //  walls.add(new Rectangle2D.Double(originX + (j-1)*100-10, originY+(i-1)*100, wallWidth,110));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY+(i-1)*100, wallWidth,110));
                    mazeBinary[2*(j-1)][2*(i-1)+2] = 1;
                    wallType.add(1);
                } 

                //RIGHT BOUND
                if (northMaze[i][j]) { // 
                    // walls.add(new Rectangle2D.Double(originX + (j-1)*100-10, originY+(i-1)*100-wallWidth, wallWidth,110));
                    walls.add(new Rectangle2D.Double(originX + (j)*100, originY+(i-1)*100, wallWidth,110));
                    mazeBinary[2*(j-1)][2*(i-1)] = 1;
                    wallType.add(1);
                
                }
                if (eastMaze[i][j]) {
                    //  walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +(i-1)*100-10, 110,wallWidth));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +i*100, 110,wallWidth));
                    mazeBinary[2*(j-1)][2*(i-1)+1] = 1;
                    wallType.add(0);
                } 
                if (westMaze[i][j]) {
                    // walls.add(new Rectangle2D.Double(originX + j*100, originY +i*100-wallWidth-10, 110,wallWidth));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +(i-1)*100, 110,wallWidth));
                    mazeBinary[2*(j-1)+2][2*(i-1)+1] = 1;
                    wallType.add(0);
                }
            }
        }
    }

    public void generateMaze (int xCoord, int yCoord){
        visited[xCoord][yCoord] = true;

        while (!visited[xCoord][yCoord+1] || !visited[xCoord][yCoord-1] || !visited[xCoord+1][yCoord] || !visited[xCoord-1][yCoord])
        {
            while (true)
            {
                randomNumber = (int) (Math.random()*4+1);

                if (randomNumber == 1 && !visited[xCoord][yCoord+1])
                {
                    northMaze[xCoord][yCoord] = southMaze [xCoord][yCoord+1] = false;
                    generateMaze (xCoord, yCoord+1);
                    break;
                }

                else if (randomNumber== 2 && !visited[xCoord+1][yCoord])
                {
                    eastMaze[xCoord][yCoord] = westMaze [xCoord+1][yCoord] = false;
                    generateMaze (xCoord+1, yCoord);
                    break;
                }

                else if (randomNumber== 3 && !visited[xCoord][yCoord-1])
                {
                    southMaze[xCoord][yCoord] = northMaze [xCoord][yCoord-1] = false;
                    generateMaze (xCoord, yCoord-1);
                    break;
                }
                else if (randomNumber == 4 && !visited[xCoord-1][yCoord])
                {
                    westMaze[xCoord][yCoord] = eastMaze [xCoord-1][yCoord] = false;
                    generateMaze (xCoord-1, yCoord);
                    break;
                }

            }
        }

    }

    public void generateMaze (){
        generateMaze (1,1);
    }

    public void mazeInit (){
        /* initialize*/
        visited = new boolean [MAXSIZE+2][MAXSIZE+2];

        /*BORDERLINE*/
        /**Outer walls**/
        for (int i = 0; i < getMazeWIDTH()+2; i++)
        {
            visited[i][0] = visited[i][getMazeWIDTH()+1] = true;
        }
        for (int j = 0; j<mazeHEIGHT+2; j++)
        {
            visited[0][j] = visited[mazeHEIGHT+1][j] = true;
        }

        /**Inside Walls**/
        northMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the north side of the all the points in the maze
        southMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the south side of the all the points in the maze
        eastMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the east side of the all the points in the maze
        westMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the west side of the all the points in the maze

        for (int i = 0; i<mazeHEIGHT+2; i++){
            for (int j = 0; j<getMazeWIDTH()+2; j++)
            {
                northMaze [i][j] = southMaze [i][j] = eastMaze [i][j] = westMaze [i][j] = true;
            }
        }
    }

    public void renderMaze(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor (new Color(76, 76, 76));

        for(int i = 0; i<walls.size();i++)
        {
            g2.fill(walls.get(i));
        }
    }

	public int getMazeWIDTH() {
		return mazeWIDTH;
	}
}
