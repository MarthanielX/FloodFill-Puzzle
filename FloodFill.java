import java.io.*;
import java.util.*;

public class FloodFill{

   static final String FILE = "Input2.txt";
   static Square[][] board;
   static int size;

   public static void main (String[] args) throws FileNotFoundException{
         
      // finds board size, initializes board, and reads in ints  
      Scanner input = new Scanner(new File(FILE));      
      while (input.hasNextLine()){
         input.nextLine();
         size++;
      }
      board = new Square[size][size];
      Scanner input2 = new Scanner(new File(FILE));
      for (int i = 0; i < size; i++){
         for (int j = 0; j < size; j++){
            board[i][j] = new Square(input2.nextInt(), i, j);
         }
      }    
      
      int moveCount = 0;
   
      System.out.println("Welcome to the Flood Fill Game! The goal of the game is to make all the squares on");
      System.out.println("the board the same number in the same number in the smallest possible number of moves.");
      System.out.println("A move consistents of taking a block, consisting of square and all squares of the same");
      System.out.println("value that touch it vertically and horizontally, and incrementing or decrementing them.\n");
   
      while (!won()){
         moveCount++;
         System.out.println(getString());  
         Scanner scan = new Scanner (System.in);
         System.out.println();         
         int col = -1;
         while (col < 0 || col > size){
            System.out.print("Enter the vertical column of your square (0 to " + size + "): ");
            col = scan.nextInt();
         }
         int row = -1;
         while (row < 0 || row > size){
            System.out.print("Enter the horizonal row of your square (0 to " + size + "): ");    
            row = scan.nextInt();
         }
         boolean increment;
         System.out.print("Enter 1 to increment or 2 to decrement: ");
         increment = (scan.nextInt() == 1);
         
         move(board[row][col], increment);
      }   
      
      System.out.println(getString());
      System.out.println("\nCongratulations! You won the game in " + moveCount + " moves.");
      
   }
   
   // returns an ArrayList of all the Squares in the same block as the parameter
   private static ArrayList<Square> getBlock (Square s){
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
   private static void move (Square s, boolean increase){
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
   
   // returns true iff the board contains only 1 number
   private static boolean won(){
      int first = board[0][0].getValue();
      for(Square[] arr : board)
         for(Square i : arr)
            if (i.getValue() != first)
               return false;
      return true;
   }
   
   // returns String representation of the board
   private static String getString(){
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