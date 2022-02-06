package Logic;

import java.util.*;

//Start of Mines Class
public class Mines
{
    final String EMPTY = "EMPTY";
    final String MINE = "MINE";
    final String NEAR_MINE = "NEAR_MINE";
    //Mines Private fields.
    private int height;
    private int width;
    private int numMines;
    private Cell[][] cellBoard;//0-Empty//1 - has mine//2 - Flag//3-Open
    private boolean showAll = false;
    private int flagCounter;

    //===============================================================================
    //Start of Cell Class
    public class Cell
    {
        //Cell private fields
        private boolean showOptionalCells;
        private String status;
        private boolean open = false;
        private CellLocation location;
        private int countMinesNearCell=0;
        private boolean hasFlag=false;
        //---------------------------------------------------------------------------
        //Cell Constructor
        public Cell(String status, int i,int j)
        {
            this.status = status;
            open = false;
            location = new CellLocation(i,j);
        }
        //---------------------------------------------------------------------------
        //Return if the cell is open.
        public boolean isOpen()
        {
            return open;
        }
        //---------------------------------------------------------------------------
        //Return the Status of given Cell
        public String getStatus()
        {
            return status;
        }
        //---------------------------------------------------------------------------
        //Return the Location of the cell
        public CellLocation getLocation()
        {
            return location;
        }
        //---------------------------------------------------------------------------
        //Return the status of the cell
        public void setStatus(String status)
        {
            this.status = status;
        }
        //---------------------------------------------------------------------------
        //Open cell
        public void setOpen()
        {
            this.open = true;
        }
        //---------------------------------------------------------------------------
        //Set the location of the cell
        public void setLocation(CellLocation location)
        {
            this.location = location;
        }
        //---------------------------------------------------------------------------
        //Add new mine near to the cell.
        public void addMineNearCell()
        {
            countMinesNearCell++;
        }
        //---------------------------------------------------------------------------
        //Return How many mines is near the cell
        public int getCountMinesNearCell()
        {
            return countMinesNearCell;
        }
        //---------------------------------------------------------------------------
        //Return If the current cell is marked with Flag
        public boolean isHasFlag()
        {
            return hasFlag;
        }
        //---------------------------------------------------------------------------
        //Put or remove flag from giving cell
        public void setHasFlag(boolean hasFlag)
        {
            this.hasFlag = hasFlag;
        }
        //---------------------------------------------------------------------------
        public void setShowOptionalCells(boolean showOptionalCells)
        {
            this.showOptionalCells = showOptionalCells;
        }
        //---------------------------------------------------------------------------
        public boolean isShowOptionalCells()
        {
            return showOptionalCells;
        }
        //---------------------------------------------------------------------------

    }
//---------------------------------------------------------------------------


