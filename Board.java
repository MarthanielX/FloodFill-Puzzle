import java.util.*;
import java.io.*;

// Represents a size by size board of squares
public class Board {
   
   private Square[][] board;
   private int size;
   
   public Board (Square[][] b){
      board = b;
      size = board.length;
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
      } catch (FileNotFoundException e) {
         System.out.println("That file location was not found. The board is still empty. Please try again."); 
      }  
   }
   
   public Board(){
   }   
   
   // returns an ArrayList of all the Squares in the same block as the parameter
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
   }
   
   public void move (int row, int col, boolean increase){
      Square s = board[row][col];
      move(s, increase);
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
