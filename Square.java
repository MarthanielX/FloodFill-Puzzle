// Represents a square on the board, has a value, a row, and a column
public class Square {
   int value;
   int row;
   int col;   
   
   public Square(int v, int a, int b){
      value = v;
      row = a;
      col = b;
   }
   
   public void setValue(int newValue){
      value = newValue;
   }
   
   public int getValue(){
      return value;
   }
   
   public int getRow(){
      return row;
   }
  
   public int getCol(){
      return col;
   }
   
   public String toString(){
      return (value + " at [ " + row + "][" + col + "]");
   }
   
}