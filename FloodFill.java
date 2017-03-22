import java.io.*;
import java.util.*;

public class FloodFill{

   static final String FILE = "Input2.txt";

   public static void main (String[] args) {
      
      // while board isn't empty or something?
      Board board = new Board(FILE);
      int moveCount = 0;
   
      System.out.println("Welcome to the Flood Fill Game! The goal of the game is to make all the squares on");
      System.out.println("the board the same number in the same number in the smallest possible number of moves.");
      System.out.println("A move consistents of taking a block, consisting of square and all squares of the same");
      System.out.println("value that touch it vertically and horizontally, and incrementing or decrementing them.\n");
   
      while (!board.won()){
         moveCount++;
         System.out.println(board);  
         Scanner scan = new Scanner (System.in);
         System.out.println();         
         int col = -1;
         while (col < 0 || col > board.getSize()){
            System.out.print("Enter the vertical column of your square (0 to " + board.getSize() + "): ");
            col = scan.nextInt();
         }
         int row = -1;
         while (row < 0 || row > board.getSize()){
            System.out.print("Enter the horizonal row of your square (0 to " + board.getSize() + "): ");    
            row = scan.nextInt();
         }
         boolean increment;
         System.out.print("Enter 1 to increment or 2 to decrement: ");
         increment = (scan.nextInt() == 1);
         
         board.move(row, col, increment);
      }   
      
      System.out.println(board.toString());
      System.out.println("\nCongratulations! You won the game in " + moveCount + " moves.");
      
   }
}