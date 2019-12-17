/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author user
 */
public class Dots2 {
   public double X;
   public double Y;
   public Dots2(double X,double Y){
      this.X=X;
      this.Y=Y;
   }
   public double getDotsX(){
      return X; 
   }
   public double getDotsY(){
      return Y; 
   } 
   public void setDotsX(double X){
      this.X=X;
   }
   public void setDotsY(double Y){
      this.Y=Y;
   }
}
