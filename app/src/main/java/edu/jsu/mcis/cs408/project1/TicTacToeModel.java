package edu.jsu.mcis.cs408.project1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TicTacToeModel {

    public static final int DEFAULT_SIZE = 3;

    private Mark[][] grid;      /* the game grid */
    private boolean xTurn;      /* is TRUE if X is the current player */
    private int size;           /* the size (size and height) of the game grid */

    private TicTacToeController controller;

    protected PropertyChangeSupport propertyChangeSupport;

    public TicTacToeModel(TicTacToeController controller, int size) {

        this.size = size;
        this.controller = controller;
        propertyChangeSupport = new PropertyChangeSupport(this);

        resetModel(size);

    }

    public void resetModel(int size) {

        //
        // This method resets the Model to its default state.  It should (re)initialize the size of
        // the grid, (re)set X as the current player, and create a new grid array of Mark objects,
        // initially filled with empty marks.
        //

        this.size = size;
        this.xTurn = true;

        /* Create grid (size x size) as a 2D Mark array */

        //
        // INSERT YOUR CODE HERE
        //

    }

    public boolean setMark(TicTacToeSquare square) {

        //
        // This method accepts the target square as a TicTacToeSquare argument, and adds the
        // current player's mark to this square.  First, it should use "isValidSquare()" to check if
        // the specified square is within range, and then it should use "isSquareMarked()" to see if
        // this square is already occupied!  If the specified location is valid, make a mark for the
        // current player, then use "firePropertyChange()" to fire the corresponding property change
        // event, which will inform the Controller that a change of state has taken place which
        // requires a change to the View.  Finally, toggle "xTurn" (from TRUE to FALSE, or vice-
        // versa) to switch to the other player.  Return TRUE if the mark was successfully added to
        // the grid; otherwise, return FALSE.
        //

        int row = square.getRow();
        int col = square.getCol();

        Mark mark;
        if(xTurn)
            mark = Mark.X;
        else
            mark = Mark.O;

        if(isValidSquare(row, col))
            if(!isSquareMarked(row, col))
            {
                grid[row][col] = mark;
                if(getResult() == Result.NONE)
                {
                    xTurn = !xTurn;
                    return true;
                }
            }

        return false;

    }

    private boolean isValidSquare(int row, int col) {

        if(row >= 0 && row < size)
            if(col >= 0 && col < size)
                return true;

        return false;

    }

    private boolean isSquareMarked(int row, int col) {

        if(grid[row][col] == Mark.X || grid[row][col] == Mark.O)
            return true;

        return false;

    }

    public Mark getMark(int row, int col) {

        // This method should return the Mark from the square at the specified location

        return grid[row][col];

    }

    public Result getResult() {

        Mark mark;
        Result result;

        if(isXTurn())
        {
            mark = Mark.X;
            result = Result.X;
        }
        else
        {
            mark = Mark.O;
            result = Result.O;
        }

        if(isMarkWin(mark))
            return result;
        else if(isTie())
            return Result.TIE;
        else
            return Result.NONE;

    }

    private boolean isMarkWin(Mark mark) {

        int count = 0;

        // check each row
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(getMark(i, j) == mark)
                    count++;
            }
            if(count == size)
                return true;
            else
                count = 0;
        }

        //check each col
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(getMark(j, i) == mark)
                    count++;
            }
            if(count == size)
                return true;
            else
                count = 0;
        }

        //check diagnol
        for(int i = 0; i < size; i++)
            if(getMark(i, i) == mark)
                count++;
        if(count == size)
            return true;
        else
            count = 0;


        //check reverse diagnol
        for(int i = size - 1, j = 0; i >= 0; i--, j++)
            if(getMark(i, j) == mark)
                count++;
        if(count == size)
            return true;
        else
            count = 0;



        return false;


    }

    private boolean isTie() {

        /* Check the squares of the grid to see if the game is a tie */

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(grid[i][j] == Mark.EMPTY)
                    return false;

        return true;

    }

    public boolean isXTurn() {

        // Getter for "xTurn"
        return xTurn;

    }

    public int getSize() {

        // Getter for "size"
        return size;

    }

    // Property Change Methods (adds/removes a PropertyChangeListener, or fires a property change)

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /* ENUM TYPE DEFINITIONS */

    // Mark (represents X, O, or an empty square)

    public enum Mark {

        X("X"),
        O("O"),
        EMPTY("-");

        private String message;

        private Mark(String msg) {
            message = msg;
        }

        @Override
        public String toString() {
            return message;
        }

    };

    // Result (represents the game state: X wins, O wins, a TIE, or NONE if the game is not over)

    public enum Result {

        X("X"),
        O("O"),
        TIE("TIE"),
        NONE("NONE");

        private String message;

        private Result(String msg) {
            message = msg;
        }

        @Override
        public String toString() {
            return message;
        }

    };

}