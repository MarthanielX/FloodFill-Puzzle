import java.util.*;
import java.io.*;

/* Don't think there is a single correct move algorith: has to be a weighting of various factors
Maybe will try to use evolutionary algorithm to set the weights but don't have a fitness function
* I believe the biggest/smallest value is always the/a correct move if it is the only one of its value
   Potential heuristics:
   * 'progress' made with respect to neighbors, the global mean, or 
         the entire board (weighted with closer squares more valuable?)
   * Want to increment/decrement groups that have more neighbors
   * Number of squares added to the group/number of groups joined
   * Valuable to eliminate all instances of a value?
*/

// Represents a size by size board of squares
public class Board {
   
   private Square[][] board;
   private int size;
   
   private ArrayList<ArrayList<Square>> blockList;
   //evens are decrement, odds are increment; scores[i] refers to blockList[i/2]
   private Double[] blockScores;
   
   public Board (Square[][] b){
      board = b;
      size = board.length;
      setBlockList();
   }
   
   // constructs board from file
   public Board (String file){
      try {
      // finds board size, initializes board, and reads in ints  
         Scanner input = new Scanner(new File(file));      
         while (input.hasNextLine()){
            input.nextLine();
            size++;
         }
         board = new Square[size][size];
         Scanner input2 = new Scanner(new File(file));
         for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
               board[i][j] = new Square(input2.nextInt(), i, j);
            }
         } 
         setBlockList(); 
      } 
      catch (FileNotFoundException e) {
         System.out.println("That file location was not found. The board is still empty. Please try again."); 
      }  
   }
   
   // increments or decrements all squares in the block containing param s
   public void move (Square s, boolean increase){
      ArrayList<Square> list = getBlock(s);
      if (increase){
         for (Square cur : list)
            cur.setValue(cur.getValue()+1);
      } 
      else {
         for (Square cur : list)
            cur.setValue(cur.getValue()-1);         
      }
      setBlockList();
      blockScores = new Double[2*blockList.size()];
      setBlockScores();
   }
   
   public void move (int row, int col, boolean increase){
      Square s = board[row][col];
      move(s, increase);
   }
   
   // does the best move available on the board according to algorithm
   public void aIMove(){
      int location = 0;
      double max = 0;
      for (int i = 0; i < blockScores.length; i++){
         if (blockScores[i] > max){ //abs value?
            max = blockScores[i];
            location = i;
         }
      }
      move (blockList.get(location/2).get(0), location%2 == 1);
   } 
   
   private void setBlockScores(){
      for (int i = 0; i < blockScores.length; i++){
         blockScores[i] = 0.0;
      // put each way of scoring each move and multipliers, blockScore[i] += ...
      // have to read in weightings from a file if going to do ML
      }
   }   
   
   // sets the blockList to reflect the position of the board
   private void setBlockList(){
      ArrayList<Square> allSquares = new ArrayList<>();
      for (Square[] arr : board)
         for (Square s : arr)
            allSquares.add(s);
      blockList = new ArrayList<>();
      while (!allSquares.isEmpty()){
         ArrayList<Square> cur = getBlock(allSquares.get(0));
         blockList.add(cur);
         for (Square s : cur){
            allSquares.remove(s);
         }
      }
   }  
      
   // returns an ArrayList of all the Squares in the same block as the parameter
   // wrote this before the getNeighbors, should probably rewrite but meh
   public ArrayList<Square> getBlock (Square s){
      ArrayList<Square> list = new ArrayList<>();
      int location = 0;
      list.add(s);
      // for each element in list, check all 4 surrounding squares
      // if within bounds, not yet added, and value == param, add to list
      while (location < list.size()){
         Square cur = list.get(location);
         location++;
         if (cur.getRow()+1 < size && !list.contains(board[cur.getRow()+1][cur.getCol()]) && s.getValue() == board[cur.getRow()+1][cur.getCol()].getValue())
            list.add(board[cur.getRow()+1][cur.getCol()]);
         if (cur.getRow()-1 >= 0 && !list.contains(board[cur.getRow()-1][cur.getCol()]) && s.getValue() == board[cur.getRow()-1][cur.getCol()].getValue())
            list.add(board[cur.getRow()-1][cur.getCol()]);
         if (cur.getCol()+1 < size && !list.contains(board[cur.getRow()][cur.getCol()+1]) && s.getValue() == board[cur.getRow()][cur.getCol()+1].getValue())
            list.add(board[cur.getRow()][cur.getCol()+1]);
         if (cur.getCol()-1 >= 0 && !list.contains(board[cur.getRow()][cur.getCol()-1]) && s.getValue() == board[cur.getRow()][cur.getCol()-1].getValue())
            list.add(board[cur.getRow()][cur.getCol()-1]);
      }
      return list;
   }
   
   // returns list (no duplicates) of all neighbors of @param block
   private ArrayList<Square> getNeighbors(ArrayList<Square> block){
      ArrayList<Square> neighbors = new ArrayList<>();
      for(Square blockPiece : block)
         for(Square s : getNeighbors(blockPiece))
            if (!neighbors.contains(s))
               neighbors.add(s);
      return neighbors;
   }
      
   // returns an ArrayList of the neighbors of param Square
   private ArrayList<Square> getNeighbors (Square s){
      ArrayList<Square> list = new ArrayList<>();
      if (s.getRow()+1 < size)
         list.add(board[s.getRow()+1][s.getCol()]);
      if (s.getRow()-1 >= 0)
         list.add(board[s.getRow()-1][s.getCol()]);
      if (s.getCol()+1 < size)
         list.add(board[s.getRow()][s.getCol()+1]);
      if (s.getCol()-1 >= 0)
         list.add(board[s.getRow()][s.getCol()-1]);
      return list;
   }
   
   // returns the int 'net value' of a square, reflecting the progress made by increasing it
   // a positive netValue means increasing the square would be good,
   // negative means decreasing it would be good, and 0 means it make net 0 progress
   public int getNetValue(Square s){
      ArrayList<Square> neighbors = getNeighbors(s);
      int netValue = 0;
      for (Square neighbor : neighbors){
         if (neighbor.getValue() > s.getValue())
            netValue++;
         else if (neighbor.getValue() < s.getValue())
            netValue--;
      }
      return netValue;
   }
   
   // returns true iff the board contains only 1 number
   public boolean won(){
      int first = board[0][0].getValue();
      for(Square[] arr : board)
         for(Square i : arr)
            if (i.getValue() != first)
               return false;
      return true;
   }
   
   public int getSize(){
      return size;
   }
   
   // returns String representation of the board
   public String toString(){
      String s = "";
      s += "   ";
      for (int i = 0; i < size; i++){
         s += i + " ";
      }
      for(int i = 0; i < size; i++){
         s += "\n" + i + ": " ; 
         for(Square cur : board[i]){
            s += cur.getValue() + " ";
         }       
      }
      return s;
   }
}
