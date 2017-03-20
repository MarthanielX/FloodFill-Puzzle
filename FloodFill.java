import java.io.*;
import java.util.*;

public class FloodFill{

   static final String FILE = "input1.txt";
   static Integer[][] board;
   static int size;
   
   public static class Duple {
      int row;
      int col;
      public Duple(int a, int b){
         row = a;
         col = b;
      }
   } 

   public static void main (String args[]) throws FileNotFoundException{
         
      // finds board size, initializes board, and reads in ints  
      Scanner input = new Scanner(new File(FILE));      
      while (input.hasNextLine()){
         input.nextLine();
         size++;
      }
      board = new Integer[size][size];
      Scanner input2 = new Scanner(new File(FILE));
      for (int i = 0; i < size; i++){
         for (int j = 0; j < size; j++){
            board[i][j] = input2.nextInt();
         }
      }    
           
      System.out.println(getString());      
      move(0, 0, true);
      System.out.println(getString());
      move(0, 0, true);
      System.out.println(getString());     
   }
   
   // increments all parts of the block containing square i,j
   private static void move(int i, int j, boolean add){
      int start = board[i][j];
      Queue<Duple> queue = new ArrayDeque<>();
      queue.add(new Duple(i, j));
      while (!queue.isEmpty()){
         Duple square = queue.remove();
         if (square.row+1 < size)
            if (board[square.row+1][square.col] == board[square.row][square.col])
               queue.add(new Duple(square.row+1, square.col));
         if (square.row-1 > 0)
            if (board[square.row-1][square.col] == board[square.row][square.col])
               queue.add(new Duple(square.row-1, square.col));
         if (square.col+1 < size)
            if (board[square.row][square.col+1] == board[square.row][square.col])
               queue.add(new Duple(square.row, square.col+1));
         if (square.col-1 > 0)
            if (board[square.row][square.col-1] == board[square.row][square.col])
               queue.add(new Duple(square.row, square.col-1));
         if (start == board[square.row][square.col] && add)
            board[square.row][square.col]++;
         else 
            board[square.row][square.col]--;
      }
      if (add)
         start++;
      else 
         start--;
   }
   
   // returns true iff the board contains only 1 number
   private boolean won(){
      int first = board[0][0];
      for(Integer[] arr : board)
         for(Integer i : arr)
            if (i != first)
               return false;
      return true;
   }
   
   // returns String representation of the board
   private static String getString(){
      String s = "";
      for(Integer[] arr : board){
         for(Integer i : arr){
            s += i + " ";
         }
         s += "\n";           
      }
      return s;
   }
}