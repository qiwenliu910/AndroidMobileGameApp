package com.example.gameproject.game3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Game3MazeView extends View {

    // Create the special view type: MazeView, which show the activity_mazequiz_mazeview.

  private cell[][] cells;
    // cells construct the whole activity_mazequiz_mazeview, 2D-array cells.
  private int columns, rows;
  // columns and rows of the cell array.
  private Paint wallPaint,// Wall between two activity_mazequiz_mazeview cells
      playerPaint, // Paint Player as a square
      question1Paint,// Paint Quiz Box active Point
      question2Paint,
      money1Paint,// Paint Money Active Point
      money2Paint,
      money3Paint,
      money4Paint;
  // paint the active point which direct player to quiz box.
  private cell player, question1, question2, moneyPoint1, moneyPoint2, moneyPoint3, moneyPoint4;
  // the cell where is player, question and other active point.
  private Random randomNum;
    // a random number used to generate the activity_mazequiz_mazeview.

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public Game3MazeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    wallPaint = new Paint();
    wallPaint.setColor(Color.BLACK);
    wallPaint.setStrokeWidth(20);
      // Paint the wall of the activity_mazequiz_mazeview.

    playerPaint = new Paint();
    playerPaint.setColor(Color.BLUE);
    // Paint player, a blue square cell.

    question1Paint = new Paint();
    question1Paint.setColor(Color.GRAY);
    question2Paint = new Paint();
    question2Paint.setColor(Color.GRAY);
    // Paint Quiz box active point.

    money1Paint = new Paint();
    money1Paint.setColor(Color.LTGRAY);
    money2Paint = new Paint();
    money2Paint.setColor(Color.LTGRAY);
    // Paint get money active point.

    money3Paint = new Paint();
    money3Paint.setColor(Color.LTGRAY);
    money4Paint = new Paint();
    money4Paint.setColor(Color.LTGRAY);
    // Paint lose money active point.

    randomNum = new Random();
      // A random generate number used to create the random activity_mazequiz_mazeview.
  }

  public void createMaze(int cols, int rows) {
    setRows(cols);
    setColumns(rows);

      // This algorithm of random generate activity_mazequiz_mazeview in createMaze function is inspired by youtube video.
    Stack<cell> stackCell = new Stack<>();

    cells = new cell[columns][rows];
      // Fill the activity_mazequiz_mazeview with cells.

    for (int x = 0; x < columns; x++) {
      for (int y = 0; y < rows; y++) {
        cells[x][y] = new cell(x, y);
          // Now the activity_mazequiz_mazeview is filled tieh columns * rows cells.
      }
    }

    player = cells[0][0];
    // First set player cell to [0][0] cell.

    question1 = cells[randomCol()][randomRow()];
    // Random generate row number and column number use help function
    // for question1 active point's cell.
    // it can't be at same cell with players.

    question2 = cells[columns - 1][rows - 1];
    // Set question2 active point's cell to the right down corner.

    moneyPoint1 = cells[randomCol()][randomRow()];
    moneyPoint2 = cells[randomCol()][randomRow()];
    moneyPoint3 = cells[randomCol()][randomRow()];
    moneyPoint4 = cells[randomCol()][randomRow()];
    // Random generate row number and column number use help function
    // for other active point's cell.

      // Now start the activity_mazequiz_mazeview generate algorithm!!!
    cell current;
    current = cells[0][0];
    current.visited = true;
    // Set current cell.

    do {
      cell next = getNeighbour(current);
      // Randomly chose a unvisited neighbor of current cell as next cell.

      if (next != null) {
        removeWall(current, next);
        // Randomly remove a wall between current cell and next cell.

        stackCell.push(current);
        current = next;
        current.visited = true;

      } else {
        current = stackCell.pop();
        // If all neighbors of current is visited, remove current from stack.
      }
    } while (!stackCell.empty());
    // Loop until stack is empty.
    // At this time, all cells has a way to connect with one of its neighbors.
  }

  int randomCol() {
    return randomNum.nextInt(columns - 1) + 1;
  } // helper function

  int randomRow() {
    return randomNum.nextInt(rows - 1) + 1;
  } // helper function

  public String checkQuestion() {

    // Check whether player is at Quiz box active point.

    if (player == question1) {
      return "q1";
    } else if (player == question2) {
      return "q2";
    } else {
      return "not_yet";
    }
  }

  public String checkMoney() {

    // Check whether player is at Quiz box active point.

    if (player == moneyPoint1 && moneyPoint1.enable) {
      moneyPoint1.enable = false;
      return "+1";
    }
    if (player == moneyPoint2 && moneyPoint2.enable) {
      moneyPoint2.enable = false;
      return "+2";
    }
    if (player == moneyPoint3 && moneyPoint3.enable) {
      moneyPoint3.enable = false;
      return "-1";
    }
    if (player == moneyPoint4 && moneyPoint4.enable) {
      moneyPoint4.enable = false;
      return "-2";
    }
    return "not_yet";
  }

  public void move(String direction) {

    // Move player cells.

    if ("Up".equals(direction)) {
      if (!player.topWall) player = cells[player.columnAt][player.rowAt - 1];
      // Move Player cell to upper cell.
    }
    if ("Down".equals(direction)) {
      if (!player.bottomWall) player = cells[player.columnAt][player.rowAt + 1];
    }
    if ("Left".equals(direction)) {
      if (!player.leftWall) player = cells[player.columnAt - 1][player.rowAt];
      // Move player cell to left hand cell.
    }
    if ("Right".equals(direction)) {
      if (!player.rightWall) player = cells[player.columnAt + 1][player.rowAt];
    }
    invalidate();
  }

  private void removeWall(cell current, cell next) {

    // Remove wall between current cell and next cell.

    if (current.columnAt == next.columnAt) {
      if (current.rowAt == next.rowAt + 1) {
        current.topWall = false;
        next.bottomWall = false;
          // If next cell is at upwards of the activity_mazequiz_mazeview
      } else if (current.rowAt == next.rowAt - 1) {
        current.bottomWall = false;
        next.topWall = false;
          // If next cell is at downwards of activity_mazequiz_mazeview
      }
    } else if (current.rowAt == next.rowAt) {
      if (current.columnAt == next.columnAt + 1) {
        current.leftWall = false;
        next.rightWall = false;
        // If next cell is at left hand side of current cell
      } else if (current.columnAt == next.columnAt - 1) {
        current.rightWall = false;
        next.leftWall = false;
        // If next cell is at right hand side of current cell
      }
    }
  }

  private cell getNeighbour(cell cellSelf) {

    // Return a random, unvisited neighbor of current cell.
    ArrayList<cell> neighbours = new ArrayList<>();

    if (cellSelf.columnAt > 0)
      if (!cells[cellSelf.columnAt - 1][cellSelf.rowAt].visited) {
        neighbours.add(cells[cellSelf.columnAt - 1][cellSelf.rowAt]);
        // If the left cell of current cell is not visited, add it to neighbors.
      }

    if (cellSelf.columnAt < columns - 1)
      if (!cells[cellSelf.columnAt + 1][cellSelf.rowAt].visited) {
        neighbours.add(cells[cellSelf.columnAt + 1][cellSelf.rowAt]);
        // If the right cell of current cell is not visited, add it to neighbors.
      }

    if (cellSelf.rowAt > 0)
      if (!cells[cellSelf.columnAt][cellSelf.rowAt - 1].visited) {
        neighbours.add(cells[cellSelf.columnAt][cellSelf.rowAt - 1]);
        // If the upper cell of current cell is not visited, add it to neighbors.
      }

    if (cellSelf.rowAt < rows - 1)
      if (!cells[cellSelf.columnAt][cellSelf.rowAt + 1].visited) {
        neighbours.add(cells[cellSelf.columnAt][cellSelf.rowAt + 1]);
        // If the lower cell of current cell is not visited, add it to neighbors.
      }

    if (neighbours.size() > 0) {
      int randomIndex = randomNum.nextInt(neighbours.size());
      return neighbours.get(randomIndex);
      // Randomly choose one from neighbors.
    }
    return null;
  }

  @Override
  protected void onDraw(Canvas canvas) {

    // Draw all cells and walls.

    super.onDraw(canvas);
    canvas.drawColor(Color.WHITE);

    int width = getWidth();
    int height = getHeight();

    float cellSize = Math.min(width / columns, height / rows);

    // Draw the wall between two cells.
    for (int x = 0; x < columns; x++) {
      for (int y = 0; y < rows; y++) {
        if (cells[x][y].topWall) {
        	// Draw TopWall Line
          canvas.drawLine(
          		x * cellSize, y * cellSize, (x + 1) * cellSize, y * cellSize, wallPaint);
        }
        if (cells[x][y].leftWall) {
        	// Draw LeftWall Line
          canvas.drawLine(
          		x * cellSize, y * cellSize, x * cellSize, (y + 1) * cellSize, wallPaint);
        }
        if (cells[x][y].rightWall) {
        	// Draw RightWall Line
          canvas.drawLine(
          		(x + 1) * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
        }
        if (cells[x][y].bottomWall) {
        	// Draw ButtomWall Line
          canvas.drawLine(
          		x * cellSize, (y + 1) * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
        }
      }
    }

    float margin = cellSize / 10;
    // Margin space for drawing

	  //Draw player and active point as square.
    drawRect(canvas, player, cellSize, margin, playerPaint);
    drawRect(canvas, question1, cellSize, margin, question1Paint);
    drawRect(canvas, question2, cellSize, margin, question2Paint);
    drawRect(canvas, moneyPoint1, cellSize, margin, money1Paint);
    drawRect(canvas, moneyPoint2, cellSize, margin, money2Paint);
    drawRect(canvas, moneyPoint3, cellSize, margin, money3Paint);
    drawRect(canvas, moneyPoint4, cellSize, margin, money4Paint);
  }

  private void drawRect(Canvas canvas, cell cells, float cellSize, float margin, Paint paint) {
  	// Helper for drawing a square
    canvas.drawRect(
        cells.columnAt * cellSize + margin,
        cells.rowAt * cellSize + margin,
        (cells.columnAt + 1) * cellSize - margin,
        (cells.rowAt + 1) * cellSize - margin,
        paint);
  }

  class cell {
	  /** Maze is filled with cells.
	   * At first, all cells are closed with four wall
	   * Using the create Maze algorithm, we randomly removed walls between cells.
	   * The result is, make each cells has two access to its neighbor cells.
       * Which prevent from create a dead activity_mazequiz_mazeview.
	   */
    boolean topWall = true;
    boolean leftWall = true;
    boolean rightWall = true;
    boolean bottomWall = true;

    boolean visited = false;
    boolean enable;

    int rowAt, columnAt;

    cell(int column, int row) {
      // Initialize a unvisited cell with four wall around it.
      this.rowAt = row;
      this.columnAt = column;
      this.enable = true;
    }
  }
}