    //Start of CellLocation Class
    //===============================================================================
    public class CellLocation
    {
        //Cell private fields
        private int i,j;
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Constructor
        public CellLocation(int i,int j)
        {
            this.i=i;
            this.j = j;
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Return the X of the Cell
        public int getX()
        {
            return i;
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Return the Y of the Cell
        public int getY()
        {
            return j;
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Return the neighbors of given Cell.
        public List<CellLocation> getNeighbors()
        {
            List<CellLocation>neighbors = new ArrayList<>();
            for(int x=i-1;x<=i+1;x++)
                for(int y=j-1;y<=j+1;y++)
                    if(x!=i||y!=j)
                        if(inBorders(x,y))
                            neighbors.add(cellBoard[x][y].getLocation());
            return neighbors;
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        private boolean inBorders(int i,int j)
        {
            return (i>=0&&i<height)&&(j>=0&&j<width);
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }//END OF CellLocation
    //====================================================================


    //Constructor
    public Mines(int height, int width, int numMines)
    {
        this.height = height;
        this.width = width;
        this.numMines = numMines;
        cellBoard = new Cell[height][width];
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
                cellBoard[i][j] = new Cell(EMPTY,i,j);
        initializeBoard(numMines);
        flagCounter = numMines;
    }
    //====================================================================
    //Initialize the Board for the Mine Game
    private void initializeBoard(int numMines)
    {
        Random rnd = new Random();
        for(int i=0;i<numMines;i++)
        {
            int row = rnd.nextInt(height);
            int column = rnd.nextInt(width);
            //Generate New Location if current cell has mine
            while(cellBoard[row][column].getStatus().equals(MINE))
            {
                row = rnd.nextInt(height);
                column = rnd.nextInt(width);
            }
            cellBoard[row][column].setStatus(MINE);
            updateNeighbors(row,column);
        }
    }

    //====================================================================
    //Update the Neighbors of mine
    private void updateNeighbors(int row,int column)
    {
        List<CellLocation> neighborsOfMine = cellBoard[row][column].location.getNeighbors();
        for(CellLocation cl:neighborsOfMine)
        {
            int x = cl.getX();
            int y = cl.getY();
            if(!cellBoard[x][y].getStatus().equals(MINE))
            {
                cellBoard[x][y].addMineNearCell();
                cellBoard[x][y].setStatus(NEAR_MINE);
            }
        }
    }
    //====================================================================
    //Add new mine in specific cell
    public boolean addMine(int i,int j)
    {
        if(!cellBoard[i][j].getStatus().equals(MINE))
        {
            cellBoard[i][j].setStatus(MINE);
            updateNeighbors(i,j);
            return true;
        }
        return false;
    }
    //====================================================================
    private boolean openRecursive(Set<CellLocation> checked,int i, int j)
    {
        checked.add(cellBoard[i][j].getLocation());
        //If the cell is already pressed
        if(cellBoard[i][j].isOpen())
            return true;
            //Else - the cell is not pressed
        else
        {
            //If the cell is already marked with Flag and has no mine
            if(cellBoard[i][j].isHasFlag()&&!cellBoard[i][j].getStatus().equals("MINE"))
            {
                toggleFlag(i,j);
                cellBoard[i][j].setOpen();//The cell is now open.
                return true;
            }
            else
            {
                //If the Cell is near mines open him and stop.
                if(cellBoard[i][j].getStatus().equals(NEAR_MINE))
                {
                    cellBoard[i][j].setOpen();//The cell is now open.
                    return true;
                }
                //If the cell is with status empty
                else
                {
                    cellBoard[i][j].setOpen();//The cell is now open.
                    List<CellLocation>neighbors = cellBoard[i][j].getLocation().getNeighbors();
                    for(CellLocation cl :neighbors)
                    {
                        //If the cell wasn't checked
                        if(!checked.contains(cl))
                        {
                            int x = cl.getX();
                            int y = cl.getY();
                            openRecursive(checked, x, y);
                        }
                    }
                }
            }
        }
        return true;
    }
    //====================================================================
    public boolean open(int i,int j)
    {
        if(cellBoard[i][j].getStatus().equals(MINE))
        {
            return false;
        }
        if(cellBoard[i][j].isHasFlag())
        {
            return true;
        }
        if(cellBoard[i][j].isOpen())
            return true;
        Set<CellLocation> checked = new HashSet<>();
        return openRecursive(checked,i,j);
    }
    //====================================================================
    public void toggleFlag(int x, int y)
    {
        if(!cellBoard[x][y].isOpen())
        {
            cellBoard[x][y].setHasFlag(!cellBoard[x][y].isHasFlag());
            //Land flag sub one from the counter
            if(cellBoard[x][y].isHasFlag())
                flagCounter--;
                //Pick up flag add one to the counter
            else
                flagCounter++;
        }
    }
    //====================================================================
    public boolean isDone()
    {
        for (int i = 0; i < height; i++)
            for (int j = 0; j <width ; j++)
                if(!cellBoard[i][j].getStatus().equals(MINE))
                    if(!cellBoard[i][j].isOpen())
                        return false;
        if(allMines())
            return false;
        return true;
    }
    //====================================================================
    private boolean allMines()
    {
        for (int i = 0; i < height; i++)
            for (int j = 0; j <width ; j++)
                if(!cellBoard[i][j].getStatus().equals(MINE))
                    return false;
        return true;

    }
    //====================================================================
    public String get(int i,int j)
    {
        if(cellBoard[i][j].isOpen())
        {
            //if the cell is mine
            if(cellBoard[i][j].getStatus().equals(MINE))
                return "X";
            //if the cell is empty
            if(cellBoard[i][j].getCountMinesNearCell()==0)
                return " ";
            return Integer.toString(cellBoard[i][j].getCountMinesNearCell());
        }
        //if the cell is closed and marked with flag
        if(cellBoard[i][j].isHasFlag())
            return "F";
        if(showAll)
        {
            if (cellBoard[i][j].getStatus().equals(MINE))
                return "X";
        }
        return ".";

    }
    //====================================================================
    //If showAll is true, all cells r open, else only the cells that already opened.
    public void setShowAll(boolean showAll)
    {
        this.showAll = showAll;
    }
    //====================================================================
    //Represent Mine Fields as string
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <getHeight() ; i++)
        {
            for (int j = 0; j <getWidth() ; j++)
            {
                sb.append(get(i,j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    //====================================================================
    //Return the width of the board
    public int getWidth()
    {
        return width;
    }
    //====================================================================
    //Return the height of the board
    public int getHeight()
    {
        return height;
    }
    //====================================================================
    //Return list of closed neighbors Cells
    public List <CellLocation> showClosedNeighborCells(int i, int j)
    {
        List <CellLocation> neighbors = cellBoard[i][j].getLocation().getNeighbors();
        cellBoard[i][j].setShowOptionalCells(true);
        List<CellLocation> optionalNeighborToShow = new ArrayList<>();
        for(CellLocation cellLocation:neighbors)
        {
            int x = cellLocation.getX();
            int y = cellLocation.getY();
            //Add All cells that arent opened or flagged.
            if(!cellBoard[x][y].isOpen())
                optionalNeighborToShow.add(cellLocation);
        }
        return optionalNeighborToShow;
    }
    //====================================================================
    //Open all optional neighbor cells beside the ones with flags. If flag is misplaced the game is over.
    public boolean openOptionalCells(int i,int j)
    {
        List <CellLocation> neighbors = showClosedNeighborCells(i,j);
        List<CellLocation> optionalCells = new ArrayList<>();
        boolean stepOnMine = false;
        for (CellLocation cellLocation : neighbors)
        {
            int x = cellLocation.getX();
            int y = cellLocation.getY();
            if(!cellBoard[x][y].isHasFlag())
            {
                if (open(x, y) == false)
                    stepOnMine = true;
            }
        }
        return !stepOnMine;
    }
    //====================================================================
    //Get the number of neighbors with flags
    public int getNeighborsWithFlags(List <CellLocation> neighbors)
    {
        int counter = 0;
        for(CellLocation cellLocation:neighbors)
        {
            int i = cellLocation.getX();
            int j = cellLocation.getY();
            if(cellBoard[i][j].isHasFlag())
                counter++;
        }
        return counter;
    }
    //====================================================================
    //Return Cell
    public Cell getCell(int i,int j)
    {
        return cellBoard[i][j];
    }
    //====================================================================
    public int getFlagCounter()
    {
        return flagCounter;
    }
    //====================================================================
    public void setFlagCounter(int flagCounter)
    {
        this.flagCounter = flagCounter;
    }
    //====================================================================
    public boolean isShowAll()
    {
        return showAll;
    }
}
